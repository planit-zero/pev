import * as React from 'react';
import { IFormSheet } from '../../../pev-interface/IForm';
import { Paper } from '@mui/material';
import FormSection from './FormSection';
import FormSheetHeader from './FormSheetHeader';
import FormSheetFooter from './FormSheetFooter';

type FormSheetProps = {
    sheet: IFormSheet;
};

const FormSheet = (props: FormSheetProps) => {
    return (
        <Paper sx={{ width: '80%', p: 2, mb: 2, borderRadius: 0 }}>
            {props.sheet.sections.map((section, idx) => {
                if (section.mdfmSctnSeq === -99) return <FormSheetHeader key={idx} section={section} />;
                if (section.mdfmSctnSeq === 99) return <FormSheetFooter key={idx} section={section} />;
                return <FormSection key={idx} section={section} />;
            })}
        </Paper>
    );
};

export default FormSheet;
