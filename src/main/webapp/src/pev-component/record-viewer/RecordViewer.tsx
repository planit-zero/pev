import * as React from 'react';
import { useDispatch, useSelector } from '../../store';
import { Box, IconButton, Tooltip } from '@mui/material';
import { Article, MenuBook } from '@mui/icons-material';
import RecordSheetContainer from './RecordSheetContainer';
import { setFinderWidth, setViewMode } from '../../store/pev-slices/environment';
import { openDrawer } from '../../store/slices/menu';
import { IconLayoutSidebarLeftCollapse, IconLayoutSidebarLeftExpand } from '@tabler/icons';
import { finderWidthNarrow, finderWidthWide } from '../../store/constant';
import ChartContainer from '../chart/ChartContainer';

const RecordViewer = () => {
    const dispatch = useDispatch();

    const { drawerOpen } = useSelector((state) => state.menu);
    const { viewMode, finderWidth } = useSelector((state) => state.environment);

    const getNarrowTooltip = () => {
        if (finderWidth !== finderWidthNarrow) return '기록지 목록 조회 화면을 좁게 표시합니다.';
        if (finderWidth === finderWidthNarrow && drawerOpen) return '기록지 목록 조회 화면을 숨깁니다.';
        return '기록지 목록 조회 화면을 더 이상 좁게 표시할 수 없습니다.';
    };

    const getWideTooltip = () => {
        if (finderWidth === finderWidthNarrow && !drawerOpen) return '기록지 목록 조회 화면을 표시합니다.';
        if (finderWidth === finderWidthNarrow && drawerOpen) return '기록지 목록 조회 화면을 넓게 표시합니다.';
        return '기록지 목록 조회 화면을 더 이상 넓게 표시할 수 없습니다.';
    };

    const resizeToNarrow = () => {
        if (finderWidth === finderWidthWide) setFinderWidth(finderWidthNarrow);
        if (finderWidth === finderWidthNarrow && drawerOpen) dispatch(openDrawer(false));
        setViewMode('double');
    };

    const resizeToWide = () => {
        if (finderWidth === finderWidthNarrow && !drawerOpen) {
            dispatch(openDrawer(true));
            setViewMode('double');
        }
        if (finderWidth === finderWidthNarrow && drawerOpen) {
            setFinderWidth(finderWidthWide);
            setViewMode('single');
        }
    };

    return (
        <Box display={'flex'} justifyContent={'center'}>
            <Box display={'flex'} width={'100%'} justifyContent={'space-between'}>
                <Box position={'fixed'} top={68} left={drawerOpen ? finderWidth + 68 : 68} display={'flex'} flexDirection={'column'}>
                    <Tooltip title={getNarrowTooltip()} placement={'right'}>
                        <span>
                            <IconButton onClick={resizeToNarrow} disabled={!drawerOpen}>
                                <IconLayoutSidebarLeftCollapse />
                            </IconButton>
                        </span>
                    </Tooltip>
                    <Tooltip title={getWideTooltip()} placement={'right'}>
                        <span>
                            <IconButton onClick={resizeToWide} disabled={finderWidth === finderWidthWide}>
                                <IconLayoutSidebarLeftExpand />
                            </IconButton>
                        </span>
                    </Tooltip>
                </Box>
                <Box
                    sx={{
                        ml: viewMode === 'single' ? 0 : '-4px',
                        width: `100%`,
                        padding: `0 28px`,
                        display: 'flex',
                        justifyContent: 'center',
                        flexWrap: 'wrap',
                        gap: 1,
                        flexDirection: viewMode === 'single' ? 'column' : 'row',
                        alignItems: viewMode === 'single' ? 'center' : 'stretch'
                    }}
                >
                    <ChartContainer />
                </Box>
                <Box position={'fixed'} top={68} right={35} display={'flex'} flexDirection={'column'}>
                    <Tooltip title={'한 페이지 모드로 변경합니다.'} placement={'left'}>
                        <IconButton color={viewMode === 'single' ? 'primary' : 'default'} onClick={() => setViewMode('single')}>
                            <Article />
                        </IconButton>
                    </Tooltip>
                    <Tooltip title={'다중 페이지 모드로 변경합니다.'} placement={'left'}>
                        <IconButton color={viewMode === 'double' ? 'primary' : 'default'} onClick={() => setViewMode('double')}>
                            <MenuBook />
                        </IconButton>
                    </Tooltip>
                </Box>
            </Box>
        </Box>
    );
};

export default RecordViewer;
