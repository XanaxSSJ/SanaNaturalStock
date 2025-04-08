import { useEffect, useState } from "react";
import "./AdminListasUI.css"; // Archivo CSS separado para mantener el estilo

export default function AdminListasUI() {
    const [listas, setListas] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/listas")
            .then((res) => res.json())
            .then((data) => setListas(data))
            .catch((err) => console.error("Error cargando listas:", err));
    }, []);

    const descargarExcel = (id) => {
        window.open(`http://localhost:8080/api/listas/${id}/exportar`, "_blank");
    };

    const formatearFecha = (fechaIso) => {
        const fecha = new Date(fechaIso);
        return fecha.toLocaleString("es-AR", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit",
        });
    };

    return (
        <div className="admin-listas-container">
            <h1 className="admin-listas-title">Listas de pedidos</h1>

            {listas.length === 0 ? (
                <p className="no-listas-message">No hay listas disponibles.</p>
            ) : (
                <table className="listas-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fecha</th>
                        <th>Tienda</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    {listas.map((lista) => (
                        <tr key={lista.id}>
                            <td>{lista.id}</td>
                            <td>{formatearFecha(lista.fechaCreacion)}</td>
                            <td>{lista.tienda}</td>
                            <td>
                                <button
                                    className="download-btn"
                                    onClick={() => descargarExcel(lista.id)}
                                >
                                    Descargar Excel
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}