import * as React from 'react';
import { Box, Tab, Tabs } from '@mui/material';
import ConditionFinder from './condition-finder/ConditionFinder';
import CertificateFinder from './certificate-finder/CertificateFinder';

const RecordFinder = () => {
    const finderItems: string[] = ['수진일별\r\n기록조회', '조건별\r\n상세조회', '진단서', '동의서', '특성화\r\n리포트'];

    const [activeFinderItem, setActiveFinderItem] = React.useState<number>(1);

    const handleItemChange = (event: React.SyntheticEvent, value: number) => {
        setActiveFinderItem(value);
    };

    return (
        <Box sx={{ px: 2, pb: 2, height: 'calc(100% - 68px)' }}>
            <Box sx={{ mb: 1 }}>
                <Tabs variant={'fullWidth'} value={activeFinderItem} onChange={handleItemChange}>
                    {finderItems.map((item, idx) => {
                        return <Tab key={idx} value={idx} label={item} sx={{ whiteSpace: 'pre-line' }} />;
                    })}
                </Tabs>
            </Box>
            {activeFinderItem === 1 && <ConditionFinder />}
            {activeFinderItem === 2 && <CertificateFinder />}
        </Box>
    );
};

export default RecordFinder;
