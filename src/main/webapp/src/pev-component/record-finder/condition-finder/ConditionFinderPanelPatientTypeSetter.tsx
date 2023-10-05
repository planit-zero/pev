import * as React from 'react';
import { FormControlLabel, Grid, Radio, RadioGroup, Typography } from '@mui/material';
import { Square } from '@mui/icons-material';
import { TPactTpCd } from '../../../pev-type/TPactTpCd';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';

type ConditionFinderPanelPatientTypeSetterProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
};

const ConditionFinderPanelPatientTypeSetter = (props: ConditionFinderPanelPatientTypeSetterProps) => {
    const [pactTpCd, setPactTpCd] = React.useState<string>(props.searchCondition.pactTpCd);

    const handlePactTpCdChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPactTpCd(event.target.value);
        props.onSearchConditionChange([{ key: 'pactTpCd', value: event.target.value }]);
    };

    return (
        <Grid container display={'flex'} alignItems={'center'}>
            <Grid item xs={2} display={'flex'} alignItems={'center'}>
                <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                <Typography variant={'body1'}>환자구분</Typography>
            </Grid>
            <Grid item xs={10}>
                <RadioGroup value={pactTpCd} row={true} onChange={handlePactTpCdChange}>
                    <FormControlLabel value={TPactTpCd.ALL} control={<Radio size={'small'} />} label={'전체'} />
                    <FormControlLabel value={TPactTpCd.OUTPATIENT} control={<Radio size={'small'} />} label={'외래'} />
                    <FormControlLabel value={TPactTpCd.INPATIENT} control={<Radio size={'small'} />} label={'입원'} />
                    <FormControlLabel value={TPactTpCd.EMERGENCY} control={<Radio size={'small'} />} label={'응급'} />
                </RadioGroup>
            </Grid>
        </Grid>
    );
};

export default ConditionFinderPanelPatientTypeSetter;
