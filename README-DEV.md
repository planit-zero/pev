# 로컬 개발 환경 구성 가이드

## 개요

이 가이드는 VPN 없이 **완전히 로컬 환경에서** PEV 시스템을 실행하는 방법을 설명합니다.
모든 데이터베이스(Meta, ODS, IRB)를 Docker PostgreSQL로 대체하여 구성합니다.

## 사전 요구사항

- ✅ Java 11
- ✅ Docker & Docker Compose
- ✅ Node.js 16+ & Yarn

## 빠른 시작

```bash
# 1. 모든 DB 컨테이너 실행
docker-compose up -d

# 2. 백엔드 실행 (dev 프로파일)
./gradlew bootRun --args='--spring.profiles.active=dev'

# 3. (선택) 프론트엔드 개발 서버
cd src/main/webapp
yarn install
yarn start
```

## 1. 데이터베이스 구성

Docker Compose로 3개의 PostgreSQL 컨테이너가 실행됩니다:

### Meta DB (port: 5432)
- **역할**: 애플리케이션 메타데이터 (로그인, 리포트, 에러, 이벤트)
- **접속**: `psql -h localhost -p 5432 -U pev -d pev`
- **스키마**: `src/main/docker/postgres/init.sql`

### ODS DB (port: 5433)
- **역할**: 의료 데이터 (환자, 의무기록, 간호기록)
- **실제 시스템**: Vertica → PostgreSQL로 대체
- **접속**: `psql -h localhost -p 5433 -U ods -d ods`
- **스키마**: `src/main/docker/postgres/init-ods.sql`
- **샘플 데이터**: 테스트 환자 2명, 부서 4개, 직원 3명 포함

### IRB DB (port: 5434)
- **역할**: IRB 연구 승인 데이터
- **실제 시스템**: Oracle → PostgreSQL로 대체
- **접속**: `psql -h localhost -p 5434 -U irb -d irb`
- **스키마**: `src/main/docker/postgres/init-irb.sql`
- **샘플 데이터**: 테스트 연구 프로젝트 3건 포함

## 2. Docker 명령어

```bash
# 모든 DB 시작
docker-compose up -d

# 로그 확인
docker-compose logs -f

# 개별 DB 재시작
docker-compose restart postgres-meta
docker-compose restart postgres-ods
docker-compose restart postgres-irb

# DB 초기화 (모든 데이터 삭제)
docker-compose down -v
docker-compose up -d

# DB 직접 접속
docker exec -it pev-meta-db psql -U pev -d pev
docker exec -it pev-ods-db psql -U ods -d ods
docker exec -it pev-irb-db psql -U irb -d irb
```

## 3. 백엔드 실행

```bash
# dev 프로파일로 실행 (로컬 Docker DB 사용)
./gradlew bootRun --args='--spring.profiles.active=dev'

# 또는 빌드 후 실행
./gradlew clean build
java -jar build/libs/pev-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

**서버 확인**: http://localhost:8080

## 4. 프론트엔드 개발

```bash
cd src/main/webapp

# 의존성 설치
yarn install

# 개발 서버 실행 (localhost:3000, 백엔드 8080 프록시)
yarn start

# 프로덕션 빌드
yarn build
```

## 5. 전체 빌드

```bash
# 백엔드 + 프론트엔드 통합 빌드
./gradlew clean build

# 빌드 결과 실행
java -jar build/libs/pev-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

## 프로파일 비교

| 프로파일 | Meta DB | ODS DB | IRB DB | 용도 |
|---------|---------|--------|--------|------|
| `local` | 내부망 | 내부망 | 내부망 | VPN 연결 + 개인 환경 |
| `dev` | Docker | Docker | Docker | VPN 없이 로컬 개발 |
| `prod` | 내부망 | 내부망 | 내부망 | 운영 서버 |

## 데이터베이스 스키마

### Meta DB 테이블
- `pev_login_log` - 로그인 로그
- `pev_report` - 에러 리포트
- `pev_report_detail` - 리포트 상세
- `pev_chart_err` - 차트 에러
- `pev_record` - 레코드 메타데이터
- `pev_record_format` - 레코드 포맷
- `pev_event_log` - 이벤트 로그

### ODS DB 주요 테이블 (Schema: ODS)
- `S_PCTPCPAM` - 환자 기본정보
- `S_MRDDRECM` - 의무기록 메인
- `S_MRFMFORM` - 의무기록 양식
- `S_MRDDRONE` - 수술 기록
- `S_MRNNRFAE` - 간호기록 양식항목
- `S_MRNNRIAM` - 간호항목 마스터
- `S_MRNNRICD` - 간호항목 카테고리
- `S_CCCCCSTE` - 공통코드
- `S_PDEDBMSM` - 부서 마스터
- `S_CNLRRUSD` - 직원 마스터

### IRB DB 테이블 (Schema: NCRIS)
- `V_IRBAPPROVAL_CDW_VIEW` - IRB 승인 정보

## 알려진 이슈

### 1. IDP 인증
`application-dev.yml`의 IDP 서버 URL이 내부망 주소입니다.
VPN 없이는 인증이 불가능할 수 있으며, 별도 인증 우회 설정이 필요할 수 있습니다.

### 2. 데이터베이스 Dialect 차이
- **Vertica → PostgreSQL**: 일부 Vertica 전용 함수 미지원 가능
- **Oracle → PostgreSQL**: 일부 Oracle 전용 함수 미지원 가능

애플리케이션 코드에서 데이터베이스 Dialect에 의존하는 쿼리는 수정이 필요할 수 있습니다.

### 3. 샘플 데이터 부족
로컬 DB는 최소한의 샘플 데이터만 포함합니다.
실제 의료 데이터를 테스트하려면 추가 데이터 삽입이 필요합니다.

## 디렉토리 구조

```
pev/
├── docker-compose.yml                # Docker 실행 설정
├── src/main/
│   ├── docker/
│   │   └── postgres/                # DB 초기화 스크립트
│   │       ├── init.sql             # Meta DB 스키마
│   │       ├── init-ods.sql         # ODS DB 스키마
│   │       └── init-irb.sql         # IRB DB 스키마
│   ├── resources/
│   │   ├── application.yml          # 기본 설정
│   │   ├── application-dev.yml      # 로컬 개발 설정 (Docker)
│   │   ├── application-local.yml    # 개인 설정 (VPN)
│   │   └── application-prod.yml     # 운영 설정
│   └── webapp/                      # React 프론트엔드
└── README-DEV.md                    # 이 문서
```

## 문제 해결

### 포트 충돌
다른 서비스가 5432, 5433, 5434 포트를 사용 중이면:
```bash
# docker-compose.yml에서 포트 변경
ports:
  - "15432:5432"  # Meta DB
  - "15433:5432"  # ODS DB
  - "15434:5432"  # IRB DB
```

### DB 연결 실패
```bash
# 컨테이너 상태 확인
docker-compose ps

# 로그 확인
docker-compose logs postgres-meta
docker-compose logs postgres-ods
docker-compose logs postgres-irb

# 재시작
docker-compose restart
```

### 스키마 변경 반영
```bash
# 볼륨 삭제 후 재생성 (모든 데이터 삭제됨!)
docker-compose down -v
docker-compose up -d
```
