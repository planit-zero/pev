import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IUser } from '../pev-interface/IUser';

export const userApi = createApi({
    reducerPath: 'userApi',
    baseQuery: fetchBaseQuery({
        baseUrl: 'api/user'
    }),
    endpoints: (builder) => ({
        getIdpLoginUser: builder.mutation<IUser, string | null>({
            query: (token) => ({
                url: token ? `?token=${token}` : '',
                method: 'GET'
            })
        }),
        signOut: builder.mutation<void, void>({
            query: () => ({
                url: 'sign-out',
                method: 'POST'
            })
        }),
        getTokenInSession: builder.mutation<{ token: string }, void>({
            query: () => ({
                url: 'token',
                method: 'GET'
            })
        })
    })
});

export const { useGetIdpLoginUserMutation, useSignOutMutation, useGetTokenInSessionMutation } = userApi;
