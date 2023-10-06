import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import { Paper } from '@mui/material';
import { useGetRecordSheetMutation } from '../../pev-service/RecordService';
import RecordSection from './RecordSection';
import { TRecordSection } from '../../pev-type/TRecordSection';

type RecordSheetProps = {
    targetRecord: IRecord;
};

const RecordSheet = (props: RecordSheetProps) => {
    const [getRecordSheet, { data: recordSheet, isLoading: isRecordSheetLoading }] = useGetRecordSheetMutation();

    React.useEffect(() => {
        getRecordSheet(props.targetRecord);
    }, []);

    return (
        <Paper sx={{ width: 600, p: 2, mb: 2, borderRadius: 0, display: 'flex', flexDirection: 'column', gap: 3 }}>
            {recordSheet && (
                <React.Fragment>
                    {recordSheet.headerSection && <RecordSection type={TRecordSection.HEADER} section={recordSheet.headerSection} />}
                    {recordSheet.sections.map((section, idx) => {
                        return <RecordSection key={idx} type={TRecordSection.BODY} section={section} />;
                    })}
                </React.Fragment>
            )}
        </Paper>
    );
};

export default RecordSheet;
