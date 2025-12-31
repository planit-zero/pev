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

-- 샘플 데이터 (선택사항)
-- INSERT INTO pev_login_log (stf_no, stf_nm, dept_cd, dept_nm, auth_cd, login_dtm)
-- VALUES ('local001', 'Local User', 'DEV', 'Development', 'S', now());
