import { useMemo } from 'react';

// material-ui
import { styled, useTheme, Theme } from '@mui/material/styles';
import { AppBar, Box, Container, CssBaseline, Toolbar, useMediaQuery } from '@mui/material';

// project imports
import Header from './Header';
import Sidebar from './Sidebar';

import LAYOUT_CONST from 'constant';
import useConfig from 'hooks/useConfig';
import { finderWidthNarrow, finderWidthWide } from 'store/constant';
import { useSelector } from 'store';

// assets
import RecordViewer from '../../pev-component/record-viewer/RecordViewer';
import CommonSnackbar from '../../pev-component/common/CommonSnackbar';

interface MainStyleProps {
    theme: Theme;
    open: boolean;
    layout: string;
    width: number;
}

// styles
const Main = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })(({ theme, open, layout, width }: MainStyleProps) => ({
    ...theme.typography.mainContent,
    borderBottomLeftRadius: 0,
    borderBottomRightRadius: 0,
    padding: `20px 10px 20px 20px`,
    marginTop: layout === LAYOUT_CONST.HORIZONTAL_LAYOUT ? 135 : 88,
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
        marginTop: 88,
        marginLeft: '20px',
        [theme.breakpoints.down('md')]: {
            marginLeft: `${-(width - 40)}px`,
            marginTop: 88
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

    const matchDownMd = useMediaQuery(theme.breakpoints.down('md'));

    const { drawerOpen } = useSelector((state) => state.menu);
    const { container, layout } = useConfig();

    const condition = layout === LAYOUT_CONST.HORIZONTAL_LAYOUT && !matchDownMd;

    const header = useMemo(
        () => (
            <Toolbar sx={{ p: condition ? '10px' : '16px' }}>
                <Header />
            </Toolbar>
        ),
        // eslint-disable-next-line react-hooks/exhaustive-deps
        [layout, matchDownMd]
    );

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />

            {/* header */}
            <AppBar enableColorOnDark position="fixed" color="inherit" elevation={0} sx={{ bgcolor: theme.palette.background.default }}>
                {header}
            </AppBar>

            {/* snackbar */}
            <CommonSnackbar />

            {/* sidebar */}
            <Sidebar />

            {/* main content */}
            <Main theme={theme} open={drawerOpen} layout={layout} width={finderWidth}>
                <Container maxWidth={container ? 'lg' : false} {...(!container && { sx: { px: { xs: 0 } } })}>
                    <RecordViewer />
                </Container>
            </Main>
        </Box>
    );
};

export default MainLayout;
