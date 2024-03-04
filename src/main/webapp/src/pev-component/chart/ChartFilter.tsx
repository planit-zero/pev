import * as React from 'react';
import { Box, Grid, IconButton, CircularProgress, TextField, Typography } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { ClearOutlined } from '@mui/icons-material';
import { setAlert } from '../../store/pev-slices/environment';

type ChartFilterProps = {
    allCount: number;
    loadedCount: number;
    searchTargetCount: number;
    searchText: string | null;
    onSearchTextChange: (value: string | null) => void;
    onSearchTextApply: () => void;
    onSearchTextClear: () => void;
};

const ChartFilter = (props: ChartFilterProps) => {
    const handleSearchClick = () => {
        if (!props.searchText || props.searchText === '') {
            setAlert({
                type: 'warning',
                message: '내용을 입력하신 후 검색해주세요.'
            });
        } else {
            props.onSearchTextApply();
        }
    };

    const handleSearchPressKey = (e: React.KeyboardEvent) => {
        if (e.code === 'Enter') handleSearchClick();
    };

    const handleClearClick = () => {
        props.onSearchTextClear();
    };

    return (
        <Box sx={{ position: 'fixed', top: 0, zIndex: 1 }}>
            <Box sx={{ width: '600px', mt: 0.5 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Grid container spacing={1}>
                        <Grid item xs={2} sx={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center' }}>
                            <Box sx={{ textAlign: 'right' }}>
                                <Typography sx={{ fontSize: '11px' }}>
                                    {props.loadedCount} / {props.allCount}
                                </Typography>
                            </Box>
                        </Grid>
                        <Grid item xs={1} sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                            <Box sx={{ position: 'relative', display: 'inline-flex' }}>
                                <CircularProgress variant={'determinate'} value={(props.loadedCount / props.allCount) * 100} />
                                <Box
                                    sx={{
                                        top: 0,
                                        left: 0,
                                        bottom: 0,
                                        right: 0,
                                        position: 'absolute',
                                        display: 'flex',
                                        justifyContent: 'center',
                                        alignItems: 'center'
                                    }}
                                >
                                    <Typography sx={{ fontSize: '11px' }}>
                                        {Math.round((props.loadedCount / props.allCount) * 100)}%
                                    </Typography>
                                </Box>
                            </Box>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                fullWidth
                                size={'small'}
                                placeholder={'검색할 내용을 입력해주세요.'}
                                value={props.searchText || ''}
                                onChange={(e) => props.onSearchTextChange(e.target.value)}
                                onKeyDown={(e) => handleSearchPressKey(e)}
                            />
                        </Grid>
                        <Grid item xs={1} sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                            <IconButton size={'small'} color={'primary'} onClick={handleSearchClick}>
                                <SearchIcon />
                            </IconButton>
                        </Grid>
                        <Grid item xs={2} sx={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}>
                            <IconButton size={'small'} color={'error'} onClick={handleClearClick}>
                                <ClearOutlined />
                            </IconButton>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Box>
    );
};

export default ChartFilter;
