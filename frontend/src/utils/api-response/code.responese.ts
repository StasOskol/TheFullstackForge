export const codeResponseError = (code: number) => {
    let responseError = '500 Ошибка подключения к серверу'

    switch (code) {
        case 400:
            responseError = 'Неверный запрос. Проверьте введённые данные';
            break;
        case 401:
        case 403:
            responseError = 'Неверный логин или пароль';
            break;
        case 404:
            responseError = 'Ресурс не найден';
            break;
        case 408:
            responseError = 'Превышено время ожидания запроса';
            break;
        case 502:
            responseError = 'Сервер временно недоступен';
            break;
        case 504:
            responseError = 'Превышено время ожидания ответа от сервера';
            break;
    }

    return responseError;
}