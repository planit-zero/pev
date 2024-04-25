import * as React from 'react';
import { Box, Checkbox, FormControlLabel, Typography } from '@mui/material';
import { IMetaRecord, IMetaRecordList } from '../../pev-interface/IMetaRecord';
import { useTheme } from '@mui/material/styles';
import { ISearchCondition, ISearchConditionKeyValue } from '../../pev-interface/IRecord';

type RecordSelectorProps = {
    metaRecordList: IMetaRecordList;
    searchCondition: ISearchCondition;
    onSearchConditionChange: (conditions: ISearchConditionKeyValue[]) => void;
};

const RecordSelector = (props: RecordSelectorProps) => {
    const theme = useTheme();

    const [searchTargets, setSearchTargets] = React.useState<string[]>(props.searchCondition.searchTargets);

    React.useEffect(() => {
        props.onSearchConditionChange([{ key: 'searchTargets', value: searchTargets }]);
    }, [searchTargets]);

    const MetaRecordBox = (metaRecord: IMetaRecord, idx: number) => {
        const isAllChildrenChecked = (mr: IMetaRecord) => {
            if (!props.metaRecordList) return false;

            let result = false;

            const childrenArr = props.metaRecordList.metaRecords.filter((d) => d.parentId === mr.id).map((d) => d.id);

            for (const child of childrenArr) {
                result = searchTargets.includes(child);
            }

            return result;
        };

        const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>, mr: IMetaRecord) => {
            if (!props.metaRecordList) return;

            const childrenArr = props.metaRecordList.metaRecords.filter((d) => d.parentId === mr.id).map((d) => d.id);

            if (e.target.checked) {
                const nextTargets = new Set([...searchTargets, ...childrenArr]);
                setSearchTargets([...nextTargets]);
            } else {
                setSearchTargets(searchTargets.filter((st) => !childrenArr.includes(st)));
            }
        };

        return (
            <Box key={idx}>
                <Box
                    sx={{
                        backgroundColor: theme.palette.primary.dark,
                        color: 'white',
                        width: '200px',
                        p: 1
                    }}
                >
                    <FormControlLabel
                        sx={{ ml: 0 }}
                        control={
                            <Checkbox
                                sx={{ mr: 1, color: 'white !important' }}
                                checked={isAllChildrenChecked(metaRecord)}
                                onChange={(e) => handleCheckboxChange(e, metaRecord)}
                            />
                        }
                        label={
                            <Typography sx={{ fontSize: 'h4.fontSize', fontWeight: 'bold' }} display={'inline'}>
                                {metaRecord.name}
                            </Typography>
                        }
                    />
                </Box>
                {MetaRecordDetailBox(metaRecord)}
            </Box>
        );
    };

    const MetaRecordDetailBox = (metaRecord: IMetaRecord) => {
        const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>, mr: IMetaRecord) => {
            if (e.target.checked) {
                setSearchTargets([...searchTargets, mr.id]);
            } else {
                setSearchTargets(searchTargets.filter((st) => st !== mr.id));
            }
        };

        return (
            <Box>
                {props.metaRecordList &&
                    props.metaRecordList.metaRecords
                        .filter((mr) => mr.parentId === metaRecord.id)
                        .map((mr, idx) => {
                            return (
                                <Box
                                    key={idx}
                                    sx={{
                                        width: '200px',
                                        px: 1,
                                        py: 0,
                                        display: 'flex',
                                        alignItems: 'center',
                                        gap: 1
                                    }}
                                >
                                    <FormControlLabel
                                        sx={{ ml: 0 }}
                                        control={
                                            <Checkbox
                                                color={'default'}
                                                checked={searchTargets.includes(mr.id)}
                                                onChange={(e) => handleCheckboxChange(e, mr)}
                                            />
                                        }
                                        label={
                                            <Typography sx={{ fontSize: 'h5.fontSize' }} display={'inline'}>
                                                {mr.name}
                                            </Typography>
                                        }
                                    />
                                </Box>
                            );
                        })}
            </Box>
        );
    };

    return (
        <Box
            sx={{
                height: '100%',
                bgcolor: 'background.paper',
                display: 'flex'
            }}
        >
            {props.metaRecordList &&
                props.metaRecordList.metaRecords
                    .filter((mr) => mr.parentId === null)
                    .map((mr, idx) => {
                        return MetaRecordBox(mr, idx);
                    })
            }
        </Box>
    );
};

export default RecordSelector;
