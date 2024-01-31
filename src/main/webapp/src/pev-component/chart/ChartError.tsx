import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import { Box, Button, Grid, Typography } from '@mui/material';
import { useSelector } from '../../store';
import { useInsertChartErrorMutation } from '../../pev-service/ReportService';

type ChartErrorProps = {
    targetRecord: IRecord;
};

const ChartError = (props: ChartErrorProps) => {
    const { info } = useSelector((state) => state.user);

    const [insertChartError] = useInsertChartErrorMutation();

    const handleChartErrorButtonClick = () => {
        const payload = {
            stfNo: info?.stfNo || 'UNKNOWN',
            targetRecord: JSON.stringify(props.targetRecord)
        };

        insertChartError(payload)
            .unwrap()
            .then(() => alert('기록지 신고가 완료되었습니다.'))
            .catch(() => alert('기록지 신고에 실패했습니다. 관리자에게 문의해주세요.'));
    };

    return (
        <Box sx={{ backgroundColor: 'mistyrose', p: 1, borderRadius: 1 }}>
            <Grid container>
                <Grid item xs={10} display={'flex'} justifyContent={'flex-start'} alignItems={'center'}>
                    <Box>
                        <Typography fontWeight={'bold'}>
                            {props.targetRecord.itemType} - {props.targetRecord.itemNm} ({props.targetRecord.writingDate})
                        </Typography>
                        <Typography>기록지 오류가 발생했습니다. 우측의 신고 버튼을 눌러주세요.</Typography>
                    </Box>
                </Grid>
                <Grid item xs={2} display={'flex'} justifyContent={'flex-end'} alignItems={'center'}>
                    <Button variant={'contained'} color={'error'} size={'small'} onClick={handleChartErrorButtonClick}>
                        신고
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
};

export default ChartError;
