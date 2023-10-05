import * as React from 'react';
import { Box, Tab, Tabs } from '@mui/material';
import ConditionFinder from './condition-finder/ConditionFinder';

type RecordFinderProps = {};

const RecordFinder = (props: RecordFinderProps) => {
    const finderItems: string[] = ['수진일별\r\n기록조회', '조건별\r\n상세조회', '진단서', '동의서', '특성화\r\n리포트'];

    const [activeFinderItem, setActiveFinderItem] = React.useState<number>(1);

    const handleItemChange = (event: React.SyntheticEvent, value: number) => {
        setActiveFinderItem(value);
    };

    return (
        <Box sx={{ p: 2, height: '100%' }}>
            <Box>
                <Tabs variant={'fullWidth'} value={activeFinderItem} onChange={handleItemChange}>
                    {finderItems.map((item, idx) => {
                        return <Tab key={idx} value={idx} label={item} sx={{ whiteSpace: 'pre-line' }} />;
                    })}
                </Tabs>
            </Box>
            {activeFinderItem === 1 && <ConditionFinder />}
            {activeFinderItem !== 1 && <span>서비스 준비 중입니다.</span>}
        </Box>
    );
};

export default RecordFinder;
