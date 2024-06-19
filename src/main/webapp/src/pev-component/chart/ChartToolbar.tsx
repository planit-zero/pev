import * as React from 'react';
import { Box, Button, Modal } from '@mui/material';
import { IRecord } from '../../pev-interface/IRecord';
import ChartComparator from '../record-comparator/ChartComparator';
import { ChartWrapperType } from '../../pev-type/TChart';
import ChartReport from './ChartReport';
import { useSelector } from '../../store';
import {useInsertChartErrorMutation} from '../../pev-service/ReportService';

type ChartToolbarProps = {
    targetRecord: IRecord;
    isChartLoading: boolean;
    isChartError: boolean;
};

const ChartToolbar = (props: ChartToolbarProps) => {
    const [activeModal, setActiveModal] = React.useState<ChartWrapperType | null>(null);

    const { info } = useSelector((state) => state.user);

    const [insertChartError] = useInsertChartErrorMutation();

    const handleChartErrorButtonClick = () => {
        if (window.confirm('해당 기록지에 데이터가 없는 경우 신고를 등록합니다.')) {
            const payload = {
                stfNo: info?.stfNo || 'UNKNOWN',
                targetRecord: JSON.stringify(props.targetRecord)
            };

            insertChartError(payload)
                .unwrap()
                .then(() => alert('기록지 신고가 완료되었습니다.'))
                .catch(() => alert('기록지 신고에 실패했습니다. 관리자에게 문의해주세요.'));
        }
    };

    return (
        <Box sx={{ width: '100%', display: 'flex', justifyContent: 'space-between', mb: 2 }}>
            <Box>
                {info && info.authCd === 'S' && (
                    <Button
                        variant={'contained'}
                        color={'primary'}
                        size={'small'}
                        onClick={() => setActiveModal('COMPARATOR')}
                        disabled={props.isChartLoading}
                    >
                        원본 대조
                    </Button>
                )}
            </Box>
            <Box>
                <Button
                    variant={'outlined'}
                    color={'error'}
                    size={'small'}
                    onClick={handleChartErrorButtonClick}
                    disabled={props.isChartLoading || props.isChartError}
                >
                    데이터 없음 신고
                </Button>
                <Button
                    variant={'contained'}
                    color={'error'}
                    size={'small'}
                    onClick={() => setActiveModal('REPORT')}
                    disabled={props.isChartLoading || props.isChartError}
                >
                    가명처리 오류 신고
                </Button>
            </Box>
            <Modal open={activeModal !== null} onClose={() => setActiveModal(null)}>
                <React.Fragment>
                    {activeModal === 'COMPARATOR' && <ChartComparator targetRecord={props.targetRecord} />}
                    {activeModal === 'REPORT' && <ChartReport targetRecord={props.targetRecord} onClose={() => setActiveModal(null)} />}
                </React.Fragment>
            </Modal>
        </Box>
    );
};

export default ChartToolbar;
