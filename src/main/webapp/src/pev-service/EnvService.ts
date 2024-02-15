import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const envApi = createApi({
    reducerPath: 'envApi',
    baseQuery: fetchBaseQuery({
        baseUrl: 'api/meta/env'
    }),
    endpoints: (builder) => ({
        getServerActiveProfile: builder.query<{ profile: string }, void>({
            query: () => ({
                url: 'profile'
            })
        })
    })
});

export const { useGetServerActiveProfileQuery } = envApi;
