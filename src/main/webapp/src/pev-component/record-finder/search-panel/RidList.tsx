import * as React from 'react';
import { Box, Button } from '@mui/material';
import { DataGrid } from 'devextreme-react';
import { IPatientByIrbP, IPatientByIrbR } from '../../../pev-interface/IPatient';
import { useGetPatientListMutation } from '../../../pev-service/PatientService';
import { Column, Scrolling, Selection } from 'devextreme-react/data-grid';

type RidListProps = {
    irb: string | null;
    onSelect: (rid: string) => void;
    onClose: () => void;
};

const RidList = (props: RidListProps) => {
    const limit: number = 15;
    const [offset, setOffset] = React.useState<number>(1);
    const [selectedPatient, setSelectedPatient] = React.useState<IPatientByIrbR | null>(null);

    const [getPatientList, { data: patientList }] = useGetPatientListMutation();

    React.useEffect(() => {
        if (props.irb) {
            const payload: IPatientByIrbP = {
                irb: props.irb,
                limit: limit,
                offset: offset
            };

            getPatientList(payload);
        }
    }, [offset]);

    const getPreviousPatientList = () => {
        if (offset > 1) setOffset(offset - 1);
    };

    const getNextPatientList = () => {
        setOffset(offset + 1);
    };

    const handlePatientSelectionChanged = (e: any) => {
        if (e && e.selectedRowsData && e.selectedRowsData.length > 0 && e.selectedRowsData[0]) {
            setSelectedPatient(e.selectedRowsData[0]);
        }
    };

    const handlePatientSelect = () => {
        if (selectedPatient) {
            props.onSelect(selectedPatient.id);
            props.onClose();
        }
    };

    return (
        <Box
            sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                width: '30vw',
                height: '60vh',
                bgcolor: 'background.paper',
                boxShadow: 24,
                p: 2
            }}
        >
            <Box sx={{ width: '100%', height: '30px', display: 'flex', justifyContent: 'space-between', mb: '10px' }}>
                <Button variant={'outlined'} size={'small'} onClick={getPreviousPatientList} disabled={offset === 1}>
                    이전
                </Button>
                <Button variant={'outlined'} size={'small'} onClick={getNextPatientList}>
                    다음
                </Button>
            </Box>
            <Box sx={{ width: '100%', height: 'calc(100% - 80px)' }}>
                <DataGrid
                    width={'100%'}
                    height={'100%'}
                    dataSource={patientList || []}
                    showBorders={true}
                    showColumnLines={true}
                    showRowLines={true}
                    wordWrapEnabled={false}
                    noDataText={''}
                    onSelectionChanged={handlePatientSelectionChanged}
                >
                    <Column dataField={'id'} caption={'연구별 환자 ID'} alignment={'center'} />
                    <Column dataField={'name'} caption={'환자명'} alignment={'center'} />
                    <Column dataField={'dob'} caption={'생년월일'} alignment={'center'} />
                    <Scrolling mode={'virtual'} showScrollbar={'always'} />
                    <Selection mode={'single'} />
                </DataGrid>
            </Box>
            <Box sx={{ width: '100%', height: '30px', display: 'flex', justifyContent: 'flex-end', mt: '10px' }}>
                <Button variant={'contained'} size={'small'} onClick={handlePatientSelect}>
                    선택
                </Button>
            </Box>
        </Box>
    );
};

export default RidList;
