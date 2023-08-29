import { IRecordState } from '../../pev-interface/IRecordDataR';
import { createSlice } from '@reduxjs/toolkit';
import { dispatch } from '../index';
import { IRecordDetailR } from '../../pev-interface/IRecordDetail';

const initialState: IRecordState = {
    targetRecords: []
};

const recordSlice = createSlice({
    name: 'recordSlice',
    initialState,
    reducers: {
        setTargetRecords(state, action) {
            state.targetRecords = action.payload;
        }
    }
});

export default recordSlice.reducer;

export const setTargetRecords = (targets: IRecordDetailR[]) => {
    dispatch(recordSlice.actions.setTargetRecords(targets));
};
