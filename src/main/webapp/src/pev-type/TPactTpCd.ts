type PactTpCdType = {
    ALL: string;
    INPATIENT: string;
    OUTPATIENT: string;
    EMERGENCY: string;
};

export const TPactTpCd: PactTpCdType = {
    ALL: 'ALL',
    INPATIENT: 'I',
    OUTPATIENT: 'O',
    EMERGENCY: 'E'
} as const;
