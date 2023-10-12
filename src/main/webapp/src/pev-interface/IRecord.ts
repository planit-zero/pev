export interface ISearchCondition extends IDateCondition, IDeptCondition, IWriterCondition {
    searchTargets: string[];
    pactTpCd: string;
}

export interface IDateCondition {
    searchFromDate: string | null;
    searchToDate: string | null;
}

export interface IDeptCondition {
    deptType: string;
    deptCd: string | null;
}

export interface IWriterCondition {
    writerType: string;
}

export interface ISearchConditionKeyValue {
    key: string;
    value: string | string[] | null;
}

export interface IRecord {
    recordType: string;
    recordDetailType: string;
    itemType: string;
    itemNm: string;
    writingDate: string;
    writingDeptCd: string;
    writingDeptNm: string;
    writerStfNo: string
    writerNm: string;
    keyId: string;
    pactId: string;
    pactTpCd: string;
    sortSeq: number;
    note: string;
    mdfmId: number;
    mdfmFomSeq: number;
    mdrcId: number;
    mdrcFomSeq: number;
    examKey: string;
    pacsImgIptnCd: string;
    accsId: string;
    recType: string;
    geneExmYn: string;
}

export interface IRecordSheet {
    headerSection: IRecordSection;
    sections: IRecordSection[];
}

export interface IRecordSection {
    entities: IRecordEntity[];
}

export interface IRecordEntity extends IRecordElement {
    type: string;
    alignment: string;
    attributes: IRecordAttribute[];
    values: IRecordValue[];
}

export interface IRecordAttribute extends IRecordElement {
    attributes: IRecordAttribute[];
    values: IRecordValue[];
}

export interface IRecordValue extends IRecordElement {

}

export interface IRecordElement {
    controlType: string;
    classType: string;
    display: string;
    textDecoration: string;
    text: string
}
