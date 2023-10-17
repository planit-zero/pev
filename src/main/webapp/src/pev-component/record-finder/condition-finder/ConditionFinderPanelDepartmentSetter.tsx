import * as React from 'react';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import { Autocomplete, Box, FormControlLabel, Grid, Radio, RadioGroup, TextField, Typography } from '@mui/material';
import { Square } from '@mui/icons-material';
import { TDept } from '../../../pev-type/TDept';
import { IRecordDeptInfo } from '../../../pev-interface/IRecordInfo';
import { useGetDepartmentListQuery } from '../../../pev-service/HospitalService';
import { useSelector } from '../../../store';
import { finderWidthWide } from '../../../store/constant';

type ConditionFinderPanelDepartmentSetterProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
};

const ConditionFinderPanelDepartmentSetter = (props: ConditionFinderPanelDepartmentSetterProps) => {
    const { finderWidth } = useSelector((state) => state.environment);

    const { data: departmentList, isLoading: isDepartmentListLoading } = useGetDepartmentListQuery();

    const [deptType, setDeptType] = React.useState<string>(props.searchCondition.deptType);
    const [, setDeptCd] = React.useState<string | null>(props.searchCondition.deptCd);

    const handleDeptTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDeptType(event.target.value);
        props.onSearchConditionChange([{ key: 'deptType', value: event.target.value }]);
    };

    const handleDeptCdChange = (e: React.SyntheticEvent, value: IRecordDeptInfo | null, reason: string) => {
        let nextDeptCd: string | null = null;

        if (value && reason === 'selectOption') {
            nextDeptCd = value.deptCd;
        }

        setDeptCd(nextDeptCd);
        props.onSearchConditionChange([{ key: 'deptCd', value: nextDeptCd }]);
    };

    const getInputWidth = () => {
        if (finderWidth === finderWidthWide) return '344px';
        return '210px';
    };

    return (
        <Grid container display={'flex'} alignItems={'center'}>
            <Grid item xs={2} display={'flex'} alignItems={'center'}>
                <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                <Typography variant={'body1'}>진료과</Typography>
            </Grid>
            <Grid item xs={10} display={'flex'} justifyContent={'flex-start'} alignItems={'center'}>
                <Box>
                    <RadioGroup value={deptType} row={true} onChange={handleDeptTypeChange}>
                        <FormControlLabel value={TDept.ALL} control={<Radio size={'small'} />} label={'전체'} />
                        <FormControlLabel value={TDept.MEDICAL} control={<Radio size={'small'} />} label={'수진과'} />
                        <FormControlLabel value={TDept.WRITER} control={<Radio size={'small'} />} label={'작성과'} />
                    </RadioGroup>
                </Box>
                <Box width={getInputWidth()}>
                    {!isDepartmentListLoading && (
                        <Autocomplete
                            size={'small'}
                            fullWidth
                            disabled={deptType === TDept.ALL}
                            renderInput={(params) => <TextField {...params} size={'small'} />}
                            renderOption={(fieldProps, option) => {
                                return (
                                    <li {...fieldProps} key={option.deptCd}>
                                        {option.deptNm}
                                    </li>
                                );
                            }}
                            options={departmentList || []}
                            getOptionLabel={(option) => option.deptNm}
                            onChange={handleDeptCdChange}
                        />
                    )}
                </Box>
            </Grid>
        </Grid>
    );
};

export default ConditionFinderPanelDepartmentSetter;
