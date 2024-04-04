import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import {
    IChartError,
    IEventSearchTargetDistribution,
    ILogEvent,
    ILogUser,
    IReport,
    IReportDetail,
    IReportDetailUpdate,
    IUserLoginDeptStatistics,
    IUserLoginHourStatistics,
    IUserLoginWeekStatistics
} from '../pev-interface/IChart';

export const reportApi = createApi({
    reducerPath: 'reportApi',
    baseQuery: fetchBaseQuery({
        baseUrl: 'api/meta'
    }),
    tagTypes: ['Report', 'Detail', 'ChartError'],
    endpoints: (builder) => ({
        getReportList: builder.query<IReport[], void>({
            query: () => ({
                url: 'report/list',
                method: 'GET'
            }),
            providesTags: ['Report']
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
            invalidatesTags: ['Report', 'Detail']
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
        }),
        getLogUserList: builder.query<ILogUser[], void>({
            query: () => ({
                url: 'report/log/user',
                method: 'GET'
            })
        }),
        getLogEventList: builder.query<ILogEvent[], void>({
            query: () => ({
                url: 'report/log/event',
                method: 'GET'
            })
        }),
        getStatisticsUserWeekList: builder.query<IUserLoginWeekStatistics[], void>({
            query: () => ({
                url: 'report/statistics/user/week',
                method: 'GET'
            })
        }),
        getStatisticsUserHourList: builder.query<IUserLoginHourStatistics[], void>({
            query: () => ({
                url: 'report/statistics/user/hour',
                method: 'GET'
            })
        }),
        getEventSearchTargetDistributionList: builder.query<IEventSearchTargetDistribution[], void>({
            query: () => ({
                url: 'report/statistics/event/search-targets-distribution',
                method: 'GET'
            })
        }),
        getStatisticsUserDeptList: builder.query<IUserLoginDeptStatistics[], void>({
            query: () => ({
                url: 'report/statistics/user/dept',
                method: 'GET'
            })
        })
    })
});

export const {
    useGetReportListQuery,
    useGetReportDetailListQuery,
    useUpdateProcessMutation,
    useInsertReportMutation,
    useInsertChartErrorMutation,
    useGetChartErrorListQuery,
    useUpdateChartErrorProcessMutation,
    useGetLogUserListQuery,
    useGetLogEventListQuery,
    useGetStatisticsUserWeekListQuery,
    useGetStatisticsUserHourListQuery,
    useGetEventSearchTargetDistributionListQuery,
    useGetStatisticsUserDeptListQuery
} = reportApi;
