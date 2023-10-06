import * as React from 'react';
import { IRecordEntity } from '../../pev-interface/IRecord';
import { Box, Typography } from '@mui/material';
import { TRecordSection } from '../../pev-type/TRecordSection';

type RecordEntityProps = {
    sectionType: string;
    entity: IRecordEntity;
};

const RecordEntity = (props: RecordEntityProps) => {
    const textAlign = props.entity.alignment || 'left';

    return (
        <Box sx={{ textAlign: textAlign }}>
            <Typography
                sx={{
                    fontSize: `h4.fontSize`,
                    fontWeight: 'bold',
                    color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : '#4cbded',
                    whiteSpace: 'pre-line',
                    wordBreak: 'break-all',
                    mr: props.entity.isInline ? 1 : 0
                }}
                display={props.entity.isInline ? 'inline' : 'block'}
            >
                {props.entity.text}
            </Typography>
            {props.entity.values.map((value, idx) => {
                return (
                    <Typography
                        key={idx}
                        sx={{
                            fontSize: props.sectionType === TRecordSection.HEADER ? 'h4.fontSize' : 'h5.fontSize',
                            color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : 'inherit',
                            whiteSpace: 'pre-line',
                            wordBreak: 'break-all'
                        }}
                        display={props.entity.isInline ? 'inline' : 'block'}
                    >
                        {value.text}
                    </Typography>
                );
            })}
        </Box>
    );
};

export default RecordEntity;
