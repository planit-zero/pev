import * as React from 'react';
import { IconButton, InputAdornment, Modal, OutlinedInput } from '@mui/material';
import { IconList, IconSearch } from '@tabler/icons';
import { styled } from '@mui/material/styles';
import { shouldForwardProp } from '@mui/system';
import RidList from './RidList';

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
    const [open, setOpen] = React.useState<boolean>(false);

    const handleRidSearch = (e: any) => {
        if (e.type === 'click' || (e.type === 'keydown' && e.code === 'Enter')) {
            props.onSubmit(props.irb, props.rid);
        }
    };

    const handleRidSelect = (ridStr: string) => {
        props.onChange(ridStr);
        props.onSubmit(props.irb, ridStr);
    };

    return (
        <React.Fragment>
            <OutlineInputStyle
                id="input-search-header"
                value={props.rid}
                onChange={(e) => props.onChange(e.target.value)}
                disabled={!props.irb}
                placeholder="연구별 환자 ID"
                startAdornment={
                    <InputAdornment position="start">
                        <IconButton sx={{ width: 32, height: 32 }} onClick={() => setOpen(true)} disabled={!props.irb}>
                            <IconList />
                        </IconButton>
                    </InputAdornment>
                }
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
            <Modal open={open} onClose={() => setOpen(false)}>
                <RidList irb={props.irb} onSelect={handleRidSelect} onClose={() => setOpen(false)} />
            </Modal>
        </React.Fragment>
    );
};

export default RidForm;
