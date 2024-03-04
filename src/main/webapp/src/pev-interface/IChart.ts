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
    data: IChartElement[];
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
    stfNo: string;
    recordInfo: string;
    values: IChartReportValue[];
}

export interface IChartReportValue extends IChartValue {
    confirmYn: 'Y' | 'N';
    report: string;
}

export interface IReport {
    rowNum: number;
    reportId: number;
    irb: string;
    rid: string;
    recordInfo: string;
    reportUser: string;
    reportDtm: string;
    processYn: string;
    allCount: number;
    processCount: number;
}

export interface IReportDetail {
    valueSeq: number;
    valueId: string;
    parentId: string;
    reportText: string;
    processYn: string;
    processText: string;
    processDtm: string;
}

export interface IReportDetailUpdate {
    processText: string;
    reportId: number;
    valueSeq: number;
}

export interface IReportRequest {
    stfNo: string;
    authCd: string;
}

export interface IChartError {
    rowNum: number;
    errId: number;
    stfNo: string;
    irb: string;
    rid: string;
    targetRecord: string;
    loadDtm: string;
    processYn: string;
    processDtm: string;
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
