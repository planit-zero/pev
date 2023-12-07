import * as React from 'react';
import { IChart, IChartAttribute, IChartEntity, IChartSection, IChartValue } from '../../pev-interface/IChart';
import { Box, Typography } from '@mui/material';

type ChartProps = {
    chart: IChart;
};

const ChartSection = (section: IChartSection) => {
    return (
        <Box>
            {section.entities.map((e, idx) => {
                return ChartEntity(e);
            })}
        </Box>
    );
};

const ChartEntity = (entity: IChartEntity) => {
    return (
        <Box sx={{ mb: 1 }}>
            <Typography
                sx={{
                    fontSize: `h4.fontSize`,
                    fontWeight: 'bold',
                    color: '#4cbded',
                    whiteSpace: 'pre-line',
                    wordBreak: 'break-all'
                }}
            >
                {entity.content}
            </Typography>
            {entity.attributes.map((a, idx) => {
                return ChartAttribute(a);
            })}
            {entity.values.map((v, idx) => {
                return ChartValue(v);
            })}
        </Box>
    );
};

const ChartAttribute = (attribute: IChartAttribute) => {
    return (
        <Box sx={{ ml: 1, mb: 1 }}>
            <Typography
                sx={{
                    fontSize: 'h5.fontSize',
                    color: '#409ac0',
                    whiteSpace: 'pre-line',
                    wordBreak: 'break-all'
                }}
            >
                {attribute.content}
            </Typography>
            {attribute.attributes.map((a, idx) => {
                return ChartAttribute(a);
            })}
            {attribute.values.map((v, idx) => {
                return ChartValue(v);
            })}
        </Box>
    );
};

const ChartValue = (value: IChartValue) => {
    return (
        <Box sx={{ ml: 1, mb: 1 }}>
            {value.controlType === 'IMAGE' && (
                <img style={{ width: '100%', height: '100%', objectFit: 'contain' }} src={value.content} alt={'가명화 이미지'} />
            )}
            {value.controlType !== 'IMAGE' && (
                <Box
                    sx={{
                        fontSize: 'h5.fontSize',
                        color: 'inherit',
                        whiteSpace: 'pre-line',
                        wordBreak: 'break-all',
                        '& em': {
                            color: 'white',
                            backgroundColor: '#3f51b5',
                            fontStyle: 'normal',
                            px: 0.5
                        }
                    }}
                    dangerouslySetInnerHTML={{ __html: value.content }}
                />
            )}
        </Box>
    );
};

const Chart = (props: ChartProps) => {
    return (
        <React.Fragment>
            {props.chart.sections.map((s, idx) => {
                return ChartSection(s);
            })}
        </React.Fragment>
    );
};

export default Chart;
