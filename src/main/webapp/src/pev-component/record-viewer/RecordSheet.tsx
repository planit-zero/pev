import * as React from 'react';
import { IRecordDataR } from '../../pev-interface/IRecordDataR';
import { Paper } from '@mui/material';

type RecordSheetProps = {
    data: IRecordDataR;
};

const RecordSheet = (props: RecordSheetProps) => {
    return (
        <React.Fragment>
            <Paper sx={{ width: 'fit-content', p: 2, mb: 2, borderRadius: 0 }}>
                ABC
            </Paper>
        </React.Fragment>
    );
};

export default RecordSheet;
