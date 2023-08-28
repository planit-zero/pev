import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IRecordDetailP, IRecordDetailR } from '../pev-interface/IRecordDetail';
import { IRecordDeptInfo } from '../pev-interface/IRecordInfo';

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
                url: '/info/dept'
            })
        })
    })
});

export const { useGetDetailListByConditionMutation, useGetDeptInfoListQuery } = recordApi;
