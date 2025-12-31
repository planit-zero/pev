-- PEV ODS Database Schema (PostgreSQL)
-- Vertica 대체용 로컬 개발 환경 스키마
-- 실제 병원 ODS는 매우 복잡하므로, 주요 테이블만 간소화하여 구성

-- Schema 생성
CREATE SCHEMA IF NOT EXISTS ODS;

-- ============================================
-- 1. 환자 관련 테이블
-- ============================================

-- 환자 기본정보 (S_PCTPCPAM)
CREATE TABLE IF NOT EXISTS ODS.S_PCTPCPAM (
    PT_NO VARCHAR(20) PRIMARY KEY,          -- 환자번호
    PT_NM VARCHAR(100) NOT NULL,            -- 환자명
    SEX_TP_CD CHAR(1),                      -- 성별 (M/F)
    PT_BRDY_DT VARCHAR(8),                  -- 생년월일 (YYYYMMDD)
    REG_DTM TIMESTAMP DEFAULT NOW(),
    UPD_DTM TIMESTAMP DEFAULT NOW()
);

-- ============================================
-- 2. 의무기록 관련 테이블
-- ============================================

-- 의무기록 메인 (S_MRDDRECM)
CREATE TABLE IF NOT EXISTS ODS.S_MRDDRECM (
    MDRC_ID VARCHAR(50) NOT NULL,           -- 의무기록ID
    MDRC_FOM_SEQ INTEGER NOT NULL,          -- 의무기록양식순번
    PT_NO VARCHAR(20) NOT NULL,             -- 환자번호
    PACT_ID VARCHAR(50),                    -- 원무접수ID
    PACT_TP_CD CHAR(1),                     -- 환자구분코드 (I:입원, O:외래, E:응급)
    PT_MED_DEPT_CD VARCHAR(10),             -- 진료과코드
    MDFM_ID VARCHAR(50),                    -- 의무기록양식ID
    MDFM_FOM_SEQ INTEGER,                   -- 양식순번
    MDFM_CLS_CD VARCHAR(10),                -- 의무기록양식분류코드
    MDFM_CLS_DTL_CD VARCHAR(10),            -- 의무기록양식분류상세코드
    WRT_STF_NO VARCHAR(20),                 -- 작성자직원번호
    WRTR_DEPT_CD VARCHAR(10),               -- 작성부서코드
    MDF_REC_DTM TIMESTAMP,                  -- 기록일시
    MED_DT DATE,                            -- 진료일자
    REC_DTM TIMESTAMP,                      -- 등록일시
    LST_YN CHAR(1) DEFAULT 'Y',             -- 최종여부
    MDRC_DC_TP_CD CHAR(1) DEFAULT 'C',      -- 의무기록문서타입코드
    MDRC_WRT_STS_CD VARCHAR(10),            -- 의무기록작성상태코드
    WK_SID VARCHAR(50),                     -- 작업세션ID
    WK_STF_NO VARCHAR(20),                  -- 작업직원번호
    PRIMARY KEY (MDRC_ID, MDRC_FOM_SEQ),
    FOREIGN KEY (PT_NO) REFERENCES ODS.S_PCTPCPAM(PT_NO)
);

-- 의무기록 양식 (S_MRFMFORM)
CREATE TABLE IF NOT EXISTS ODS.S_MRFMFORM (
    MDFM_ID VARCHAR(50) NOT NULL,           -- 의무기록양식ID
    MDFM_FOM_SEQ INTEGER NOT NULL,          -- 양식순번
    MDFM_NM VARCHAR(200),                   -- 양식명
    MDFM_CLS_CD VARCHAR(10),                -- 양식분류코드
    MDFM_APY_CTRA_CD VARCHAR(10),           -- 적용기준코드
    USE_YN CHAR(1) DEFAULT 'Y',
    PRIMARY KEY (MDFM_ID, MDFM_FOM_SEQ)
);

-- 수술 기록 (S_MRDDRONE)
CREATE TABLE IF NOT EXISTS ODS.S_MRDDRONE (
    MDRC_ID VARCHAR(50) NOT NULL,           -- 의무기록ID
    MDRC_FOM_SEQ INTEGER NOT NULL,          -- 의무기록양식순번
    OP_DTM TIMESTAMP,                       -- 수술일시
    PRIMARY KEY (MDRC_ID, MDRC_FOM_SEQ),
    FOREIGN KEY (MDRC_ID, MDRC_FOM_SEQ) REFERENCES ODS.S_MRDDRECM(MDRC_ID, MDRC_FOM_SEQ)
);

