import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import { Paper } from '@mui/material';
import { useGetRecordSheetMutation } from '../../pev-service/RecordService';
import RecordSection from './RecordSection';

type RecordSheetProps = {
    targetRecord: IRecord;
};

const RecordSheet = (props: RecordSheetProps) => {
    const [getRecordSheet, { data: recordSheet, isLoading: isRecordSheetLoading }] = useGetRecordSheetMutation();

    React.useEffect(() => {
        getRecordSheet(props.targetRecord);
    }, []);

    return (
        <Paper sx={{ width: 600, p: 2, mb: 2, borderRadius: 0 }}>
            {recordSheet &&
                recordSheet.sections.map((section, idx) => {
                    return <RecordSection key={idx} section={section} />;
                })}
        </Paper>
    );
};

export default RecordSheet;
