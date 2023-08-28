import * as React from 'react';
import {
    Autocomplete,
    Backdrop,
    Box,
    Button,
    Checkbox,
    CircularProgress,
    Divider,
    FormControlLabel,
    FormGroup,
    Grid,
    MenuItem,
    Paper,
    Radio,
    RadioGroup,
    Select,
    SelectChangeEvent,
    Stack,
    TextField,
    Typography
} from '@mui/material';
import { Square, Bookmark, Search } from '@mui/icons-material';
import { DataGrid } from 'devextreme-react';
import { Column, FilterRow, Scrolling, Selection } from 'devextreme-react/data-grid';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { IRecordDetailP } from '../../pev-interface/IRecordDetail';
import dayjs, { ManipulateType } from 'dayjs';
import { TDatePeriod } from '../../pev-type/TDatePeriod';
import { TPactTpCd } from '../../pev-type/TPactTpCd';
import { TDept } from '../../pev-type/TDept';
import { TWriter } from '../../pev-type/TWriter';
import { TRecord } from '../../pev-type/TRecord';
import { useGetDeptInfoListQuery, useGetDetailListByConditionMutation } from '../../pev-service/RecordService';
import { IRecordDeptInfo } from '../../pev-interface/IRecordInfo';

const TabItemDetail = () => {
    const { data: deptInfoList, isLoading: isDeptInfoListLoading } = useGetDeptInfoListQuery();

    const initialDetailForm: IRecordDetailP = {
        ptNo: '36010929',
        searchFromDate: dayjs().add(-1, 'month').format('YYYY-MM-DD'),
        searchToDate: dayjs().add(-1, 'day').format('YYYY-MM-DD'),
        pactTpCd: TPactTpCd.ALL,
        recordType: [TRecord.DR],
        recordDetailType: [],
        deptType: TDept.ALL,
        deptCd: '',
        writerType: TWriter.ALL
    };

    const [detailForm, setDetailForm] = React.useState<IRecordDetailP>(initialDetailForm);
    const [datePeriod, setDatePeriod] = React.useState<string>(TDatePeriod.ONE_MONTH);

    const handleDetailFormDateChange = (value: string | null, key: string) => {
        setDetailForm({
            ...detailForm,
            [key]: value
        });
    };

    const handleDatePeriodSelectChange = (event: SelectChangeEvent) => {
        let value: number = -1;
        let unit: ManipulateType = 'day';

        if (event.target.value === TDatePeriod.FIVE_YEARS) {
            value = -5;
            unit = 'year';
        }

        if (event.target.value === TDatePeriod.ONE_YEAR) {
            value = -1;
            unit = 'year';
        }

        if (event.target.value === TDatePeriod.SIX_MONTHS) {
            value = -6;
            unit = 'month';
        }

        if (event.target.value === TDatePeriod.THREE_MONTHS) {
            value = -3;
            unit = 'month';
        }

        if (event.target.value === TDatePeriod.ONE_MONTH) {
            value = -1;
            unit = 'month';
        }

        setDetailForm({
            ...detailForm,
            searchFromDate: dayjs().add(value, unit).format('YYYY-MM-DD'),
            searchToDate: dayjs().add(-1, 'day').format('YYYY-MM-DD')
        });

        setDatePeriod(event.target.value);
    };

    const handleRadioChange = (event: React.ChangeEvent<HTMLInputElement>, key: string) => {
        setDetailForm({
            ...detailForm,
            [key]: event.target.value
        });
    };

    const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.checked && !detailForm.recordType.includes(event.target.value)) {
            setDetailForm({
                ...detailForm,
                recordType: [...detailForm.recordType, event.target.value]
            });
        }

        if (!event.target.checked) {
            setDetailForm({
                ...detailForm,
                recordType: [...detailForm.recordType].filter((item) => item !== event.target.value)
            });
        }
    };

    const handleDeptChange = (e: React.SyntheticEvent, value: IRecordDeptInfo | null, reason: string) => {
        if (reason === 'clear') {
            setDetailForm({
                ...detailForm,
                deptCd: ''
            });
        }

        if (reason === 'selectOption' && value) {
            setDetailForm({
                ...detailForm,
                deptCd: value.deptCd
            });
        }
    };

    const [getDetailListByCondition, { isLoading: isDetailListLoading, data: detailListByCondition }] =
        useGetDetailListByConditionMutation();

    const handleRetrieve = () => {
        getDetailListByCondition(detailForm)
            .unwrap()
            .then((data) => {})
            .catch((error) => {});
    };

    return (
        <React.Fragment>
            <Grid container sx={{ mt: 2, height: 'calc(100% - 81px)' }}>
                <Grid item xs={12} height={'325px'} display={'flex'}>
                    <Grid item xs={9} sx={{ pr: 1 }}>
                        <Box display={'flex'} alignItems={'center'}>
                            <Bookmark color={'primary'} fontSize={'small'} />
                            <Typography variant={'body1'}>조회조건</Typography>
                        </Box>
                        <Divider sx={{ mt: 1 }} />
                        <Paper sx={{ mt: 1, p: 1, height: 'calc(100% - 37px)', borderRadius: 0 }} elevation={1}>
                            <Box height={'100%'} display={'flex'} justifyContent={'space-between'} flexDirection={'column'}>
                                <Grid container>
                                    <Grid item xs={2} display={'flex'} alignItems={'center'}>
                                        <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                                        <Typography variant={'body1'}>기록일자</Typography>
                                    </Grid>
                                    <Grid item xs={10} display={'flex'} alignItems={'center'} gap={0.5}>
                                        <Grid item xs={5}>
                                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                                <DatePicker
                                                    inputFormat={'yyyy-MM-dd'}
                                                    renderInput={(props) => <TextField fullWidth size={'small'} {...props} />}
                                                    value={detailForm.searchFromDate}
                                                    onChange={(event) => handleDetailFormDateChange(event, 'searchFromDate')}
                                                />
                                            </LocalizationProvider>
                                        </Grid>
                                        <Grid item xs={5}>
                                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                                <DatePicker
                                                    inputFormat={'yyyy-MM-dd'}
                                                    renderInput={(props) => <TextField fullWidth size={'small'} {...props} />}
                                                    value={detailForm.searchToDate}
                                                    onChange={(event) => handleDetailFormDateChange(event, 'searchToDate')}
                                                />
                                            </LocalizationProvider>
                                        </Grid>
                                        <Grid item xs={2}>
                                            <Select size={'small'} fullWidth value={datePeriod} onChange={handleDatePeriodSelectChange}>
                                                <MenuItem value={TDatePeriod.ALL}>전체</MenuItem>
                                                <MenuItem value={TDatePeriod.FIVE_YEARS}>5년</MenuItem>
                                                <MenuItem value={TDatePeriod.ONE_YEAR}>1년</MenuItem>
                                                <MenuItem value={TDatePeriod.SIX_MONTHS}>6개월</MenuItem>
                                                <MenuItem value={TDatePeriod.THREE_MONTHS}>3개월</MenuItem>
                                                <MenuItem value={TDatePeriod.ONE_MONTH}>1개월</MenuItem>
                                            </Select>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid container>
                                    <Grid item xs={2} display={'flex'} alignItems={'center'}>
                                        <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                                        <Typography variant={'body1'}>환자구분</Typography>
                                    </Grid>
                                    <Grid item xs={10} display={'flex'} alignItems={'center'}>
                                        <RadioGroup
                                            value={detailForm.pactTpCd}
                                            row={true}
                                            onChange={(event) => handleRadioChange(event, 'pactTpCd')}
                                        >
                                            <FormControlLabel value={TPactTpCd.ALL} control={<Radio />} label={'전체'} />
                                            <FormControlLabel value={TPactTpCd.OUTPATIENT} control={<Radio />} label={'외래'} />
                                            <FormControlLabel value={TPactTpCd.INPATIENT} control={<Radio />} label={'입원'} />
                                            <FormControlLabel value={TPactTpCd.EMERGENCY} control={<Radio />} label={'응급'} />
                                        </RadioGroup>
                                    </Grid>
                                </Grid>
                                <Grid container>
                                    <Grid item xs={2} display={'flex'} alignItems={'center'}>
                                        <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                                        <Typography variant={'body1'}>진료과</Typography>
                                    </Grid>
                                    <Grid item xs={10} display={'flex'} justifyContent={'space-between'} alignItems={'center'}>
                                        <RadioGroup
                                            value={detailForm.deptType}
                                            row={true}
                                            onChange={(event) => handleRadioChange(event, 'deptType')}
                                        >
                                            <FormControlLabel value={TDept.ALL} control={<Radio />} label={'전체'} />
                                            <FormControlLabel value={TDept.MEDICAL} control={<Radio />} label={'수진과'} />
                                            <FormControlLabel value={TDept.WRITER} control={<Radio />} label={'작성과'} />
                                        </RadioGroup>
                                        {deptInfoList && (
                                            <Autocomplete
                                                size={'small'}
                                                sx={{ width: 'calc(100% - 250px)' }}
                                                disabled={detailForm.deptType === TDept.ALL}
                                                renderInput={(params) => <TextField {...params} />}
                                                renderOption={(props, option) => {
                                                    return (
                                                        <li {...props} key={option.deptCd}>
                                                            {option.deptNm}
                                                        </li>
                                                    );
                                                }}
                                                options={deptInfoList || []}
                                                getOptionLabel={(option) => option.deptNm}
                                                onChange={handleDeptChange}
                                            />
                                        )}
                                    </Grid>
                                </Grid>
                                <Grid container>
                                    <Grid item xs={2} display={'flex'} alignItems={'center'}>
                                        <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                                        <Typography variant={'body1'}>작성자</Typography>
                                    </Grid>
                                    <Grid item xs={10} display={'flex'} alignItems={'center'} gap={0.5}>
                                        <RadioGroup
                                            value={detailForm.writerType}
                                            row={true}
                                            onChange={(event) => handleRadioChange(event, 'writerType')}
                                        >
                                            <FormControlLabel value={TWriter.ALL} control={<Radio />} label={'전체'} />
                                            <FormControlLabel value={TWriter.SELF} control={<Radio />} label={'본인'} />
                                        </RadioGroup>
                                    </Grid>
                                </Grid>
                                <Grid container>
                                    <Grid item xs={2} display={'flex'} alignItems={'center'}>
                                        <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                                        <Typography variant={'body1'}>상세유형</Typography>
                                    </Grid>
                                    <Grid item xs={10} display={'flex'} alignItems={'center'} gap={0.5}>
                                        <Select fullWidth size={'small'}>
                                            <MenuItem>상세 유형을 선택하세요.</MenuItem>
                                        </Select>
                                    </Grid>
                                </Grid>
                                <Grid container display={'flex'} justifyContent={'flex-end'} alignItems={'center'}>
                                    <Button
                                        variant={'contained'}
                                        startIcon={<Search />}
                                        size={'small'}
                                        onClick={handleRetrieve}
                                        disabled={isDetailListLoading}
                                    >
                                        목록 조회
                                    </Button>
                                </Grid>
                            </Box>
                        </Paper>
                    </Grid>
                    <Grid item xs={3} sx={{ pl: 1 }}>
                        <Box display={'flex'} alignItems={'center'}>
                            <Bookmark color={'primary'} fontSize={'small'} />
                            <Typography variant={'body1'}>기록유형</Typography>
                        </Box>
                        <Divider sx={{ mt: 1 }} />
                        <Paper sx={{ mt: 1, p: 1, height: 'calc(100% - 37px)', borderRadius: 0 }} elevation={1}>
                            <FormGroup>
                                <Grid container>
                                    <Grid item xs={12}>
                                        <FormControlLabel
                                            sx={{ height: 30 }}
                                            control={
                                                <Checkbox
                                                    size={'small'}
                                                    value={TRecord.DR}
                                                    checked={detailForm.recordType.includes(TRecord.DR)}
                                                    onChange={handleCheckboxChange}
                                                />
                                            }
                                            label={<Typography variant={'caption'}>진료기록</Typography>}
                                        />
                                    </Grid>
                                    <Divider sx={{ width: '100%', mt: 0.5, mb: 0.5 }} />
                                    <Grid container>
                                        <Grid item xs={5}>
                                            <FormControlLabel
                                                sx={{ height: 30 }}
                                                control={
                                                    <Checkbox
                                                        size={'small'}
                                                        value={TRecord.OR}
                                                        checked={detailForm.recordType.includes(TRecord.OR)}
                                                        onChange={handleCheckboxChange}
                                                    />
                                                }
                                                label={<Typography variant={'caption'}>처방</Typography>}
                                            />
                                        </Grid>
                                        <Grid item xs={7}>
                                            <Stack>
                                                <FormControlLabel
                                                    sx={{ height: 30 }}
                                                    control={<Checkbox size={'small'} />}
                                                    label={<Typography variant={'caption'}>수행사인 포함</Typography>}
                                                />
                                                <FormControlLabel
                                                    sx={{ height: 30 }}
                                                    control={<Checkbox size={'small'} />}
                                                    label={<Typography variant={'caption'}>이력 포함</Typography>}
                                                />
                                            </Stack>
                                        </Grid>
                                    </Grid>
                                    <Divider sx={{ width: '100%', mt: 0.5, mb: 0.5 }} />
                                    <Grid container>
                                        <Grid item xs={5}>
                                            <FormControlLabel
                                                sx={{ height: 30 }}
                                                control={
                                                    <Checkbox
                                                        size={'small'}
                                                        value={TRecord.NR}
                                                        checked={detailForm.recordType.includes(TRecord.NR)}
                                                        onChange={handleCheckboxChange}
                                                    />
                                                }
                                                label={<Typography variant={'caption'}>간호기록</Typography>}
                                            />
                                        </Grid>
                                        <Grid item xs={7}>
                                            <FormControlLabel
                                                sx={{ height: 30 }}
                                                control={<Checkbox size={'small'} />}
                                                label={<Typography variant={'caption'}>취소수진 포함</Typography>}
                                            />
                                        </Grid>
                                    </Grid>
                                    <Divider sx={{ width: '100%', mt: 0.5, mb: 0.5 }} />
                                    <Grid item xs={12}>
                                        <FormControlLabel
                                            sx={{ height: 30 }}
                                            control={
                                                <Checkbox
                                                    size={'small'}
                                                    value={TRecord.EX}
                                                    checked={detailForm.recordType.includes(TRecord.EX)}
                                                    onChange={handleCheckboxChange}
                                                />
                                            }
                                            label={<Typography variant={'caption'}>검사</Typography>}
                                        />
                                    </Grid>
                                    <Divider sx={{ width: '100%', mt: 0.5, mb: 0.5 }} />
                                    <Grid item xs={12}>
                                        <FormControlLabel
                                            sx={{ height: 30 }}
                                            control={
                                                <Checkbox
                                                    size={'small'}
                                                    value={TRecord.SC}
                                                    checked={detailForm.recordType.includes(TRecord.SC)}
                                                    onChange={handleCheckboxChange}
                                                />
                                            }
                                            label={<Typography variant={'caption'}>스캔자료</Typography>}
                                        />
                                    </Grid>
                                    <Divider sx={{ width: '100%', mt: 0.5, mb: 0.5 }} />
                                    <Grid item xs={12}>
                                        <FormControlLabel
                                            sx={{ height: 30 }}
                                            control={<Checkbox size={'small'} />}
                                            label={<Typography variant={'caption'}>특성화 기록</Typography>}
                                        />
                                    </Grid>
                                </Grid>
                            </FormGroup>
                        </Paper>
                    </Grid>
                </Grid>
                <Grid item xs={12} sx={{ mt: 2, height: 'calc(100% - 325px)' }}>
                    <Box display={'flex'} alignItems={'center'}>
                        <Grid container>
                            <Grid item xs={2} display={'flex'} alignItems={'center'}>
                                <Bookmark color={'primary'} fontSize={'small'} />
                                <Typography variant={'body1'}>기록목록</Typography>
                            </Grid>
                            <Grid item xs={5} display={'flex'} alignItems={'center'}>
                                {detailListByCondition && (
                                    <React.Fragment>
                                        <Typography variant={'body1'}>전체: {detailListByCondition.length}</Typography>
                                        <Typography variant={'body1'} sx={{ ml: 2 }}>
                                            선택: 0
                                        </Typography>
                                        <Typography variant={'body1'} sx={{ ml: 2 }}>
                                            조회: 0
                                        </Typography>
                                    </React.Fragment>
                                )}
                            </Grid>
                            <Grid item xs={5}>
                                <FormGroup
                                    sx={{
                                        display: 'flex',
                                        justifyContent: 'flex-end',
                                        alignItems: 'center',
                                        flexDirection: 'row'
                                    }}
                                >
                                    <FormControlLabel
                                        sx={{ height: 30 }}
                                        control={<Checkbox size={'small'} />}
                                        label={<Typography variant={'caption'}>영문조회</Typography>}
                                    />
                                    <FormControlLabel
                                        sx={{ height: 30 }}
                                        control={<Checkbox size={'small'} />}
                                        label={<Typography variant={'caption'}>검체 한글명 병기</Typography>}
                                    />
                                    <Button variant={'contained'} startIcon={<Search />} size={'small'}>
                                        조회
                                    </Button>
                                </FormGroup>
                            </Grid>
                        </Grid>
                    </Box>
                    <Divider sx={{ mt: 1, mb: 1 }} />
                    <DataGrid
                        dataSource={detailListByCondition ? detailListByCondition : []}
                        height={380}
                        showBorders={true}
                        showColumnLines={true}
                        showRowLines={true}
                    >
                        <FilterRow visible={true} />
                        <Column dataField={'pactTpCd'} caption={'환자구분'} alignment={'center'} width={100} />
                        <Column dataField={'itemType'} caption={'항목구분'} alignment={'center'} width={100} />
                        <Column dataField={'itemNm'} caption={'항목명'} alignment={'left'} />
                        <Column dataField={'writingDate'} caption={'작성일자'} width={120} alignment={'center'} />
                        <Column dataField={'writingDeptNm'} caption={'작성과'} width={100} alignment={'center'} />
                        <Column dataField={'writerNm'} caption={'작성자'} width={85} alignment={'center'} />
                        <Column dataField={'mdrcWrtStsCdYn'} caption={'서명'} alignment={'center'} width={75} />
                        <Scrolling mode={'virtual'} />
                        <Selection mode={'multiple'} />
                    </DataGrid>
                </Grid>
            </Grid>
            <Backdrop
                open={isDetailListLoading}
                sx={{ color: (theme) => theme.palette.primary.main, zIndex: (theme) => theme.zIndex.modal + 1 }}
            >
                <CircularProgress color={'inherit'} />
            </Backdrop>
        </React.Fragment>
    );
};

export default TabItemDetail;
