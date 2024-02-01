// material-ui
import { styled, useTheme, Theme } from '@mui/material/styles';
import { Box, CssBaseline } from '@mui/material';

// project imports
import Sidebar from './Sidebar';

import { finderWidthNarrow, finderWidthWide } from 'store/constant';
import { useSelector } from 'store';

// assets
import RecordViewer from '../../pev-component/record-viewer/RecordViewer';
import * as React from 'react';
import CommonLayout from '../../pev-component/common/CommonLayout';

interface MainStyleProps {
    theme: Theme;
    open: boolean;
    finderwidth: number;
}

// styles
const Main = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })(({ theme, open, finderwidth }: MainStyleProps) => ({
    ...theme.typography.mainContent,
    borderBottomLeftRadius: 0,
    borderBottomRightRadius: 0,
    padding: '20px 40px',
    marginTop: '48px',
    height: 'calc(100vh - 48px)',
    overflowX: 'scroll',
    overflowY: 'scroll',
    ...(!open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.shorter + 200
        }),
        width: `calc(100vh - 48px - 40px)`,
        marginLeft: `-${finderwidth - 20}px`
    }),
    ...(open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.shorter + 200
        }),
        width: `calc(100% - ${finderwidth}px - 48px - 40px)`,
        marginLeft: '20px',
        [theme.breakpoints.down('md')]: {
            marginLeft: `${-(finderwidth - 40 - 48)}px`
        }
    }),
    ...((finderwidth === finderWidthNarrow || finderwidth === finderWidthWide) && {
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.standard + 200
        })
    })
}));

// ==============================|| MAIN LAYOUT ||============================== //

const MainLayout = () => {
    const theme = useTheme();

    const { finderWidth } = useSelector((state) => state.environment);
    const { drawerOpen } = useSelector((state) => state.menu);

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />

            {/* Common Layout */}
            <CommonLayout />

            {/* App Sidebar */}
            <Sidebar />

            {/* main content */}
            <Main theme={theme} open={drawerOpen} finderwidth={finderWidth}>
                <Box sx={{ width: '100%', height: '100%' }}>
                    <RecordViewer />
                </Box>
            </Main>
        </Box>
    );
};

export default MainLayout;
