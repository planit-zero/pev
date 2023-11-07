import * as React from 'react';

// material-ui
import { useTheme, styled } from '@mui/material/styles';
import {
    Box,
    Button,
    InputAdornment,
    Modal,
    OutlinedInput,
    ToggleButton,
    ToggleButtonGroup,
    Tooltip,
    Typography,
    useMediaQuery
} from '@mui/material';

// assets
import { IconSearch } from '@tabler/icons';
import { shouldForwardProp } from '@mui/system';
import { IPatientR } from '../../../../pev-interface/IPatient';
import { useGetPatientMutation } from '../../../../pev-service/PatientService';
import { DataGrid } from 'devextreme-react';
import { Column, Scrolling, Selection } from 'devextreme-react/data-grid';
import { useGetIrbListQuery } from '../../../../pev-service/IrbService';
import { IIrb } from '../../../../pev-interface/IIrb';
import { useSelector } from '../../../../store';
import { finderWidthNarrow, finderWidthWide } from '../../../../store/constant';

const OutlineInputStyle = styled(OutlinedInput, { shouldForwardProp })(({ theme }) => ({
    width: 280,
    paddingLeft: 16,
    paddingRight: 16,
    '& input': {
        background: 'transparent !important',
        paddingLeft: '4px !important'
    }
}));

const SearchSection = () => {
    const theme = useTheme();
    const [patient, setPatient] = React.useState<IPatientR | null>(null);
    const [irb, setIrb] = React.useState<IIrb | null>(null);

    const RidSearchPanel = () => {
        const [rid, setRid] = React.useState<string>('R-729-00000074');

        const [getPatient] = useGetPatientMutation();

        const handleRidSearch = () => {
            if (irb) {
                getPatient({ ridList: [rid], irb: irb?.irbNo })
                    .unwrap()
                    .then((data) => setPatient(data))
                    .catch(() => setPatient(null));
            }
        };

        return (
            <OutlineInputStyle
                id="input-search-header"
                value={rid}
                onChange={(e) => setRid(e.target.value)}
                placeholder="연구별 환자 ID"
                startAdornment={
                    <InputAdornment position="start">
                        <IconSearch stroke={1.5} size="16px" color={theme.palette.grey[500]} />
                    </InputAdornment>
                }
                endAdornment={
                    <InputAdornment position="end">
                        <Button variant={'contained'} size={'small'} onClick={handleRidSearch} disabled={!irb}>
                            조회
                        </Button>
                    </InputAdornment>
                }
                aria-describedby="search-helper-text"
                inputProps={{ 'aria-label': 'weight' }}
            />
        );
    };

    const IrbSelector = () => {
        const [open, setOpen] = React.useState<boolean>(false);
        const [selectedIrb, setSelectedIrb] = React.useState<IIrb | null>(null);

        const { data: irbList, isLoading: isIrbListLoading } = useGetIrbListQuery('66206');

        const gridRef = React.useRef<DataGrid>(null);

        const handleIrbSelectionChanged = (e: any) => {
            if (e && e.selectedRowsData && e.selectedRowsData.length > 0) {
                setSelectedIrb(e.selectedRowsData[0]);
            } else {
                setSelectedIrb(null);
            }
        };

        const handleIrbSelect = () => {
            if (selectedIrb) {
                setIrb(selectedIrb);
                setOpen(false);
            }
        };

        return (
            <React.Fragment>
                <Tooltip
                    title={irb ? `현재 선택된 IRB는 ${irb.irbKrNm}(${irb.irbNo})입니다.` : '버튼을 눌러 IRB를 선택해주세요.'}
                    placement={'right'}
                >
                    <Button
                        variant={'contained'}
                        color={'primary'}
                        size={'small'}
                        sx={{ p: '13px', borderRadius: 2 }}
                        onClick={() => setOpen(true)}
                    >
                        IRB
                    </Button>
                </Tooltip>
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
                                justifyContent: 'flex-end',
                                marginTop: '10px'
                            }}
                        >
                            <Button variant={'contained'} size={'small'} onClick={handleIrbSelect} disabled={!selectedIrb}>
                                선택
                            </Button>
                        </Box>
                    </Box>
                </Modal>
            </React.Fragment>
        );
    };

    const PatientInfo = () => {
        const { finderWidth } = useSelector((state) => state.environment);

        return (
            <Box
                sx={{
                    width: finderWidth === finderWidthWide ? '364px' : '200px',
                    p: '6.8px',
                    height: '100%',
                    color: '#3f51b5',
                    fontSize: 'h5.fontSize',
                    border: '1px solid #3f51b5',
                    borderRadius: 2,
                    textAlign: 'left'
                }}
            >
                <span>
                    IRB: <strong>{irb ? irb.irbNo : '선택하지 않음'}</strong>
                </span>
                <br />
                <span>
                    환자: <strong>{patient ? `${patient.name} (${patient.dob}, ${patient.gender})` : '조회하지 않음'}</strong>
                </span>
            </Box>
        );
    };

    return (
        <Box display={'flex'} justifyContent={'flex-start'} alignItems={'center'} gap={1} sx={{ width: '100%' }}>
            <IrbSelector />
            <RidSearchPanel />
            <PatientInfo />
        </Box>
    );
};

export default SearchSection;
