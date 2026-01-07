# JSON Server Middleware API 테스트 가이드

json-server-middleware.js의 API 엔드포인트를 curl로 테스트하는 방법입니다.

## 사전 준비

json-server가 실행 중이어야 합니다:
```bash
cd src/main/webapp
npx json-server --watch db.json --port 28092 --middlewares json-server-middleware.js
```

**포트**: 28092

---

## API 엔드포인트 테스트

### 1. POST /api/ann/identification
**기능**: RID → PID 변환

#### 성공 케이스

**Bash / Git Bash:**
```bash
curl -X POST http://localhost:28092/api/ann/identification \
  -H "Content-Type: application/json" \
  -d '{"ridList": ["RID-001-0001"]}'
```

**PowerShell:**
```powershell
curl -X POST http://localhost:28092/api/ann/identification -H "Content-Type: application/json" -d '{\"ridList\": [\"RID-001-0001\"]}'
```

**응답 예시:**
```json
["0001"]
```

---

#### 다양한 RID 테스트

**RID-001-0002**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/identification \
  -H "Content-Type: application/json" \
  -d '{"ridList": ["RID-001-0002"]}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/identification -H "Content-Type: application/json" -d '{\"ridList\": [\"RID-001-0002\"]}'
```

응답: `["0002"]`

---

**RID-101-0005**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/identification \
  -H "Content-Type: application/json" \
  -d '{"ridList": ["RID-101-0005"]}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/identification -H "Content-Type: application/json" -d '{\"ridList\": [\"RID-101-0005\"]}'
```

응답: `["0005"]`

---

**존재하지 않는 RID (기본값 반환)**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/identification \
  -H "Content-Type: application/json" \
  -d '{"ridList": ["RID-999-9999"]}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/identification -H "Content-Type: application/json" -d '{\"ridList\": [\"RID-999-9999\"]}'
```

응답: `["00000001"]`

---

#### 에러 케이스

**ridList 없음**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/identification \
  -H "Content-Type: application/json" \
  -d '{}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/identification -H "Content-Type: application/json" -d '{}'
```

응답: `{"error": "ridList is required"}` (HTTP 400)

---

**빈 ridList**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/identification \
  -H "Content-Type: application/json" \
  -d '{"ridList": []}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/identification -H "Content-Type: application/json" -d '{\"ridList\": []}'
```

응답: `{"error": "ridList is required"}` (HTTP 400)

---

### 2. POST /api/ann/irb-rid
**기능**: IRB별 환자 목록 조회

#### IRB-2024-001 (5명)

**Bash:**
```bash
curl -X POST http://localhost:28092/api/ann/irb-rid \
  -H "Content-Type: application/json" \
  -d '{"irb": "IRB-2024-001"}'
```

**PowerShell:**
```powershell
curl -X POST http://localhost:28092/api/ann/irb-rid -H "Content-Type: application/json" -d '{\"irb\": \"IRB-2024-001\"}'
```

**응답 예시:**
```json
[
  {"id": "RID-001-0001", "name": "테스트환자1", "dob": "19800101"},
  {"id": "RID-001-0002", "name": "테스트환자2", "dob": "19900515"},
  {"id": "RID-001-0003", "name": "테스트환자3", "dob": "19750320"},
  {"id": "RID-001-0004", "name": "테스트환자4", "dob": "19951208"},
  {"id": "RID-001-0005", "name": "테스트환자5", "dob": "20001105"}
]
```

---

#### 다른 IRB 조회

**IRB-2024-002 (3명)**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/irb-rid \
  -H "Content-Type: application/json" \
  -d '{"irb": "IRB-2024-002"}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/irb-rid -H "Content-Type: application/json" -d '{\"irb\": \"IRB-2024-002\"}'
```

---

**IRB-2024-003 (2명)**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/irb-rid \
  -H "Content-Type: application/json" \
  -d '{"irb": "IRB-2024-003"}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/irb-rid -H "Content-Type: application/json" -d '{\"irb\": \"IRB-2024-003\"}'
```

---

**IRB-2024-101 (5명)**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/irb-rid \
  -H "Content-Type: application/json" \
  -d '{"irb": "IRB-2024-101"}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/irb-rid -H "Content-Type: application/json" -d '{\"irb\": \"IRB-2024-101\"}'
```

---

**IRB-2024-102 (4명)**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/irb-rid \
  -H "Content-Type: application/json" \
  -d '{"irb": "IRB-2024-102"}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/irb-rid -H "Content-Type: application/json" -d '{\"irb\": \"IRB-2024-102\"}'
```

---

#### 존재하지 않는 IRB (기본값 반환)

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/irb-rid \
  -H "Content-Type: application/json" \
  -d '{"irb": "IRB-9999-999"}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/irb-rid -H "Content-Type: application/json" -d '{\"irb\": \"IRB-9999-999\"}'
```

응답: `[{"id": "RID-999-0001", "name": "테스트환자1", "dob": "19800101"}]`

---

### 3. POST /api/ann/rex
**기능**: GID → RID 변환

**로직**: GID의 마지막 4자리를 추출하여 `RID-001-{suffix}` 형식으로 변환

#### 기본 테스트

**Bash:**
```bash
curl -X POST http://localhost:28092/api/ann/rex \
  -H "Content-Type: application/json" \
  -d '{"irbNo": "IRB-2024-001", "data": [{"gid": "GID-ABCD-1234"}]}'
```

**PowerShell:**
```powershell
curl -X POST http://localhost:28092/api/ann/rex -H "Content-Type: application/json" -d '{\"irbNo\": \"IRB-2024-001\", \"data\": [{\"gid\": \"GID-ABCD-1234\"}]}'
```

**응답 예시:**
```json
{
  "irbNo": "IRB-2024-001",
  "data": [
    {"rid": "RID-001-1234"}
  ]
}
```

---

#### 여러 GID 변환

**Bash:**
```bash
curl -X POST http://localhost:28092/api/ann/rex \
  -H "Content-Type: application/json" \
  -d '{"irbNo": "IRB-2024-001", "data": [{"gid": "GID-ABCD-0001"}, {"gid": "GID-EFGH-0002"}, {"gid": "GID-IJKL-0003"}]}'
```

**PowerShell:**
```powershell
curl -X POST http://localhost:28092/api/ann/rex -H "Content-Type: application/json" -d '{\"irbNo\": \"IRB-2024-001\", \"data\": [{\"gid\": \"GID-ABCD-0001\"}, {\"gid\": \"GID-EFGH-0002\"}, {\"gid\": \"GID-IJKL-0003\"}]}'
```

**응답:**
```json
{
  "irbNo": "IRB-2024-001",
  "data": [
    {"rid": "RID-001-0001"},
    {"rid": "RID-001-0002"},
    {"rid": "RID-001-0003"}
  ]
}
```

---

#### 엣지 케이스

**빈 data 배열**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/rex \
  -H "Content-Type: application/json" \
  -d '{"irbNo": "IRB-2024-001", "data": []}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/rex -H "Content-Type: application/json" -d '{\"irbNo\": \"IRB-2024-001\", \"data\": []}'
```

응답: `{"irbNo": "IRB-2024-001", "data": []}`

---

**GID가 4자리보다 짧은 경우 (기본값 반환)**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/rex \
  -H "Content-Type: application/json" \
  -d '{"irbNo": "IRB-2024-001", "data": [{"gid": "ABC"}]}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/rex -H "Content-Type: application/json" -d '{\"irbNo\": \"IRB-2024-001\", \"data\": [{\"gid\": \"ABC\"}]}'
```

응답: `{"irbNo": "IRB-2024-001", "data": [{"rid": "RID-001-0001"}]}`

---

**data 없음**

Bash:
```bash
curl -X POST http://localhost:28092/api/ann/rex \
  -H "Content-Type: application/json" \
  -d '{"irbNo": "IRB-2024-001"}'
```

PowerShell:
```powershell
curl -X POST http://localhost:28092/api/ann/rex -H "Content-Type: application/json" -d '{\"irbNo\": \"IRB-2024-001\"}'
```

응답: `{"irbNo": "IRB-2024-001", "data": []}`

---

## PowerShell에서 Invoke-RestMethod 사용 (추천)

PowerShell에서는 `Invoke-RestMethod`를 사용하면 더 깨끗하게 작성할 수 있습니다:

```powershell
# 1. POST /api/ann/identification
$body = @{
    ridList = @("RID-001-0001")
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri http://localhost:28092/api/ann/identification -ContentType 'application/json' -Body $body

# 2. POST /api/ann/irb-rid
$body = @{
    irb = "IRB-2024-001"
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri http://localhost:28092/api/ann/irb-rid -ContentType 'application/json' -Body $body

# 3. POST /api/ann/rex
$body = @{
    irbNo = "IRB-2024-001"
    data = @(
        @{ gid = "GID-ABCD-1234" }
    )
} | ConvertTo-Json

Invoke-RestMethod -Method Post -Uri http://localhost:28092/api/ann/rex -ContentType 'application/json' -Body $body
```

---

## jq를 사용한 보기 좋은 출력 (Bash)

```bash
curl -X POST http://localhost:28092/api/ann/irb-rid \
  -H "Content-Type: application/json" \
  -d '{"irb": "IRB-2024-001"}' | jq '.'
```

---

## 참고 사항

- 모든 엔드포인트는 POST 메서드만 지원합니다
- Content-Type은 반드시 `application/json`이어야 합니다
- 서버 포트: **28092**
- PowerShell에서 curl 사용 시 JSON 내부의 큰따옴표를 `\"`로 이스케이프해야 합니다
- 매칭되지 않는 요청은 기본 JSON Server 동작으로 전달됩니다