-- ============================================
-- 3. 간호기록 관련 테이블
-- ============================================

-- 간호기록 양식항목 (S_MRNNRFAE)
CREATE TABLE IF NOT EXISTS ODS.S_MRNNRFAE (
    NREC_ID VARCHAR(50) NOT NULL,           -- 간호기록ID
    NREC_DTL_ID VARCHAR(50) NOT NULL,       -- 간호기록상세ID
    NR_ITEM_ID INTEGER,                     -- 간호항목ID
    REC_CNTE TEXT,                          -- 기록내용
    LST_YN CHAR(1) DEFAULT 'Y',             -- 최종여부
    PRIMARY KEY (NREC_ID, NREC_DTL_ID)
);

-- 간호항목 마스터 (S_MRNNRIAM)
CREATE TABLE IF NOT EXISTS ODS.S_MRNNRIAM (
    NR_ITEM_ID INTEGER PRIMARY KEY,         -- 간호항목ID
    NR_ITEM_NM VARCHAR(200),                -- 간호항목명
    NR_ITEM_CTG_ID VARCHAR(50),             -- 간호항목카테고리ID
    USE_YN CHAR(1) DEFAULT 'Y',
    MDREC_VWR_PRNT_YN CHAR(1) DEFAULT 'Y',  -- 의무기록뷰어출력여부
    SORT_SEQ INTEGER
);

-- 간호항목 카테고리 (S_MRNNRICD)
CREATE TABLE IF NOT EXISTS ODS.S_MRNNRICD (
    NR_ITEM_CTG_ID VARCHAR(50) PRIMARY KEY, -- 간호항목카테고리ID
    NR_ITEM_CTG_NM VARCHAR(200),            -- 간호항목카테고리명
    NREC_CLS_CD VARCHAR(10),                -- 간호기록분류코드
    USE_YN CHAR(1) DEFAULT 'Y',
    LST_YN CHAR(1) DEFAULT 'Y',
    SORT_SEQ INTEGER
);

-- ============================================
-- 4. 마스터/공통코드 테이블
-- ============================================

-- 공통코드 (S_CCCCCSTE)
CREATE TABLE IF NOT EXISTS ODS.S_CCCCCSTE (
    COMN_GRP_CD VARCHAR(20) NOT NULL,       -- 공통그룹코드
    COMN_CD VARCHAR(20) NOT NULL,           -- 공통코드
    COMN_CD_NM VARCHAR(200),                -- 공통코드명
    USE_YN CHAR(1) DEFAULT 'Y',
    PRIMARY KEY (COMN_GRP_CD, COMN_CD)
);

-- 부서 마스터 (S_PDEDBMSM)
CREATE TABLE IF NOT EXISTS ODS.S_PDEDBMSM (
    DEPT_CD VARCHAR(10) PRIMARY KEY,        -- 부서코드
    DEPT_NM VARCHAR(200),                   -- 부서명
    USE_YN CHAR(1) DEFAULT 'Y'
);

-- 직원 마스터 (S_CNLRRUSD)
CREATE TABLE IF NOT EXISTS ODS.S_CNLRRUSD (
    STF_NO VARCHAR(20) PRIMARY KEY,         -- 직원번호
    STF_NM VARCHAR(100),                    -- 직원명
    DEPT_CD VARCHAR(10),                    -- 부서코드
    OCTY_TP_CD VARCHAR(10),                 -- 직종코드
    USE_YN CHAR(1) DEFAULT 'Y',
    FOREIGN KEY (DEPT_CD) REFERENCES ODS.S_PDEDBMSM(DEPT_CD)
);

-- ============================================
-- 인덱스 생성
-- ============================================

CREATE INDEX idx_mrddrecm_pt_no ON ODS.S_MRDDRECM(PT_NO);
CREATE INDEX idx_mrddrecm_mdfm_cls ON ODS.S_MRDDRECM(MDFM_CLS_CD);
CREATE INDEX idx_mrddrecm_pact_tp ON ODS.S_MRDDRECM(PACT_TP_CD);
CREATE INDEX idx_mrddrecm_med_dt ON ODS.S_MRDDRECM(MED_DT);
CREATE INDEX idx_mrddrecm_rec_dtm ON ODS.S_MRDDRECM(MDF_REC_DTM);

