import * as React from 'react';
import { Box, Button, Modal } from '@mui/material';
import { IRecord } from '../../pev-interface/IRecord';
import ChartComparator from '../record-comparator/ChartComparator';
import { ChartWrapperType } from '../../pev-type/TChart';
import ChartReport from './ChartReport';
import { useSelector } from '../../store';

type ChartToolbarProps = {
    targetRecord: IRecord;
    isChartLoading: boolean;
    isChartError: boolean;
};

const ChartToolbar = (props: ChartToolbarProps) => {
    const [activeModal, setActiveModal] = React.useState<ChartWrapperType | null>(null);

    const { info } = useSelector((state) => state.user);

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
            <Button
                variant={'contained'}
                color={'error'}
                size={'small'}
                onClick={() => setActiveModal('REPORT')}
                disabled={props.isChartLoading || props.isChartError}
            >
                가명처리 오류 신고
            </Button>
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
