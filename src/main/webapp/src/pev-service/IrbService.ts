import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IPatientGidP, IPatientR } from '../pev-interface/IPatient';
import { IIrb } from '../pev-interface/IIrb';

export const irbApi = createApi({
    reducerPath: 'irbApi',
    baseQuery: fetchBaseQuery({
        baseUrl: '/api/irb'
    }),
    endpoints: (builder) => ({
        getIrbList: builder.query<IIrb[], string>({
            query: (payload) => ({
                url: `list?stfNo=${payload}`,
                method: 'GET'
            })
        })
    })
});

export const { useGetIrbListQuery } = irbApi;
