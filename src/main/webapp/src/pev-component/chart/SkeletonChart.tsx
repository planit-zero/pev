import * as React from 'react';
import { Box, Skeleton } from '@mui/material';

type SkeletonChartProps = {};

const SkeletonChart = (props: SkeletonChartProps) => {
    return (
        <Box display={'flex'} flexDirection={'column'} gap={2}>
            <Box>
                <Skeleton variant={'text'} width={'50%'} height={40} />
            </Box>
            <Box>
                <Skeleton variant={'text'} width={'30%'} height={30} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
            </Box>
            <Box>
                <Skeleton variant={'text'} width={'30%'} height={30} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
                <Skeleton variant={'text'} width={'100%'} height={20} />
            </Box>
            <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                <Skeleton variant={'text'} width={'30%'} height={30} />
            </Box>
        </Box>
    );
};

export default SkeletonChart;
