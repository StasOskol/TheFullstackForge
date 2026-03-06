import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { authController } from "@/services/api/controllers/auth-controller";

const Auth = () => {
    const navigate = useNavigate();
    const [login, setLogin] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const sendBackAuth = () => {
        const data = {
            login: login,
            password: password
        }

        authController.login(data)
            .then((response) => {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('userId', response.data.userId.toString());
                localStorage.setItem('username', response.data.username);

                navigate('/');
            })
            .catch((error) => console.log(error));
    }

    return <div>
        <input onChange={(e) => setLogin(e.target.value)} />
        <input type='password' onChange={(e) => setPassword(e.target.value)} />
        <button onClick={sendBackAuth}>
            Войти
        </button>
    </div>
}

export default Auth;