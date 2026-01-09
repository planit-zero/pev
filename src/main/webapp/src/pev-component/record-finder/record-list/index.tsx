import React, { useState } from 'react';
import {
  Box,
  ToggleButton,
  ToggleButtonGroup,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Chip
} from '@mui/material';

type DeptFilter = '수진과' | '작성과';
type TypeFilter = '전체' | '외래' | '입원' | '응급';

const RecordList = () => {
  const [deptFilter, setDeptFilter] = useState<DeptFilter>('수진과');
  const [typeFilter, setTypeFilter] = useState<TypeFilter>('전체');

  const handleDeptChange = (_event: React.MouseEvent<HTMLElement>, newValue: DeptFilter | null) => {
    if (newValue !== null) {
      setDeptFilter(newValue);
    }
  };

  const handleTypeChange = (_event: React.MouseEvent<HTMLElement>, newValue: TypeFilter | null) => {
    if (newValue !== null) {
      setTypeFilter(newValue);
    }
  };

  const toggleButtonStyle = {
    py: 0.5,
    px: 1.5,
    fontSize: '0.75rem',
    textTransform: 'none',
    fontWeight: 500,
    '&.Mui-selected': {
      bgcolor: '#1976d2',
      color: '#fff',
      '&:hover': {
        bgcolor: '#1565c0',
      }
    }
  };

  // 샘플 데이터
  const records = [
    { date: '2024-01-10', time: '14:30', type: '외래', dept: '내과', doctor: '홍길동', content: '진료기록' },
    { date: '2024-01-09', time: '10:15', type: '입원', dept: '외과', doctor: '김철수', content: '수술기록' },
    { date: '2024-01-08', time: '16:45', type: '응급', dept: '응급의학과', doctor: '이영희', content: '응급기록' },
  ];

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      {/* 필터 영역 */}
      <Box sx={{ mb: 1.5, display: 'flex', gap: 2, alignItems: 'center', flexWrap: 'wrap' }}>
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
          <Typography sx={{ fontSize: '0.75rem', fontWeight: 600, color: '#546e7a', minWidth: 'fit-content' }}>
            구분
          </Typography>
          <ToggleButtonGroup
            value={deptFilter}
            exclusive
            onChange={handleDeptChange}
            size="small"
            sx={{ height: '28px' }}
          >
            <ToggleButton value="수진과" sx={toggleButtonStyle}>
              수진과
            </ToggleButton>
            <ToggleButton value="작성과" sx={toggleButtonStyle}>
              작성과
            </ToggleButton>
          </ToggleButtonGroup>
        </Box>

        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
          <Typography sx={{ fontSize: '0.75rem', fontWeight: 600, color: '#546e7a', minWidth: 'fit-content' }}>
            유형
          </Typography>
          <ToggleButtonGroup
            value={typeFilter}
            exclusive
            onChange={handleTypeChange}
            size="small"
            sx={{ height: '28px' }}
          >
            <ToggleButton value="전체" sx={toggleButtonStyle}>
              전체
            </ToggleButton>
            <ToggleButton value="외래" sx={toggleButtonStyle}>
              외래
            </ToggleButton>
            <ToggleButton value="입원" sx={toggleButtonStyle}>
              입원
            </ToggleButton>
            <ToggleButton value="응급" sx={toggleButtonStyle}>
              응급
            </ToggleButton>
          </ToggleButtonGroup>
        </Box>
      </Box>

      {/* 테이블 영역 */}
      <TableContainer sx={{ flex: 1, overflow: 'auto', border: '1px solid #e0e0e0', borderRadius: '4px' }}>
        <Table stickyHeader size="small" sx={{ '& .MuiTableCell-root': { fontSize: '0.75rem' } }}>
          <TableHead>
            <TableRow>
              <TableCell sx={{ bgcolor: '#f8f9fa', fontWeight: 600, color: '#37474f', py: 0.6, fontSize: '0.7rem' }}>날짜</TableCell>
              <TableCell sx={{ bgcolor: '#f8f9fa', fontWeight: 600, color: '#37474f', py: 0.6, fontSize: '0.7rem' }}>시간</TableCell>
              <TableCell sx={{ bgcolor: '#f8f9fa', fontWeight: 600, color: '#37474f', py: 0.6, fontSize: '0.7rem' }}>유형</TableCell>
              <TableCell sx={{ bgcolor: '#f8f9fa', fontWeight: 600, color: '#37474f', py: 0.6, fontSize: '0.7rem' }}>진료과</TableCell>
              <TableCell sx={{ bgcolor: '#f8f9fa', fontWeight: 600, color: '#37474f', py: 0.6, fontSize: '0.7rem' }}>담당의</TableCell>
              <TableCell sx={{ bgcolor: '#f8f9fa', fontWeight: 600, color: '#37474f', py: 0.6, fontSize: '0.7rem' }}>내용</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {records.map((record, index) => (
              <TableRow
                key={index}
                hover
                sx={{
                  cursor: 'pointer',
                  '&:hover': { bgcolor: '#f5f5f5' }
                }}
              >
                <TableCell sx={{ py: 0.8 }}>{record.date}</TableCell>
                <TableCell sx={{ py: 0.8 }}>{record.time}</TableCell>
                <TableCell sx={{ py: 0.8 }}>
                  <Chip
                    label={record.type}
                    size="small"
                    sx={{
                      height: '20px',
                      fontSize: '0.7rem',
                      bgcolor: record.type === '외래' ? '#e3f2fd' : record.type === '입원' ? '#fff3e0' : '#ffebee',
                      color: record.type === '외래' ? '#1976d2' : record.type === '입원' ? '#f57c00' : '#d32f2f',
                      fontWeight: 600
                    }}
                  />
                </TableCell>
                <TableCell sx={{ py: 0.8 }}>{record.dept}</TableCell>
                <TableCell sx={{ py: 0.8 }}>{record.doctor}</TableCell>
                <TableCell sx={{ py: 0.8 }}>{record.content}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default RecordList;
