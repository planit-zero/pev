import * as React from 'react';
import {useState} from 'react';
import {
    Button,
    Collapse,
    IconButton,
    InputAdornment,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    TextField
} from '@mui/material';
import {IRecord} from '../../pev-interface/IRecord';
import {IChartError, ILogEvent, ILogUser, IReport, ReportContentType} from '../../pev-interface/IChart';
import {AccountCircle, KeyboardArrowDown, KeyboardArrowUp} from '@mui/icons-material';
import ReportDetailList from './ReportDetailList';
import {useSelector} from '../../store';
import {useUpdateChartErrorProcessMutation} from '../../pev-service/ReportService';
import {CryptoUtils} from '../../pev-utils/CryptoUtils';
import {UrlUtils} from '../../pev-utils/UrlUtils';
import {ContentTypeProps} from './Report';

type ReportContentGridProps = {
    contentType: ContentTypeProps;
    dataSource: ReportContentType;
    isLogPage: boolean;
};

const ReportContentGrid = (props: ReportContentGridProps) => {
    const { info } = useSelector((state) => state.user);
    const { profile } = useSelector((state) => state.environment);

    const maskingDataSource = props.dataSource as IReport[];
    const chartDataSource = props.dataSource as IChartError[];
    const logUserDataSourceOrigin = props.dataSource as ILogUser[];
    const logEventDataSource = props.dataSource as ILogEvent[];

    const [logUserDataSource, setLogUserDataSource] = useState<ILogUser[]>(logUserDataSourceOrigin);

    const getRecordInfoText = (recordInfo: string) => {
        const record: IRecord = JSON.parse(recordInfo);

        let text = record.itemType;
        if (record.itemNm) text += ` - ${record.itemNm}`;
        if (record.writingDate) text += ` (${record.writingDate})`;
        return text;
    };

    const MaskingTableHead = () => {
        return (
            <TableRow>
                <TableCell />
                <TableCell align={'center'}>순번</TableCell>
                <TableCell align={'center'}>ID</TableCell>
                <TableCell align={'center'}>신고자</TableCell>
                <TableCell align={'center'}>IRB</TableCell>
                <TableCell align={'center'}>연구별 환자 ID</TableCell>
                <TableCell>기록지 정보</TableCell>
                <TableCell align={'center'}>신고일시</TableCell>
                <TableCell align={'center'}>처리여부</TableCell>
            </TableRow>
        );
    };

    const ChartTableHead = () => {
        return (
            <TableRow>
                <TableCell align={'center'}>순번</TableCell>
                <TableCell align={'center'}>신고자</TableCell>
                <TableCell align={'center'}>IRB</TableCell>
                <TableCell align={'center'}>연구별 환자 ID</TableCell>
                <TableCell>기록지 정보</TableCell>
                <TableCell align={'center'}>신고일시</TableCell>
                <TableCell align={'center'}>처리여부</TableCell>
                <TableCell align={'center'}>처리일시</TableCell>
            </TableRow>
        );
    };

    const searchUser = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            const { value } = e.target as HTMLInputElement;
            if (value) {
                setLogUserDataSource(logUserDataSourceOrigin.filter(i => i.stfNo.includes(value) || i.stfNm.includes(value)));
            } else {
                setLogUserDataSource(logUserDataSourceOrigin);
            }
        }
    }

    const LogUserTableHead = () => {
        return (
            <>
                <TextField
                    label="사용자 검색 (사번 or 직원명) + Enter 키"
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <AccountCircle />
                            </InputAdornment>
                        ),
                    }}
                    variant="standard"
                    onKeyUp={searchUser}
                />
                <Button variant={'contained'} size={'small'} onClick={() => setLogUserDataSource(logUserDataSourceOrigin)}>
                    초기화
                </Button>
                <TableRow>
                    <TableCell align={'center'}>순번</TableCell>
                    <TableCell align={'center'}>사번</TableCell>
                    <TableCell align={'center'}>직원명</TableCell>
                    <TableCell align={'center'}>부서코드</TableCell>
                    <TableCell align={'center'}>부서명</TableCell>
                    <TableCell align={'center'}>접속일시</TableCell>
                </TableRow>
            </>
        );
    };

    const LogEventTableHead = () => {
        return (
            <TableRow>
                <TableCell align={'center'}>순번</TableCell>
                <TableCell align={'center'}>병록번호</TableCell>
                <TableCell align={'center'}>기록유형</TableCell>
                <TableCell align={'center'}>기록일자</TableCell>
                <TableCell align={'center'}>환자구분</TableCell>
                <TableCell align={'center'}>진료과</TableCell>
                <TableCell align={'center'}>작성과</TableCell>
                <TableCell align={'center'}>사번</TableCell>
                <TableCell align={'center'}>발생일시</TableCell>
            </TableRow>
        );
    };

    const CommonTableHead = () => {
        if (props.contentType === 'masking') return MaskingTableHead();
        if (props.contentType === 'chart') return ChartTableHead();
        if (props.contentType === 'logUser') return LogUserTableHead();
        if (props.contentType === 'logEvent') return LogEventTableHead();
        return null;
    };

    const handleRidClick = (irb: string, rid: string, recordInfo: string) => {
        const rawKey = `${irb}|||${rid}`;
        const encryptKey = CryptoUtils.encrypt(rawKey, 'planitsquare2023');

        const record: IRecord = JSON.parse(recordInfo);
        const searchDate = record.writingDate.substring(0, 10);

        const url = `${UrlUtils.getHost(profile)}?key=${encryptKey}&searchTarget=${record.recordDetailType}&searchDate=${searchDate}`;

        window.open(url, 'review');
    };

    const MaskingTableBody = () => {
        const [activeReportId, setActiveReportId] = React.useState<number | null>(null);

        return (
            <React.Fragment>
                {maskingDataSource.map((report, idx) => {
                    return (
                        <React.Fragment key={idx}>
                            <TableRow>
                                <TableCell>
                                    <IconButton
                                        onClick={() => {
                                            if (activeReportId === report.reportId) {
                                                setActiveReportId(null);
                                            } else {
                                                setActiveReportId(report.reportId);
                                            }
                                        }}
                                    >
                                        {activeReportId === report.reportId ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
                                    </IconButton>
                                </TableCell>
                                <TableCell align={'center'}>{report.rowNum}</TableCell>
                                <TableCell align={'center'}>{report.reportId}</TableCell>
                                <TableCell align={'center'}>{report.reportUser}</TableCell>
                                <TableCell align={'center'}>{report.irb}</TableCell>
                                <TableCell
                                    align={'center'}
                                    onClick={() => handleRidClick(report.irb, report.rid, report.recordInfo)}
                                    sx={{ cursor: 'pointer' }}
                                >
                                    {report.rid}
                                </TableCell>
                                <TableCell>{getRecordInfoText(report.recordInfo)}</TableCell>
                                <TableCell align={'center'}>{report.reportDtm}</TableCell>
                                <TableCell align={'center'}>{report.processYn}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell sx={{ py: 0 }} colSpan={12}>
                                    <Collapse in={activeReportId === report.reportId} unmountOnExit={true}>
                                        <ReportDetailList report={report} />
                                    </Collapse>
                                </TableCell>
                            </TableRow>
                        </React.Fragment>
                    );
                })}
            </React.Fragment>
        );
    };

    const ChartTableBody = () => {
        const [updateChartErrorProcess] = useUpdateChartErrorProcessMutation();

        const handleUpdateChartErrorProcess = (errId: number) => {
            updateChartErrorProcess(errId);
        };

        return (
            <React.Fragment>
                {chartDataSource.map((chartError, idx) => {
                    return (
                        <React.Fragment key={idx}>
                            <TableRow>
                                <TableCell align={'center'}>{chartError.rowNum}</TableCell>
                                <TableCell align={'center'}>{chartError.stfNo}</TableCell>
                                <TableCell align={'center'}>{chartError.irb}</TableCell>
                                <TableCell
                                    align={'center'}
                                    onClick={() => handleRidClick(chartError.irb, chartError.rid, chartError.targetRecord)}
                                    sx={{ cursor: 'pointer' }}
                                >
                                    {chartError.rid}
                                </TableCell>
                                <TableCell>{getRecordInfoText(chartError.targetRecord)}</TableCell>
                                <TableCell align={'center'}>{chartError.loadDtm}</TableCell>
                                <TableCell align={'center'}>{chartError.processYn}</TableCell>
                                <TableCell align={'center'}>
                                    {(!info || info.authCd !== 'S' || chartError.processYn === 'Y') && chartError.processDtm}
                                    {info && info.authCd === 'S' && chartError.processYn === 'N' && (
                                        <Button variant={'contained'} onClick={() => handleUpdateChartErrorProcess(chartError.errId)}>
                                            처리 완료
                                        </Button>
                                    )}
                                </TableCell>
                            </TableRow>
                        </React.Fragment>
                    );
                })}
            </React.Fragment>
        );
    };

    const getLogUserDataSource = (): ILogUser[] => {
        if (logUserDataSource.length !== 0) {
            return logUserDataSource;
        }
        return logUserDataSourceOrigin;
    }

    const LogUserTableBody = () => {
        return (
            <React.Fragment>
                {getLogUserDataSource().map((log, idx) => {
                    return (
                        <React.Fragment key={idx}>
                            <TableRow>
                                <TableCell align={'center'}>{log.rowNum}</TableCell>
                                <TableCell align={'center'}>{log.stfNo}</TableCell>
                                <TableCell align={'center'}>{log.stfNm}</TableCell>
                                <TableCell align={'center'}>{log.deptCd}</TableCell>
                                <TableCell align={'center'}>{log.deptNm}</TableCell>
                                <TableCell align={'center'}>{log.loginDtm}</TableCell>
                            </TableRow>
                        </React.Fragment>
                    );
                })}
            </React.Fragment>
        );
    };

    const getPactTpCdDesc = (pactTpCd: string) => {
        if (pactTpCd === 'I') return '입원';
        if (pactTpCd === 'O') return '외래';
        if (pactTpCd === 'E') return '응급';
        return '전체';
    };

    const getDeptTypeDesc = (deptType: string) => {
        if (deptType === 'MEDICAL') return '수진과';
        if (deptType === 'WRITER') return '작성과';
        return '전체';
    };

    const LogEventTableBody = () => {
        return (
            <React.Fragment>
                {logEventDataSource.map((log, idx) => {
                    return (
                        <React.Fragment key={idx}>
                            <TableRow>
                                <TableCell align={'center'}>{log.id}</TableCell>
                                <TableCell align={'center'}>{log.ptNo}</TableCell>
                                <TableCell align={'center'}>{log.searchTargets}</TableCell>
                                <TableCell align={'center'}>
                                    {log.searchFromDate} ~ {log.searchToDate}
                                </TableCell>
                                <TableCell align={'center'}>{getPactTpCdDesc(log.pactTpCd)}</TableCell>
                                <TableCell align={'center'}>{getDeptTypeDesc(log.deptType)}</TableCell>
                                <TableCell align={'center'}>{log.deptCd}</TableCell>
                                <TableCell align={'center'}>{log.stfNo}</TableCell>
                                <TableCell align={'center'}>{log.loadDtm}</TableCell>
                            </TableRow>
                        </React.Fragment>
                    );
                })}
            </React.Fragment>
        );
    };

    const CommonTableBody = () => {
        if (props.contentType === 'masking') return MaskingTableBody();
        if (props.contentType === 'chart') return ChartTableBody();
        if (props.contentType === 'logUser') return LogUserTableBody();
        if (props.contentType === 'logEvent') return LogEventTableBody();
        return null;
    };

    const getWidth = () => {
        if (props.isLogPage) return '60%';
        return '100%';
    };

    return (
        <Paper sx={{ width: getWidth(), height: 'calc(100% - 75px)', p: '20px', overflowY: 'scroll' }}>
            <Table
                sx={{
                    '& .MuiTableRow-root:hover': {
                        backgroundColor: '#eef2f6',
                        fontWeight: 'bold'
                    }
                }}
            >
                <TableHead>
                    <CommonTableHead />
                </TableHead>
                <TableBody>
                    <CommonTableBody />
                </TableBody>
            </Table>
        </Paper>
    );
};

export default ReportContentGrid;
