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
      <Box ml={2} mt={1} mb={isLast ? 2 : 1} sx={{ position: 'relative', pt: 2, height: '100%' }}>
        <Box
          sx={{
            position: 'absolute',
            top: 0,
            left: 16,
            bgcolor: `${color}14`,
            color: color,
            px: 1.5,
            py: 0.4,
            borderRadius: '4px',
            borderLeft: `3px solid ${color}`,
            zIndex: 1,
            letterSpacing: '0.3px'
          }}
        >
          <Typography variant="body2" fontWeight="600" sx={{ fontSize: '0.875rem' }}>
            {title}
          </Typography>
        </Box>
        <Paper
          elevation={0}
          sx={{
            border: '1px solid',
            borderColor: 'grey.200',
            borderRadius: '8px',
            p: 2.5,
            bgcolor: '#fff',
            boxShadow: '0 1px 3px rgba(0,0,0,0.08)',
            height: '100%',
            display: 'flex',
            flexDirection: 'column'
          }}
        >
          {children}
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
    <Grid container direction="column" height={"calc(100vh - 48px)"} spacing={1} sx={{ flexWrap: 'nowrap' }}>
      <SectionBox title="환자 정보" color="#667eea" flex={2}>
        <PatientInfo />
      </SectionBox>
      <SectionBox title="기록 목록" color="#11998e" flex={5}>
        <RecordList />
      </SectionBox>
      <SectionBox title="서식 목록" color="#f5576c" flex={5} isLast>
        <FormList />
      </SectionBox>
    </Grid >
  );
};

export default RecordFinder;
