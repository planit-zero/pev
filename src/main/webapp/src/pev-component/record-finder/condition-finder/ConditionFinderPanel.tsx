import * as React from 'react';
import { Box, Button, Divider, Paper, Typography } from '@mui/material';
import { Bookmark } from '@mui/icons-material';
import ConditionFinderPanelDateSetter from './ConditionFinderPanelDateSetter';
import ConditionFinderPanelDepartmentSetter from './ConditionFinderPanelDepartmentSetter';
import ConditionFinderPanelPatientTypeSetter from './ConditionFinderPanelPatientTypeSetter';
import ConditionFinderPanelRecordSetter from './ConditionFinderPanelRecordSetter';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import SearchIcon from '@mui/icons-material/Search';

type ConditionFinderPanelProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
    onListSearch: () => void;
};

const ConditionFinderPanel = (props: ConditionFinderPanelProps) => {
    return (
        <Box width={'100%'} height={247}>
            <Box display={'flex'} alignItems={'center'}>
                <Bookmark color={'primary'} fontSize={'small'} />
                <Typography variant={'body1'}>조회조건</Typography>
            </Box>
            <Divider sx={{ mt: 1 }} />
            <Paper
                sx={{
                    mt: 1,
                    p: 0,
                    height: '210px',
                    borderRadius: 0,
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'space-between'
                }}
                elevation={0}
            >
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
                {/*<ConditionFinderPanelWriterSetter*/}
                {/*    searchCondition={props.searchCondition}*/}
                {/*    onSearchConditionChange={props.onSearchConditionChange}*/}
                {/*/>*/}
                <ConditionFinderPanelRecordSetter
                    searchCondition={props.searchCondition}
                    onSearchConditionChange={props.onSearchConditionChange}
                />
                <Box display={'flex'} justifyContent={'flex-end'} alignItems={'center'}>
                    <Button variant={'contained'} size={'small'} startIcon={<SearchIcon fontSize="small" />} onClick={props.onListSearch}>
                        목록 조회
                    </Button>
                </Box>
            </Paper>
        </Box>
    );
};

export default ConditionFinderPanel;
