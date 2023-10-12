import * as React from 'react';
import { IRecordEntity } from '../../pev-interface/IRecord';
import { Box, Typography } from '@mui/material';
import { TRecordSection } from '../../pev-type/TRecordSection';
import RecordValue from './RecordValue';
import RecordAttribute from './RecordAttribute';
import { TRecordElementDisplay } from '../../pev-type/TRecordElement';

type RecordEntityProps = {
    sectionType: string;
    entity: IRecordEntity;
};

const RecordEntity = (props: RecordEntityProps) => {
    const textAlign = props.entity.alignment || 'left';

    return (
        <Box sx={{ textAlign: textAlign, display: props.entity.display }}>
            <Typography
                sx={{
                    fontSize: `h4.fontSize`,
                    fontWeight: 'bold',
                    color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : '#4cbded',
                    whiteSpace: 'pre-line',
                    wordBreak: 'break-all',
                    mr: props.entity.display === TRecordElementDisplay.INLINE ? 1 : 0,
                    textDecoration: props.entity.textDecoration
                }}
                display={props.entity.display}
            >
                {props.entity.text}
            </Typography>
            {props.entity.attributes &&
                props.entity.attributes.map((attribute, idx) => {
                    return (
                        <React.Fragment>
                            <RecordAttribute key={idx} sectionType={props.sectionType} attribute={attribute} />
                        </React.Fragment>
                    );
                })}
            {props.entity.values &&
                props.entity.values.map((value, idx) => {
                    return <RecordValue key={idx} sectionType={props.sectionType} display={props.entity.display} value={value} />;
                })}
        </Box>
    );
};

export default RecordEntity;
