-- PEV Meta Database Schema (PostgreSQL)
-- 로컬 개발 환경용 스키마 정의

-- 1. 로그인 로그 테이블
CREATE TABLE IF NOT EXISTS pev_login_log (
    stf_no VARCHAR(50) NOT NULL,        -- 사번
    stf_nm VARCHAR(100) NOT NULL,       -- 직원명
    dept_cd VARCHAR(50),                -- 부서코드
    dept_nm VARCHAR(200),               -- 부서명
    auth_cd VARCHAR(50),                -- 권한코드
    login_dtm TIMESTAMP NOT NULL,       -- 로그인일시
    PRIMARY KEY (stf_no, login_dtm)
);

-- 2. 리포트 테이블
CREATE TABLE IF NOT EXISTS pev_report (
    report_id SERIAL PRIMARY KEY,       -- 리포트ID (auto increment)
    record_info TEXT,                   -- 레코드 정보
    report_user VARCHAR(50) NOT NULL,   -- 리포트 작성자
    report_dtm TIMESTAMP NOT NULL,      -- 리포트 작성일시
    irb VARCHAR(50),                    -- IRB 번호
    rid VARCHAR(50)                     -- 연구ID
);

-- 3. 리포트 상세 테이블
CREATE TABLE IF NOT EXISTS pev_report_detail (
    report_id INTEGER NOT NULL,         -- 리포트ID (FK)
    value_seq INTEGER NOT NULL,         -- 값 순번
    value_id VARCHAR(100),              -- 값 ID
    value_parent_id VARCHAR(100),       -- 부모 값 ID
    report_text TEXT,                   -- 리포트 내용
    process_yn CHAR(1) DEFAULT 'N',    -- 처리여부
    process_text TEXT,                  -- 처리 내용
    process_dtm TIMESTAMP,              -- 처리일시
    PRIMARY KEY (report_id, value_seq),
    FOREIGN KEY (report_id) REFERENCES pev_report(report_id) ON DELETE CASCADE
);

-- 4. 차트 에러 테이블
CREATE TABLE IF NOT EXISTS pev_chart_err (
    err_id SERIAL PRIMARY KEY,          -- 에러ID (auto increment)
    stf_no VARCHAR(50) NOT NULL,        -- 사번
    irb VARCHAR(50),                    -- IRB 번호
    rid VARCHAR(50),                    -- 연구ID
    target_record VARCHAR(200),         -- 대상 레코드
    process_yn CHAR(1) DEFAULT 'N',    -- 처리여부
    load_dtm TIMESTAMP NOT NULL,        -- 로드일시
    process_dtm TIMESTAMP               -- 처리일시
);

-- 5. 레코드 메타 테이블
CREATE TABLE IF NOT EXISTS pev_record (
    id VARCHAR(50) PRIMARY KEY,         -- 레코드ID
    parent_id VARCHAR(50),              -- 부모 레코드ID
    name VARCHAR(200) NOT NULL,         -- 레코드명
    display_seq INTEGER                 -- 표시 순서
);

-- 6. 레코드 포맷 테이블
CREATE TABLE IF NOT EXISTS pev_record_format (
    record_id VARCHAR(50) NOT NULL,     -- 레코드ID
    mdfm_id VARCHAR(50) NOT NULL,       -- 의무기록양식ID
    mdfm_fom_seq INTEGER NOT NULL,      -- 의무기록양식순번
    section_id VARCHAR(50) NOT NULL,    -- 섹션ID
    id VARCHAR(100) NOT NULL,           -- ID
    parent_id VARCHAR(100) NOT NULL,    -- 부모ID
    mdfm_cpem_no VARCHAR(50),           -- 의무기록구성요소번호
    class_type VARCHAR(50),             -- 클래스 타입
    control_type VARCHAR(50),           -- 컨트롤 타입
    masking_type VARCHAR(50),           -- 마스킹 타입
    content TEXT,                       -- 내용
    "desc" TEXT,                        -- 설명
    load_dtm TIMESTAMP,                 -- 로드일시
    PRIMARY KEY (record_id, mdfm_id, mdfm_fom_seq, section_id, id, parent_id)
);

