import * as React from 'react';
import { Box } from '@mui/material';
import ReportContentHeader from './ReportContentHeader';
import ReportContentList from './ReportContentList';
import { ReportContentType } from '../../pev-interface/IChart';
import { ContentTypeProps } from './Report';

type ReportContentProps = {
    contentType: ContentTypeProps;
    menuText: string;
    menuGroupText: string;
    dataSource: ReportContentType;
};

const ReportContent = (props: ReportContentProps) => {
    return (
        <Box sx={{ width: '100%', height: '100%' }}>
            <ReportContentHeader menuText={props.menuText} menuGroupText={props.menuGroupText} count={props.dataSource.length} />
            <ReportContentList contentType={props.contentType} dataSource={props.dataSource} />
        </Box>
    );
};

export default ReportContent;
