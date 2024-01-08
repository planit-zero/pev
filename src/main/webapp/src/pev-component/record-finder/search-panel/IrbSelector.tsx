import { IIrb } from 'pev-interface/IIrb';
import * as React from 'react';
import { useGetIrbListQuery } from '../../../pev-service/IrbService';
import { DataGrid } from 'devextreme-react';
import { Box, Button, Modal, TextField, Typography } from '@mui/material';
import { Column, Scrolling, Selection } from 'devextreme-react/data-grid';
import { useSelector } from '../../../store';

type IrbSelectorProps = {
    irb: string | null;
    onChange: (irbNo: string) => void;
};

const IrbSelector = (props: IrbSelectorProps) => {
    const { info } = useSelector((state) => state.user);
    const [open, setOpen] = React.useState<boolean>(false);
    const [selectedIrb, setSelectedIrb] = React.useState<IIrb | null>(null);
    const [inputIrb, setInputIrb] = React.useState<string | null>(null);

    const stfNoForIrb = () => {
        if (!info) return '66206';
        if (!info.stfNo) return '66206';
        if (info.stfNo && info.stfNo === 'CHUCK') return '66206';
        return info.stfNo;
    };

    const { data: irbList, isLoading: isIrbListLoading } = useGetIrbListQuery(stfNoForIrb());

    const gridRef = React.useRef<DataGrid>(null);

    const handleIrbSelectionChanged = (e: any) => {
        if (e && e.selectedRowsData && e.selectedRowsData.length > 0) {
            setSelectedIrb(e.selectedRowsData[0]);
        } else {
            setSelectedIrb(null);
        }
    };

    const handleRowDblClick = (e: any) => {
        if (e && e.data && e.data.irbNo) {
            props.onChange(e.data.irbNo);
            setOpen(false);
        }
    };

    const handleIrbSelect = () => {
        if (selectedIrb) {
            props.onChange(selectedIrb.irbNo);
            setOpen(false);
        }
    };

    const handleIrbInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputIrb(e.target.value);
    };

    const handleIrbInputClick = () => {
        if (inputIrb) {
            props.onChange(inputIrb);
            setOpen(false);
        }
    };

    return (
        <React.Fragment>
            <Button
                variant={'contained'}
                size={'small'}
                sx={{ width: '100%', height: '100%', borderRadius: 2, boxShadow: 'none' }}
                onClick={() => setOpen(true)}
            >
                <Typography sx={{ fontSize: 'h5.fontSize' }}>
                    <strong>IRB</strong>&emsp;{props.irb ? props.irb : '선택하지 않음'}
                </Typography>
            </Button>
            <Modal open={open} onClose={() => setOpen(false)}>
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: '50vw',
                        height: '50vh',
                        bgcolor: 'background.paper',
                        boxShadow: 24,
                        p: 2
                    }}
                >
                    <Box sx={{ width: '100%', height: 'calc(100% - 40px)' }}>
                        {!isIrbListLoading && (
                            <DataGrid
                                ref={gridRef}
                                height={'100%'}
                                dataSource={irbList || []}
                                showBorders={true}
                                showColumnLines={true}
                                showRowLines={true}
                                wordWrapEnabled={false}
                                noDataText={''}
                                onSelectionChanged={handleIrbSelectionChanged}
                                onRowDblClick={handleRowDblClick}
                            >
                                <Column dataField={'irbNo'} caption={'IRB 번호'} width={130} alignment={'center'} />
                                <Column dataField={'irbKrNm'} caption={'연구과제명'} />
                                <Column dataField={'irbTypeDesc'} caption={'구분'} width={85} alignment={'center'} />
                                <Column dataField={'fromDt'} caption={'유효기간 시작일'} width={120} alignment={'center'} />
                                <Column dataField={'toDt'} caption={'유효기간 종료일'} width={120} alignment={'center'} />
                                <Column dataField={'ptCnt'} caption={'대상환자 건수'} width={100} alignment={'right'} />
                                <Scrolling mode={'virtual'} showScrollbar={'always'} />
                                <Selection mode={'single'} />
                            </DataGrid>
                        )}
                    </Box>
                    <Box
                        sx={{
                            width: '100%',
                            height: '30px',
                            display: 'flex',
                            justifyContent: 'space-between',
                            marginTop: '10px'
                        }}
                    >
                        <Box>
                            {Boolean(info && info.authCd && info.authCd === 'S') && (
                                <React.Fragment>
                                    <Typography display={'inline'} fontWeight={'bold'}>
                                        직접 입력
                                    </Typography>
                                    <TextField sx={{ mx: 1 }} variant={'standard'} onChange={handleIrbInputChange} />
                                    <Button variant={'contained'} size={'small'} onClick={handleIrbInputClick} disabled={!inputIrb}>
                                        적용
                                    </Button>
                                </React.Fragment>
                            )}
                        </Box>
                        <Box>
                            <Button variant={'contained'} size={'small'} onClick={handleIrbSelect} disabled={!selectedIrb}>
                                선택
                            </Button>
                        </Box>
                    </Box>
                </Box>
            </Modal>
        </React.Fragment>
    );
};

export default IrbSelector;
