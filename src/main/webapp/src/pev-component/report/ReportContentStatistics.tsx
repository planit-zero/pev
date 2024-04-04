import * as React from 'react';
import { Card, Paper } from '@mui/material';
import {
    useGetStatisticsUserWeekListQuery,
    useGetStatisticsUserHourListQuery,
    useGetEventSearchTargetDistributionListQuery,
    useGetStatisticsUserDeptListQuery
} from '../../pev-service/ReportService';
import Chart, { ArgumentAxis, Export, Format, Label, Legend, Series, Tooltip } from 'devextreme-react/chart';
import { PieChart } from 'devextreme-react';
import { Connector } from 'devextreme-react/pie-chart';

type ReportContentStatisticsProps = {
    menuIndex: number;
};

const ReportContentStatistics = (props: ReportContentStatisticsProps) => {
    const { data: statisticsUserWeekList } = useGetStatisticsUserWeekListQuery();
    const { data: statisticsUserHourList } = useGetStatisticsUserHourListQuery();
    const { data: eventSearchTargetDistributionList } = useGetEventSearchTargetDistributionListQuery();
    const { data: statisticsUserDeptList } = useGetStatisticsUserDeptListQuery();

    const getStatisticsUserWeek = () => {
        if (props.menuIndex === 7) return userChart();
        if (props.menuIndex === 8) return searchChart();
    };

    const deptTooltip = (arg: { argument: string; value: number }) => {
        return {
            text: `${arg.argument} - ${arg.value}`
        };
    };

    const userChart = () => {
        return (
            <>
                <Card sx={{ marginBottom: '10vh' }}>
                    <Chart title={'주간 접속 분포'} dataSource={statisticsUserWeekList} height={'35vh'}>
                        <ArgumentAxis>
                            <Label format="decimal" />
                        </ArgumentAxis>
                        <Series argumentField={'weekFromMonday'} valueField={'count'} type={'spline'} color={'#3f51b5'} />
                        <Legend visible={false} />
                        <Export enabled={true} />
                        <Tooltip enabled={true} />
                    </Chart>
                </Card>
                <Card sx={{ marginBottom: '10vh' }}>
                    <Chart title={'접속 시간대'} dataSource={statisticsUserHourList} height={'35vh'}>
                        <ArgumentAxis tickInterval={1}>
                            <Label format="decimal" />
                        </ArgumentAxis>
                        <Series argumentField={'hour'} valueField={'count'} type={'bar'} color={'#3f51b5'} />
                        <Legend visible={false} />
                        <Export enabled={true} />
                        <Tooltip enabled={true} />
                    </Chart>
                </Card>
                <Card>
                    <Chart title={'상위 접속 부서'} dataSource={statisticsUserDeptList} height={'35vh'}>
                        <ArgumentAxis>
                            <Label format="decimal" overlappingBehavior={'none'} />
                        </ArgumentAxis>
                        <Series argumentField={'deptNm'} valueField={'count'} type={'bar'} color={'#3f51b5'} />
                        <Legend visible={false} />
                        <Export enabled={true} />
                        <Tooltip enabled={true} customizeTooltip={deptTooltip} />
                    </Chart>
                </Card>
            </>
        );
    };

    const searchTooltip = (arg: { argument: string; percent: number }) => {
        return {
            text: `${arg.argument} - ${(arg.percent * 100).toFixed(2)}%`
        };
    };

    const searchLabel = (arg: any) => {
        return arg.argument;
    };

    const searchChart = () => {
        return (
            <>
                <Card>
                    <PieChart
                        type={'doughnut'}
                        title={'기록유형 분포'}
                        palette={'Soft Pastel'}
                        dataSource={eventSearchTargetDistributionList}
                        height={'80vh'}
                    >
                        <Legend
                            orientation={'horizontal'}
                            itemTextPosition={'right'}
                            horizontalAlignment={'center'}
                            verticalAlignment={'bottom'}
                            columnCount={4}
                        />
                        <Series argumentField="searchTargets" valueField={'count'}>
                            <Label visible={true} format="fixedPoint" customizeText={searchLabel}>
                                <Connector visible={true} />
                            </Label>
                        </Series>
                        <Export enabled={true} />
                        <Tooltip enabled={true} customizeTooltip={searchTooltip}>
                            <Format type={'millions'} />
                        </Tooltip>
                    </PieChart>
                </Card>
            </>
        );
    };

    return (
        <Paper sx={{ width: '40%', height: 'calc(100% - 75px)', p: '20px', ml: '15px', overflowY: 'scroll' }}>
            {getStatisticsUserWeek()}
        </Paper>
    );
};

export default ReportContentStatistics;
