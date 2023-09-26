// material-ui
import { useTheme } from '@mui/material/styles';
import { Avatar, Box, Typography, useMediaQuery } from '@mui/material';

// project imports
import LAYOUT_CONST from 'constant';
import useConfig from 'hooks/useConfig';
import LogoSection from '../LogoSection';
import SearchSection from './SearchSection';
import MobileSection from './MobileSection';
import ProfileSection from './ProfileSection';
import LocalizationSection from './LocalizationSection';
import MegaMenuSection from './MegaMenuSection';
import NotificationSection from './NotificationSection';

import { useDispatch, useSelector } from 'store';
import { openDrawer } from 'store/slices/menu';

// assets
import { IconMenu2, IconReportMedical } from '@tabler/icons';

// ==============================|| MAIN NAVBAR / HEADER ||============================== //

const Header = () => {
    const theme = useTheme();

    return (
        <>
            {/* logo & toggler button */}
            <Box width={576}>
                <Box component="span" display={'flex'} justifyContent={'flex-start'} alignItems={'center'} gap={1}>
                    <IconReportMedical color={'#3f51b5'} />
                    <Typography sx={{ fontSize: 'h3.fontSize', fontWeight: 'bold' }}>가명화 EMR Viewer</Typography>
                </Box>
            </Box>

            {/* header search */}
            <Box sx={{ width: 'calc(100% - 576px)' }}>
                <SearchSection />
            </Box>
        </>
    );
};

export default Header;
