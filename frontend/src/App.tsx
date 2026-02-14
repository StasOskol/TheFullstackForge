import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import MainLayout from '@layouts/MainLayout/MainLayout';

import '@styles/main.scss';

import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import About from './pages/About/About';
import NotFound from './pages/NotFound/NotFound';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Home />} />
          <Route path='/login' element={<Login />} />
          <Route path="about" element={<About />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default App;