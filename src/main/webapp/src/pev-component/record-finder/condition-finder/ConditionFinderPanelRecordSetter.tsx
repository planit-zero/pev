import * as React from 'react';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import { Box, Button, Grid, Modal, Tooltip, Typography } from '@mui/material';
import { Square } from '@mui/icons-material';
import RecordSelector from '../../record-selector/RecordSelector';
import { useGetMetaRecordListQuery } from '../../../pev-service/MetaRecordService';

type ConditionFinderPanelRecordSetterProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
};

const ConditionFinderPanelRecordSetter = (props: ConditionFinderPanelRecordSetterProps) => {
    const [open, setOpen] = React.useState<boolean>(false);

    const { data: metaRecordList } = useGetMetaRecordListQuery();

    const getSelectedRecordsAsString = (records: string[], mode: string) => {
        if (!metaRecordList) return '';

        const recordNames = records.map((r) => {
            return metaRecordList.metaRecords.find((mr) => r === mr.id)?.name || '알 수 없음';
        });

        if (mode === 'tooltip') {
            return recordNames.join('\r\n');
        }

        if (mode === 'display' && recordNames.length > 5) {
            return `${recordNames.slice(0, 4).join(', ')} 포함 ${recordNames.length} 건`;
        }

        return recordNames.join(', ');
    };

    return (
        <Grid container display={'flex'} alignItems={'center'}>
            <Grid item xs={2} display={'flex'} alignItems={'center'}>
                <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                <Typography variant={'body1'}>기록유형</Typography>
            </Grid>
            <Grid item xs={10} display={'flex'} alignItems={'center'}>
                <Box sx={{ width: '100%' }}>
                    <Button variant={'outlined'} size={'small'} onClick={() => setOpen(true)}>
                        기록유형 선택
                    </Button>
                    <Tooltip
                        title={
                            <span style={{ whiteSpace: 'pre-line' }}>
                                {getSelectedRecordsAsString(props.searchCondition.searchTargets, 'tooltip')}
                            </span>
                        }
                        placement={'bottom-end'}
                    >
                        <Typography display={'inline'} sx={{ ml: 1 }}>
                            {getSelectedRecordsAsString(props.searchCondition.searchTargets, 'display')}
                        </Typography>
                    </Tooltip>
                    {metaRecordList && (
                        <Modal open={open} onClose={() => setOpen(false)}>
                            <RecordSelector
                                metaRecordList={metaRecordList}
                                searchCondition={props.searchCondition}
                                onSearchConditionChange={props.onSearchConditionChange}
                            />
                        </Modal>
                    )}
                </Box>
            </Grid>
        </Grid>
    );
};

export default ConditionFinderPanelRecordSetter;
