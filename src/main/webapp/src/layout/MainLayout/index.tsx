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

interface MainStyleProps {
    theme: Theme;
    open: boolean;
    width: number;
}

// styles
const Main = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })(({ theme, open, width }: MainStyleProps) => ({
    ...theme.typography.mainContent,
    borderBottomLeftRadius: 0,
    borderBottomRightRadius: 0,
    padding: '20px 40px',
    marginTop: '68px',
    ...(!open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.shorter + 200
        }),
        marginLeft: `${-(width - 40)}px`
    }),
    ...(open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.shorter + 200
        }),
        width: `calc(100% - ${width}px)`,
        marginLeft: '20px',
        [theme.breakpoints.down('md')]: {
            marginLeft: `${-(width - 40)}px`
        }
    }),
    ...((width === finderWidthNarrow || width === finderWidthWide) && {
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

    const header = () => {
        return (
            <Toolbar sx={{ p: 1 }}>
                <Header />
            </Toolbar>
        );
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />

            {/* header */}
            <AppBar enableColorOnDark position="fixed" color="inherit" elevation={0} sx={{ bgcolor: theme.palette.background.default }}>
                {header()}
            </AppBar>

            {/* snackbar */}
            <CommonSnackbar />

            {/* sidebar */}
            <Sidebar />

            {/* main content */}
            <Main theme={theme} open={drawerOpen} width={finderWidth}>
                <Box sx={{ width: '100%' }}>
                    <RecordViewer />
                </Box>
            </Main>
        </Box>
    );
};

export default MainLayout;
