import { AlertColor } from '@mui/material';

type AlertType = {
    SUCCESS: AlertColor;
    INFO: AlertColor;
    WARNING: AlertColor;
    ERROR: AlertColor;
};

export const TAlert: AlertType = {
    SUCCESS: 'success',
    INFO: 'info',
    WARNING: 'warning',
    ERROR: 'error'
};
