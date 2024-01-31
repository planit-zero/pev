import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const reportApi = createApi({
    reducerPath: 'reportApi',
    baseQuery: fetchBaseQuery({
        baseUrl: 'api/meta'
    }),
    endpoints: (builder) => ({
        insertReport: builder.mutation<void, any>({
            query: (payload) => ({
                url: 'report',
                method: 'POST',
                body: payload
            })
        }),
        insertChartError: builder.mutation<void, any>({
            query: (payload) => ({
                url: 'report/chart',
                method: 'POST',
                body: payload
            })
        })
    })
});

export const { useInsertReportMutation, useInsertChartErrorMutation } = reportApi;
