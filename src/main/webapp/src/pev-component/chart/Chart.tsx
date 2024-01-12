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
import { IRecord } from '../../pev-interface/IRecord';

type ChartProps = {
    mode: ChartWrapperType;
    record: IRecord;
    chart: IChart;
    reportValues?: IChartReportValue[];
    onValueChange?: (value: IChartReportValue) => void;
};

const Chart = (props: ChartProps) => {
    const styleSx = (element: IChartEntity | IChartAttribute | IChartValue) => {
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

        if (!element.style) return {};

        return {
            position: 'absolute',
            display: 'flex',
            zIndex: isNaN(Number(element.style.zIndex)) ? 0 : Number(element.style.zIndex),
            top: `${element.style.top}px`,
            left: `${element.style.left}px`,
            width: `${element.style.width}px`,
            height: `${isNaN(Number(element.style.height)) ? Number(element.style.minHeight) : Number(element.style.height)}px`,
            textAlign: element.style.textAlignment?.toLowerCase() || 'left',
            fontStyle: element.style.fontStyle?.toLowerCase() || 'normal',
            fontWeight: element.style.fontWeight?.toLowerCase() || 'normal',
            fontSize: `${Number(element.style.fontSize) - 3}px`,
            backgroundColor: props.mode === 'REPORT' && element.classType === 'VALUE' ? 'pink' : getColor(element.style.background),
            color: getColor(element.style.foreGround),
            boxShadow: getBoxShadow(element.style),
            justifyContent: getJustifyContent(element.style),
            alignItems: element.style.vContentAlignment?.toLowerCase() || 'center',
            paddingLeft: isNaN(Number(element.style.indentUnit)) ? 0 : `${element.style.indentUnit}px`
        };
    };

    const ChartSection = (section: IChartSection, sIdx: number) => {
        return (
            <Box
                key={sIdx}
                position={'relative'}
                width={section.style ? `${section.style.width}px` : '600px'}
                height={section.style ? `${section.style.height}px` : 'fit-content'}
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
            <Box key={eIdx} onClick={() => console.log('### entity', entity)}>
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
                {value.controlType === 'TABLE' && value.content && (
                    <Box sx={{ width: '100%' }}>
                        <table
                            style={{
                                width: '100%',
                                tableLayout: 'fixed',
                                border: '1px solid black',
                                borderCollapse: 'collapse'
                            }}
                        >
                            <tbody>
                                {value.content.split('\r\n').map((c, cIdx) => {
                                    return (
                                        <tr key={cIdx}>
                                            {cIdx === 0 &&
                                                c.split('|||').map((h, hIdx) => {
                                                    return (
                                                        <th
                                                            key={hIdx}
                                                            style={{
                                                                border: '1px solid black',
                                                                borderCollapse: 'collapse',
                                                                padding: '4px 8px',
                                                                backgroundColor: '#3f51b5',
                                                                color: 'white'
                                                            }}
                                                        >
                                                            {h}
                                                        </th>
                                                    );
                                                })}
                                            {cIdx !== 0 &&
                                                c.split('|||').map((d, dIdx) => {
                                                    return (
                                                        <td
                                                            key={dIdx}
                                                            style={{
                                                                border: '1px solid black',
                                                                borderCollapse: 'collapse',
                                                                padding: '4px 8px'
                                                            }}
                                                        >
                                                            {d}
                                                        </td>
                                                    );
                                                })}
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </table>
                    </Box>
                )}
                {value.controlType !== 'IMAGE' && value.controlType !== 'TABLE' && (
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
                        dangerouslySetInnerHTML={{ __html: value.controlType === 'RADIO_BUTTON' ? value.desc : value.content }}
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
                    ...styleSx(element),
                    '& em': {
                        color: 'white',
                        backgroundColor: 'grey',
                        fontStyle: 'normal',
                        px: 0.5
                    }
                }}
                onClick={() => console.log('### element', element)}
            >
                <Box width={'100%'} height={'100%'} position={'relative'} onClick={() => handleValueClick(element)}>
                    {props.mode === 'REPORT' &&
                        props.reportValues &&
                        props.reportValues.findIndex((v) => v.id === element.id && v.parentId === element.parentId) > -1 && (
                            <Box sx={{ position: 'absolute', top: 0, right: 0, zIndex: 9999 }}>
                                <Chip
                                    label={props.reportValues.findIndex((v) => v.id === element.id && v.parentId === element.parentId) + 1}
                                    color={'error'}
                                    size={'small'}
                                />
                            </Box>
                        )}
                    {(element.controlType === 'LABEL' ||
                        element.controlType === 'TEXT_BOX' ||
                        element.controlType === 'DATE_TEXT_BOX' ||
                        element.controlType === 'NUMERIC_TEXT_BOX') && (
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
                            <Radio size={'small'} checked={element.content === '1'} readOnly={true} sx={{ p: 0, pr: 0.5 }} />
                            <Typography sx={{ fontSize: 'inherit', color: 'inherit' }}>{element.desc}</Typography>
                        </label>
                    )}
                    {element.controlType === 'CHECK_BOX' && (
                        <label style={{ display: 'flex', alignItems: 'center', width: '100%', height: '100%' }}>
                            <Checkbox size={'small'} checked={element.content === '1'} readOnly={true} sx={{ p: 0, pr: 0.5 }} />
                            <Typography sx={{ fontSize: 'inherit', color: 'inherit' }}>{element.desc}</Typography>
                        </label>
                    )}
                    {element.controlType === 'IMAGE' && element.content && (
                        <img style={{ width: '100%', height: '100%', objectFit: 'contain' }} src={element.content} alt={element.content} />
                    )}
                </Box>
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
        if (value.classType !== 'VALUE') return;
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

    const getHeaderSection = (record: IRecord) => {
        if (['D009', 'D020', 'D035', 'SC'].includes(record.recordDetailType)) return null;
        return (
            <Box sx={{ mb: 2, fontStyle: 'italic', color: '#aa58d2' }}>
                <Typography
                    display={'inline'}
                    sx={{
                        fontSize: 'h4.fontSize',
                        fontWeight: 'bold',
                        fontStyle: 'italic',
                        color: '#aa58d2',
                        mr: 1
                    }}
                >
                    {record.itemType}
                </Typography>
                <Typography
                    display={'inline'}
                    sx={{
                        fontSize: 'h4.fontSize',
                        fontWeight: 'normal',
                        fontStyle: 'italic',
                        color: '#aa58d2'
                    }}
                >
                    ({record.writingDate})
                </Typography>
                {Boolean(record.writingDeptNm) && record.writingDeptNm !== '' && (
                    <Typography sx={{ fontSize: 'h4.fontSize', fontWeight: 'bold' }}>작성과: {record.writingDeptNm}</Typography>
                )}
                {Boolean(record.ptMedDeptNm) && record.ptMedDeptNm !== '' && (
                    <Typography sx={{ fontSize: 'h4.fontSize', fontWeight: 'bold' }}>수진과: {record.ptMedDeptNm}</Typography>
                )}
            </Box>
        );
    };

    return (
        <React.Fragment>
            {getHeaderSection(props.record)}
            {props.chart.sections.map((s, idx) => {
                return ChartSection(s, idx);
            })}
        </React.Fragment>
    );
};

export default Chart;
