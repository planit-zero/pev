export interface IFormElement {
    sectionSeq: number;
    id: string;
    parentId: string;
    classType: string;
    controlType: string;
    value: string;
    defaultValue: string;
}

export interface IFormValue extends IFormElement {}

export interface IFormAttribute extends IFormElement {
    values: IFormValue[];
}

export interface IFormEntity extends IFormElement {
    inline: boolean;
    hasAttributes: boolean;
    attributes: IFormAttribute[];
    values: IFormValue[];
}

export interface IFormSection {
    mdfmId: number;
    mdfmFomSeq: number;
    mdfmClsCd: string;
    mdfmSctnSeq: number;
    entities: IFormEntity[];
}

export interface IFormInfoBasic {
    mdrcId: number;
    mdrcFomSeq: number;
    mdfmId: number;
    mdfmFomSeq: number;
    itemNm: string;
    writingDate: string;
    writingDateTime: string;
    writingDeptNm: string;
    ptMedDeptNm: string;
    writerNm: string;
}

export interface IFormSheet extends IFormInfoBasic {
    sections: IFormSection[]
}

export interface FormIdentifier {
    mdrcId: number;
    mdrcFomSeq: number;
    mdfmId: number;
    mdfmFomSeq: number;
}

export interface IFormContentP {
    identifiers: FormIdentifier[];
}

export interface IFormContentR {
    sheets: IFormSheet[];
}
