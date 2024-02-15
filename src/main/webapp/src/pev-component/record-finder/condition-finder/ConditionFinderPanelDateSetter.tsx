import * as React from 'react';
import { Box, Grid, MenuItem, Select, SelectChangeEvent, TextField, Typography } from '@mui/material';
import { Square } from '@mui/icons-material';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import dayjs, { ManipulateType } from 'dayjs';
import { TDatePeriod } from '../../../pev-type/TDatePeriod';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import { useSelector } from '../../../store';
import { finderWidthWide } from '../../../store/constant';

type ConditionFinderPanelDateSetterProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
};

const ConditionFinderPanelDateSetter = (props: ConditionFinderPanelDateSetterProps) => {
    const { finderWidth } = useSelector((state) => state.environment);

    const [from, setFrom] = React.useState<string | null>(props.searchCondition.searchFromDate);
    const [to, setTo] = React.useState<string | null>(props.searchCondition.searchToDate);
    const [period, setPeriod] = React.useState<string>(TDatePeriod.ONE_MONTH);

    React.useEffect(() => {
        if (props.searchCondition.searchFromDate !== from) setFrom(props.searchCondition.searchFromDate);
    }, [props.searchCondition.searchFromDate]);

    React.useEffect(() => {
        if (props.searchCondition.searchToDate !== to) setTo(props.searchCondition.searchToDate);
    }, [props.searchCondition.searchToDate]);

    const handleFromChange = (value: string | null) => {
        let nextValue = value;
        if (value) nextValue = dayjs(value).format('YYYY-MM-DD');

        setFrom(nextValue);
        props.onSearchConditionChange([{ key: 'searchFromDate', value: nextValue }]);
    };

    const handleToChange = (value: string | null) => {
        let nextValue = value;
        if (value) nextValue = dayjs(value).format('YYYY-MM-DD');

        setTo(nextValue);
        props.onSearchConditionChange([{ key: 'searchToDate', value: nextValue }]);
    };

    const handleFromToChange = (fromValue: string | null, toValue: string | null) => {
        let nextFromValue = fromValue;
        let nextToValue = toValue;

        setFrom(nextFromValue);
        setTo(nextToValue);
        props.onSearchConditionChange([
            { key: 'searchFromDate', value: nextFromValue },
            {
                key: 'searchToDate',
                value: nextToValue
            }
        ]);
    };

    const handlePeriodChange = (event: SelectChangeEvent) => {
        let value: number = -1;
        let unit: ManipulateType = 'day';

        switch (event.target.value) {
            case TDatePeriod.FIVE_YEARS:
                value = -5;
                unit = 'year';
                break;
            case TDatePeriod.ONE_YEAR:
                value = -1;
                unit = 'year';
                break;
            case TDatePeriod.SIX_MONTHS:
                value = -6;
                unit = 'month';
                break;
            case TDatePeriod.THREE_MONTHS:
                value = -3;
                unit = 'month';
                break;
            case TDatePeriod.ONE_MONTH:
                value = -1;
                unit = 'month';
                break;
        }

        let nextFrom = dayjs().add(value, unit).format('YYYY-MM-DD');
        const nextTo = dayjs().add(-1, 'day').format('YYYY-MM-DD');

        if (event.target.value === TDatePeriod.ALL) {
            nextFrom = '1998-01-01';
        }

        handleFromToChange(nextFrom, nextTo);
        setPeriod(event.target.value);
    };

    const getInputWidth = () => {
        if (finderWidth === finderWidthWide) return '228px';
        return '160px';
    };

    return (
        <Grid container display={'flex'} alignItems={'center'}>
            <Grid item xs={2} display={'flex'} alignItems={'center'}>
                <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                <Typography variant={'body1'}>기록일자</Typography>
            </Grid>
            <Grid item xs={10} display={'flex'} justifyContent={'space-between'} alignItems={'center'} gap={1}>
                <Box>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                            inputFormat={'yyyy-MM-dd'}
                            renderInput={(fieldProps) => <TextField sx={{ width: getInputWidth() }} size={'small'} {...fieldProps} />}
                            value={from}
                            onChange={handleFromChange}
                        />
                    </LocalizationProvider>
                </Box>
                <Box>
                    <Typography variant={'body1'}>~</Typography>
                </Box>
                <Box>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker
                            inputFormat={'yyyy-MM-dd'}
                            renderInput={(fieldProps) => <TextField sx={{ width: getInputWidth() }} size={'small'} {...fieldProps} />}
                            value={to}
                            onChange={handleToChange}
                        />
                    </LocalizationProvider>
                </Box>
                <Box>
                    <Select sx={{ width: '100px' }} size={'small'} value={period} onChange={handlePeriodChange}>
                        <MenuItem value={TDatePeriod.ALL}>전체</MenuItem>
                        <MenuItem value={TDatePeriod.FIVE_YEARS}>5년</MenuItem>
                        <MenuItem value={TDatePeriod.ONE_YEAR}>1년</MenuItem>
                        <MenuItem value={TDatePeriod.SIX_MONTHS}>6개월</MenuItem>
                        <MenuItem value={TDatePeriod.THREE_MONTHS}>3개월</MenuItem>
                        <MenuItem value={TDatePeriod.ONE_MONTH}>1개월</MenuItem>
                    </Select>
                </Box>
            </Grid>
        </Grid>
    );
};

export default ConditionFinderPanelDateSetter;
