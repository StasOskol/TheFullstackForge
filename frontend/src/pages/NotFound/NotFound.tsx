import { Link } from 'react-router-dom';
import './NotFound.scss';

const NotFound = () => {
    return <div className="not-found-page">
        <div className="container text-center">
            <h1>404</h1>
            <h2>Страница не найдена</h2>
            <Link to="/" className="button button-primary">
                На главную
            </Link>
        </div>
    </div>
};

export default NotFound;