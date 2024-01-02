import { ChartClassType, ChartControlType, ChartMaskingType } from '../pev-type/TChart';
import { IRecord } from './IRecord';

export interface IChartP {
    maskingYn: 'Y' | 'N';
    record: IRecord;
}

export interface IChartReplyP {
    maskingYn: 'Y' | 'N';
    mdrcId: number;
    mdrcFomSeq: number;
}

export interface IChartReplyR {
    replyYn: string;
    chart: IChart;
    record: IRecord;
}

export interface IChart {
    headerSection: IChartSection | null;
    sections: IChartSection[];
}

export interface IChartSection {
    sectionId: number;
    entities: IChartEntity[];
    style: IChartStyledSection;
}

export interface IChartElement {
    sectionId: number;
    id: string;
    parentId: string;
    classType: ChartClassType;
    controlType: ChartControlType;
    maskingType: ChartMaskingType;
    content: string;
    desc: string;
    style: IChartStyledItem;
}

export interface IChartEntity extends IChartElement {
    attributes: IChartAttribute[];
    values: IChartValue[];
}

export interface IChartAttribute extends IChartElement {
    attributes: IChartAttribute[];
    values: IChartValue[];
}

export interface IChartValue extends IChartElement {}

export interface IChartReport {
    recordInfo: string;
    values: IChartReportValue[];
}

export interface IChartReportValue extends IChartValue {
    confirmYn: 'Y' | 'N';
    report: string;
}

export interface IChartStyledSection {
    mdfmSctnSeq: number;
    sectionThemeType: string;
    width: string;
    height: string;
    items: IChartStyledItem[];
}

export interface IChartStyledItem {
    id: string;
    parentId: string;
    type: string;
    isArabic: string;
    isReadOnly: string;
    isSuffix: string;
    visibility: string;
    printable: string;
    text: string;
    textWrapping: string;
    flowDirection: string;
    zIndex: string;
    top: string;
    left: string;
    absoluteTop: string;
    absoluteLeft: string;
    width: string;
    minWidth: string;
    maxWidth: string;
    height: string;
    minHeight: string;
    isAutoHeight: string;
    fontFamily: string;
    fontSize: string;
    fontStyle: string;
    fontWeight: string;
    textAlignment: string;
    vContentAlignment: string;
    hContentAlignment: string;
    borderThickness: string;
    borderBrush: string;
    foreGround: string;
    background: string;
    tableDepth: string;
    colNum: string;
    rowNum: string;
    totalColNum: string;
    totalRowNum: string;
    colSpan: string;
    rowSpan: string;
    archDepth: string;
    indentUnit: string;
    overlapGroupId: string;
    verticalInterval: string;
    value: string;
    children: IChartStyledItem[];
}
