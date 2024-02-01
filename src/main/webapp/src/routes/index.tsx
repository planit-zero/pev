import { useRoutes } from 'react-router-dom';

// routes
import MainLayout from '../layout/MainLayout';
import Report from '../pev-component/report/Report';

// ==============================|| ROUTING RENDER ||============================== //

export default function ThemeRoutes() {
    return useRoutes([
        { path: '/', element: <MainLayout /> },
        { path: '/report', element: <Report /> }
    ]);
}
