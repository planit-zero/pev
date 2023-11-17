export interface IPatientRidP {
    ridList: string[];
    irb: string;
}

export interface IPatientR {
    id: string;
    name: string;
    gender: string;
    dob: string;
}

export interface IPatientByIrbP {
    irb: string;
}

export interface IPatientByIrbR {
    id: string;
    name: string;
    dob: string;
}

export interface IRidByGidP {
    stfNo: string;
    irbNo: string;
    data: IRidByGidPatient[];
}

export interface IRidByGidR {
    irbNo: string;
    data: IRidByGidPatient[];
}

interface IRidByGidPatient {
    gid: string;
    rid?: string;
}
