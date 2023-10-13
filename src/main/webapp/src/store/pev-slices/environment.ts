import { IEnvironment, IEnvironmentAlert } from '../../pev-interface/IEnvironment';
import { createSlice } from '@reduxjs/toolkit';
import { dispatch } from '../index';

const initialState: IEnvironment = {
    alert: null,
    viewMode: 'single'
};

const environmentSlice = createSlice({
    name: 'environmentSlice',
    initialState,
    reducers: {
        setAlert(state, action) {
            state.alert = action.payload;
        },
        setViewMode(state, action) {
            state.viewMode = action.payload;
        }
    }
});

export default environmentSlice.reducer;

export const setAlert = (alert: IEnvironmentAlert | null) => {
    dispatch(environmentSlice.actions.setAlert(alert));
};

export const setViewMode = (viewMode: string) => {
    dispatch(environmentSlice.actions.setViewMode(viewMode));
};
