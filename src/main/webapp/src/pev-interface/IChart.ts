import { ChartClassType, ChartControlType, ChartMaskingType } from '../pev-type/TChart';
import { IRecord } from './IRecord';

export interface IChartP {
    maskingYn: 'Y' | 'N';
    record: IRecord;
}

export interface IChart {
    headerSection: IChartSection | null;
    sections: IChartSection[];
}

export interface IChartSection {
    sectionId: number;
    entities: IChartEntity[];
}

export interface IChartElement {
    sectionId: number;
    id: string;
    parentId: string;
    classType: ChartClassType;
    controlType: ChartControlType;
    maskingType: ChartMaskingType;
    content: string;
    desc: string;
}

export interface IChartEntity extends IChartElement {
    attributes: IChartAttribute[];
    values: IChartValue[];
}

export interface IChartAttribute extends IChartElement {
    attributes: IChartAttribute[];
    values: IChartValue[];
}

export interface IChartValue extends IChartElement {}

export interface IChartReport {
    record: IRecord;
    values: IChartReportValue[];
}

export interface IChartReportValue extends IChartValue {
    confirmYn: 'Y' | 'N';
    report: string;
}
