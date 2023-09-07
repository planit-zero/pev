import * as React from 'react';
import { IFormSection } from '../../../pev-interface/IForm';
import { Box } from '@mui/material';

type FormHeaderSectionProps = {
    section: IFormSection;
};

const FormSheetHeader = (props: FormHeaderSectionProps) => {
    return (
        <Box sx={{ mb: 2, fontSize: 'h4.fontSize' }}>
            {props.section.entities.map((entity, eIdx) => {
                return (
                    <Box sx={{ display: 'flex', gap: 1 }}>
                        <span key={eIdx} className={'record-header normal'}>
                            {entity.value}
                        </span>
                        {entity.values.map((value, vIdx) => {
                            return (
                                <span key={vIdx} className={'record-header normal'}>
                                    {value.value}
                                </span>
                            );
                        })}
                    </Box>
                );
            })}
        </Box>
    );
};

export default FormSheetHeader;
