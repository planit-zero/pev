// third-party
import { configureStore } from '@reduxjs/toolkit';
import { useDispatch as useAppDispatch, useSelector as useAppSelector, TypedUseSelectorHook } from 'react-redux';

import { persistStore } from 'redux-persist';

// project imports
import rootReducer from './reducer';
import { recordApi } from '../pev-service/RecordService';
import { recordExceptionApi } from '../pev-service/RecordExceptionService';
import { formApi } from '../pev-service/FormService';
import { patientApi } from '../pev-service/PatientService';
import { ErrorLogger } from '../pev-service/ErrorLogger';
import { hospitalApi } from '../pev-service/HospitalService';
import { irbApi } from '../pev-service/IrbService';
import { reportApi } from '../pev-service/ReportService';
import { metaRecordApi } from '../pev-service/MetaRecordService';

// ==============================|| REDUX - MAIN STORE ||============================== //

const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => {
        return getDefaultMiddleware({ serializableCheck: false, immutableCheck: false })
            .concat(ErrorLogger)
            .concat(recordApi.middleware)
            .concat(recordExceptionApi.middleware)
            .concat(formApi.middleware)
            .concat(patientApi.middleware)
            .concat(hospitalApi.middleware)
            .concat(irbApi.middleware)
            .concat(reportApi.middleware)
            .concat(metaRecordApi.middleware);
    }
});

const persister = persistStore(store);

export type RootState = ReturnType<typeof rootReducer>;

export type AppDispatch = typeof store.dispatch;

const { dispatch } = store;

const useDispatch = () => useAppDispatch<AppDispatch>();
const useSelector: TypedUseSelectorHook<RootState> = useAppSelector;

export { store, persister, dispatch, useSelector, useDispatch };
