import * as React from 'react';
import { Box } from '@mui/material';
import ReportContentHeader from './ReportContentHeader';
import ReportContentGrid from './ReportContentGrid';
import { ReportContentType } from '../../pev-interface/IChart';
import { ContentTypeProps } from './Report';
import ReportContentStatistics from './ReportContentStatistics';

type ReportContentProps = {
    contentType: ContentTypeProps;
    menuText: string;
    menuGroupText: string;
    dataSource: ReportContentType;
    menuIndex: number;
};

const ReportContent = (props: ReportContentProps) => {
    const isLogPage = (): boolean => {
        return props.menuIndex === 7 || props.menuIndex === 8;
    };

    return (
        <Box sx={{ width: '100%', height: '100%' }}>
            <ReportContentHeader menuText={props.menuText} menuGroupText={props.menuGroupText} count={props.dataSource.length} />
            <Box height={'100%'} sx={{ display: 'flex', overflow: 'hidden', overflowY: 'scroll' }}>
                <ReportContentGrid contentType={props.contentType} dataSource={props.dataSource} isLogPage={isLogPage()} />
                {isLogPage() && <ReportContentStatistics menuIndex={props.menuIndex} />}
            </Box>
        </Box>
    );
};

export default ReportContent;
