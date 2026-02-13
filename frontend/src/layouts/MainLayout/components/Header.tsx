import { Link, NavLink } from 'react-router-dom';
import './Header.scss';

const Header = () => {
    const navItems = [
        { path: '/', label: 'Главная' },
        { path: '/about', label: 'О проекте' },
    ];

    return <header className="header">
        <div className="container">
            <div className="header-content">
                <Link to="/" className="logo">
                    React Template
                </Link>

                <nav className="nav">
                    {navItems.map((item) => (
                        <NavLink
                            key={item.path}
                            to={item.path}
                            className={({ isActive }) =>
                                isActive ? 'nav-link active' : 'nav-link'
                            }
                        >
                            {item.label}
                        </NavLink>
                    ))}
                </nav>
            </div>
        </div>
    </header>
};

export default Header;