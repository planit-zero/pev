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
