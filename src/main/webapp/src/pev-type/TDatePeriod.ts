export type DatePeriodType = {
    ALL: string;
    FIVE_YEARS: string;
    ONE_YEAR: string;
    SIX_MONTHS: string;
    THREE_MONTHS: string;
    ONE_MONTH: string;
};

export const TDatePeriod: DatePeriodType = {
    ALL: 'ALL',
    FIVE_YEARS: 'FIVE_YEARS',
    ONE_YEAR: 'ONE_YEAR',
    SIX_MONTHS: 'SIX_MONTHS',
    THREE_MONTHS: 'THREE_MONTHS',
    ONE_MONTH: 'ONE_MONTH'
} as const;
