import * as React from 'react';
import { IRecordItem, IRecordSection } from '../../../pev-interface/IRecordDataR';
import { Box } from '@mui/material';
import RecordFormBasicItem from './RecordFormBasicItem';
import RecordFormBasicCommonException from './exception/RecordFormBasicCommonException';
import { IRecordFormInfoR } from '../../../pev-interface/IRecordInfo';

type RecordFormBasicProps = {
    recordFormInfo: IRecordFormInfoR;
    sections: IRecordSection[];
};

const RecordFormBasic = (props: RecordFormBasicProps) => {
    const isHiddenSection = (section: IRecordSection): boolean => {
        const noValueItems = section.items.filter((item) => item.value === null);
        return section.items.length === noValueItems.length;
    };

    return (
        <Box>
            {props.sections.map((section, sIdx) => {
                return (
                    <React.Fragment>
                        {section.mdfmSctnSeq === -1 && <RecordFormBasicCommonException recordFormInfo={props.recordFormInfo} />}
                        <Box key={sIdx} sx={{ width: `${section.width}px`, mb: 2 }}>
                            {!isHiddenSection(section) &&
                                section.items.map((item, iIdx) => {
                                    return <RecordFormBasicItem key={iIdx} item={item} />;
                                })}
                        </Box>
                    </React.Fragment>
                );
            })}
        </Box>
    );
};

export default RecordFormBasic;
