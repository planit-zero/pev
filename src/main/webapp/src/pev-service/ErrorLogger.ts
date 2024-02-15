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

        // 임시 처리
        if (action.payload.data.message === '인증 토큰이 존재하지 않습니다.') {
            window.location.href = 'http://172.26.33.22:18020?destination=deview';
        }
    }

    return next(action);
};
