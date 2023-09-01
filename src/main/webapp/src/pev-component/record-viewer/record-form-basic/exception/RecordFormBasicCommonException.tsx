import * as React from 'react';
import { IRecordFormInfoR } from '../../../../pev-interface/IRecordInfo';
import RecordFormBasicSurgeryException from './RecordFormBasicSurgeryException';

type RecordFormBasicCommonExceptionProps = {
    recordFormInfo: IRecordFormInfoR;
};

const RecordFormBasicCommonException = (props: RecordFormBasicCommonExceptionProps) => {
    if (props.recordFormInfo.mdfmClsCd === 'D005') return <RecordFormBasicSurgeryException recordFormInfo={props.recordFormInfo} />;
    return null;
};

export default RecordFormBasicCommonException;
