import { Box, Grid, IconButton, InputAdornment, TextField, Typography } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';

const PatientInfo = () => {
  const fieldLabelStyle = {
    fontSize: '0.7rem',
    fontWeight: 600,
    color: '#546e7a',
    mb: 0.3,
    textTransform: 'uppercase' as const,
    letterSpacing: '0.3px',
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis'
  };

  const fieldBoxStyle = {
    mb: 0.5
  };

  const inputFieldStyle = {
    '& .MuiOutlinedInput-root': {
      bgcolor: '#fff',
      fontSize: '0.8rem',
      minHeight: '30px',
      '& fieldset': {
        borderColor: '#1976d2',
      },
      '&:hover fieldset': {
        borderColor: '#1976d2',
      },
      '&.Mui-focused fieldset': {
        borderColor: '#1976d2',
        borderWidth: '2px'
      }
    },
    '& .MuiOutlinedInput-input': {
      padding: '5px 10px',
      fontWeight: 500
    }
  };

  const readOnlyFieldStyle = {
    '& .MuiOutlinedInput-root': {
      bgcolor: '#f8f9fa',
      fontSize: '0.8rem',
      '& fieldset': {
        borderColor: '#e0e0e0',
      }
    },
    '& .MuiOutlinedInput-input': {
      padding: '6px 10px',
      color: '#37474f'
    }
  };

  const infoTextStyle = {
    fontSize: '0.8rem',
    color: '#37474f',
    bgcolor: '#f8f9fa',
    border: '1px solid #e0e0e0',
    borderRadius: '4px',
    px: 1.2,
    py: 0.7,
    minHeight: '30px',
    display: 'flex',
    alignItems: 'center'
  };

  return (
    <Box>
      <Grid container spacing={1}>
        <Grid item xs={6}>
          <Box sx={fieldBoxStyle}>
            <Typography sx={fieldLabelStyle}>등록번호</Typography>
            <TextField
              size="small"
              fullWidth
              placeholder="환자 등록번호 입력 후 검색"
              sx={inputFieldStyle}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton size="small" edge="end" sx={{ color: '#1976d2' }}>
                      <SearchIcon fontSize="small" />
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
          </Box>
        </Grid>
        <Grid item xs={3}>
          <Box sx={fieldBoxStyle}>
            <Typography sx={fieldLabelStyle}>성명</Typography>
            <Box sx={infoTextStyle}>-</Box>
          </Box>
        </Grid>
        <Grid item xs={3}>
          <Box sx={fieldBoxStyle}>
            <Typography sx={fieldLabelStyle}>성별/나이</Typography>
            <Box sx={infoTextStyle}>-</Box>
          </Box>
        </Grid>
        <Grid item xs={6}>
          <Box sx={fieldBoxStyle}>
            <Typography sx={fieldLabelStyle}>주민번호</Typography>
            <Box sx={infoTextStyle}>-</Box>
          </Box>
        </Grid>
        <Grid item xs={6}>
          <Box sx={fieldBoxStyle}>
            <Typography sx={fieldLabelStyle}>진료부서</Typography>
            <Box sx={infoTextStyle}>-</Box>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

export default PatientInfo;
