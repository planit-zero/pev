import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IMetaRecordList } from '../pev-interface/IMetaRecord';

export const metaRecordApi = createApi({
    reducerPath: 'metaRecordApi',
    baseQuery: fetchBaseQuery({
        baseUrl: 'api/meta/record'
    }),
    endpoints: (builder) => ({
        getMetaRecordList: builder.query<IMetaRecordList, void>({
            query: () => ({
                url: '',
                method: 'GET'
            })
        })
    })
});

export const { useGetMetaRecordListQuery } = metaRecordApi;
