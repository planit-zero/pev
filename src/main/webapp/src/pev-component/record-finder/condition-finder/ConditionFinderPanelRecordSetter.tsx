import * as React from 'react';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import { Box, Button, Grid, Popover, Typography } from '@mui/material';
import { Square } from '@mui/icons-material';
import { IRecordType } from '../../../pev-interface/IRecordDetail';
import { TreeView } from 'devextreme-react';

type ConditionFinderPanelRecordSetterProps = {
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
};

const ConditionFinderPanelRecordSetter = (props: ConditionFinderPanelRecordSetterProps) => {
    const initialRecordTypes: IRecordType[] = [
        {
            id: 'DR',
            name: '진료기록',
            selected: true,
            items: [
                { id: 'D001', name: '외래초진', selected: true, items: [] },
                { id: 'D002', name: '외래경과', selected: true, items: [] },
                { id: 'D003', name: '입원초진', selected: true, items: [] },
                { id: 'D004', name: '입원경과', selected: true, items: [] },
                { id: 'D031', name: '응급기록', selected: true, items: [] },
                { id: 'D005', name: '수술기록', selected: true, items: [] },
                { id: 'D006', name: '퇴원기록', selected: true, items: [] },
                { id: 'D009', name: '진단서', selected: true, items: [] },
                { id: 'D035', name: '산재진단서', selected: true, items: [] },
                { id: 'D010', name: '마취기록', selected: true, items: [] },
                { id: 'D011', name: '마취전평가', selected: true, items: [] },
                { id: 'D007', name: '타과의뢰', selected: true, items: [] },
                { id: 'D020', name: '과별서식', selected: true, items: [] },
                { id: 'D030', name: '의무기록표지', selected: true, items: [] }
            ]
        },
        { id: 'OR', name: '처방', selected: false, items: [] },
        { id: 'NR', name: '간호기록', selected: false, items: [] },
        {
            id: 'EX',
            name: '검사',
            selected: false,
            items: [
                { id: 'EX_PICTURE', name: '영상검사', selected: false, items: [] },
                { id: 'EX_PATHOLOGY', name: '병리검사', selected: false, items: [] },
                { id: 'EX_SPECIMEN', name: '검체검사', selected: false, items: [] },
                { id: 'EX_FUNCTION', name: '기능검사', selected: false, items: [] }
            ]
        },
        { id: 'SC', name: '스캔 자료', selected: false, items: [] },
        { id: 'SR', name: '특성화 기록', selected: false, items: [] }
    ];

    const [recordTypes] = React.useState<IRecordType[]>(initialRecordTypes);

    const RecordTypeItemRender = (item: IRecordType) => {
        return item.name;
    };

    const treeViewRef = React.useRef<TreeView>(null);

    const handleTreeViewSelectionChanged = () => {
        if (treeViewRef && treeViewRef.current) {
            const selectedNodes = treeViewRef.current.instance.getSelectedNodes().map((node) => node.key);
            props.onSearchConditionChange([{ key: 'searchTargets', value: selectedNodes }]);
        }
    };

    const [anchorEl, setAnchorEl] = React.useState<HTMLButtonElement | null>(null);

    const handleAnchorClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleAnchorClose = () => {
        setAnchorEl(null);
    };

    return (
        <Grid container display={'flex'} alignItems={'center'}>
            <Grid item xs={2} display={'flex'} alignItems={'center'}>
                <Square color={'primary'} sx={{ width: 10, mr: 1 }} />
                <Typography variant={'body1'}>기록유형</Typography>
            </Grid>
            <Grid item xs={10} display={'flex'} alignItems={'center'}>
                <Box sx={{ width: '100%' }}>
                    <Button variant={'outlined'} size={'small'} onClick={handleAnchorClick}>
                        기록유형 선택
                    </Button>
                    <Popover
                        sx={{ height: '619px' }}
                        open={Boolean(anchorEl)}
                        anchorEl={anchorEl}
                        onClose={handleAnchorClose}
                        anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
                    >
                        <Box sx={{ width: '300px', p: 1 }}>
                            <TreeView
                                ref={treeViewRef}
                                items={recordTypes}
                                selectNodesRecursive={true}
                                selectByClick={true}
                                showCheckBoxesMode={'normal'}
                                selectionMode={'multiple'}
                                itemRender={RecordTypeItemRender}
                                onSelectionChanged={handleTreeViewSelectionChanged}
                            />
                        </Box>
                    </Popover>
                </Box>
            </Grid>
        </Grid>
    );
};

export default ConditionFinderPanelRecordSetter;
