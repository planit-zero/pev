// material-ui
import { useTheme } from '@mui/material/styles';
import { Box, Typography, useMediaQuery } from '@mui/material';

// assets
import { IconPower, IconReportMedical } from '@tabler/icons';
import { useSelector } from '../../../store';

// ==============================|| MAIN NAVBAR / HEADER ||============================== //

const Header = () => {
    const theme = useTheme();
    const matchDownMd = useMediaQuery(theme.breakpoints.down('md'));

    const { info } = useSelector((state) => state.user);

    return (
        <Box width={'100%'} display={'flex'} justifyContent={'space-between'} alignItems={'center'} sx={{ pl: '40px' }}>
            <Box component="span" display={'flex'} justifyContent={'flex-start'} alignItems={'center'} gap={1}>
                <IconReportMedical color={'#3f51b5'} />
                {!matchDownMd && <Typography sx={{ fontSize: 'h3.fontSize', fontWeight: 'bold' }}>가명화 EMR Viewer</Typography>}
            </Box>
            {info && (
                <Box display={'flex'} justifyContent={'flex-start'} alignItems={'center'} gap={1}>
                    <Typography sx={{ fontSize: 'h5.fontSize' }}>
                        {info.deptNm} {info.stfNm}
                    </Typography>
                    <IconPower color={'#3f51b5'} cursor={'pointer'} />
                </Box>
            )}
        </Box>
    );
};

export default Header;
