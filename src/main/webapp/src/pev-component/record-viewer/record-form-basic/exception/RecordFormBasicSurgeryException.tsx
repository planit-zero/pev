import * as React from 'react';
import { IRecordFormInfoR } from '../../../../pev-interface/IRecordInfo';
import { useGetSurgeryDefaultValueListMutation } from '../../../../pev-service/RecordExceptionService';
import { Box, Skeleton, Typography } from '@mui/material';

type RecordFormBasicSurgeryExceptionProps = {
    recordFormInfo: IRecordFormInfoR;
};

const RecordFormBasicSurgeryException = (props: RecordFormBasicSurgeryExceptionProps) => {
    const [getSurgeryDefaultValueList, { data: surgeryDefaultValueList }] = useGetSurgeryDefaultValueListMutation();

    React.useEffect(() => {
        getSurgeryDefaultValueList({
            mdrcId: props.recordFormInfo.mdrcId,
            mdrcFomSeq: props.recordFormInfo.mdrcFomSeq
        });
    }, []);

    const concatNameList = (nameList: string[]) => {
        let name = '';

        nameList.forEach((n, idx) => {
            if (idx === 0 && n) name += n;
            if (idx !== 0 && n) name += `/${n}`;
        });

        return name;
    };

    return (
        <React.Fragment>
            {surgeryDefaultValueList && (
                <React.Fragment>
                    <Box sx={{ mb: 2 }}>
                        {surgeryDefaultValueList
                            .filter((surgeryDefaultValue) => ['11', '12'].includes(surgeryDefaultValue.oprcElmtClsCd))
                            .map((surgeryDefaultValue, idx) => {
                                if (surgeryDefaultValue.oprcElmtClsCd === '11') {
                                    return (
                                        <React.Fragment key={idx}>
                                            <Typography sx={{ fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' }}>
                                                수술명
                                            </Typography>
                                            <Typography sx={{ fontSize: `h5.fontSize` }}>- {surgeryDefaultValue.opNmDgnsNm}</Typography>
                                        </React.Fragment>
                                    );
                                }

                                if (surgeryDefaultValue.oprcElmtClsCd === '12') {
                                    return <Typography sx={{ fontSize: `h5.fontSize` }}>({surgeryDefaultValue.opNmDgnsNm})</Typography>;
                                }
                            })}
                    </Box>
                    <Box sx={{ mb: 2 }}>
                        {surgeryDefaultValueList
                            .filter((surgeryDefaultValue) => ['13', '14'].includes(surgeryDefaultValue.oprcElmtClsCd))
                            .map((surgeryDefaultValue, idx) => {
                                if (surgeryDefaultValue.oprcElmtClsCd === '13') {
                                    return (
                                        <React.Fragment key={idx}>
                                            <Typography sx={{ fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' }}>
                                                수술전 진단명
                                            </Typography>
                                            <Typography sx={{ fontSize: `h5.fontSize` }}>- {surgeryDefaultValue.opNmDgnsNm}</Typography>
                                        </React.Fragment>
                                    );
                                }

                                if (surgeryDefaultValue.oprcElmtClsCd === '14') {
                                    return <Typography sx={{ fontSize: `h5.fontSize` }}>({surgeryDefaultValue.opNmDgnsNm})</Typography>;
                                }
                            })}
                    </Box>
                    <Box sx={{ mb: 2 }}>
                        {surgeryDefaultValueList
                            .filter((surgeryDefaultValue) => ['15', '16'].includes(surgeryDefaultValue.oprcElmtClsCd))
                            .map((surgeryDefaultValue, idx) => {
                                if (surgeryDefaultValue.oprcElmtClsCd === '15') {
                                    return (
                                        <React.Fragment key={idx}>
                                            <Typography sx={{ fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' }}>
                                                수술후 진단명
                                            </Typography>
                                            <Typography sx={{ fontSize: `h5.fontSize` }}>- {surgeryDefaultValue.opNmDgnsNm}</Typography>
                                        </React.Fragment>
                                    );
                                }

                                if (surgeryDefaultValue.oprcElmtClsCd === '16') {
                                    return <Typography sx={{ fontSize: `h5.fontSize` }}>({surgeryDefaultValue.opNmDgnsNm})</Typography>;
                                }
                            })}
                    </Box>
                    <Box sx={{ mb: 2 }}>
                        {surgeryDefaultValueList
                            .filter((surgeryDefaultValue) => ['17'].includes(surgeryDefaultValue.oprcElmtClsCd))
                            .map((surgeryDefaultValue, idx) => {
                                if (surgeryDefaultValue.oprcElmtClsCd === '17') {
                                    return (
                                        <React.Fragment key={idx}>
                                            <Typography
                                                sx={{ fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' }}
                                                display={'inline'}
                                            >
                                                마취종류 :&nbsp;
                                            </Typography>
                                            <Typography sx={{ fontSize: `h4.fontSize` }} display={'inline'}>
                                                {surgeryDefaultValue.anstKndNm}
                                            </Typography>
                                            <br />
                                            <Typography
                                                sx={{ fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' }}
                                                display={'inline'}
                                            >
                                                수술일자 :&nbsp;
                                            </Typography>
                                            <Typography sx={{ fontSize: `h4.fontSize` }} display={'inline'}>
                                                {surgeryDefaultValue.opDtm}
                                            </Typography>
                                            <br />
                                            <Typography
                                                sx={{ fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' }}
                                                display={'inline'}
                                            >
                                                집도의 :&nbsp;
                                            </Typography>
                                            <Typography sx={{ fontSize: `h4.fontSize` }} display={'inline'}>
                                                {concatNameList([
                                                    surgeryDefaultValue.th1PfdrStfNm,
                                                    surgeryDefaultValue.th2PfdrStfNm,
                                                    surgeryDefaultValue.th3PfdrStfNm
                                                ])}
                                            </Typography>
                                            <br />
                                            <Typography
                                                sx={{ fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' }}
                                                display={'inline'}
                                            >
                                                보조의 :&nbsp;
                                            </Typography>
                                            <Typography sx={{ fontSize: `h4.fontSize` }} display={'inline'}>
                                                {concatNameList([
                                                    surgeryDefaultValue.th1AtdrStfNm,
                                                    surgeryDefaultValue.th2AtdrStfNm,
                                                    surgeryDefaultValue.th3AtdrStfNm,
                                                    surgeryDefaultValue.th4AtdrStfNm
                                                ])}
                                            </Typography>
                                        </React.Fragment>
                                    );
                                }
                            })}
                    </Box>
                </React.Fragment>
            )}
            {!surgeryDefaultValueList && (
                <React.Fragment>
                    <Skeleton width={'30%'} height={20} />
                    <Skeleton width={'50%'} height={50} variant={'rectangular'} />
                    <br />
                    <Skeleton width={'30%'} height={20} />
                    <Skeleton width={'50%'} height={50} variant={'rectangular'} />
                    <br />
                    <Skeleton width={'30%'} height={20} />
                    <Skeleton width={'50%'} height={50} variant={'rectangular'} />
                    <br />
                    <Skeleton width={'30%'} height={50} variant={'rectangular'} />
                </React.Fragment>
            )}
        </React.Fragment>
    );
};

export default RecordFormBasicSurgeryException;
