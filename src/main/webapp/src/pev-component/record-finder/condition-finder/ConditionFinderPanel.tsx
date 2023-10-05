import * as React from 'react';
import { Box, Divider, Paper, Typography } from '@mui/material';
import { Bookmark, Square } from '@mui/icons-material';
import ConditionFinderPanelDateSetter from './ConditionFinderPanelDateSetter';
import ConditionFinderPanelDepartmentSetter from './ConditionFinderPanelDepartmentSetter';
import ConditionFinderPanelPatientTypeSetter from './ConditionFinderPanelPatientTypeSetter';
import ConditionFinderPanelWriterSetter from './ConditionFinderPanelWriterSetter';
import ConditionFinderPanelRecordSetter from './ConditionFinderPanelRecordSetter';
import { IDateCondition, IDeptCondition, ISearchCondition, IWriterCondition } from '../../../pev-interface/IRecord';

type ConditionFinderPanelProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (key: string, value: string | null) => void;
};

const ConditionFinderPanel = (props: ConditionFinderPanelProps) => {
    return (
        <Box>
            <Box display={'flex'} alignItems={'center'}>
                <Bookmark color={'primary'} fontSize={'small'} />
                <Typography variant={'body1'}>조회조건</Typography>
            </Box>
            <Divider sx={{ mt: 1 }} />
            <Paper sx={{ mt: 1, p: 1, height: 'calc(100% - 37px)', borderRadius: 0 }} elevation={1}>
                <ConditionFinderPanelDateSetter
                    searchCondition={props.searchCondition}
                    onSearchConditionChange={props.onSearchConditionChange}
                />
                <ConditionFinderPanelPatientTypeSetter
                    searchCondition={props.searchCondition}
                    onSearchConditionChange={props.onSearchConditionChange}
                />
                <ConditionFinderPanelDepartmentSetter
                    searchCondition={props.searchCondition}
                    onSearchConditionChange={props.onSearchConditionChange}
                />
                <ConditionFinderPanelWriterSetter
                    searchCondition={props.searchCondition}
                    onSearchConditionChange={props.onSearchConditionChange}
                />
                <ConditionFinderPanelRecordSetter />
            </Paper>
        </Box>
    );
};

export default ConditionFinderPanel;
