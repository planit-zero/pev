# 로컬 개발 환경 구성 가이드

## 사전 요구사항

- Java 11 ✅
- Docker & Docker Compose
- Node.js 16+ & Yarn ✅
- (선택) 병원 내부망 VPN 접속

## 1. Docker로 Meta DB 실행

```bash
# Docker Compose로 PostgreSQL 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f postgres-meta

# DB 접속 확인
docker exec -it pev-meta-db psql -U pev -d pev -c "\dt"
```

초기 스키마는 `docker/postgres/init.sql`에서 자동으로 생성됩니다.

### DB 접속 정보
- **Host**: localhost
- **Port**: 5432
- **Database**: pev
- **User**: pev
- **Password**: pev_local

## 2. 개발 프로파일 선택

### 옵션 A: Meta DB만 사용 (VPN 없이)

**상황**: ODS/IRB DB 접근 불가, Meta DB 기능만 테스트

```yaml
# application-dev.yml에서 ODS, IRB 주석 처리
spring:
  # ods: ...  # 주석 처리
  # irb: ...  # 주석 처리
```

### 옵션 B: 전체 DB 사용 (VPN 연결)

**상황**: 병원 내부망 VPN으로 모든 DB 접근 가능

```bash
# VPN 연결 후
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## 3. 백엔드 실행

```bash
# dev 프로파일로 실행
./gradlew bootRun --args='--spring.profiles.active=dev'

# 또는 빌드 후 실행
./gradlew clean build
java -jar build/libs/pev-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

**서버 시작 확인**: http://localhost:8080

## 4. 프론트엔드 개발 서버 (선택)

```bash
cd src/main/webapp

# 의존성 설치
yarn install

# 개발 서버 실행 (localhost:3000, 백엔드는 8080 프록시)
yarn start
```

## 5. 전체 빌드 (프론트엔드 포함)

```bash
# 전체 빌드 (yarn install + yarn build + bootJar)
./gradlew clean build

# 빌드된 JAR 실행
java -jar build/libs/pev-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

## 데이터베이스 구성

### Meta DB (PostgreSQL) - Docker
- **역할**: 사용자 로그, 리포트, 에러 로그, 이벤트 등 애플리케이션 메타데이터
- **테이블**:
  - `pev_login_log` - 로그인 로그
  - `pev_report`, `pev_report_detail` - 에러 리포트
  - `pev_chart_err` - 차트 에러
  - `pev_record`, `pev_record_format` - 레코드 메타
  - `pev_event_log` - 이벤트 로그

### ODS DB (Vertica) - 병원 내부망
- **역할**: 환자 의료 기록 데이터
- **접근**: VPN 필요 (`172.26.33.19:5433`)

### IRB DB (Oracle) - 병원 내부망
- **역할**: IRB(Institutional Review Board) 데이터
- **접근**: VPN 필요 (`172.26.76.38:13625`)

## 문제 해결

### Docker DB 초기화

```bash
# 컨테이너와 볼륨 삭제 후 재생성
docker-compose down -v
docker-compose up -d
```

### Meta DB만으로 테스트하려면?

ODS/IRB 연결 실패 시 애플리케이션이 시작되지 않을 수 있습니다.
이 경우 `application-dev.yml`에서 해당 DB 설정을 주석 처리하거나,
데이터베이스 설정 클래스를 수정해야 할 수 있습니다.

### IDP 인증 우회

IDP 서버(`172.26.33.22:18020`)도 내부망에 있어 VPN 없이는 인증이 불가능합니다.
개발 시 인증을 우회하려면 별도 설정이 필요합니다.

## 프로파일 정리

- `local`: 개발자 개인 환경 (기존)
- `dev`: Docker 기반 로컬 개발 환경 (신규)
- `prod`: 운영 환경 (`deview.snuh.org`)
