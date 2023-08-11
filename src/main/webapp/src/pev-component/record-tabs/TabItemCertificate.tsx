import * as React from 'react';
import { Box, Button, Divider, Grid, Typography } from '@mui/material';
import { Search, Bookmark } from '@mui/icons-material';
import { DataGrid } from 'devextreme-react';
import { Column } from 'devextreme-react/data-grid';

const TabItemCertificate = () => {
    return (
        <Grid container sx={{ mt: 2, height: 'calc(100% - 81px)' }}>
            <Grid item xs={12} height={'30.75px'}>
                <Box display={'flex'} justifyContent={'space-between'} alignItems={'center'}>
                    <Box display={'flex'} alignItems={'center'}>
                        <Bookmark color={'primary'} fontSize={'small'} />
                        <Typography variant={'body1'}>진단서/의뢰서</Typography>
                    </Box>
                    <Button variant={'contained'} startIcon={<Search />} size={'small'}>
                        목록 조회
                    </Button>
                </Box>
            </Grid>
            <Grid item xs={12}>
                <Divider sx={{ mt: 1, mb: 1 }} />
            </Grid>
            <Grid item xs={12} sx={{ height: 'calc(100% - 30.75px)' }}>
                <Box height={'100%'}>
                    <DataGrid height={'100%'} showBorders={true} showColumnLines={true} showRowLines={true}>
                        <Column dataField={'pactTpCd'} caption={'구분'} alignment={'center'} width={120} />
                        <Column dataField={'writingDate'} caption={'작성일'} alignment={'center'} width={120} />
                        <Column dataField={'writingDeptNm'} caption={'작성과'} alignment={'center'} width={150} />
                        <Column dataField={'itemNm'} caption={'기록명'} />
                        <Column dataField={'mdrcWrtStsCd'} caption={'서명'} alignment={'center'} width={80} />
                    </DataGrid>
                </Box>
            </Grid>
        </Grid>
    );
};

export default TabItemCertificate;
