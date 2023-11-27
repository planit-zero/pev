import * as React from 'react';
import { Grid } from '@mui/material';
import IrbSelector from './IrbSelector';
import { IIrb } from '../../../pev-interface/IIrb';
import RidForm from './RidForm';
import { useGetPatientMutation, useGetRidByGidMutation } from '../../../pev-service/PatientService';
import { IPatientR, IPatientRidP, IRidByGidP } from '../../../pev-interface/IPatient';
import PatientInfo from './PatientInfo';
import { useSearchParams } from 'react-router-dom';
import { CryptoUtils } from '../../../pev-utils/CryptoUtils';
import { setAlert } from '../../../store/pev-slices/environment';

const RecordSearchPanel = () => {
    const [searchParams] = useSearchParams();

    const [stfNo, setStfNo] = React.useState<string | null>(null);
    const [irb, setIrb] = React.useState<string | null>(null);
    const [rid, setRid] = React.useState<string>('');
    const [patient, setPatient] = React.useState<IPatientR | null>(null);

    const [getPatient] = useGetPatientMutation();
    const [getRidByGid] = useGetRidByGidMutation();

    React.useEffect(() => {
        const token = searchParams.get('token');

        if (token) {
            const decrypt = CryptoUtils.decrypt(token, 'planitsquare2023');
            const decryptArr = decrypt.split('|||');

            const decryptedAuthCd = decryptArr[0] || null;
            const decryptedStfNo = decryptArr[1] || null;
            const decryptedIrbNo = decryptArr[2] || null;
            const decryptedGid = decryptArr[3] || null;

            if (decryptedStfNo && decryptedIrbNo && decryptedGid) {
                setStfNo(decryptedStfNo.toUpperCase());

                const payload: IRidByGidP = {
                    stfNo: decryptedStfNo,
                    irbNo: decryptedIrbNo,
                    data: [{ gid: decryptedGid }]
                };

                getRidByGid(payload)
                    .unwrap()
                    .then((res) => {
                        setIrb(res.irbNo);

                        if (res.data.length > 0 && res.data[0].rid) {
                            setRid(res.data[0].rid);
                            handleRidSubmit(res.irbNo, res.data[0].rid);
                        }
                    });
            } else {
                setAlert({
                    type: 'error',
                    message: '연동 정보가 부정확합니다.'
                });
            }
        }
    }, []);

    const handleIrbChangeByObj = (irbObj: IIrb) => {
        setIrb(irbObj.irbNo);
    };

    const handleRidChange = (value: string) => {
        setRid(value);
    };

    const handleRidSubmit = (irbStr: string | null, ridStr: string | null) => {
        if (!irbStr) return;
        if (!ridStr) return;

        const payload: IPatientRidP = {
            irb: irbStr,
            ridList: [ridStr]
        };

        getPatient(payload)
            .unwrap()
            .then((data) => setPatient(data))
            .catch(() => setPatient(null));
    };

    return (
        <Grid container spacing={1}>
            <Grid item xs={4}>
                <IrbSelector stfNo={stfNo} irb={irb} onChange={handleIrbChangeByObj} />
            </Grid>
            <Grid item xs={4}>
                <RidForm irb={irb} rid={rid} onChange={handleRidChange} onSubmit={handleRidSubmit} />
            </Grid>
            <Grid item xs={4}>
                <PatientInfo patient={patient} />
            </Grid>
        </Grid>
    );
};

export default RecordSearchPanel;
