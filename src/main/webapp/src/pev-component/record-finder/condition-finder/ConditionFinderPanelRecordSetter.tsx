import * as React from 'react';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import { Box, Button, Grid, Modal, Typography } from '@mui/material';
import { Square } from '@mui/icons-material';
import RecordSelector from '../../record-selector/RecordSelector';

type ConditionFinderPanelRecordSetterProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
};

const ConditionFinderPanelRecordSetter = (props: ConditionFinderPanelRecordSetterProps) => {
    const [open, setOpen] = React.useState<boolean>(false);

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
                    <Modal open={open} onClose={() => setOpen(false)}>
                        <RecordSelector searchCondition={props.searchCondition} onSearchConditionChange={props.onSearchConditionChange} />
                    </Modal>
                </Box>
            </Grid>
        </Grid>
    );
};

export default ConditionFinderPanelRecordSetter;
