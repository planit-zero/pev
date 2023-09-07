import * as React from 'react';
import { IFormSection } from '../../../pev-interface/IForm';
import { Box } from '@mui/material';
import FormEntity from './FormEntity';

type FormSectionProps = {
    section: IFormSection;
};

const FormSection = (props: FormSectionProps) => {
    return (
        <Box>
            {props.section.entities.map((entity, idx) => {
                return <FormEntity key={idx} entity={entity} />;
            })}
        </Box>
    );
};

export default FormSection;
