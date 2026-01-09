import { Paper } from "@mui/material";
import React from "react"
import MainCard from "ui-component/cards/MainCard"

import { Grid } from "@mui/material";

const PatientInfo = () => {
  return <Grid container spacing={1} padding={1} >
    <Grid item xs={6}> 1 </Grid>
    <Grid item xs={6}> 2 </Grid>
    <Grid item xs={6}> 3 </Grid>
    <Grid item xs={6}> 4 </Grid>
    <Grid item xs={6}> 5 </Grid>
    <Grid item xs={6}> 6 </Grid>
  </Grid>

}

export default PatientInfo;
