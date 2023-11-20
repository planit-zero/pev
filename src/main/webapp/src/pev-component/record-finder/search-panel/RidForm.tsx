import * as React from 'react';
import { IconButton, InputAdornment, OutlinedInput } from '@mui/material';
import { IconSearch } from '@tabler/icons';
import { styled } from '@mui/material/styles';
import { shouldForwardProp } from '@mui/system';

type RidFormProps = {
    irb: string | null;
    rid: string;
    onChange: (value: string) => void;
    onSubmit: (irb: string | null, rid: string | null) => void;
};

const OutlineInputStyle = styled(OutlinedInput, { shouldForwardProp })(() => ({
    width: '100%',
    height: '100%',
    paddingLeft: 8,
    paddingRight: 8,
    '& input': {
        background: 'transparent !important',
        paddingLeft: '4px !important',
        paddingRight: '4px !important'
    }
}));

const RidForm = (props: RidFormProps) => {
    const handleRidSearch = (e: any) => {
        if (e.type === 'click' || (e.type === 'keydown' && e.code === 'Enter')) {
            props.onSubmit(props.irb, props.rid);
        }
    };

    return (
        <OutlineInputStyle
            id="input-search-header"
            value={props.rid}
            onChange={(e) => props.onChange(e.target.value)}
            disabled={!props.irb}
            placeholder="연구별 환자 ID"
            endAdornment={
                <InputAdornment position="end">
                    <IconButton sx={{ width: 32, height: 32 }} onClick={handleRidSearch} disabled={!props.irb}>
                        <IconSearch />
                    </IconButton>
                </InputAdornment>
            }
            aria-describedby="search-helper-text"
            inputProps={{ 'aria-label': 'weight', onKeyDown: handleRidSearch }}
        />
    );
};

export default RidForm;
