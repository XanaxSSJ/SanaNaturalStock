import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProductosUI from "./pages/ProductosUI";
import AdminListasUI from "./pages/AdminListasUI"; // Asegurate que este archivo exista

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<ProductosUI />} />
                <Route path="/admin/listas" element={<AdminListasUI />} />
            </Routes>
        </Router>
    );
}
