import { IRecordDetailR } from './IRecordDetail';

export interface IRecordState {
    targetRecords: IRecordDetailR[];
}

export interface IRecordDataP {
    targets: IRecordDetailR[];
}

export interface IRecordDataR {
    mdfmId: number;
    mdfmFomSeq: number;
    mdrcId: number;
    mdrcFomSeq: number;
    sections: IRecordSection[];
}

export interface IRecordSection {
    mdfmSctnSeq: number;
    sectionThemeType: string;
    width: string;
    height: string;
    items: IRecordItem[];
}

export interface IRecordItem {
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
    children: IRecordItem[];
}
