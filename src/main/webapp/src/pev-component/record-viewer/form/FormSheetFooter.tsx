import * as React from 'react';
import { IFormSection } from '../../../pev-interface/IForm';
import { Box } from '@mui/material';

type FormSheetFooterProps = {
    section: IFormSection;
};

const FormSheetFooter = (props: FormSheetFooterProps) => {
    return (
        <Box sx={{ mb: 2, fontSize: 'h4.fontSize', textAlign: 'right' }}>
            {props.section.entities.map((entity, eIdx) => {
                return (
                    <Box sx={{ display: 'flex', gap: 1, justifyContent: 'flex-end' }}>
                        <span key={eIdx} className={'record-footer writer'}>
                            {entity.value}
                        </span>
                        {entity.values.map((value, vIdx) => {
                            return <span key={vIdx}>{value.value}</span>;
                        })}
                    </Box>
                );
            })}
        </Box>
    );
};

export default FormSheetFooter;