-- ============================================
-- 샘플 데이터 (선택사항)
-- ============================================

-- 부서 마스터 샘플
INSERT INTO ODS.S_PDEDBMSM (DEPT_CD, DEPT_NM) VALUES
('DEV001', '내과'),
('DEV002', '외과'),
('DEV003', '소아과'),
('DEV004', '정형외과')
ON CONFLICT DO NOTHING;

-- 직원 마스터 샘플
INSERT INTO ODS.S_CNLRRUSD (STF_NO, STF_NM, DEPT_CD, OCTY_TP_CD) VALUES
('DOC001', '홍길동', 'DEV001', 'D01'),
('DOC002', '김철수', 'DEV002', 'D01'),
('NUR001', '이영희', 'DEV003', 'N01')
ON CONFLICT DO NOTHING;

-- 공통코드 샘플 (의무기록 분류)
INSERT INTO ODS.S_CCCCCSTE (COMN_GRP_CD, COMN_CD, COMN_CD_NM) VALUES
('RDO062', 'D001', '경과기록'),
('RDO062', 'D002', '입원기록'),
('RDO062', 'D003', '퇴원요약'),
('RDO062', 'D004', '수술기록'),
('RDO062', 'D009', '검사결과'),
('RDO062', 'D020', '동의서')
ON CONFLICT DO NOTHING;

-- 환자 샘플 (5명)
INSERT INTO ODS.S_PCTPCPAM (PT_NO, PT_NM, SEX_TP_CD, PT_BRDY_DT) VALUES
('00000001', '테스트환자1', 'M', '19800101'),
('00000002', '테스트환자2', 'F', '19900515'),
('00000003', '테스트환자3', 'M', '19750320'),
('00000004', '테스트환자4', 'F', '19951208'),
('00000005', '테스트환자5', 'M', '20001105')
ON CONFLICT DO NOTHING;

-- 의무기록 양식 샘플 (5건)
INSERT INTO ODS.S_MRFMFORM (MDFM_ID, MDFM_FOM_SEQ, MDFM_NM, MDFM_CLS_CD, MDFM_APY_CTRA_CD, USE_YN) VALUES
('MDFM001', 1, '외래 경과기록지', 'D001', 'C01', 'Y'),
('MDFM002', 1, '입원 기록지', 'D002', 'C02', 'Y'),
('MDFM003', 1, '퇴원 요약지', 'D003', 'C03', 'Y'),
('MDFM004', 1, '수술 기록지', 'D004', 'C04', 'Y'),
('MDFM005', 1, '검사 결과지', 'D009', 'C05', 'Y')
ON CONFLICT DO NOTHING;

