import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IPatientByIrbP, IPatientByIrbR, IPatientRidP, IPatientR, IRidByGidP, IRidByGidR } from '../pev-interface/IPatient';

export const patientApi = createApi({
    reducerPath: 'patientApi',
    baseQuery: fetchBaseQuery({
        baseUrl: '/api/patient'
    }),
    endpoints: (builder) => ({
        getPatient: builder.mutation<IPatientR, IPatientRidP>({
            query: (payload) => ({
                url: 'rid',
                method: 'POST',
                body: payload
            })
        }),
        getRidByGid: builder.mutation<IRidByGidR, IRidByGidP>({
            query: (payload) => ({
                url: 'gid',
                method: 'POST',
                body: payload
            })
        }),
        getPatientList: builder.mutation<IPatientByIrbR[], IPatientByIrbP>({
            query: (payload) => ({
                url: 'list',
                method: 'POST',
                body: payload
            })
        })
    })
});

export const { useGetPatientMutation, useGetRidByGidMutation, useGetPatientListMutation } = patientApi;
