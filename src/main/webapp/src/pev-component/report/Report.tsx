import * as React from 'react';
import { Box } from '@mui/material';
import CommonLayout from '../common/CommonLayout';
import { useGetChartErrorListQuery, useGetLogAllListQuery, useGetReportListQuery } from '../../pev-service/ReportService';
import ReportContent from './ReportContent';
import ReportMenu from './ReportMenu';

export type ContentTypeProps = 'masking' | 'chart' | 'log' | 'statistics' | '';

const Report = () => {
    const { data: reportList } = useGetReportListQuery();
    const { data: chartErrorList } = useGetChartErrorListQuery();
    const { data: logAllList } = useGetLogAllListQuery();

    const [menuIndex, setMenuIndex] = React.useState<number>(0);

    const getDataByMenu = () => {
        if (!reportList || typeof reportList === 'undefined' || !chartErrorList || typeof chartErrorList === 'undefined') return [];
        if (menuIndex === 0) return reportList;
        if (menuIndex === 1) return reportList.filter((r) => r.processCount > 0 && r.allCount === r.processCount);
        if (menuIndex === 2) return reportList.filter((r) => r.processCount > 0 && r.allCount > r.processCount);
        if (menuIndex === 3) return reportList.filter((r) => r.processCount === 0);
        if (menuIndex === 4) return chartErrorList;
        if (menuIndex === 5) return chartErrorList.filter((c) => c.processYn === 'Y');
        if (menuIndex === 6) return chartErrorList.filter((c) => c.processYn === 'N');
        if (menuIndex === 7 || menuIndex === 8) return logAllList;
    };

    const getMenuGroupText = () => {
        if (menuIndex === 0 || menuIndex === 1 || menuIndex === 2 || menuIndex === 3) return `가명처리 오류신고`;
        if (menuIndex === 4 || menuIndex === 5 || menuIndex === 6) return `기록지 오류신고`;
        if (menuIndex === 7 || menuIndex === 8) return `사용자 접속`;
        return ``;
    };

    const getMenuText = () => {
        if (menuIndex === 0 || menuIndex === 4) return `모든 신고내역`;
        if (menuIndex === 1 || menuIndex === 5) return `처리된 신고내역`;
        if (menuIndex === 2) return `처리 중인 신고내역`;
        if (menuIndex === 3 || menuIndex === 6) return `처리되지 않은 신고내역`;
        if (menuIndex === 7) return `로그`;
        if (menuIndex === 8) return `통계`;
        return ``;
    };

    const getContentType = (): ContentTypeProps => {
        if (menuIndex === 0 || menuIndex === 1 || menuIndex === 2 || menuIndex === 3) return `masking`;
        if (menuIndex === 4 || menuIndex === 5 || menuIndex === 6) return `chart`;
        if (menuIndex === 7) return `log`;
        if (menuIndex === 8) return `statistics`;
        return ``;
    };

    return (
        <Box sx={{ width: '100vw', height: '100vh', overflow: 'hidden' }}>
            <CommonLayout />
            <Box
                display={'flex'}
                justifyContent={'space-between'}
                gap={2}
                sx={{
                    mt: '48px',
                    ml: '48px',
                    p: '20px',
                    width: 'calc(100% - 48px)',
                    height: 'calc(100% - 48px)',
                    backgroundColor: '#eef2f6'
                }}
            >
                <ReportMenu menuIndex={menuIndex} setMenuIndex={setMenuIndex} />
                <ReportContent
                    contentType={getContentType()}
                    menuText={getMenuText()}
                    menuGroupText={getMenuGroupText()}
                    dataSource={getDataByMenu() || []}
                />
            </Box>
        </Box>
    );
};

export default Report;
