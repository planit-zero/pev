import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IFormContentP, IFormContentR } from '../pev-interface/IForm';

export const formApi = createApi({
    reducerPath: 'formApi',
    baseQuery: fetchBaseQuery({
        baseUrl: `/api/form`
    }),
    endpoints: (builder) => ({
        getFormContent: builder.mutation<IFormContentR, IFormContentP>({
            query: (payload) => ({
                url: 'content',
                method: 'POST',
                body: payload
            })
        })
    })
});

export const { useGetFormContentMutation } = formApi;
