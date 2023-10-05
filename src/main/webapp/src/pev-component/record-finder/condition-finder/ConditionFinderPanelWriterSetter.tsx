import * as React from 'react';
import { FormControlLabel, Grid, Radio, RadioGroup, Typography } from '@mui/material';
import { Square } from '@mui/icons-material';
import { TWriter } from '../../../pev-type/TWriter';
import { ISearchCondition, IWriterCondition } from '../../../pev-interface/IRecord';

type ConditionFinderPanelWriterSetterProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (key: string, value: string | null) => void;
};

const ConditionFinderPanelWriterSetter = (props: ConditionFinderPanelWriterSetterProps) => {
    const [writerType, setWriterType] = React.useState<string>(props.searchCondition.writerType);

    const handleWriterTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setWriterType(event.target.value);
        props.onSearchConditionChange('writerType', event.target.value);
    }

    return (
        <Grid container display={'flex'} alignItems={'center'}>
            <Grid item xs={2} display={'flex'} alignItems={'center'}>
                <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                <Typography variant={'body1'}>작성자</Typography>
            </Grid>
            <Grid item xs={10} display={'flex'} alignItems={'center'}>
                <RadioGroup value={writerType} row={true} onChange={handleWriterTypeChange}>
                    <FormControlLabel value={TWriter.ALL} control={<Radio size={'small'} />} label={'전체'} />
                    <FormControlLabel value={TWriter.SELF} control={<Radio size={'small'} />} label={'본인'} />
                </RadioGroup>
            </Grid>
        </Grid>
    );
};

export default ConditionFinderPanelWriterSetter;
