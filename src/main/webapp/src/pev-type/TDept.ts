type DeptType = {
    ALL: string;
    MEDICAL: string;
    WRITER: string;
};

export const TDept: DeptType = {
    ALL: 'ALL',
    MEDICAL: 'MEDICAL',
    WRITER: 'WRITER'
} as const;
