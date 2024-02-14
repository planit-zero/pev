import * as React from 'react';
import { AppBar, Box, Toolbar } from '@mui/material';
import CommonSnackbar from './CommonSnackbar';
import { IconGridDots, IconHome, IconMessageReport, IconReportMedical, IconSettings } from '@tabler/icons';
import Header from '../../layout/MainLayout/Header';
import { useSelector } from '../../store';
import { useTheme } from '@mui/material/styles';
import { useNavigate } from 'react-router-dom';
import { useGetTokenInSessionMutation } from '../../pev-service/UserService';

const CommonLayout = () => {
    const theme = useTheme();
    const navigate = useNavigate();
    const { info } = useSelector((state) => state.user);

    const header = () => {
        return (
            <Toolbar sx={{ height: '48px' }}>
                <Header />
            </Toolbar>
        );
    };

    const [getTokenInSession] = useGetTokenInSessionMutation();

    const goToRex = () => {
        getTokenInSession()
            .unwrap()
            .then((data) => {
                window.location.href = `https://supreme.snuh.org/sso?token=${data.token}`;
            });
    };

    return (
        <React.Fragment>
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
                <Box
                    width={48}
                    height={48}
                    display={'flex'}
                    justifyContent={'center'}
                    alignItems={'center'}
                    sx={{ cursor: 'pointer' }}
                    onClick={goToRex}
                >
                    <IconHome color={'white'} />
                </Box>
                <Box
                    width={48}
                    height={48}
                    display={'flex'}
                    justifyContent={'center'}
                    alignItems={'center'}
                    sx={{ cursor: 'pointer' }}
                    onClick={() => navigate('/')}
                >
                    <IconReportMedical color={'white'} />
                </Box>
                <Box
                    width={48}
                    height={48}
                    display={'flex'}
                    justifyContent={'center'}
                    alignItems={'center'}
                    sx={{ cursor: 'pointer' }}
                    onClick={() => navigate('/report')}
                >
                    <IconMessageReport color={'white'} />
                </Box>
                {info && info.authCd === 'S' && (
                    <Box width={48} height={48} display={'flex'} justifyContent={'center'} alignItems={'center'} sx={{ cursor: 'pointer' }}>
                        <IconSettings color={'white'} />
                    </Box>
                )}
            </Box>
        </React.Fragment>
    );
};

export default CommonLayout;
