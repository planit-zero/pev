// third-party
import { createSlice } from '@reduxjs/toolkit';

// project imports
import { dispatch } from '../index';

// types
import { IUser, IUserState } from '../../pev-interface/IUser';

// ----------------------------------------------------------------------

const initialState: IUserState = {
    info: null
};

const userSlice = createSlice({
    name: 'userSlice',
    initialState,
    reducers: {
        setInfo(state, action) {
            state.info = action.payload;
        }
    }
});

export default userSlice.reducer;

export const setUserInfo = (info: IUser) => {
    dispatch(userSlice.actions.setInfo(info));
};
