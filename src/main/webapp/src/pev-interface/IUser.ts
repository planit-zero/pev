export interface IUser {
    stfNo: string;
    stfNm: string;
    deptCd: string;
    deptNm: string;
    hspAuth: string;
    stfTel: string;
    stfEml: string;
    stfPic: string;
    authCd: string;
    prtStfNo: string;
    prtStfNm: string;
    dbKey: string;
    loginType: string;
    insteadLogin: boolean;
}

export interface IUserState {
    info: IUser | null;
}
