type RecordSectionType = {
    HEADER: string;
    BODY: string;
    FOOTER: string;
};

export const TRecordSection: RecordSectionType = {
    HEADER: 'HEADER',
    BODY: 'BODY',
    FOOTER: 'FOOTER'
} as const;
