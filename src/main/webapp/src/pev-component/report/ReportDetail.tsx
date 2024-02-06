import * as React from 'react';
import { IReportDetail, IReportDetailUpdate } from '../../pev-interface/IChart';
import { Button, Divider, Grid, TextField, Typography } from '@mui/material';
import { useSelector } from '../../store';
import { useUpdateProcessMutation } from '../../pev-service/ReportService';

type ReportDetailProps = {
    reportId: number;
    detail: IReportDetail;
};

const ReportDetail = (props: ReportDetailProps) => {
    const { info } = useSelector((state) => state.user);

    const [detail, setDetail] = React.useState<IReportDetail>(props.detail);

    React.useEffect(() => {
        setDetail(props.detail);
    }, [props.detail]);

    const handleProcessTextChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setDetail({
            ...detail,
            processText: e.target.value
        });
    };

    const [updateProcess] = useUpdateProcessMutation();

    const handleProcessUpdate = () => {
        if (!detail.processText || detail.processText === '') return;

        const payload: IReportDetailUpdate = {
            reportId: props.reportId,
            valueSeq: detail.valueSeq,
            processText: detail.processText
        };

        updateProcess(payload);
    };

    return (
        <React.Fragment>
            <Divider />
            <Grid container sx={{ width: '100%', height: '100%', p: 2 }}>
                <Grid item xs={1} sx={{ textAlign: 'center' }}>
                    <Typography>{detail.valueSeq}</Typography>
                </Grid>
                <Grid item xs={4}>
                    <TextField multiline={true} fullWidth={true} InputProps={{ readOnly: true }} value={detail.reportText} />
                </Grid>
                <Grid item xs={1} sx={{ textAlign: 'center' }}>
                    <Typography>{detail.processYn}</Typography>
                </Grid>
                <Grid item xs={4}>
                    <TextField
                        multiline={true}
                        fullWidth={true}
                        InputProps={{ readOnly: !(info && info.authCd === 'S') }}
                        value={detail.processText || ''}
                        onChange={handleProcessTextChange}
                    />
                </Grid>
                <Grid item xs={2} sx={{ textAlign: 'center' }}>
                    {info && info.authCd === 'S' && detail.processYn === 'N' && (
                        <Button variant={'contained'} onClick={handleProcessUpdate}>
                            제출
                        </Button>
                    )}
                    {info && info.authCd === 'S' && detail.processYn === 'Y' && <Typography>{detail.processDtm}</Typography>}
                    {info && info.authCd !== 'S' && <Typography>{detail.processYn === 'N' ? '처리 전' : detail.processDtm}</Typography>}
                </Grid>
            </Grid>
        </React.Fragment>
    );
};

export default ReportDetail;
