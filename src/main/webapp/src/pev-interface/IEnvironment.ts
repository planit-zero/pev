import { AlertColor } from '@mui/material';

export interface IEnvironment {
    alert: IEnvironmentAlert | null;
    viewMode: string;
}

export interface IEnvironmentAlert {
    type: AlertColor;
    message: string;
}