-- 의무기록 샘플 (8건 - 다양한 환자, 분류)
INSERT INTO ODS.S_MRDDRECM (MDRC_ID, MDRC_FOM_SEQ, PT_NO, PACT_ID, PACT_TP_CD, PT_MED_DEPT_CD, MDFM_ID, MDFM_FOM_SEQ, MDFM_CLS_CD, MDFM_CLS_DTL_CD, WRT_STF_NO, WRTR_DEPT_CD, MDF_REC_DTM, MED_DT, REC_DTM, LST_YN, MDRC_DC_TP_CD, MDRC_WRT_STS_CD, WK_SID, WK_STF_NO) VALUES
('REC20241201001', 1, '00000001', 'PACT001', 'O', 'DEV001', 'MDFM001', 1, 'D001', 'D01', 'DOC001', 'DEV001', '2024-12-01 10:30:00'::timestamp, '2024-12-01', '2024-12-01 10:35:00'::timestamp, 'Y', 'C', 'C', 'SID001', 'DOC001'),
('REC20241205001', 1, '00000002', 'PACT002', 'I', 'DEV002', 'MDFM002', 1, 'D002', 'D02', 'DOC002', 'DEV002', '2024-12-05 09:00:00'::timestamp, '2024-12-05', '2024-12-05 09:10:00'::timestamp, 'Y', 'C', 'C', 'SID002', 'DOC002'),
('REC20241210001', 1, '00000001', 'PACT003', 'I', 'DEV001', 'MDFM002', 1, 'D002', 'D02', 'DOC001', 'DEV001', '2024-12-10 14:00:00'::timestamp, '2024-12-10', '2024-12-10 14:05:00'::timestamp, 'Y', 'C', 'C', 'SID003', 'DOC001'),
('REC20241212001', 1, '00000003', 'PACT004', 'E', 'DEV004', 'MDFM001', 1, 'D001', 'D01', 'DOC002', 'DEV004', '2024-12-12 16:30:00'::timestamp, '2024-12-12', '2024-12-12 16:40:00'::timestamp, 'Y', 'C', 'C', 'SID004', 'DOC002'),
('REC20241215001', 1, '00000004', 'PACT005', 'O', 'DEV003', 'MDFM005', 1, 'D009', 'D09', 'NUR001', 'DEV003', '2024-12-15 11:20:00'::timestamp, '2024-12-15', '2024-12-15 11:25:00'::timestamp, 'Y', 'C', 'C', 'SID005', 'NUR001'),
('REC20241218001', 1, '00000005', 'PACT006', 'I', 'DEV002', 'MDFM004', 1, 'D004', 'D04', 'DOC002', 'DEV002', '2024-12-18 08:00:00'::timestamp, '2024-12-18', '2024-12-18 08:15:00'::timestamp, 'Y', 'C', 'C', 'SID006', 'DOC002'),
('REC20241220001', 1, '00000002', 'PACT007', 'I', 'DEV002', 'MDFM003', 1, 'D003', 'D03', 'DOC002', 'DEV002', '2024-12-20 15:00:00'::timestamp, '2024-12-20', '2024-12-20 15:10:00'::timestamp, 'Y', 'C', 'C', 'SID007', 'DOC002'),
('REC20241222001', 1, '00000003', 'PACT008', 'O', 'DEV001', 'MDFM001', 1, 'D001', 'D01', 'DOC001', 'DEV001', '2024-12-22 10:00:00'::timestamp, '2024-12-22', '2024-12-22 10:10:00'::timestamp, 'Y', 'C', 'C', 'SID008', 'DOC001')
ON CONFLICT DO NOTHING;

-- 수술 기록 샘플 (2건)
INSERT INTO ODS.S_MRDDRONE (MDRC_ID, MDRC_FOM_SEQ, OP_DTM) VALUES
('REC20241218001', 1, '2024-12-18 10:00:00'::timestamp),
('REC20241210001', 1, '2024-12-10 15:30:00'::timestamp)
ON CONFLICT DO NOTHING;

-- 간호항목 카테고리 샘플 (3건)
INSERT INTO ODS.S_MRNNRICD (NR_ITEM_CTG_ID, NR_ITEM_CTG_NM, NREC_CLS_CD, USE_YN, LST_YN, SORT_SEQ) VALUES
('CTG001', 'Vital Signs', 'N01', 'Y', 'Y', 1),
('CTG002', 'I/O', 'N02', 'Y', 'Y', 2),
('CTG003', 'Observation', 'N03', 'Y', 'Y', 3)
ON CONFLICT DO NOTHING;

-- 간호항목 샘플 (5건)
INSERT INTO ODS.S_MRNNRIAM (NR_ITEM_ID, NR_ITEM_NM, NR_ITEM_CTG_ID, USE_YN, MDREC_VWR_PRNT_YN, SORT_SEQ) VALUES
(1001, 'Blood Pressure', 'CTG001', 'Y', 'Y', 1),
(1002, 'Heart Rate', 'CTG001', 'Y', 'Y', 2),
(1003, 'Temperature', 'CTG001', 'Y', 'Y', 3),
(1004, 'Intake', 'CTG002', 'Y', 'Y', 4),
(1005, 'Output', 'CTG002', 'Y', 'Y', 5)
ON CONFLICT DO NOTHING;

-- 간호기록 샘플 (5건)
INSERT INTO ODS.S_MRNNRFAE (NREC_ID, NREC_DTL_ID, NR_ITEM_ID, REC_CNTE, LST_YN) VALUES
('NREC20241205001', 'DTL001', 1001, '120/80 mmHg', 'Y'),
('NREC20241205001', 'DTL002', 1002, '72 bpm', 'Y'),
('NREC20241205001', 'DTL003', 1003, '36.5 °C', 'Y'),
('NREC20241210001', 'DTL001', 1004, '1500 mL', 'Y'),
('NREC20241210001', 'DTL002', 1005, '1200 mL', 'Y')
ON CONFLICT DO NOTHING;
