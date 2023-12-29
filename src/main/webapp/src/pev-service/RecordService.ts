import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IRecordDetailP, IRecordDetailR } from '../pev-interface/IRecordDetail';
import { IRecordDeptInfo, IRecordFormInfoP, IRecordFormInfoR } from '../pev-interface/IRecordInfo';
import { IRecordDataP, IRecordDataR } from '../pev-interface/IRecordDataR';
import { IRecord, ISearchCondition } from '../pev-interface/IRecord';
import { IChart, IChartP, IChartReplyP, IChartReplyR } from '../pev-interface/IChart';

export const recordApi = createApi({
    reducerPath: 'recordApi',
    baseQuery: fetchBaseQuery({ baseUrl: '/api/record' }),
    endpoints: (builder) => ({
        getDetailListByCondition: builder.mutation<IRecordDetailR[], IRecordDetailP>({
            query: (payload) => ({
                url: 'detail',
                method: 'POST',
                body: payload
            })
        }),
        getDeptInfoList: builder.query<IRecordDeptInfo[], void>({
            query: () => ({
                url: 'info/dept'
            })
        }),
        getRecordFormInfo: builder.mutation<IRecordFormInfoR, IRecordFormInfoP>({
            query: (payload) => ({
                url: 'info/form',
                method: 'POST',
                body: payload
            })
        }),
        getRecordDataList: builder.mutation<IRecordDataR[], IRecordDataP>({
            query: (payload) => ({
                url: 'data/medical',
                method: 'POST',
                body: payload
            })
        }),
        getRecordList: builder.mutation<IRecord[], ISearchCondition>({
            query: (payload) => ({
                url: 'list',
                method: 'POST',
                body: payload
            })
        }),
        getChart: builder.mutation<IChart, IChartP>({
            query: (payload) => ({
                url: 'chart',
                method: 'POST',
                body: payload
            })
        }),
        getChartReply: builder.mutation<IChartReplyR, IChartReplyP>({
            query: (payload) => ({
                url: 'chart/reply',
                method: 'POST',
                body: payload
            })
        })
    })
});

export const {
    useGetDetailListByConditionMutation,
    useGetDeptInfoListQuery,
    useGetRecordListMutation,
    useGetChartMutation,
    useGetChartReplyMutation
} = recordApi;