-- 7. 이벤트 로그 테이블
CREATE TABLE IF NOT EXISTS pev_event_log (
    id SERIAL PRIMARY KEY,              -- 이벤트ID (auto increment)
    type VARCHAR(50) NOT NULL,          -- 이벤트 타입
    pt_no VARCHAR(50),                  -- 환자번호
    search_targets TEXT,                -- 검색 대상 (콤마 구분)
    search_from_date VARCHAR(50),       -- 검색 시작일
    search_to_date VARCHAR(50),         -- 검색 종료일
    pact_tp_cd VARCHAR(50),             -- 환자구분코드
    dept_type VARCHAR(50),              -- 진료과 타입
    dept_cd VARCHAR(50),                -- 진료과코드
    stf_no VARCHAR(50),                 -- 사번
    stf_nm VARCHAR(100),                -- 직원명
    load_dtm TIMESTAMP NOT NULL         -- 발생일시
);

-- 인덱스 생성
CREATE INDEX idx_login_log_dtm ON pev_login_log(login_dtm DESC);
CREATE INDEX idx_login_log_dept ON pev_login_log(dept_nm);
CREATE INDEX idx_report_user ON pev_report(report_user);
CREATE INDEX idx_report_dtm ON pev_report(report_dtm DESC);
CREATE INDEX idx_chart_err_dtm ON pev_chart_err(load_dtm DESC);
CREATE INDEX idx_event_log_dtm ON pev_event_log(load_dtm DESC);
CREATE INDEX idx_event_log_pt ON pev_event_log(pt_no);

-- ============================================
-- 샘플 데이터
-- ============================================

-- 1. 로그인 로그 샘플 (5건)
INSERT INTO pev_login_log (stf_no, stf_nm, dept_cd, dept_nm, auth_cd, login_dtm)
VALUES
('DOC001', '홍길동', 'DEV001', '내과', 'S', '2024-12-20 09:15:00'::timestamp),
('DOC002', '김철수', 'DEV002', '외과', 'S', '2024-12-19 10:30:00'::timestamp),
('NUR001', '이영희', 'DEV003', '소아과', 'U', '2024-12-18 08:45:00'::timestamp),
('DOC001', '홍길동', 'DEV001', '내과', 'S', '2024-12-15 14:20:00'::timestamp),
('DOC002', '김철수', 'DEV002', '외과', 'S', '2024-12-10 11:00:00'::timestamp)
ON CONFLICT DO NOTHING;

-- 2. 레코드 메타 샘플 (5건 - 부모-자식 관계)
INSERT INTO pev_record (id, parent_id, name, display_seq)
VALUES
('REC001', NULL, '경과기록', 1),
('REC002', NULL, '입원기록', 2),
('REC003', 'REC002', '입원기록-초진', 3),
('REC004', 'REC002', '입원기록-경과', 4),
('REC005', NULL, '수술기록', 5)
ON CONFLICT DO NOTHING;

-- 3. 에러 리포트 샘플 (3건)
INSERT INTO pev_report (record_info, report_user, report_dtm, irb, rid)
VALUES
('{"pt_no":"00000001","record_type":"경과기록"}', 'DOC001', '2024-12-15 15:30:00'::timestamp, 'IRB-2024-001', 'R001'),
('{"pt_no":"00000002","record_type":"입원기록"}', 'DOC002', '2024-12-10 16:45:00'::timestamp, 'IRB-2024-002', 'R002'),
('{"pt_no":"00000001","record_type":"수술기록"}', 'DOC001', '2024-12-05 14:20:00'::timestamp, 'IRB-2024-001', 'R001')
ON CONFLICT DO NOTHING;

-- 4. 리포트 상세 샘플 (6건 - 리포트별 2건씩)
INSERT INTO pev_report_detail (report_id, value_seq, value_id, value_parent_id, report_text, process_yn, process_text, process_dtm)
VALUES
(1, 1, 'field001', 'parent001', '환자명 마스킹 오류', 'Y', '수정 완료', '2024-12-16 10:00:00'::timestamp),
(1, 2, 'field002', 'parent001', '주민번호 노출', 'Y', '마스킹 처리 완료', '2024-12-16 10:15:00'::timestamp),
(2, 1, 'field003', 'parent002', '차트 렌더링 오류', 'N', NULL, NULL),
(2, 2, 'field004', 'parent002', '날짜 형식 오류', 'N', NULL, NULL),
(3, 1, 'field005', 'parent003', '수술기록 누락', 'Y', '데이터 복구 완료', '2024-12-06 09:30:00'::timestamp),
(3, 2, 'field006', 'parent003', '의사명 오타', 'N', NULL, NULL)
ON CONFLICT DO NOTHING;

