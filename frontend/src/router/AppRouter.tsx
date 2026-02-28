import { Route, Routes } from 'react-router-dom'

import MainLayout from '@/layouts/MainLayout/MainLayout';
import Home from '@/pages/Home/Home';
import Login from '@/pages/Login/Login';
import About from '@/pages/About/About';
import NotFound from '@/pages/NotFound/NotFound';

const AppRouter = () => {
    return <Routes>
        <Route path="/" element={<MainLayout />}>
            <Route index element={<Home />} />
            <Route path="login" element={<Login />} />
            <Route path="about" element={<About />} />
            <Route path="*" element={<NotFound />} />
        </Route>
    </Routes>
};

export default AppRouter;