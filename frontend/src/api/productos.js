import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api';

export const fetchProductosFiltrados = (params) => {
    return axios.get(`${BASE_URL}/productos/filtros`, { params });
};
