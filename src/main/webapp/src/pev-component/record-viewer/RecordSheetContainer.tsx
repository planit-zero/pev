import * as React from 'react';
import { useSelector } from '../../store';
import RecordSheet from './RecordSheet';
import dayjs from 'dayjs';

const RecordSheetContainer = () => {
    const { targetRecords } = useSelector((state) => state.record);

    return (
        <React.Fragment>
            {targetRecords.map((target, idx) => {
                return <RecordSheet key={`${dayjs()}-${idx}`} targetRecord={target} />;
            })}
        </React.Fragment>
    );
};

export default React.memo(RecordSheetContainer);
