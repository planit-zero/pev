import { Grid } from "@mui/material";

const PatientInfo = () => {
  return <Grid container spacing={1} padding={1} >
    <Grid item xs={12} container>
      <Grid item xs={6}>
        등록번호 :
      </Grid>
      <Grid item xs={6}>
        성명
      </Grid>
    </Grid>
    <Grid item xs={12} container>
      <Grid item xs={6}> 주민번호 </Grid>
      <Grid item xs={6}> 성별/나이 </Grid>
    </Grid>
    <Grid item xs={12} container>
      진료부서
    </Grid>
  </Grid>

}

export default PatientInfo;
