import * as React from 'react';
import { IFormValue } from '../../../pev-interface/IForm';
import { Typography } from '@mui/material';

type FormValueProps = {
    value: IFormValue;
    inline: boolean;
};

const FormValue = (props: FormValueProps) => {
    return (
        <Typography sx={{ fontSize: props.inline ? 'h4.fontSize' : 'h5.fontSize', whiteSpace: 'pre-line', wordBreak: 'break-all' }}>
            {props.value.value}
        </Typography>
    );
};

export default FormValue;
