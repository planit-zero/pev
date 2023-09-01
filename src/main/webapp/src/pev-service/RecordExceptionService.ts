import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IRecordExceptionP, ISurgeryDefaultValue } from '../pev-interface/IRecordException';

export const recordExceptionApi = createApi({
    reducerPath: 'recordExceptionApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/record/exception' }),
    endpoints: (builder) => ({
        getSurgeryDefaultValueList: builder.mutation<ISurgeryDefaultValue[], IRecordExceptionP>({
            query: (payload) => ({
                url: 'surgery',
                method: 'POST',
                body: payload
            })
        })
    })
});

export const { useGetSurgeryDefaultValueListMutation } = recordExceptionApi;
