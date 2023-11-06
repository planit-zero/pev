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
import { setAlert } from '../../../../store/pev-slices/environment';

const OutlineInputStyle = styled(OutlinedInput, { shouldForwardProp })(({ theme }) => ({
    width: 320,
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
                placeholder="연구별 환자 ID 를 입력하세요."
                startAdornment={
                    <InputAdornment position="start">
                        <IconSearch stroke={1.5} size="16px" color={theme.palette.grey[500]} />
                    </InputAdornment>
                }
                endAdornment={
                    <InputAdornment position="end">
                        <Button variant={'contained'} size={'small'} onClick={handleRidSearch}>
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

        const { data: irbList, isLoading: isIrbListLoading } = useGetIrbListQuery('66206');

        const gridRef = React.useRef<DataGrid>(null);

        const handleIrbSelect = () => {
            if (gridRef && gridRef.current) {
                const selected = gridRef.current.instance.getSelectedRowsData();

                if (selected && selected.length > 0) {
                    setIrb(selected[0]);
                    setOpen(false);
                }
            }
        };

        return (
            <React.Fragment>
                <Button variant={'outlined'} size={'small'} sx={{ p: '13px', borderRadius: 2 }} onClick={() => setOpen(true)}>
                    IRB 선택 {irb && `(${irb.irbNo})`}
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
                            <Button variant={'contained'} size={'small'} onClick={handleIrbSelect}>
                                선택
                            </Button>
                        </Box>
                    </Box>
                </Modal>
            </React.Fragment>
        );
    };

    return (
        <Box display={'flex'} alignItems={'center'}>
            {patient && (
                <Box
                    sx={{
                        mr: 1,
                        p: '14px',
                        height: '100%',
                        color: 'white',
                        fontSize: 'h4.fontSize',
                        backgroundColor: '#3f51b5',
                        borderRadius: 1,
                        boxShadow: '0px 3px 1px -2px rgba(0,0,0,0.2), 0px 2px 2px 0px rgba(0,0,0,0.14), 0px 1px 5px 0px rgba(0,0,0,0.12)'
                    }}
                >
                    <span>
                        <strong>환자명:</strong> {patient.name}&emsp;
                    </span>
                    <span>
                        <strong>성별:</strong> {patient.gender}&emsp;
                    </span>
                    <span>
                        <strong>생년월일:</strong> {patient.dob}
                    </span>
                </Box>
            )}
            <Box display={'flex'} alignItems={'center'} gap={1}>
                <Box display={'flex'} alignItems={'center'} gap={1}>
                    <IrbSelector />
                    <RidSearchPanel />
                </Box>
            </Box>
        </Box>
    );
};

export default SearchSection;
