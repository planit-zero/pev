-- PEV IRB Database Schema (PostgreSQL)
-- Oracle 대체용 로컬 개발 환경 스키마
-- IRB (Institutional Review Board) 승인 데이터

-- Schema 생성
CREATE SCHEMA IF NOT EXISTS NCRIS;

-- ============================================
-- IRB 승인 뷰 (V_IRBAPPROVAL_CDW_VIEW 대체)
-- ============================================

-- IRB 승인 테이블 (실제로는 뷰이지만, 로컬에서는 테이블로 생성)
CREATE TABLE IF NOT EXISTS NCRIS.V_IRBAPPROVAL_CDW_VIEW (
    IRBNO VARCHAR(50) PRIMARY KEY,          -- IRB 번호
    RESKORNM VARCHAR(500),                  -- 연구 한글명
    AVAISTARTDT VARCHAR(10),                -- 승인 시작일자 (YYYY-MM-DD)
    AVAIENDDT VARCHAR(10),                  -- 승인 종료일자 (YYYY-MM-DD)
    APPROVEDT VARCHAR(10),                  -- 승인일자 (YYYY-MM-DD)
    IRBMETHOD VARCHAR(10),                  -- IRB 심사방법 (1:정규, 2:신속, 3:긴급, 4:면제, 5:공동)
    HUMINRMANCT INTEGER,                    -- 대상자 수 (환자 수)
    PROFESSORID VARCHAR(20),                -- 연구책임자 직원번호
    REG_DTM TIMESTAMP DEFAULT NOW()
);

-- ============================================
-- 인덱스 생성
-- ============================================

CREATE INDEX idx_irb_professor ON NCRIS.V_IRBAPPROVAL_CDW_VIEW(PROFESSORID);
CREATE INDEX idx_irb_enddt ON NCRIS.V_IRBAPPROVAL_CDW_VIEW(AVAIENDDT);

-- ============================================
-- 샘플 데이터 (선택사항)
-- ============================================

-- IRB 승인 샘플 데이터 (8건)
INSERT INTO NCRIS.V_IRBAPPROVAL_CDW_VIEW
(IRBNO, RESKORNM, AVAISTARTDT, AVAIENDDT, APPROVEDT, IRBMETHOD, HUMINRMANCT, PROFESSORID)
VALUES
-- 기존 프로젝트 (3건)
('IRB-2024-001', '테스트 연구 프로젝트 1', '2024-01-01', '2025-12-31', '2024-01-01', '2', 100, 'DOC001'),
('IRB-2024-002', '테스트 연구 프로젝트 2', '2024-06-01', '2026-05-31', '2024-05-15', '1', 50, 'DOC001'),
('IRB-2024-003', '테스트 연구 프로젝트 3 (면제)', '2024-03-01', NULL, '2024-03-01', '4', 0, 'DOC002'),
-- 추가 프로젝트 (5건)
('IRB-2024-004', '당뇨병 환자 대상 임상연구', '2024-02-01', '2025-06-30', '2024-01-25', '2', 200, 'DOC001'),
('IRB-2024-005', '심혈관질환 관찰 연구', '2024-04-01', '2026-03-31', '2024-03-20', '1', 150, 'DOC002'),
('IRB-2024-006', '응급실 환자 데이터 분석 연구', '2024-05-01', '2024-12-31', '2024-04-28', '2', 80, 'DOC001'),
('IRB-2024-007', '소아 성장발달 추적 연구', '2024-07-01', '2027-06-30', '2024-06-15', '1', 300, 'NUR001'),
('IRB-2024-008', '수술 후 회복 패턴 연구 (긴급)', '2024-11-15', '2025-05-14', '2024-11-14', '3', 50, 'DOC002')
ON CONFLICT DO NOTHING;
