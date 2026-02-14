import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { codeResponseError } from '@/utils/api-response/code.responese';

import { authController } from '@/services/api/controllers/auth-controller';

import { ApiError } from '@/types/error-api/error-api.type';

import './Login.scss';

const Login = () => {
    const navigete = useNavigate();
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const send = async () => {
        setError('');

        try {
            const response = await authController.login({
                login,
                password
            });

            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('userId', response.data.userId);
                localStorage.setItem('username', response.data.username);

                console.log('Успешно!', response.data);
                navigete('/')
            }
        } catch (err) {
            const error = err as ApiError;  
            console.log('Ошибка:', error);

            if (error.response?.status) {
                setError(codeResponseError(error.response?.status))
            }
        }
    };

    return <div className='form-auth'>
        <figure className='form-auth-login'>
            <span className='form-auth-login-text'>Логин: </span>
            <input
                className='form-auth-login-inp'
                type="text"
                onChange={(e) => setLogin(e.target.value)}
            />
        </figure>
        <figure className='form-auth-login'>
            <span className='form-auth-login-text'>Пароль:</span>
            <input
                className='form-auth-login-inp'
                type="password"
                onChange={(e) => setPassword(e.target.value)}
            />
        </figure>
        {error && (
            <div className='form-auth-login-error'>
                {error}
            </div>
        )}
        <button
            className='form-auth-login-sing-in'
            onClick={send}
        >
            Войти
        </button>
    </div>
};

export default Login;