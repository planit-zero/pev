// material-ui
import { useTheme } from '@mui/material/styles';
import { Backdrop, Box, CircularProgress, Typography, useMediaQuery } from '@mui/material';

// assets
import { IconPower, IconReportMedical } from '@tabler/icons';
import { useSelector } from '../../../store';
import { useSignOutMutation } from '../../../pev-service/UserService';
import * as React from 'react';
import { UrlUtils } from '../../../pev-utils/UrlUtils';

// ==============================|| MAIN NAVBAR / HEADER ||============================== //

const Header = () => {
    const theme = useTheme();
    const matchDownMd = useMediaQuery(theme.breakpoints.down('md'));

    const { info } = useSelector((state) => state.user);
    const { profile } = useSelector((state) => state.environment);

    const [signOut, { isLoading: isSignOutLoading }] = useSignOutMutation();

    const handleSignOutClick = () => {
        signOut()
            .unwrap()
            .then(() => (window.location.href = UrlUtils.getIdpUrl(profile)));
    };

    return (
        <Box width={'100%'} display={'flex'} justifyContent={'space-between'} alignItems={'center'} sx={{ pl: '40px' }}>
            <Box
                component="span"
                display={'flex'}
                justifyContent={'flex-start'}
                alignItems={'center'}
                gap={1}
                sx={{ cursor: 'pointer' }}
                onClick={() => window.location.href = 'https://deview.snuh.org'}
            >
                <IconReportMedical color={'#3f51b5'} />
                {!matchDownMd && <Typography sx={{ fontSize: 'h3.fontSize', fontWeight: 'bold' }}>가명의무기록 뷰어</Typography>}
            </Box>
            {info && (
                <Box display={'flex'} justifyContent={'flex-start'} alignItems={'center'} gap={1}>
                    <Backdrop sx={{ color: '#fff', zIndex: (t) => t.zIndex.drawer + 1 }} open={isSignOutLoading}>
                        <CircularProgress color="inherit" />
                    </Backdrop>
                    <Typography sx={{ fontSize: 'h5.fontSize' }}>
                        {info.deptNm} {info.stfNm}
                    </Typography>
                    <IconPower color={'#3f51b5'} cursor={'pointer'} onClick={handleSignOutClick} />
                </Box>
            )}
        </Box>
    );
};

export default Header;
