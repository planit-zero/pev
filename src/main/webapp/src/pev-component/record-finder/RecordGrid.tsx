import * as React from 'react';
import { Box, Button, Divider, Typography } from '@mui/material';
import { Bookmark } from '@mui/icons-material';
import { IRecord } from '../../pev-interface/IRecord';
import { DataGrid } from 'devextreme-react';
import { Column, FilterRow, HeaderFilter, Scrolling, Selection } from 'devextreme-react/data-grid';
import { setTargetRecords } from '../../store/pev-slices/record';
import { setAlert } from '../../store/pev-slices/environment';
import { TAlert } from '../../pev-type/TAlert';
import { useSelector } from '../../store';
import { IconCheck, IconListCheck, IconSquareX } from '@tabler/icons';

type ConditionFinderGridProps = {
    recordList: IRecord[];
};

const RecordGrid = (props: ConditionFinderGridProps) => {
    const { finderWidth } = useSelector((state) => state.environment);
    const { targetRecords } = useSelector((state) => state.record);
    const [selectedRecordList, setSelectedRecordList] = React.useState<IRecord[]>([]);

    const handleSelectionChanged = (e: any) => {
        if (e && e.selectedRowsData) {
            if (e.selectedRowsData.length === 0) {
                setTargetRecords([]);
                return;
            }

            setSelectedRecordList(e.selectedRowsData.reverse());
            handleRetrieve(e.selectedRowsData.reverse());
        }
    };

    const handleRetrieve = (recordList: IRecord[]) => {
        if (recordList.length === 0) {
            setAlert({ type: TAlert.WARNING, message: '기록 목록에서 기록을 한 개 이상 선택 후 조회 버튼을 눌러주세요.' });
            return;
        }
        setTargetRecords(recordList);
    };

    const gridRef = React.useRef<DataGrid>(null);

    React.useEffect(() => {
        if (gridRef && gridRef.current) {
            void gridRef.current.instance.refresh();
        }
    }, [finderWidth]);

    const [selectionMode, setSelectionMode] = React.useState<'single' | 'multiple'>('single');

    const deselectAll = () => {
        if (gridRef && gridRef.current) {
            void gridRef.current.instance.deselectAll();
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
                <Box display={'flex'} alignItems={'center'} gap={1}>
                    {selectionMode === 'multiple' && (
                        <React.Fragment>
                            <Button
                                variant={'outlined'}
                                color={'error'}
                                size={'small'}
                                startIcon={<IconSquareX fontSize="small" />}
                                onClick={deselectAll}
                            >
                                선택 해제
                            </Button>
                            <Button
                                variant={'outlined'}
                                size={'small'}
                                startIcon={<IconCheck fontSize="small" />}
                                onClick={() => setSelectionMode('single')}
                            >
                                단일 선택
                            </Button>
                        </React.Fragment>
                    )}
                    {selectionMode === 'single' && (
                        <Button
                            variant={'outlined'}
                            size={'small'}
                            startIcon={<IconListCheck fontSize="small" />}
                            onClick={() => setSelectionMode('multiple')}
                        >
                            다중 선택
                        </Button>
                    )}
                    {/*<Button*/}
                    {/*    variant={'contained'}*/}
                    {/*    size={'small'}*/}
                    {/*    startIcon={<SearchIcon fontSize="small" />}*/}
                    {/*    onClick={() => handleRetrieve(selectedRecordList)}*/}
                    {/*    disabled={selectedRecordList.length === 0}*/}
                    {/*>*/}
                    {/*    조회*/}
                    {/*</Button>*/}
                </Box>
            </Box>
            <Divider sx={{ mt: 1, mb: 1 }} />
            <Box width={'100%'} height={'calc(100% - 48px)'} sx={{ userSelect: 'none' }}>
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
                    // onRowClick={() => handleRetrieve()}
                >
                    <Column dataField={'pactTpNm'} caption={'구분'} alignment={'center'} width={85} />
                    <Column dataField={'itemType'} caption={'유형'} alignment={'center'} width={85} />
                    <Column dataField={'itemNm'} caption={'항목명'} alignment={'left'} minWidth={170} />
                    <Column dataField={'writingDate'} caption={'작성일자'} width={110} alignment={'center'} />
                    <Column dataField={'writingDeptNm'} caption={'작성과'} width={100} alignment={'left'} />
                    <Column dataField={'writerNm'} caption={'작성자'} width={100} alignment={'center'} />
                    <Column dataField={'mdrcWrtStsCdYn'} caption={'서명'} alignment={'center'} width={85} />
                    <Scrolling mode={'virtual'} showScrollbar={'always'} />
                    <Selection mode={selectionMode} showCheckBoxesMode={'onClick'} />
                    <FilterRow visible={true} />
                    <HeaderFilter visible={true} />
                </DataGrid>
            </Box>
        </Box>
    );
};

export default RecordGrid;
