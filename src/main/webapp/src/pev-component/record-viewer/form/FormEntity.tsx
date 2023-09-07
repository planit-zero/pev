import * as React from 'react';
import { IFormEntity } from '../../../pev-interface/IForm';
import { Box, SxProps, Typography } from '@mui/material';
import FormValue from './FormValue';
import { Theme } from '@mui/system';

type FormEntityProps = {
    entity: IFormEntity;
};

const FormEntity = (props: FormEntityProps) => {
    let boxSx: SxProps<Theme> = { mb: 2 };
    if (props.entity.inline) boxSx = { mb: 2, display: 'flex', alignItems: 'center', gap: 1 };

    return (
        <Box sx={boxSx}>
            <Typography
                sx={{
                    fontSize: `h4.fontSize`,
                    fontWeight: 'bold',
                    color: '#4cbded',
                    whiteSpace: 'pre-line',
                    wordBreak: 'break-all'
                }}
            >
                {props.entity.value}
            </Typography>
            {props.entity.values.map((value, idx) => {
                return <FormValue key={idx} value={value} inline={props.entity.inline} />;
            })}
        </Box>
    );
};

export default FormEntity;
