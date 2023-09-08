import * as React from 'react';
import { useSelector } from '../../store';
import { Backdrop, Box, CircularProgress } from '@mui/material';
import { useGetFormContentMutation } from '../../pev-service/FormService';
import { IFormContentP } from '../../pev-interface/IForm';
import FormSheet from './form/FormSheet';

type RecordViewerProps = {};

const RecordViewer = (props: RecordViewerProps) => {
    const { targetRecords } = useSelector((state) => state.record);

    const [getFormContent, { data: formContent, isLoading }] = useGetFormContentMutation();

    React.useEffect(() => {
        const payload: IFormContentP = {
            identifiers: targetRecords.map((record) => {
                return {
                    recordType: record.recordType,
                    recordDetailType: record.recordDetailType,
                    keyId: record.keyId
                };
            })
        };

        getFormContent(payload);
    }, [targetRecords]);

    return (
        <React.Fragment>
            <Box display={'flex'} flexDirection={'column'} alignItems={'center'}>
                {formContent &&
                    formContent.sheets.map((sheet, idx) => {
                        return <FormSheet key={idx} sheet={sheet} />;
                    })}
            </Box>
            <Backdrop open={isLoading} sx={{ color: (theme) => theme.palette.primary.main, zIndex: (theme) => theme.zIndex.modal + 1 }}>
                <CircularProgress color={'inherit'} />
            </Backdrop>
        </React.Fragment>
    );
};

export default RecordViewer;
