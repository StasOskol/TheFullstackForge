import { BrowserRouter } from 'react-router-dom';
import AppRouter from './router/Router';

import '@styles/main.scss';

const App = () => {
  return <BrowserRouter>
    <AppRouter />
  </BrowserRouter>
};

export default App;