const API_BASE = "http://localhost:8080";

// Función principal para hacer peticiones con token
export async function fetchConToken(endpoint, options = {}) {
    const token = localStorage.getItem("token");

    // Configuración básica de headers
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers,
        'Authorization': `Bearer ${token}`
    };

    try {
        const response = await fetch(`${API_BASE}${endpoint}`, {
            ...options,
            headers,
            credentials: 'include' // Necesario para cookies de autenticación
        });

        // Si es respuesta 403 (Forbidden/No autorizado)
        if (response.status === 403) {
            // Opcional: Intentar refrescar el token aquí si tienes ese mecanismo
            localStorage.removeItem("token"); // Limpiar token inválido
            window.location.href = '/login'; // Redirigir a login
            throw new Error('Sesión expirada o no autorizado');
        }

        // Si hay otro error HTTP
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            throw new Error(errorData.message || `Error HTTP: ${response.status}`);
        }

        // Para respuestas sin contenido (como en DELETE)
        if (response.status === 204) {
            return null;
        }

        return await response.json();
    } catch (error) {
        console.error(`Error en petición a ${endpoint}:`, error);
        throw error; // Re-lanzar el error para manejo en componentes
    }
}

// Funciones específicas mejoradas

export async function getProductosFiltrados(filtros = {}) {
    const queryParams = new URLSearchParams(filtros).toString();
    return fetchConToken(`/api/productos/filtro?${queryParams}`);
}

export async function getTiendas() {
    return fetchConToken("/api/tiendas");
}

export async function guardarListaDePedido(payload) {
    return fetchConToken("/api/listas", {
        method: "POST",
        body: JSON.stringify(payload)
    });
}

// Función adicional para refrescar token si es necesario
export async function refrescarToken() {
    const refreshToken = localStorage.getItem("refreshToken");
    if (!refreshToken) throw new Error('No hay refresh token disponible');

    const response = await fetch(`${API_BASE}/api/auth/refresh`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ refreshToken })
    });

    if (!response.ok) {
        throw new Error('Error al refrescar token');
    }
    const data = await response.text();
    return data.token;
}