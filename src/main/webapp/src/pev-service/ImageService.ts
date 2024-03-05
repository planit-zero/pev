import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { IImage } from '../pev-interface/IImage';

export const imageApi = createApi({
    reducerPath: 'imageApi',
    baseQuery: fetchBaseQuery({
        baseUrl: '/api/image'
    }),
    endpoints: (builder) => ({
        getMaskedImage: builder.mutation<IImage, IImage>({
            query: (payload) => ({
                url: ``,
                method: 'POST',
                body: payload
            })
        })
    })
});

export const { useGetMaskedImageMutation } = imageApi;
