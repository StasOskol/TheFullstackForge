import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:3001';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request interceptor
apiClient.interceptors.request.use(
    (config) => {
        // Здесь можно добавить токен авторизации
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response != null) {
            const numberStatus: number = Math.round(error.response.status / 100);

            switch (numberStatus) {
                case 4: // 4xx ошибки
                    switch (error.response.status) {
                        case 400:
                            window.location.replace(`/error?code=${error.response.status}`);
                            break;
                        case 401:
                        case 403:
                            window.location.replace("/login/1");
                            break;
                        case 404:
                        case 405:
                            window.location.replace(`/error?code=${error.response.status}`);
                            break;
                        default:
                            window.location.replace(`/error?code=${error.response.status}`);
                    }
                    break;

                case 5: // 5xx ошибки
                    if (error.response.status >= 500 && error.response.status <= 505) {
                        window.location.replace(`/error?code=${error.response.status}`);
                    }
                    break;

                default:
                    window.location.replace("/error");
                    break;
            }
        }

        if (error.response == null) {
            // Сетевая ошибка (сервер недоступен)
            window.location.replace("/error");
            return Promise.reject(error);
        }

        return Promise.reject(error);
    }
);

export default apiClient;