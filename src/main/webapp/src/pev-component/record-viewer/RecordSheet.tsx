import * as React from 'react';
import { IRecordDataR } from '../../pev-interface/IRecordDataR';
import { Box, Paper } from '@mui/material';
import { useGetRecordFormInfoMutation } from '../../pev-service/RecordService';
import RecordFormBasic from './record-form-basic/RecordFormBasic';
import RecordSection from './RecordSection';

type RecordSheetProps = {
    data: IRecordDataR;
};

const RecordSheet = (props: RecordSheetProps) => {
    const [getRecordFormInfo, { data: recordFormInfo }] = useGetRecordFormInfoMutation();

    React.useEffect(() => {
        getRecordFormInfo({
            mdrcId: props.data.mdrcId,
            mdrcFomSeq: props.data.mdrcFomSeq
        });
    }, []);

    const isWritingDateTimeExists = (mdfmClsCd: string): boolean => {
        const targets: string[] = ['D003', 'D004', 'D005', 'D006', 'D031'];
        return targets.includes(mdfmClsCd);
    };

    return (
        <React.Fragment>
            {recordFormInfo && (
                <Paper sx={{ width: 'fit-content', p: 2, mb: 2, borderRadius: 0 }}>
                    <React.Fragment>
                        <Box sx={{ fontSize: 'h4.fontSize' }}>
                            {/* Header */}
                            <span className={`record-header normal`}>{recordFormInfo.itemType}</span>
                            <span className={`record-header date`}>&nbsp;({recordFormInfo.writingDate})</span>
                            <br />
                            <span className={`record-header normal`}>작성과: {recordFormInfo.writingDeptNm}</span>
                            <br />
                            <span className={`record-header normal`}>수진과: {recordFormInfo.medDeptNm}</span>
                        </Box>
                        <Box sx={{ mt: 2, mb: 8 }}>
                            {/* Body */}
                            <RecordFormBasic recordFormInfo={recordFormInfo} sections={props.data.sections} />
                        </Box>
                        <Box sx={{ fontSize: 'h4.fontSize', textAlign: 'right' }}>
                            <span className={`record-footer writer`}>작성자</span>
                            <span>&nbsp;{recordFormInfo.writerNm}</span>
                        </Box>
                        {isWritingDateTimeExists(recordFormInfo.mdfmClsCd) && (
                            <Box sx={{ fontSize: 'h4.fontSize', textAlign: 'right' }}>
                                <span className={`record-footer writer`}>작성시간</span>
                                <span>&nbsp;{recordFormInfo.writingDateTime}</span>
                            </Box>
                        )}
                    </React.Fragment>
                    {/*{props.data.sections.map((section, idx) => {*/}
                    {/*    return <RecordSection key={idx} section={section} />;*/}
                    {/*})}*/}
                </Paper>
            )}
        </React.Fragment>
    );
};

export default RecordSheet;
