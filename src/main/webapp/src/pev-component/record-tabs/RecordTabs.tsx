import * as React from 'react';
import { Box, Tab, Tabs } from '@mui/material';
import TabItemCertificate from './TabItemCertificate';
import TabItemDetail from './TabItemDetail';

const RecordTabs = () => {
    const tabItems: string[] = ['수진일별 기록조회', '조건별 상세조회', '진단서', '동의서', '특성화 리포트'];

    const [activeTabItem, setActiveTabItem] = React.useState<number>(1);

    const handleTabChange = (event: React.SyntheticEvent, value: number) => {
        setActiveTabItem(value);
    };

    return (
        <Box sx={{ p: 2, height: '100%' }}>
            <Box>
                <Tabs variant={'fullWidth'} value={activeTabItem} onChange={handleTabChange}>
                    {tabItems.map((item, idx) => {
                        return <Tab key={idx} value={idx} label={item} />;
                    })}
                </Tabs>
            </Box>
            {activeTabItem === 1 && <TabItemDetail />}
            {activeTabItem === 2 && <TabItemCertificate />}
        </Box>
    );
};

export default RecordTabs;
