import { isRejectedWithValue, Middleware, MiddlewareAPI } from '@reduxjs/toolkit';
import { setAlert } from '../store/pev-slices/environment';
import { IEnvironmentAlert } from '../pev-interface/IEnvironment';
import { TAlert } from '../pev-type/TAlert';
import { UrlUtils } from '../pev-utils/UrlUtils';

export const ErrorLogger: Middleware = (api: MiddlewareAPI) => (next) => (action) => {
    if (isRejectedWithValue(action)) {
        const error: IEnvironmentAlert = {
            type: TAlert.ERROR,
            message: action.payload.data.message
        };

        setAlert(error);

        // 임시 처리
        if (action.payload.data.message === '인증 토큰이 존재하지 않습니다.') {
            let profile = 'prod';
            if (window.location.href.indexOf('localhost') > -1) profile = 'local';

            window.location.href = UrlUtils.getIdpUrl(profile);
        }
    }

    return next(action);
};
