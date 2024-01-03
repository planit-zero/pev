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
        })
    })
});

export const { useGetIdpLoginUserMutation, useSignOutMutation } = userApi;
