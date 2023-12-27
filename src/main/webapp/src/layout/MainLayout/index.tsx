// material-ui
import { styled, useTheme, Theme } from '@mui/material/styles';
import { AppBar, Box, CssBaseline, Toolbar } from '@mui/material';

// project imports
import Header from './Header';
import Sidebar from './Sidebar';

import { finderWidthNarrow, finderWidthWide } from 'store/constant';
import { useSelector } from 'store';

// assets
import RecordViewer from '../../pev-component/record-viewer/RecordViewer';
import CommonSnackbar from '../../pev-component/common/CommonSnackbar';
import * as React from 'react';
import { IconGridDots, IconMessageReport, IconReportMedical, IconSettings } from '@tabler/icons';

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

    const { info } = useSelector((state) => state.user);

    const header = () => {
        return (
            <Toolbar sx={{ height: '48px' }}>
                <Header />
            </Toolbar>
        );
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />

            {/* header */}
            <AppBar
                enableColorOnDark
                position="fixed"
                color="inherit"
                elevation={0}
                sx={{ background: theme.palette.background.default, ml: '48px', zIndex: 1 }}
            >
                {header()}
            </AppBar>

            {/* snackbar */}
            <CommonSnackbar />

            {/* Platform Sidebar */}
            <Box
                sx={{
                    zIndex: 2,
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    width: 48,
                    height: '100vh',
                    backgroundColor: '#3f51b5'
                }}
            >
                <Box width={48} height={48} display={'flex'} justifyContent={'center'} alignItems={'center'} sx={{ cursor: 'pointer' }}>
                    <IconGridDots color={'white'} />
                </Box>
                <Box width={48} height={48} display={'flex'} justifyContent={'center'} alignItems={'center'} sx={{ cursor: 'pointer' }}>
                    <IconReportMedical color={'white'} />
                </Box>
                <Box width={48} height={48} display={'flex'} justifyContent={'center'} alignItems={'center'} sx={{ cursor: 'pointer' }}>
                    <IconMessageReport color={'white'} />
                </Box>
                {info && info.authCd === 'S' && (
                    <Box width={48} height={48} display={'flex'} justifyContent={'center'} alignItems={'center'} sx={{ cursor: 'pointer' }}>
                        <IconSettings color={'white'} />
                    </Box>
                )}
            </Box>

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
