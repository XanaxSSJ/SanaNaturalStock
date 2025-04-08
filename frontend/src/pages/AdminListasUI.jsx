import { useEffect, useState } from "react";
import "./AdminListasUI.css";

export default function AdminListasUI() {
    const [listas, setListas] = useState([]);
    const token = localStorage.getItem('token'); // Obtener el token del almacenamiento local

    useEffect(() => {
        fetch("http://localhost:8080/api/listas", {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then((res) => {
                if (!res.ok) throw new Error('Error en la respuesta del servidor');
                return res.json();
            })
            .then((data) => setListas(data))
            .catch((err) => {
                console.error("Error cargando listas:", err);
                alert("Error al cargar listas: " + err.message);
            });
    }, [token]); // Dependencia del efecto

    const descargarExcel = async (id) => {
        try {
            const token = localStorage.getItem('token');
            const url = `http://localhost:8080/api/listas/${id}/exportar`;

            const response = await fetch(url, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                }
            });

            if (!response.ok) {
                throw new Error('Error al descargar el archivo');
            }

            // Obtener el nombre del archivo del header 'Content-Disposition'
            const contentDisposition = response.headers.get('Content-Disposition');
            const filename = contentDisposition
                ? contentDisposition.split('filename=')[1].replace(/"/g, '')
                : `lista_${id}.xlsx`;

            // Crear el blob y descargar
            const blob = await response.blob();
            const downloadUrl = window.URL.createObjectURL(blob);
            const link = document.createElement('a');

            link.href = downloadUrl;
            link.setAttribute('download', filename);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(downloadUrl);

        } catch (error) {
            console.error('Error al descargar:', error);
            alert('Error al descargar el archivo: ' + error.message);
        }
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