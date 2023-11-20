import * as React from 'react';
import { Alert, IconButton, Snackbar } from '@mui/material';
import { useSelector } from '../../store';
import { setAlert } from '../../store/pev-slices/environment';
import CloseIcon from '@mui/icons-material/Close';

const CommonSnackbar = () => {
    const { alert } = useSelector((state) => state.environment);

    const vertical = 'top';
    const horizontal = 'right';

    const handleClose = () => {
        setAlert(null);
    };

    if (!alert) return null;

    return (
        <Snackbar
            sx={{ marginTop: '25px', marginRight: '10px' }}
            open={Boolean(alert)}
            autoHideDuration={3000}
            onClose={handleClose}
            anchorOrigin={{ vertical, horizontal }}
            key={vertical + horizontal}
        >
            <Alert
                sx={{ whiteSpace: 'pre-line' }}
                variant={'filled'}
                severity={alert.type}
                action={
                    <IconButton aria-label="close" color="inherit" size="small" onClick={handleClose}>
                        <CloseIcon />
                    </IconButton>
                }
            >
                {alert.message}
            </Alert>
        </Snackbar>
    );
};

export default CommonSnackbar;
