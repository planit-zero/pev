import * as React from 'react';

// material-ui
import { useTheme } from '@mui/material/styles';
import { Box, Drawer, useMediaQuery } from '@mui/material';
import { useDispatch, useSelector } from 'store';
import { openDrawer } from 'store/slices/menu';
import RecordFinder from '../../../pev-component/record-finder/RecordFinder';

// ==============================|| SIDEBAR DRAWER ||============================== //

const Sidebar = () => {
    const theme = useTheme();
    const dispatch = useDispatch();

    const { drawerOpen } = useSelector((state) => state.menu);
    const { finderWidth } = useSelector((state) => state.environment);

    const matchUpMd = useMediaQuery(theme.breakpoints.up('md'));

    const drawer = React.useMemo(() => <RecordFinder />, [drawerOpen]);

    return (
        <Box component="nav" sx={{ width: finderWidth, height: 'calc(100vh - 48px)', mt: '48px', ml: '48px' }} aria-label="record finder">
            <Drawer
                variant={matchUpMd ? 'persistent' : 'temporary'}
                anchor="left"
                open={drawerOpen}
                onClose={() => dispatch(openDrawer(!drawerOpen))}
                sx={{
                    '& .MuiDrawer-paper': {
                        zIndex: 1,
                        position: 'fixed',
                        top: '48px',
                        left: '48px',
                        width: finderWidth,
                        height: 'calc(100vh - 48px)',
                        backgroundColor: 'transparent',
                        color: theme.palette.text.primary,
                        borderRight: 'none'
                    }
                }}
                ModalProps={{ keepMounted: true }}
                color="inherit"
            >
                {drawer}
            </Drawer>
        </Box>
    );
};

export default React.memo(Sidebar);
