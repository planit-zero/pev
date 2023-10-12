import * as React from 'react';
import { IRecordEntity } from '../../pev-interface/IRecord';
import { Box, Typography } from '@mui/material';

type RecordEntityTableProps = {
    entity: IRecordEntity;
};

const RecordEntityTable = (props: RecordEntityTableProps) => {
    return (
        <Box display={'flex'} justifyContent={'space-between'} alignItems={'center'}>
            {props.entity.attributes.map((attribute, aIdx) => {
                return (
                    <table
                        key={aIdx}
                        border={1}
                        style={{
                            width: 'calc(100% / 3)',
                            borderCollapse: 'collapse'
                        }}
                    >
                        <tbody>
                            <tr>
                                <th style={{ backgroundColor: '#3f51b5' }}>
                                    <Typography
                                        sx={{
                                            color: '#ffffff',
                                            fontSize: 'h4.fontSize',
                                            fontWeight: 'bold'
                                        }}
                                    >
                                        {attribute.text}
                                    </Typography>
                                </th>
                            </tr>
                            {attribute.values.map((value, vIdx) => {
                                return (
                                    <tr>
                                        <td key={vIdx} style={{ height: '25px', padding: '4px' }}>
                                            <Typography sx={{ fontSize: 'h6.fontSize' }}>{value.text}</Typography>
                                        </td>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                );
            })}
        </Box>
    );
};

export default RecordEntityTable;
