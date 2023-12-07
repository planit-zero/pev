import * as React from 'react';
import { useSelector } from '../../store';
import ChartWrapper from './ChartWrapper';
import ChartToolbar from './ChartToolbar';
import { Paper } from '@mui/material';

type ChartContainerProps = {};

const ChartContainer = (props: ChartContainerProps) => {
    const { targetRecords } = useSelector((state) => state.record);

    return (
        <React.Fragment>
            {targetRecords.map((targetRecord, idx) => {
                return (
                    <React.Fragment>
                        <Paper sx={{ minWidth: 600, p: 2, mb: 2, borderRadius: 0 }}>
                            <ChartToolbar targetRecord={targetRecord} />
                            <ChartWrapper key={idx} maskingYn={'Y'} targetRecord={targetRecord} />
                        </Paper>
                    </React.Fragment>
                );
            })}
        </React.Fragment>
    );
};

export default React.memo(ChartContainer);
