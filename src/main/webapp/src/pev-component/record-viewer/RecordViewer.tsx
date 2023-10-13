import * as React from 'react';
import { useSelector } from '../../store';
import { Box, IconButton, Tooltip } from '@mui/material';
import { Article, MenuBook } from '@mui/icons-material';
import RecordSheetContainer from './RecordSheetContainer';
import { setViewMode } from '../../store/pev-slices/environment';

const RecordViewer = () => {
    const { viewMode } = useSelector((state) => state.environment);

    const handleViewModeChange = () => {
        if (viewMode === 'single') {
            setViewMode('double');
            return;
        }

        if (viewMode === 'double') {
            setViewMode('single');
            return;
        }
    };

    const getTooltipTitle = () => {
        if (viewMode === 'single') return '두 페이지 모드로 변경합니다.';
        if (viewMode === 'double') return '한 페이지 모드로 변경합니다.';
    };

    return (
        <Box>
            <Box position={'fixed'} top={'95px'} right={'30px'}>
                <Tooltip title={getTooltipTitle()} placement={'left'}>
                    <IconButton onClick={handleViewModeChange}>
                        {viewMode === 'double' && <Article />}
                        {viewMode === 'single' && <MenuBook />}
                    </IconButton>
                </Tooltip>
            </Box>
            <Box
                sx={{
                    width: '100%',
                    display: 'flex',
                    justifyContent: 'flex-start',
                    flexWrap: 'wrap',
                    gap: 1,
                    flexDirection: viewMode === 'single' ? 'column' : 'row',
                    alignItems: viewMode === 'single' ? 'center' : 'stretch'
                }}
            >
                <RecordSheetContainer />
            </Box>
        </Box>
    );
};

export default RecordViewer;
