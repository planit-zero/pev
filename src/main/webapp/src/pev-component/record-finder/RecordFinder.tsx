import * as React from 'react';
import { Box } from '@mui/material';
import ConditionFinder from './condition-finder/ConditionFinder';
import SearchSection from '../../layout/MainLayout/Header/SearchSection';

const RecordFinder = () => {
    return (
        <Box sx={{ pl: 2, pr: 0, pb: 2, width: '100%', height: '100%' }}>
            <Box sx={{ width: '100%', height: '52px', mb: 2.5 }}>
                <SearchSection />
            </Box>
            <ConditionFinder />
        </Box>
    );
};

export default RecordFinder;
