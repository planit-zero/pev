export interface IMetaRecord {
    id: string;
    parentId: string;
    name: string;
    displaySeq: number;
}

export interface IMetaRecordList {
    metaRecords: IMetaRecord[];
}
