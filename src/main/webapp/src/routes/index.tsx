import { useRoutes } from 'react-router-dom';

// routes
import MainLayout from '../layout/MainLayout';

// ==============================|| ROUTING RENDER ||============================== //

export default function ThemeRoutes() {
    return useRoutes([{ path: '/', element: <MainLayout /> }]);
}
