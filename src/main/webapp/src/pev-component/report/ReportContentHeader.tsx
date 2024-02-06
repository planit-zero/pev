import * as React from 'react';
import { Box, Paper, Typography } from '@mui/material';

type ReportContentHeaderProps = {
    menuText: string;
    menuGroupText: string;
    count: number;
};

const ReportContentHeader = (props: ReportContentHeaderProps) => {
    return (
        <Paper sx={{ width: '100%', height: '60px', mb: '15px' }}>
            <Box display={'flex'} justifyContent={'space-between'} alignItems={'center'} sx={{ width: '100%', height: '100%', px: '20px' }}>
                <Typography sx={{ fontSize: 'h4.fontSize', fontWeight: 'bold' }}>
                    {props.menuGroupText} / {props.menuText}
                </Typography>
                <Typography sx={{ fontSize: 'h5.fontSize' }}>
                    총 {props.count} 건의 {props.menuText}이 있습니다.
                </Typography>
            </Box>
        </Paper>
    );
};

export default ReportContentHeader;
