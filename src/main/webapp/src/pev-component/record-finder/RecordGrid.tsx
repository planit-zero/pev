import * as React from 'react';
import { Box, Button, Divider, Typography } from '@mui/material';
import { Bookmark } from '@mui/icons-material';
import { IRecord } from '../../pev-interface/IRecord';
import { DataGrid } from 'devextreme-react';
import { Column, FilterRow, HeaderFilter, Scrolling, Selection } from 'devextreme-react/data-grid';
import SearchIcon from '@mui/icons-material/Search';
import { setTargetRecords } from '../../store/pev-slices/record';
import { setAlert } from '../../store/pev-slices/environment';
import { TAlert } from '../../pev-type/TAlert';
import { useSelector } from '../../store';
import { IconArrowBigRight } from '@tabler/icons';

type ConditionFinderGridProps = {
    recordList: IRecord[];
};

const RecordGrid = (props: ConditionFinderGridProps) => {
    const { finderWidth } = useSelector((state) => state.environment);
    const { targetRecords } = useSelector((state) => state.record);
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

    const gridRef = React.useRef<DataGrid>(null);

    React.useEffect(() => {
        if (gridRef && gridRef.current) {
            void gridRef.current.instance.refresh();
        }
    }, [finderWidth]);

    const CellTemplate = (e: any) => {
        return (
            <Box
                width={'100%'}
                height={'100%'}
                display={'flex'}
                justifyContent={'center'}
                alignItems={'center'}
                sx={{ cursor: 'pointer' }}
                onClick={() => handleCellClick(e)}
            >
                <IconArrowBigRight width={16} height={16} color={'#3f51b5'} />
            </Box>
        );
    };

    const handleCellClick = (e: any) => {
        if (e && e.data) {
            setTargetRecords([e.data]);
        }
    };

    return (
        <Box width={'100%'} height={'100%'}>
            <Box width={'100%'} height={'31px'} display={'flex'} justifyContent={'space-between'} alignItems={'center'}>
                <Box display={'flex'} alignItems={'center'}>
                    <Bookmark color={'primary'} fontSize={'small'} />
                    <Typography variant={'body1'}>
                        기록목록&emsp;
                        {`전체: ${props.recordList.length} 건`}&emsp;
                        {`선택: ${selectedRecordList.length} 건`}&emsp;
                        {`조회: ${targetRecords.length} 건`}
                    </Typography>
                </Box>
                <Box display={'flex'} alignItems={'center'}>
                    <Button
                        variant={'contained'}
                        size={'small'}
                        startIcon={<SearchIcon fontSize="small" />}
                        onClick={handleRetrieve}
                        disabled={selectedRecordList.length === 0}
                    >
                        조회
                    </Button>
                </Box>
            </Box>
            <Divider sx={{ mt: 1, mb: 1 }} />
            <Box width={'100%'} height={'calc(100% - 48px)'}>
                <DataGrid
                    ref={gridRef}
                    dataSource={props.recordList}
                    height={'100%'}
                    showBorders={true}
                    showColumnLines={true}
                    showRowLines={true}
                    wordWrapEnabled={false}
                    noDataText={''}
                    onSelectionChanged={handleSelectionChanged}
                >
                    <Column caption={''} cellRender={CellTemplate} alignment={'center'} width={50} />
                    <Column dataField={'pactTpNm'} caption={'환자구분'} alignment={'center'} width={110} />
                    <Column dataField={'itemType'} caption={'항목구분'} alignment={'center'} width={110} />
                    <Column dataField={'itemNm'} caption={'항목명'} alignment={'left'} minWidth={170} />
                    <Column dataField={'writingDate'} caption={'작성일자'} width={110} alignment={'center'} />
                    <Column dataField={'writingDeptNm'} caption={'작성과'} width={100} alignment={'left'} />
                    <Column dataField={'writerNm'} caption={'작성자'} width={100} alignment={'center'} />
                    <Column dataField={'mdrcWrtStsCdYn'} caption={'서명'} alignment={'center'} width={85} />
                    <Scrolling mode={'virtual'} showScrollbar={'always'} />
                    <Selection mode={'multiple'} showCheckBoxesMode={'onClick'} />
                    <FilterRow visible={true} />
                    <HeaderFilter visible={true} />
                </DataGrid>
            </Box>
        </Box>
    );
};

export default RecordGrid;
