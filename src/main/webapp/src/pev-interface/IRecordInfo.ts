export interface IRecordDeptInfo {
    deptCd: string
    deptNm: string
}

export interface IRecordFormInfoP {
    mdrcId: number
    mdrcFomSeq: number
}

export interface IRecordFormInfoR {
    mdrcId: number
    mdrcFomSeq: number
    mdfmClsCd: string
    itemType: string
    writingDate: string
    writingDateTime: string
    writingDeptNm: string
    medDeptNm: string
    writerNm: string
}
