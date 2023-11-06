import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IPatientGidP, IPatientR } from '../pev-interface/IPatient';

export const patientApi = createApi({
    reducerPath: 'patientApi',
    baseQuery: fetchBaseQuery({
        baseUrl: '/api/patient'
    }),
    endpoints: (builder) => ({
        getPatient: builder.mutation<IPatientR, IPatientGidP>({
            query: (payload) => ({
                url: 'rid',
                method: 'POST',
                body: payload
            })
        })
    })
});

export const { useGetPatientMutation } = patientApi;
