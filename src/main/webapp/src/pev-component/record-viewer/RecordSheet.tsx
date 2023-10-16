import * as React from 'react';
import { IRecord, IRecordAttribute, IRecordEntity, IRecordSheet, IRecordValue } from '../../pev-interface/IRecord';
import { Box, Paper, Skeleton } from '@mui/material';
import { useGetRecordSheetMutation } from '../../pev-service/RecordService';
import RecordSection from './RecordSection';
import { TRecordSection } from '../../pev-type/TRecordSection';
import StyledElement from './StyledElement';

type RecordSheetProps = {
    targetRecord: IRecord;
};

const RecordSheet = (props: RecordSheetProps) => {
    const [getRecordSheet, { data: recordSheet, isLoading: isRecordSheetLoading }] = useGetRecordSheetMutation();

    React.useEffect(() => {
        getRecordSheet(props.targetRecord);
    }, [props.targetRecord]);

    const StyledEntity = (entity: IRecordEntity) => {
        return (
            <React.Fragment>
                <StyledElement type={'entity'} entity={entity} />
                {entity.attributes.map((attribute) => {
                    return StyledAttribute(attribute);
                })}
                {entity.values.map((value) => {
                    return StyledValue(value);
                })}
            </React.Fragment>
        );
    };

    const StyledAttribute = (attribute: IRecordAttribute) => {
        return (
            <React.Fragment>
                <StyledElement type={'attribute'} attribute={attribute} />
                {attribute.attributes.map((subAttribute) => {
                    return StyledAttribute(subAttribute);
                })}
                {attribute.values.map((value) => {
                    return StyledValue(value);
                })}
            </React.Fragment>
        );
    };

    const StyledValue = (value: IRecordValue) => {
        return (
            <React.Fragment>
                <StyledElement type={'value'} value={value} />
            </React.Fragment>
        );
    };

    const StyledSection = (sheet: IRecordSheet) => {
        return (
            <Box>
                {sheet.sections.map((section, idx) => {
                    return (
                        <Box key={idx} position={'relative'} width={`${section.width}px`} height={`${section.height}px`}>
                            {section.entities.map((entity) => {
                                return StyledEntity(entity);
                            })}
                        </Box>
                    );
                })}
            </Box>
        );
    };

    const CommonSection = (sheet: IRecordSheet) => {
        return (
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
                <React.Fragment>
                    {sheet.headerSection && <RecordSection type={TRecordSection.HEADER} section={sheet.headerSection} />}
                    {sheet.sections.map((section, idx) => {
                        return <RecordSection key={idx} type={TRecordSection.BODY} section={section} />;
                    })}
                </React.Fragment>
            </Box>
        );
    };

    const SkeletonSheet = () => {
        return (
            <Box display={'flex'} flexDirection={'column'} gap={2}>
                <Box>
                    <Skeleton variant={'text'} width={'50%'} height={40} />
                </Box>
                <Box>
                    <Skeleton variant={'text'} width={'30%'} height={30} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                </Box>
                <Box>
                    <Skeleton variant={'text'} width={'30%'} height={30} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                    <Skeleton variant={'text'} width={'100%'} height={20} />
                </Box>
                <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                    <Skeleton variant={'text'} width={'30%'} height={30} />
                </Box>
            </Box>
        );
    };

    const hasStyle = (): boolean => {
        const styledRecordDetailType: string[] = ['D009', 'D035'];

        return styledRecordDetailType.includes(props.targetRecord.recordDetailType);
    };

    return (
        <Paper sx={{ minWidth: 600, p: 2, mb: 2, borderRadius: 0 }}>
            {!isRecordSheetLoading && recordSheet && hasStyle() && StyledSection(recordSheet)}
            {!isRecordSheetLoading && recordSheet && !hasStyle() && CommonSection(recordSheet)}
            {isRecordSheetLoading && SkeletonSheet()}
        </Paper>
    );
};

export default RecordSheet;
