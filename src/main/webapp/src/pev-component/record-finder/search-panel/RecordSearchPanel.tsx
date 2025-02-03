import * as React from 'react';
import { Grid } from '@mui/material';
import IrbSelector from './IrbSelector';
import RidForm from './RidForm';
import {useGetPatientMutation, useGetRidByGidMutation} from '../../../pev-service/PatientService';
import {IPatientR, IPatientRidP, IRidByGidP} from '../../../pev-interface/IPatient';
import PatientInfo from './PatientInfo';
import { useSearchParams } from 'react-router-dom';
import { useGetIdpLoginUserMutation } from '../../../pev-service/UserService';
import { setUserInfo } from '../../../store/slices/user';
import { CryptoUtils } from '../../../pev-utils/CryptoUtils';
import { useSelector } from '../../../store';
import { UrlUtils } from '../../../pev-utils/UrlUtils';

type RecordSearchPanelProps = {
    setCurrentIrb: (irb: string | null) => void;
    setCurrentRid: (rid: string | null) => void;
};

const RecordSearchPanel = (props: RecordSearchPanelProps) => {
    const [searchParams] = useSearchParams();

    const { profile } = useSelector((state) => state.environment);

    const [irb, setIrb] = React.useState<string | null>(null);
    const [rid, setRid] = React.useState<string>('');
    const [patient, setPatient] = React.useState<IPatientR | null>(null);
    const [feasibility, setFeasibility] = React.useState<boolean>(false);

    const [getPatient] = useGetPatientMutation();
    const [getRidByGid] = useGetRidByGidMutation();
    const [getIdpLoginUser] = useGetIdpLoginUserMutation();

    React.useEffect(() => {
        handlePanel()
    }, []);

    const handlePanel = async () => {
        try {
            const token = searchParams.get('token');
            const key = searchParams.get('key');
            const user = await getIdpLoginUser(token).unwrap()

            setUserInfo(user);

            if (key) {
                const decryptStr = CryptoUtils.decrypt(key, 'planitsquare2023');
                const decryptArr = decryptStr.split('|||');

                if (decryptArr.length === 3) {
                    let irbParam = decryptArr[0];
                    let ridParam = decryptArr[1];
                    const feasibilityParam = decryptArr[2];

                    // 미리보기 기능이라면
                    if (feasibilityParam === 'true') {
                        const gid = ridParam;
                        irbParam = 'FEASIBILITY_CHECK';
                        setFeasibility(true);

                        const payload: IRidByGidP = {
                            stfNo: user.stfNo,
                            irbNo: irbParam,
                            data: [{gid}]
                        }

                        // GID -> RID
                        const response = await getRidByGid(payload).unwrap();

                        ridParam = response.data[0].rid || '';
                    }

                    handleIrbChangeByObj(irbParam);
                    handleRidChange(ridParam);

                    handleRidSubmit(irbParam, ridParam);
                }
            }
        } catch(err) {
            window.location.href = UrlUtils.getIdpUrl(profile);
        }
    }

    const handleIrbChangeByObj = (irbNo: string) => {
        setIrb(irbNo);
        props.setCurrentIrb(irbNo);
    };

    const handleRidChange = (value: string) => {
        setRid(value);
        props.setCurrentRid(value);
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
                <IrbSelector irb={irb} onChange={handleIrbChangeByObj} feasibility={feasibility} />
            </Grid>
            <Grid item xs={5}>
                <RidForm irb={irb} rid={rid} onChange={handleRidChange} onSubmit={handleRidSubmit} feasibility={feasibility} />
            </Grid>
            <Grid item xs={3.5}>
                <PatientInfo patient={patient} />
            </Grid>
        </Grid>
    );
};

export default RecordSearchPanel;
