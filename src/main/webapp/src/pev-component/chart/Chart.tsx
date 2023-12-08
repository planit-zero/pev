import * as React from 'react';
import { IChart, IChartAttribute, IChartEntity, IChartReportValue, IChartSection, IChartValue } from '../../pev-interface/IChart';
import { Box, Chip, Typography } from '@mui/material';
import { ChartWrapperType } from '../../pev-type/TChart';
import { setAlert } from '../../store/pev-slices/environment';

type ChartProps = {
    mode: ChartWrapperType;
    chart: IChart;
    reportValues?: IChartReportValue[];
    onValueChange?: (value: IChartReportValue) => void;
};

const Chart = (props: ChartProps) => {
    const ChartSection = (section: IChartSection, sIdx: number) => {
        return (
            <Box key={sIdx}>
                {section.entities.map((e, idx) => {
                    return ChartEntity(e, idx);
                })}
            </Box>
        );
    };

    const ChartEntity = (entity: IChartEntity, eIdx: number) => {
        return (
            <Box key={eIdx} sx={{ mb: 1 }}>
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
                    return ChartAttribute(a, idx);
                })}
                {entity.values.map((v, idx) => {
                    return ChartValue(v, idx);
                })}
            </Box>
        );
    };

    const ChartAttribute = (attribute: IChartAttribute, aIdx: number) => {
        return (
            <Box key={aIdx} sx={{ ml: 1, mb: 1 }}>
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
                    return ChartAttribute(a, idx);
                })}
                {attribute.values.map((v, idx) => {
                    return ChartValue(v, idx);
                })}
            </Box>
        );
    };

    const ChartValue = (value: IChartValue, vIdx: number) => {
        return (
            <Box
                key={vIdx}
                sx={{
                    ml: 1,
                    mb: 1,
                    backgroundColor: props.mode === 'REPORT' ? 'pink' : 'transparent',
                    position: 'relative'
                }}
                onClick={() => handleValueClick(value)}
            >
                {props.mode === 'REPORT' &&
                    props.reportValues &&
                    props.reportValues.findIndex((v) => v.id === value.id && v.parentId === value.parentId) > -1 && (
                        <Box sx={{ position: 'absolute', top: -12, right: -12 }}>
                            <Chip
                                label={props.reportValues.findIndex((v) => v.id === value.id && v.parentId === value.parentId) + 1}
                                color={'error'}
                                size={'small'}
                            />
                        </Box>
                    )}
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
                                backgroundColor: 'grey',
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

    const handleValueClick = (value: IChartValue) => {
        if (props.mode !== 'REPORT') return;
        if (!props.onValueChange) return;
        if (!props.reportValues) return;
        if (props.reportValues.length > 0 && props.reportValues[props.reportValues.length - 1].confirmYn === 'N') {
            setAlert({
                type: 'warning',
                message: '먼저 선택한 영역에 대해 작성을 완료한 후 다시 선택해주세요.'
            });
            return;
        }
        props.onValueChange({
            ...value,
            confirmYn: 'N',
            report: ''
        });
    };

    return (
        <React.Fragment>
            {props.chart.sections.map((s, idx) => {
                return ChartSection(s, idx);
            })}
        </React.Fragment>
    );
};

export default Chart;
