import { Box, Grid, Paper, Typography, useTheme } from '@mui/material';
import * as React from 'react';
import FormList from './form-list';
import PatientInfo from './patient-info';
import RecordList from './record-list';

interface SectionBoxProps {
  title: string;
  color: string;
  children: React.ReactNode;
  flex: number;
  isLast?: boolean;
}

const SectionBox: React.FC<SectionBoxProps> = ({ title, color, children, flex, isLast = false }) => {
  return (
    <Grid item sx={{ flex: flex }}>
      <Box ml={2} mt={1} mb={isLast ? 2 : 1} sx={{ height: '100%' }}>
        <Paper
          elevation={0}
          sx={{
            border: '1px solid',
            borderColor: '#e0e0e0',
            borderRadius: '4px',
            bgcolor: '#fff',
            height: '100%',
            display: 'flex',
            flexDirection: 'column',
            overflow: 'hidden'
          }}
        >
          <Box
            sx={{
              bgcolor: '#f8f9fa',
              borderBottom: '2px solid #1976d2',
              px: 2.5,
              py: 1.2,
              display: 'flex',
              alignItems: 'center',
              gap: 1
            }}
          >
            <Box
              sx={{
                width: 4,
                height: 16,
                bgcolor: color,
                borderRadius: '2px'
              }}
            />
            <Typography
              variant="body2"
              fontWeight="600"
              sx={{
                fontSize: '0.875rem',
                color: '#37474f',
                letterSpacing: '0.3px',
                textTransform: 'uppercase'
              }}
            >
              {title}
            </Typography>
          </Box>
          <Box sx={{ p: 2.5, flex: 1, overflow: 'auto' }}>
            {children}
          </Box>
        </Paper>
      </Box>
    </Grid>
  );
};

// const RecordFinder = () => {
//     const [currentIrb, setCurrentIrb] = React.useState<string | null>(null);
//     const [currentRid, setCurrentRid] = React.useState<string | null>(null);
//
//     return (
//         <Box sx={{ pl: 2, pr: 0, pb: 2, width: '100%', height: '100%' }}>
//             <Box sx={{ width: '100%', height: '52px', mb: 2.5 }}>
//                 <RecordSearchPanel setCurrentIrb={setCurrentIrb} setCurrentRid={setCurrentRid} />
//             </Box>
//             <ConditionFinder currentIrb={currentIrb} currentRid={currentRid} />
//         </Box>
//     );
// };
//
// export default RecordFinder;
const RecordFinder = () => {
  const [currentIrb, setCurrentIrb] = React.useState<string | null>(null);
  const [currentRid, setCurrentRid] = React.useState<string | null>(null);
  const theme = useTheme();

  return (
    <Grid container direction="column" height={"calc(100vh - 48px)"} spacing={1} sx={{ flexWrap: 'nowrap', bgcolor: '#fafafa', p: 1 }}>
      <SectionBox title="환자 정보" color="#1976d2" flex={2}>
        <PatientInfo />
      </SectionBox>
      <SectionBox title="기록 목록" color="#0288d1" flex={5}>
        <RecordList />
      </SectionBox>
      <SectionBox title="서식 목록" color="#0097a7" flex={5} isLast>
        <FormList />
      </SectionBox>
    </Grid >
  );
};

export default RecordFinder;
