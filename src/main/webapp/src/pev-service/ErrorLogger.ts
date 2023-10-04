import { isRejectedWithValue, Middleware, MiddlewareAPI } from '@reduxjs/toolkit';
import { setAlert } from '../store/pev-slices/environment';
import { IEnvironmentAlert } from '../pev-interface/IEnvironment';
import { TAlert } from '../pev-type/TAlert';

export const ErrorLogger: Middleware = (api: MiddlewareAPI) => (next) => (action) => {
    if (isRejectedWithValue(action)) {
        const error: IEnvironmentAlert = {
            type: TAlert.ERROR,
            message: action.payload.data.message
        };

        setAlert(error);
    }

    return next(action);
};
