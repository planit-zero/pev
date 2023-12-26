// material-ui
import { Grid, Stack, Switch, TextField } from '@mui/material';

// project imports
import useConfig from 'hooks/useConfig';

const InputFilled = () => {
    const { outlinedFilled, onChangeOutlinedField } = useConfig();

    return (
        <Grid item xs={12} container spacing={2} alignItems="center">
            <Grid item>
                <Stack spacing={2}>
                    <Switch
                        checked={outlinedFilled}
                        onChange={(event: React.ChangeEvent<HTMLInputElement>) => onChangeOutlinedField(event.target.checked)}
                        inputProps={{ 'aria-label': 'controlled' }}
                    />
                    <TextField fullWidth id="outlined-basic" label={outlinedFilled ? 'With Background' : 'Without Background'} />
                </Stack>
            </Grid>
        </Grid>
    );
};

export default InputFilled;
