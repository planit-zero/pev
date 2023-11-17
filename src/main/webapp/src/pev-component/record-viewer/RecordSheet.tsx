import * as React from 'react';
import { IRecord, IRecordAttribute, IRecordEntity, IRecordSheet, IRecordValue } from '../../pev-interface/IRecord';
import { Alert, AlertTitle, Box, Button, Paper, Skeleton } from '@mui/material';
import { useGetRecordSheetMutation } from '../../pev-service/RecordService';
import RecordSection from './RecordSection';
import { TRecordSection } from '../../pev-type/TRecordSection';
import StyledElement from './StyledElement';
import { setAlert } from '../../store/pev-slices/environment';

type RecordSheetProps = {
    targetRecord: IRecord;
};

const RecordSheet = (props: RecordSheetProps) => {
    const [getRecordSheet, { data: recordSheet, isLoading: isRecordSheetLoading, error: recordSheetError }] = useGetRecordSheetMutation();

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
        const styledRecordDetailType: string[] = ['D009', 'D020', 'D035', 'EX_FUNCTION'];

        return styledRecordDetailType.includes(props.targetRecord.recordDetailType);
    };

    const SheetError = () => {
        if (!recordSheetError) return null;
        return (
            <Alert
                severity={'error'}
                action={
                    <Button variant={'contained'} color={'error'} size={'small'} onClick={handleSheetError}>
                        신고
                    </Button>
                }
            >
                <AlertTitle>기록지 오류</AlertTitle>
                <strong>{`${props.targetRecord.itemNm} (${props.targetRecord.writingDate})`}</strong> 기록지를 불러오는 데 실패했습니다.
                <br />
                우측의 <strong>신고 버튼</strong>을 눌러 관리자에게 문의해주세요.
            </Alert>
        );
    };

    const handleSheetError = () => {
        // TODO: 기록지 오류 신고 처리 필요
        setAlert({ type: 'info', message: '기록지 오류 신고 완료되었습니다.\r\n조치 후 안내드리도록 하겠습니다.' });
    };

    return (
        <Paper sx={{ minWidth: 600, width: hasStyle() ? 'fit-content' : 600, p: 2, mb: 2, borderRadius: 0 }}>
            {!isRecordSheetLoading && !recordSheetError && recordSheet && hasStyle() && StyledSection(recordSheet)}
            {!isRecordSheetLoading && !recordSheetError && recordSheet && !hasStyle() && CommonSection(recordSheet)}
            {isRecordSheetLoading && SkeletonSheet()}
            {SheetError()}
        </Paper>
    );
};

export default RecordSheet;
