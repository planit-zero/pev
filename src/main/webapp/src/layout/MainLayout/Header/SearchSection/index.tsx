import * as React from 'react';

// material-ui
import { styled } from '@mui/material/styles';
import { Box, Button, Grid, IconButton, InputAdornment, Modal, OutlinedInput, Typography } from '@mui/material';

// assets
import { IconSearch } from '@tabler/icons';
import { shouldForwardProp } from '@mui/system';
import { IPatientR } from '../../../../pev-interface/IPatient';
import { useGetPatientMutation } from '../../../../pev-service/PatientService';
import { DataGrid } from 'devextreme-react';
import { Column, Scrolling, Selection } from 'devextreme-react/data-grid';
import { useGetIrbListQuery } from '../../../../pev-service/IrbService';
import { IIrb } from '../../../../pev-interface/IIrb';

const OutlineInputStyle = styled(OutlinedInput, { shouldForwardProp })(() => ({
    width: '100%',
    height: '100%',
    paddingLeft: 8,
    paddingRight: 8,
    '& input': {
        background: 'transparent !important',
        paddingLeft: '4px !important',
        paddingRight: '4px !important'
    }
}));

const SearchSection = () => {
    const patientInSession = sessionStorage.getItem('pev-pt');
    const irbInSession = sessionStorage.getItem('pev-irb');

    const [patient, setPatient] = React.useState<IPatientR | null>(patientInSession ? JSON.parse(patientInSession) : null);
    const [irb, setIrb] = React.useState<IIrb | null>(irbInSession ? JSON.parse(irbInSession) : null);
    const [getPatient] = useGetPatientMutation();

    const RidSearchPanel = () => {
        const ridInSession = sessionStorage.getItem('pev-rid');
        const [rid, setRid] = React.useState<string>(ridInSession || '');

        const handleRidSearch = (e: any) => {
            if (e.type === 'click' || (e.type === 'keydown' && e.code === 'Enter')) {
                sessionStorage.setItem('pev-rid', rid);

                if (irb) {
                    getPatient({ ridList: [rid], irb: irb?.irbNo })
                        .unwrap()
                        .then((data) => {
                            sessionStorage.setItem('pev-pt', JSON.stringify(data));
                            setPatient(data);
                        })
                        .catch(() => {
                            sessionStorage.removeItem('pev-rid');
                            sessionStorage.removeItem('pev-pt');
                            setPatient(null);
                        });
                }
            }
        };

        return (
            <OutlineInputStyle
                id="input-search-header"
                value={rid}
                onChange={(e) => setRid(e.target.value)}
                disabled={!irb}
                placeholder="연구별 환자 ID"
                endAdornment={
                    <InputAdornment position="end">
                        <IconButton sx={{ width: 32, height: 32 }} onClick={handleRidSearch} disabled={!irb}>
                            <IconSearch />
                        </IconButton>
                    </InputAdornment>
                }
                aria-describedby="search-helper-text"
                inputProps={{ 'aria-label': 'weight', onKeyDown: handleRidSearch }}
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
                sessionStorage.setItem('pev-irb', JSON.stringify(selectedIrb));
                setOpen(false);
            }
        };

        return (
            <React.Fragment>
                <Button
                    variant={'contained'}
                    color={irb ? 'primary' : 'secondary'}
                    size={'small'}
                    sx={{ width: '100%', height: '100%', borderRadius: 2, boxShadow: 'none' }}
                    onClick={() => setOpen(true)}
                >
                    <Typography sx={{ fontSize: 'h5.fontSize' }}>
                        <strong>IRB</strong>&emsp;{irb ? irb.irbNo : '선택하지 않음'}
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
        return (
            <Box
                sx={{
                    width: '100%',
                    height: '100%',
                    color: '#3f51b5',
                    fontSize: 'h5.fontSize',
                    border: '1px solid #3f51b5',
                    borderRadius: 2,
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                }}
            >
                <span>{patient ? `${patient.name} / ${patient.gender} / ${patient.dob}` : '조회하지 않음'}</span>
            </Box>
        );
    };

    return (
        <Grid container spacing={1}>
            <Grid item xs={4}>
                <IrbSelector />
            </Grid>
            <Grid item xs={4}>
                <RidSearchPanel />
            </Grid>
            <Grid item xs={4}>
                <PatientInfo />
            </Grid>
        </Grid>
    );
};

export default SearchSection;
