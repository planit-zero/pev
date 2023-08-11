export interface IRecordDetailP {
    ptNo: string;
    searchFromDate: string;
    searchToDate: string;
    pactTpCd: string;
    recordType: string[];
    recordDetailType: string;
    deptType: string;
    deptCd: string;
    writerType: string;
}

export interface IRecordDetailR {
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
    printSeq: number;
    mdrcId: string;
    mdfmId: string;
    examKey: string;
    pacsImgIptnCd: string;
    accsId: string;
    recType: string;
    geneExmYn: string;
}
