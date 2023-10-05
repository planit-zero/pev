import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IHospitalDepartment } from '../pev-interface/IHospital';

export const hospitalApi = createApi({
    reducerPath: 'hospitalApi',
    baseQuery: fetchBaseQuery({
        baseUrl: '/api/hospital'
    }),
    endpoints: (builder) => ({
        getDepartmentList: builder.query<IHospitalDepartment[], void>({
            query: () => ({
                url: 'dept'
            })
        })
    })
});

export const { useGetDepartmentListQuery } = hospitalApi;
