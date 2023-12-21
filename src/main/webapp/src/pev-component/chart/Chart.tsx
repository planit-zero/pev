import * as React from 'react';
import {
    IChart,
    IChartAttribute,
    IChartEntity,
    IChartReportValue,
    IChartSection,
    IChartStyledItem,
    IChartValue
} from '../../pev-interface/IChart';
import { Box, Checkbox, Chip, Radio, Typography } from '@mui/material';
import { ChartWrapperType } from '../../pev-type/TChart';
import { setAlert } from '../../store/pev-slices/environment';

type ChartProps = {
    mode: ChartWrapperType;
    chart: IChart;
    reportValues?: IChartReportValue[];
    onValueChange?: (value: IChartReportValue) => void;
};

const Chart = (props: ChartProps) => {
    const styleSx = (item: IChartStyledItem | null) => {
        const getColor = (color: string) => {
            if (!color) return 'inherit';
            return `#${color.substring(3)}`;
        };

        const getJustifyContent = (i: IChartStyledItem): string => {
            if (i.hContentAlignment === 'Left') return 'flex-start';
            if (i.hContentAlignment === 'Right') return 'flex-end';
            return 'center';
        };

        const getBoxShadow = (i: IChartStyledItem): string => {
            if (!i.borderThickness) return `none`;

            const borderArr = i.borderThickness.split(',');

            if (borderArr.length === 4) {
                let top = borderArr[1];
                let left = borderArr[0];
                let right = borderArr[2];
                let bottom = borderArr[3];

                return `0 ${top}px ${getColor(i.borderBrush)} inset, ${right}px 0 ${getColor(i.borderBrush)}, ${left}px 0 ${getColor(
                    i.borderBrush
                )} inset, 0 ${bottom}px ${getColor(i.borderBrush)}`;
            }

            return 'none';
        };

        if (!item) return {};

        return {
            position: 'absolute',
            display: 'flex',
            zIndex: isNaN(Number(item.zIndex)) ? 0 : Number(item.zIndex),
            top: `${item.top}px`,
            left: `${item.left}px`,
            width: `${item.width}px`,
            height: `${isNaN(Number(item.height)) ? Number(item.minHeight) : Number(item.height)}px`,
            textAlign: item.textAlignment?.toLowerCase() || 'left',
            fontStyle: item.fontStyle?.toLowerCase() || 'normal',
            fontWeight: item.fontWeight?.toLowerCase() || 'normal',
            fontSize: `${Number(item.fontSize) - 2}px`,
            backgroundColor: props.mode === 'REPORT' && item.type === 'VALUE' ? 'pink' : getColor(item.background),
            color: getColor(item.foreGround),
            boxShadow: getBoxShadow(item),
            justifyContent: getJustifyContent(item),
            alignItems: item.vContentAlignment?.toLowerCase() || 'center',
            paddingLeft: isNaN(Number(item.indentUnit)) ? 0 : `${item.indentUnit}px`
        };
    };
    const ChartSection = (section: IChartSection, sIdx: number) => {
        return (
            <Box
                key={sIdx}
                position={'relative'}
                width={section.style ? `${section.style.width}px` : ''}
                height={section.style ? `${section.style.height}px` : ''}
            >
                {section.entities.map((e, idx) => {
                    return ChartEntity(e, idx);
                })}
            </Box>
        );
    };

    const ChartEntity = (entity: IChartEntity, eIdx: number) => {
        if (entity.style) return StyledElementWithChildren(entity, eIdx);
        return (
            <Box key={eIdx} sx={styleSx(entity.style)} onClick={() => console.log('### entity', entity)}>
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
        if (attribute.style) return StyledElementWithChildren(attribute, aIdx);
        return (
            <Box key={aIdx} sx={{ ml: 1, mb: 1 }} onClick={() => console.log('### attribute', attribute)}>
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
        if (value.style) return StyledElement(value, vIdx);
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
                {value.controlType === 'IMAGE' && value.content && (
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

    const StyledElement = (element: IChartEntity | IChartAttribute | IChartValue, index: number) => {
        if (!element.style) return null;
        if (element.style.id === '-1000') return null;
        if (element.style.visibility === 'Collapsed') return null;

        return (
            <Box
                key={index}
                sx={{
                    ...styleSx(element.style),
                    '& em': {
                        color: 'white',
                        backgroundColor: 'grey',
                        fontStyle: 'normal',
                        px: 0.5
                    }
                }}
                onClick={() => console.log('### element', element)}
            >
                {(element.controlType === 'LABEL' || element.controlType === 'TEXT_BOX') && (
                    <Box width={'100%'} height={'100%'} sx={{ p: 0.5 }} dangerouslySetInnerHTML={{ __html: element.content }} />
                )}
                {element.controlType === 'RICH_TEXT_BOX' && (
                    <Box
                        width={'100%'}
                        height={'100%'}
                        sx={{ p: 0.5, overflowY: 'scroll' }}
                        dangerouslySetInnerHTML={{ __html: element.content }}
                    />
                )}
                {element.controlType === 'RADIO_BUTTON' && (
                    <label style={{ display: 'flex', alignItems: 'center', width: '100%', height: '100%' }}>
                        <Radio size={'small'} checked={element.content === '1'} readOnly={true} sx={{ p: 0, pr: 1 }} />
                        <Typography sx={{ fontSize: 'inherit', color: 'inherit' }}>{element.desc}</Typography>
                    </label>
                )}
                {element.controlType === 'CHECK_BOX' && (
                    <label style={{ display: 'flex', alignItems: 'center', width: '100%', height: '100%' }}>
                        <Checkbox size={'small'} checked={element.content === '1'} readOnly={true} sx={{ p: 0, pr: 1 }} />
                        <Typography sx={{ fontSize: 'inherit', color: 'inherit' }}>{element.desc}</Typography>
                    </label>
                )}
                {element.controlType === 'IMAGE' && (
                    <img style={{ width: '100%', height: '100%', objectFit: 'contain' }} src={element.content} alt={element.content} />
                )}
            </Box>
        );
    };

    const StyledElementWithChildren = (element: IChartEntity | IChartAttribute, index: number) => {
        return (
            <React.Fragment key={index}>
                {StyledElement(element, index)}
                {element.attributes.map((attribute, idx) => {
                    return StyledElementWithChildren(attribute, idx);
                })}
                {element.values.map((value, idx) => {
                    return StyledElement(value, idx);
                })}
            </React.Fragment>
        );
    };

    const handleValueClick = (value: IChartValue) => {
        console.log('### value', value);

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
