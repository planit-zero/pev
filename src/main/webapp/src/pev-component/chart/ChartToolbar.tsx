import * as React from 'react';
import { Box, Button, Modal } from '@mui/material';
import { IRecord } from '../../pev-interface/IRecord';
import RecordComparator from '../record-comparator/RecordComparator';

type ChartToolbarProps = {
    targetRecord: IRecord;
};

const ChartToolbar = (props: ChartToolbarProps) => {
    const [open, setOpen] = React.useState<boolean>(false);

    return (
        <Box sx={{ width: '100%', display: 'flex', justifyContent: 'space-between', mb: 2 }}>
            <Button variant={'contained'} color={'primary'} size={'small'} onClick={() => setOpen(true)}>
                원본 대조
            </Button>
            <Button variant={'contained'} color={'error'} size={'small'}>
                비식별화 처리 미비 신고
            </Button>
            <Modal open={open} onClose={() => setOpen(false)}>
                <RecordComparator targetRecord={props.targetRecord} />
            </Modal>
        </Box>
    );
};

export default ChartToolbar;