-- 5. 차트 에러 샘플 (3건)
INSERT INTO pev_chart_err (stf_no, irb, rid, target_record, process_yn, load_dtm, process_dtm)
VALUES
('DOC001', 'IRB-2024-001', 'R001', '경과기록-2024-12-15', 'Y', '2024-12-15 15:30:00'::timestamp, '2024-12-16 09:00:00'::timestamp),
('DOC002', 'IRB-2024-002', 'R002', '입원기록-2024-12-10', 'N', '2024-12-10 16:45:00'::timestamp, NULL),
('NUR001', 'IRB-2024-003', 'R003', '간호기록-2024-12-08', 'N', '2024-12-08 11:20:00'::timestamp, NULL)
ON CONFLICT DO NOTHING;

-- 6. 레코드 포맷 샘플 (5건)
INSERT INTO pev_record_format (record_id, mdfm_id, mdfm_fom_seq, section_id, id, parent_id, mdfm_cpem_no, class_type, control_type, masking_type, content, "desc", load_dtm)
VALUES
('REC001', 'MDFM001', 1, 'SEC001', 'ID001', 'ROOT', 'CPEM001', 'TextField', 'text', 'name', '환자명', '환자 이름 입력', '2024-12-01 00:00:00'::timestamp),
('REC001', 'MDFM001', 1, 'SEC001', 'ID002', 'ROOT', 'CPEM002', 'TextField', 'date', 'none', '진료일자', '진료 날짜', '2024-12-01 00:00:00'::timestamp),
('REC002', 'MDFM002', 1, 'SEC002', 'ID003', 'ROOT', 'CPEM003', 'TextArea', 'textarea', 'partial', '주증상', '환자 주증상 기록', '2024-12-01 00:00:00'::timestamp),
('REC002', 'MDFM002', 1, 'SEC002', 'ID004', 'ROOT', 'CPEM004', 'TextField', 'text', 'ssn', '주민등록번호', '환자 주민번호', '2024-12-01 00:00:00'::timestamp),
('REC005', 'MDFM005', 1, 'SEC005', 'ID005', 'ROOT', 'CPEM005', 'TextArea', 'textarea', 'none', '수술소견', '수술 진행 상황', '2024-12-01 00:00:00'::timestamp)
ON CONFLICT DO NOTHING;

-- 7. 이벤트 로그 샘플 (5건)
INSERT INTO pev_event_log (type, pt_no, search_targets, search_from_date, search_to_date, pact_tp_cd, dept_type, dept_cd, stf_no, stf_nm, load_dtm)
VALUES
('SEARCH', '00000001', '경과기록,입원기록', '2024-01-01', '2024-12-31', 'I', 'ALL', 'DEV001', 'DOC001', '홍길동', '2024-12-20 09:30:00'::timestamp),
('VIEW', '00000002', '퇴원요약', '2024-06-01', '2024-12-31', 'O', 'DEPT', 'DEV002', 'DOC002', '김철수', '2024-12-19 11:00:00'::timestamp),
('EXPORT', '00000001', '수술기록', '2024-01-01', '2024-12-31', 'I', 'ALL', 'DOC001', 'DOC001', '홍길동', '2024-12-15 16:00:00'::timestamp),
('SEARCH', '00000002', '검사결과,경과기록', '2024-09-01', '2024-12-31', 'E', 'DEPT', 'DEV003', 'NUR001', '이영희', '2024-12-10 10:15:00'::timestamp),
('VIEW', '00000001', '간호기록', '2024-10-01', '2024-12-31', 'I', 'ALL', 'DEV001', 'DOC001', '홍길동', '2024-12-05 14:45:00'::timestamp)
ON CONFLICT DO NOTHING;
