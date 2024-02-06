import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IChartError, IReport, IReportDetail, IReportDetailUpdate, IReportRequest } from '../pev-interface/IChart';

export const reportApi = createApi({
    reducerPath: 'reportApi',
    baseQuery: fetchBaseQuery({
        baseUrl: 'api/meta'
    }),
    tagTypes: ['Detail', 'ChartError'],
    endpoints: (builder) => ({
        getReportList: builder.mutation<IReport[], IReportRequest>({
            query: (payload) => ({
                url: 'report/list',
                method: 'POST',
                body: payload
            })
        }),
        getReportDetailList: builder.query<IReportDetail[], number>({
            query: (payload) => ({
                url: `report/detail/list?reportId=${payload}`
            }),
            providesTags: ['Detail']
        }),
        updateProcess: builder.mutation<void, IReportDetailUpdate>({
            query: (payload) => ({
                url: 'report/detail',
                method: 'POST',
                body: payload
            }),
            invalidatesTags: ['Detail']
        }),
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
        }),
        getChartErrorList: builder.query<IChartError[], void>({
            query: () => ({
                url: 'report/chart/list'
            }),
            providesTags: ['ChartError']
        }),
        updateChartErrorProcess: builder.mutation<void, number>({
            query: (payload) => ({
                url: `report/chart?errId=${payload}`,
                method: 'PUT'
            }),
            invalidatesTags: ['ChartError']
        })
    })
});

export const {
    useGetReportListMutation,
    useGetReportDetailListQuery,
    useUpdateProcessMutation,
    useInsertReportMutation,
    useInsertChartErrorMutation,
    useGetChartErrorListQuery,
    useUpdateChartErrorProcessMutation
} = reportApi;
