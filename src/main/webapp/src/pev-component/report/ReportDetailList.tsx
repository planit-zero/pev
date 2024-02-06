import * as React from 'react';
import { useGetReportDetailListQuery } from '../../pev-service/ReportService';
import { IReport } from '../../pev-interface/IChart';
import { Box, Grid, Typography } from '@mui/material';
import ReportDetail from './ReportDetail';
import dayjs from 'dayjs';

type ReportDetailListProps = {
    report: IReport;
};

const ReportDetailList = (props: ReportDetailListProps) => {
    const { data: reportDetailList } = useGetReportDetailListQuery(props.report.reportId);

    return (
        <Box sx={{ p: '20px' }}>
            <Grid container sx={{ width: '100%', height: '100%', p: 2 }}>
                <Grid item xs={1} sx={{ textAlign: 'center' }}>
                    <Typography sx={{ fontWeight: 'bold' }}>순번</Typography>
                </Grid>
                <Grid item xs={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>신고내용</Typography>
                </Grid>
                <Grid item xs={1} sx={{ textAlign: 'center' }}>
                    <Typography sx={{ fontWeight: 'bold' }}>처리여부</Typography>
                </Grid>
                <Grid item xs={4}>
                    <Typography sx={{ fontWeight: 'bold' }}>처리내용</Typography>
                </Grid>
                <Grid item xs={2} sx={{ textAlign: 'center' }}>
                    <Typography sx={{ fontWeight: 'bold' }}>처리일시</Typography>
                </Grid>
            </Grid>
            {reportDetailList &&
                reportDetailList.map((detail, idx) => {
                    return <ReportDetail key={`${dayjs}-${idx}`} reportId={props.report.reportId} detail={detail} />;
                })}
        </Box>
    );
};

export default ReportDetailList;
