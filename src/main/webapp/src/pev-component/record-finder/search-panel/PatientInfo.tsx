import * as React from 'react';
import { IPatientR } from '../../../pev-interface/IPatient';
import { Box } from '@mui/material';
import { useSelector } from '../../../store';
import { useGetPidMutation } from '../../../pev-service/PatientService';

type PatientInfoProps = {
    patient: IPatientR | null;
};

const PatientInfo = (props: PatientInfoProps) => {
    const { info } = useSelector((state) => state.user);

    const [getPid] = useGetPidMutation();

    const handlePanelClick = () => {
        if (!info || info.authCd !== 'S') return;

        getPid()
            .unwrap()
            .then((data) => console.log('### pid', data.pid));
    };

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
            onClick={handlePanelClick}
        >
            <span>{props.patient ? `${props.patient.name} / ${props.patient.gender} / ${props.patient.dob}` : '조회하지 않음'}</span>
        </Box>
    );
};

export default PatientInfo;
