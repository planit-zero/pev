import * as React from 'react';

// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Box, Button, InputAdornment, OutlinedInput, ToggleButton, ToggleButtonGroup, useMediaQuery } from '@mui/material';

// assets
import { IconSearch } from '@tabler/icons';
import { shouldForwardProp } from '@mui/system';
import { IPatientR } from '../../../../pev-interface/IPatient';
import { useGetPatientWithGidMutation } from '../../../../pev-service/PatientService';

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

    const matchUpMd = useMediaQuery(theme.breakpoints.up('md'));
    const matchDownMd = useMediaQuery(theme.breakpoints.down('md'));
    const matchDownSm = useMediaQuery(theme.breakpoints.down('sm'));

    const [mode, setMode] = React.useState<string>('gid');

    const ModeSelector = () => {
        const handleModeChange = (event: React.MouseEvent<HTMLElement>, modeValue: string) => {
            setMode(modeValue);
        };

        return (
            <Box>
                <ToggleButtonGroup color={'primary'} value={mode} exclusive={true} onChange={handleModeChange}>
                    <ToggleButton value={'gid'}>
                        {matchUpMd && '가명화 환자 ID'}
                        {matchDownMd && !matchDownSm && 'GID'}
                        {matchDownSm && 'G'}
                    </ToggleButton>
                    <ToggleButton value={'rid'}>
                        {matchUpMd && '연구별 환자 ID'}
                        {matchDownMd && !matchDownSm && 'RID'}
                        {matchDownSm && 'R'}
                    </ToggleButton>
                </ToggleButtonGroup>
            </Box>
        );
    };

    const [patient, setPatient] = React.useState<IPatientR | null>(null);

    const GidSearchPanel = () => {
        const [gid, setGid] = React.useState<string>('G010000741553');

        const [getPatientWithGid, { isLoading: gidLoading }] = useGetPatientWithGidMutation();

        const handleGidSearch = () => {
            getPatientWithGid({ gid: gid })
                .unwrap()
                .then((data) => setPatient(data))
                .catch(() => setPatient(null));
        };

        return (
            <OutlineInputStyle
                id="input-search-header"
                value={gid}
                onChange={(e) => setGid(e.target.value)}
                placeholder="가명화 환자 ID 를 입력하세요."
                startAdornment={
                    <InputAdornment position="start">
                        <IconSearch stroke={1.5} size="16px" color={theme.palette.grey[500]} />
                    </InputAdornment>
                }
                endAdornment={
                    <InputAdornment position="end">
                        <Button variant={'contained'} size={'small'} onClick={handleGidSearch} disabled={gidLoading}>
                            조회
                        </Button>
                    </InputAdornment>
                }
                aria-describedby="search-helper-text"
                inputProps={{ 'aria-label': 'weight' }}
            />
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
                <ModeSelector />
                <Box display={'flex'} alignItems={'center'} gap={1}>
                    <GidSearchPanel />
                </Box>
            </Box>
        </Box>
    );
};

export default SearchSection;
