import * as React from 'react';
import { IRecordEntity } from '../../pev-interface/IRecord';
import { Box, Typography } from '@mui/material';
import { TRecordSection } from '../../pev-type/TRecordSection';
import RecordValue from './RecordValue';
import RecordAttribute from './RecordAttribute';

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
            {props.entity.attributes &&
                props.entity.attributes.map((attribute, idx) => {
                    return (
                        <React.Fragment>
                            <RecordAttribute key={idx} sectionType={props.sectionType} attribute={attribute} />
                            {!props.entity.isInline && <br />}
                        </React.Fragment>
                    );
                })}
            {props.entity.values &&
                props.entity.values.map((value, idx) => {
                    return <RecordValue key={idx} sectionType={props.sectionType} isInline={props.entity.isInline} value={value} />;
                })}
        </Box>
    );
};

export default RecordEntity;
