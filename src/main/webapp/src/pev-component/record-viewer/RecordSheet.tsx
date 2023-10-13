import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import { Box, Paper, Skeleton } from '@mui/material';
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
    }, [props.targetRecord]);

    return (
        <Paper sx={{ width: 600, p: 2, mb: 2, borderRadius: 0 }}>
            {!isRecordSheetLoading && recordSheet && (
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
                    <React.Fragment>
                        {recordSheet.headerSection && <RecordSection type={TRecordSection.HEADER} section={recordSheet.headerSection} />}
                        {recordSheet.sections.map((section, idx) => {
                            return <RecordSection key={idx} type={TRecordSection.BODY} section={section} />;
                        })}
                    </React.Fragment>
                </Box>
            )}
            {isRecordSheetLoading && (
                <Box display={'flex'} flexDirection={'column'} gap={2}>
                    <Box>
                        <Skeleton variant={'text'} width={'50%'} height={40} />
                    </Box>
                    <Box>
                        <Skeleton variant={'text'} width={'30%'} height={30} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                    </Box>
                    <Box>
                        <Skeleton variant={'text'} width={'30%'} height={30} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                        <Skeleton variant={'text'} width={'100%'} height={20} />
                    </Box>
                    <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                        <Skeleton variant={'text'} width={'30%'} height={30} />
                    </Box>
                </Box>
            )}
        </Paper>
    );
};

export default RecordSheet;
