import { Route, Routes } from 'react-router-dom'

import MainLayout from '@/layouts/MainLayout/MainLayout';
import Home from '@/pages/Home/Home';
import About from '@/pages/About/About';
import NotFound from '@/pages/NotFound/NotFound';
import Auth from '@/pages/Auth/Auth';

const AppRouter = () => {
    return <Routes>
        <Route path="/" element={<MainLayout />}>
            <Route path='auth' element={<Auth />} />
            <Route index element={<Home />} />
            <Route path="about" element={<About />} />
            <Route path="*" element={<NotFound />} />
        </Route>
    </Routes>
};

export default AppRouter;