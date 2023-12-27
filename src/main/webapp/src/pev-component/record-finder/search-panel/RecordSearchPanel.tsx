import * as React from 'react';
import { Grid } from '@mui/material';
import IrbSelector from './IrbSelector';
import RidForm from './RidForm';
import { useGetPatientMutation, useGetRidByGidMutation } from '../../../pev-service/PatientService';
import { IPatientR, IPatientRidP, IRidByGidP } from '../../../pev-interface/IPatient';
import PatientInfo from './PatientInfo';
import { useSearchParams } from 'react-router-dom';
import { useGetIdpLoginUserMutation } from '../../../pev-service/UserService';
import { setUserInfo } from '../../../store/slices/user';

const RecordSearchPanel = () => {
    const [searchParams] = useSearchParams();

    const [irb, setIrb] = React.useState<string | null>(null);
    const [rid, setRid] = React.useState<string>('');
    const [patient, setPatient] = React.useState<IPatientR | null>(null);

    const [getPatient] = useGetPatientMutation();
    const [getIdpLoginUser] = useGetIdpLoginUserMutation();
    const [getRidByGid] = useGetRidByGidMutation();

    React.useEffect(() => {
        const token = searchParams.get('token');
        const irbParam = searchParams.get('irb');
        const gidParam = searchParams.get('gid');

        getIdpLoginUser(token)
            .unwrap()
            .then((res) => {
                setUserInfo(res);

                if (irbParam && gidParam) {
                    // const gid = CryptoUtils.decrypt(gidParam, 'planitsquare2023');
                    const gid = gidParam;

                    const payload: IRidByGidP = {
                        stfNo: res.stfNo,
                        irbNo: irbParam,
                        data: [{ gid: gid }]
                    };

                    getRidByGid(payload)
                        .unwrap()
                        .then((r) => {
                            if (r.data.length > 0 && r.data[0].rid) {
                                setIrb(r.irbNo);
                                setRid(r.data[0].rid);

                                handleRidSubmit(r.irbNo, r.data[0].rid);
                            }
                        });
                }
            })
            .catch((error) => {
                alert(error.data?.message || '인증 토큰이 존재하지 않습니다.');
                window.location.href = 'https://supreme.snuh.org/';
            });
    }, []);

    const handleIrbChangeByObj = (irbNo: string) => {
        setIrb(irbNo);
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
            <Grid item xs={3.5}>
                <IrbSelector irb={irb} onChange={handleIrbChangeByObj} />
            </Grid>
            <Grid item xs={5}>
                <RidForm irb={irb} rid={rid} onChange={handleRidChange} onSubmit={handleRidSubmit} />
            </Grid>
            <Grid item xs={3.5}>
                <PatientInfo patient={patient} />
            </Grid>
        </Grid>
    );
};

export default RecordSearchPanel;
