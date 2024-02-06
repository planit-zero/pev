import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import { Box, Button, Chip, TextField, Typography } from '@mui/material';
import ChartWrapper from './ChartWrapper';
import { IChartReport, IChartReportValue } from '../../pev-interface/IChart';
import { setAlert } from '../../store/pev-slices/environment';
import { useInsertReportMutation } from '../../pev-service/ReportService';
import { useSelector } from '../../store';

type ChartReportProps = {
    targetRecord: IRecord;
    onClose: () => void;
};

const ChartReport = (props: ChartReportProps) => {
    const { info } = useSelector((state) => state.user);

    const initialChartReportForm: IChartReport = {
        stfNo: info?.stfNo || 'UNKNOWN',
        recordInfo: JSON.stringify(props.targetRecord),
        values: []
    };

    const [chartReportForm, setChartReportForm] = React.useState<IChartReport>(initialChartReportForm);

    const handleValueChange = (value: IChartReportValue) => {
        const valueIndex = chartReportForm.values.findIndex((v) => v.id === value.id && v.parentId === value.parentId);

        let nextValues = [...chartReportForm.values];

        if (valueIndex > -1) {
            nextValues = chartReportForm.values.map((v) => {
                if (v.id === value.id && v.parentId === value.parentId) return value;
                return v;
            });
        } else {
            nextValues.push(value);
        }

        setChartReportForm({
            ...chartReportForm,
            values: nextValues
        });
    };

    const handleValueRemove = (value: IChartReportValue) => {
        setChartReportForm({
            ...chartReportForm,
            values: chartReportForm.values.filter((v) => !(v.id === value.id && v.parentId === value.parentId))
        });
    };

    const handleComplete = (value: IChartReportValue) => {
        if (value.report === null || value.report === '') {
            setAlert({
                type: 'warning',
                message: '신고 내용을 작성 후 완료 버튼을 눌러주세요.'
            });
            return;
        }

        handleValueChange({ ...value, confirmYn: 'Y' });
    };

    const [insertReport] = useInsertReportMutation();

    const handleSubmit = () => {
        if (chartReportForm.values.length === 0) {
            setAlert({
                type: 'warning',
                message: '신고 내용을 작성 완료한 후 제출 버튼을 눌러주세요.'
            });
            return;
        }

        insertReport(chartReportForm)
            .unwrap()
            .then(() => {
                setAlert({
                    type: 'success',
                    message: '신고가 완료되었습니다.'
                });
                props.onClose();
            });
    };

    return (
        <Box
            sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                width: '1263px',
                height: '800px',
                backgroundColor: '#eef2f6',
                boxShadow: 24,
                p: 2,
                display: 'flex',
                gap: 2
            }}
        >
            <Box sx={{ minWidth: 600, height: '100%', bgcolor: 'background.paper', p: 3, overflowY: 'scroll' }}>
                <ChartWrapper
                    mode={'REPORT'}
                    maskingYn={'Y'}
                    targetRecord={props.targetRecord}
                    reportValues={chartReportForm.values}
                    onValueChange={handleValueChange}
                />
            </Box>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <Box
                    sx={{
                        minWidth: 600,
                        height: 'fit-content',
                        bgcolor: 'background.paper',
                        p: 2,
                        display: 'flex',
                        justifyContent: 'space-between',
                        alignItems: 'center'
                    }}
                >
                    <Typography fontWeight={'bold'}>좌측 기록지에서 신고할 영역을 선택해주세요.</Typography>
                    <Button variant={'contained'} color={'primary'} size={'small'} onClick={handleSubmit}>
                        신고 제출
                    </Button>
                </Box>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, overflowY: 'scroll' }}>
                    {chartReportForm.values.map((v, idx) => {
                        return (
                            <Box
                                key={idx}
                                sx={{
                                    minWidth: 600,
                                    height: 'fit-content',
                                    bgcolor: 'background.paper',
                                    p: 2
                                }}
                            >
                                <Box>
                                    <Chip label={idx + 1} color={'error'} size={'small'} />
                                    <TextField
                                        sx={{ ml: '8px', width: 'calc(100% - 32px)' }}
                                        multiline
                                        minRows={2}
                                        variant={'filled'}
                                        label={'신고 내용을 입력해주세요.'}
                                        disabled={v.confirmYn === 'Y'}
                                        value={v.report}
                                        onChange={(e) => handleValueChange({ ...v, report: e.target.value })}
                                    />
                                </Box>
                                <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2 }}>
                                    <Button variant={'outlined'} color={'primary'} size={'small'} onClick={() => handleValueRemove(v)}>
                                        취소
                                    </Button>
                                    {v.confirmYn === 'N' && (
                                        <Button variant={'contained'} color={'primary'} size={'small'} onClick={() => handleComplete(v)}>
                                            작성 완료
                                        </Button>
                                    )}
                                    {v.confirmYn === 'Y' && (
                                        <Button
                                            variant={'contained'}
                                            color={'primary'}
                                            size={'small'}
                                            onClick={() => handleValueChange({ ...v, confirmYn: 'N' })}
                                        >
                                            내용 변경
                                        </Button>
                                    )}
                                </Box>
                            </Box>
                        );
                    })}
                </Box>
            </Box>
        </Box>
    );
};

export default ChartReport;
