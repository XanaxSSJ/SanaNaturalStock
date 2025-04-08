import { useEffect, useState } from "react";
import {
    getProductosFiltrados,
    getTiendas,
    guardarListaDePedido
} from "../api";
import "./ProductosUI.css";

export default function ProductosUI() {
    const [productos, setProductos] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [proveedores, setProveedores] = useState([]);
    const [tiendas, setTiendas] = useState([]);
    const [filtroNombre, setFiltroNombre] = useState("");
    const [categoriaSeleccionada, setCategoriaSeleccionada] = useState("");
    const [proveedorSeleccionado, setProveedorSeleccionado] = useState("");
    const [tiendaSeleccionada, setTiendaSeleccionada] = useState("");
    const [cantidad, setCantidad] = useState(1);
    const [listaProductos, setListaProductos] = useState([]);

    useEffect(() => {
        getProductosFiltrados().then(data => {
            setProductos(data);
            const cats = [...new Set(data.map(p => p.categoria))];
            setCategorias(cats);
            const provs = [...new Set(data.map(p => p.proveedor))];
            setProveedores(provs);
        });

        getTiendas().then(data => setTiendas(data));
    }, []);

    const productosFiltrados = productos.filter(p => {
        const coincideNombre = p.nombre.toLowerCase().includes(filtroNombre.toLowerCase());
        const coincideCategoria = !categoriaSeleccionada || p.categoria === categoriaSeleccionada;
        const coincideProveedor = !proveedorSeleccionado || p.proveedor === proveedorSeleccionado;
        return coincideNombre && coincideCategoria && coincideProveedor;
    });

    const agregarProducto = (producto) => {
        const cantidadInt = parseInt(cantidad);
        if (!cantidadInt || cantidadInt < 1) return;

        const existente = listaProductos.find(p => p.id === producto.id);
        if (existente) {
            setListaProductos(prev =>
                prev.map(p =>
                    p.id === producto.id ? { ...p, cantidad: p.cantidad + cantidadInt } : p
                )
            );
        } else {
            setListaProductos(prev => [...prev, { ...producto, cantidad: cantidadInt }]);
        }
        setCantidad(1);
    };

    const eliminarProducto = (id) => {
        setListaProductos(prev => prev.filter(p => p.id !== id));
    };

    const guardarLista = async () => {
        if (!tiendaSeleccionada || listaProductos.length === 0) {
            alert("Selecciona una tienda y agrega productos antes de guardar.");
            return;
        }

        const payload = {
            tiendaId: parseInt(tiendaSeleccionada),
            detalles: listaProductos.map(p => ({
                productoId: p.id,
                cantidad: p.cantidad
            }))
        };

        try {
            const response = await guardarListaDePedido(payload);
            const contentType = response.headers.get('content-type');

            let message;
            if (contentType && contentType.includes('application/json')) {
                const data = await response.json();
                message = data.mensaje || "Lista guardada exitosamente";
            } else {
                message = await response.text();
            }

            if (response.ok) {
                alert(`✅ ${message}`);
                setListaProductos([]);
                setTiendaSeleccionada("");
            } else {
                alert(`❌ Error al guardar la lista: ${message}`);
            }
        } catch (err) {
            alert(`❌ Error de red: ${err.message}`);
        }
    };



    return (
        <div className="productos-ui-container">
            <div className="productos-busqueda">
                <input
                    type="text"
                    placeholder="Buscar producto"
                    value={filtroNombre}
                    onChange={(e) => setFiltroNombre(e.target.value)}
                    className="input-busqueda"
                />

                <div className="filtros">
                    <select
                        value={categoriaSeleccionada}
                        onChange={e => setCategoriaSeleccionada(e.target.value)}
                        className="select-filtro"
                    >
                        <option value="">Todas las categorías</option>
                        {categorias.map(cat => (
                            <option key={cat} value={cat}>{cat}</option>
                        ))}
                    </select>

                    <select
                        value={proveedorSeleccionado}
                        onChange={e => setProveedorSeleccionado(e.target.value)}
                        className="select-filtro"
                    >
                        <option value="">Todos los proveedores</option>
                        {proveedores.map(prov => (
                            <option key={prov} value={prov}>{prov}</option>
                        ))}
                    </select>

                    <input
                        type="number"
                        value={cantidad}
                        onChange={e => setCantidad(parseInt(e.target.value))}
                        className="input-cantidad"
                        min={1}
                    />
                </div>

                <div className="lista-productos">
                    {productosFiltrados.slice(0, 10).map(producto => (
                        <div key={producto.id} className="item-producto">
                            <span>{producto.nombre}</span>
                            <button onClick={() => agregarProducto(producto)}>Agregar</button>
                        </div>
                    ))}
                </div>
            </div>

            <div className="productos-agregados">
                <div className="lista-header">
                    <h2>Productos agregados</h2>

                    <div className="lista-controls">

                        <select
                            value={tiendaSeleccionada}
                            onChange={(e) => setTiendaSeleccionada(e.target.value)}
                            className="select-tienda"
                        >
                            <option value="">Seleccionar tienda</option>
                            {tiendas.map(tienda => (
                                <option key={tienda.id} value={tienda.id}>
                                    {tienda.nombre}
                                </option>
                            ))}
                        </select>

                        <button
                            onClick={guardarLista}
                            className="guardar-lista-btn"
                        >
                            Guardar Lista
                        </button>
                    </div>
                </div>

                <table>
                    <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Categoría</th>
                        <th>Proveedor</th>
                        <th>Cantidad</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {listaProductos.map(p => (
                        <tr key={p.id}>
                            <td>{p.nombre}</td>
                            <td>{p.categoria}</td>
                            <td>{p.proveedor}</td>
                            <td>{p.cantidad}</td>
                            <td>
                                <button onClick={() => eliminarProducto(p.id)}>Eliminar</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}