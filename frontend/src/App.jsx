import { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginForm from "./components/LoginForm";
import ProductosUI from "./pages/ProductosUI";
import AdminListasUI from "./pages/AdminListasUI";

function RutaPrivada({ children, rolRequerido }) {
    const token = localStorage.getItem("token");
    const userRole = localStorage.getItem("role");

    if (!token) return <Navigate to="/login" />;
    if (rolRequerido && userRole !== rolRequerido) return <Navigate to="/" />;

    return children;
}

export default function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsAuthenticated(!!token);
    }, []);

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginForm onLoginSuccess={() => setIsAuthenticated(true)} />} />
                <Route path="/" element={<RutaPrivada><ProductosUI /></RutaPrivada>} />
                <Route path="/admin/listas" element={<RutaPrivada rolRequerido="ADMIN"><AdminListasUI /></RutaPrivada>} />
            </Routes>
        </Router>
    );
}
