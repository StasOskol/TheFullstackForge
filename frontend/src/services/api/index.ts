import apiClient from './config';

export const api = {
    // Примеры методов API
    get: <T>(url: string, params?: object) =>
        apiClient.get<T>(url, { params }),

    post: <T>(url: string, data?: object) =>
        apiClient.post<T>(url, data),

    put: <T>(url: string, data?: object) =>
        apiClient.put<T>(url, data),

    patch: <T>(url: string, data?: object) =>
        apiClient.patch<T>(url, data),

    delete: <T>(url: string) =>
        apiClient.delete<T>(url),
};