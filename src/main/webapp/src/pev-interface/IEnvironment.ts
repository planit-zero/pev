import { AlertColor } from '@mui/material';

export interface IEnvironment {
    alert: IEnvironmentAlert | null;
}

export interface IEnvironmentAlert {
    type: AlertColor;
    message: string;
}
