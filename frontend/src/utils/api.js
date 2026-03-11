import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// Axios instance with default config
const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request interceptor to add JWT token
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

/**
 * Authentication Services
 */
export const login = async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    if (response.data.token) {
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
};

export const registerUser = async (userData) => {
    const response = await api.post('/auth/signup/user', userData);
    return response.data;
};

export const registerSeller = async (sellerData) => {
    const response = await api.post('/auth/signup/seller', sellerData);
    return response.data;
};

/**
 * Crop Marketplace Services
 */
export const fetchCrops = async (category = null, page = 0, size = 10) => {
    let url = '/crops';
    if (category) {
        url = `/crops/category/${category}?page=${page}&size=${size}`;
    }
    const response = await api.get(url);
    return response.data;
};

export const fetchCropById = async (id) => {
    const response = await api.get(`/crops/${id}`);
    return response.data;
};

export const addCrop = async (cropData) => {
    const response = await api.post('/crops', cropData);
    return response.data;
};

export const updateCrop = async (id, cropData) => {
    const response = await api.put(`/crops/${id}`, cropData);
    return response.data;
};

export const deleteCrop = async (id) => {
    const response = await api.delete(`/crops/${id}`);
    return response.data;
};

/**
 * Order Services
 */
export const placeOrder = async (orderData) => {
    const response = await api.post('/orders', orderData);
    return response.data;
};

/**
 * Analytics Services
 */
export const getMostTradedCrops = async () => {
    const response = await api.get('/analytics/most-traded');
    return response.data;
};

export const getTotalListings = async () => {
    const response = await api.get('/analytics/total-listings');
    return response.data;
};

export const getSellerRevenue = async () => {
    const response = await api.get('/analytics/seller-revenue');
    return response.data;
};

/**
 * AI Services
 */
export const getPricePrediction = async (predictionData) => {
    const response = await api.post('/ai/cropsense/predict', predictionData);
    return response.data;
};

export default {
    login,
    registerUser,
    registerSeller,
    fetchCrops,
    fetchCropById,
    addCrop,
    updateCrop,
    deleteCrop,
    placeOrder,
    getMostTradedCrops,
    getTotalListings,
    getSellerRevenue,
    getPricePrediction,
};
