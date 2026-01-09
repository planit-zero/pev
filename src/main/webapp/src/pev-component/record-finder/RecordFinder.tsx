import * as React from 'react';
import { Box, Grid, Paper, useTheme } from '@mui/material';
import ConditionFinder from './condition-finder/ConditionFinder';
import RecordSearchPanel from './search-panel/RecordSearchPanel';
import { gridSpacing } from 'store/constant';
import PatientInfo from './patient-info';
import RecordList from './record-list';
import FormList from './form-list';

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
    <Grid container direction="column" height={"calc(100vh - 48px)"} >
      <Grid item xs={2}>
        <Box ml={2} mt={1}>
          <PatientInfo />
        </Box>
      </Grid>
      <Grid item xs={5}>
        <Box ml={2} mt={1}>
          <RecordList />
        </Box>
      </Grid>
      <Grid item xs={5}>
        <Box ml={2} mt={1}>
          <FormList />
        </Box>
      </Grid>
    </Grid >
  );
};

export default RecordFinder;
{/* <box sx={{ pl: 2, pr: 0, pb: 2, width: '100%', height: '100%' }}> */ }
{/*   <box sx={{ width: '100%', height: '52px', mb: 2.5 }}> */ }
{/*     <patientinfo /> */ }
{/*     <recordsearchpanel setcurrentirb={setcurrentirb} setcurrentrid={setcurrentrid} /> */ }
{/*   </box> */ }
{/*   <c
onditionfinder currentirb={currentirb} currentrid={currentrid} /> */ }
{/* </box> */ }
