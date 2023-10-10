import * as React from 'react';
import { Box, Button, Divider, Typography } from '@mui/material';
import { Bookmark } from '@mui/icons-material';
import { IRecord } from '../../../pev-interface/IRecord';
import { DataGrid } from 'devextreme-react';
import { Column, Scrolling, Selection } from 'devextreme-react/data-grid';
import SearchIcon from '@mui/icons-material/Search';
import { setTargetRecords } from '../../../store/pev-slices/record';
import { setAlert } from '../../../store/pev-slices/environment';
import { TAlert } from '../../../pev-type/TAlert';

type ConditionFinderGridProps = {
    recordList: IRecord[];
};

const ConditionFinderGrid = (props: ConditionFinderGridProps) => {
    const [selectedRecordList, setSelectedRecordList] = React.useState<IRecord[]>([]);

    const handleSelectionChanged = (e: any) => {
        if (e && e.selectedRowsData) setSelectedRecordList(e.selectedRowsData);
    };

    const handleRetrieve = () => {
        if (selectedRecordList.length === 0) {
            setAlert({ type: TAlert.WARNING, message: '기록 목록에서 기록을 한 개 이상 선택 후 조회 버튼을 눌러주세요.' });
            return;
        }
        setTargetRecords(selectedRecordList);
    };

    return (
        <Box width={'100%'} height={'calc(100% - 310px)'} marginTop={'20px'}>
            <Box width={'100%'} height={'31px'} display={'flex'} justifyContent={'space-between'} alignItems={'center'}>
                <Box display={'flex'} alignItems={'center'}>
                    <Bookmark color={'primary'} fontSize={'small'} />
                    <Typography variant={'body1'}>기록목록</Typography>
                </Box>
                <Box display={'flex'} alignItems={'center'}>
                    <Button variant={'contained'} size={'small'} startIcon={<SearchIcon fontSize="small" />} onClick={handleRetrieve}>
                        조회
                    </Button>
                </Box>
            </Box>
            <Divider sx={{ mt: 1, mb: 1 }} />
            <Box width={'100%'} height={'calc(100% - 48px)'}>
                <DataGrid
                    dataSource={props.recordList}
                    height={'100%'}
                    showBorders={true}
                    showColumnLines={true}
                    showRowLines={true}
                    wordWrapEnabled={true}
                    noDataText={''}
                    onSelectionChanged={handleSelectionChanged}
                >
                    <Column dataField={'pactTpNm'} caption={'환자구분'} alignment={'center'} width={50} />
                    <Column dataField={'itemType'} caption={'항목구분'} alignment={'center'} width={80} />
                    <Column dataField={'itemNm'} caption={'항목명'} alignment={'left'} width={180} />
                    <Column dataField={'writingDate'} caption={'작성일자'} width={90} alignment={'center'} />
                    <Column dataField={'writingDeptNm'} caption={'작성과'} width={100} alignment={'left'} />
                    <Column dataField={'writerNm'} caption={'작성자'} width={80} alignment={'center'} />
                    <Column dataField={'mdrcWrtStsCdYn'} caption={'서명'} alignment={'center'} width={60} />
                    <Scrolling mode={'virtual'} />
                    <Selection mode={'multiple'} showCheckBoxesMode={'onClick'} />
                </DataGrid>
            </Box>
        </Box>
    );
};

export default ConditionFinderGrid;
