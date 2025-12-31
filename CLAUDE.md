# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

DeView (가명화 EMR 뷰어) is a pseudonymized EMR (Electronic Medical Record) viewer for SNUH (Seoul National University Hospital). This is a full-stack application built with Spring Boot (Java 11) backend and React TypeScript frontend, deployed at https://deview.snuh.org.

## Build and Development Commands

### Backend (Gradle + Spring Boot)
```bash
# Full build (includes frontend build)
./gradlew clean build

# Run backend only (development)
./gradlew bootRun

# Run tests (currently disabled in build.gradle)
./gradlew test
```

### Frontend (React + TypeScript)
```bash
cd src/main/webapp

# Install dependencies
yarn install

# Development server (proxies to localhost:8080)
yarn start

# Production build
yarn build

# Lint TypeScript files
yarn lint
```

### Deployment
```bash
# Deploy to production server (WIFI6_2)
sh ./script/deploy.sh
```

The deployment script:
1. Builds the entire application (backend + frontend)
2. Backs up existing JAR on remote server
3. Copies new JAR to server at `172.23.100.18:9101`
4. Restarts the application

## Architecture

### Multi-Database Architecture

The application connects to **three separate databases**:

1. **ODS Database** (Primary - Vertica)
   - Medical records data from hospital EMR system
   - Config: `OdsDatabaseConfig.java`
   - Mapper location: `mapper/ods/*.xml`
   - Package: `ai.planit.pev.domain.ods.**`

2. **IRB Database** (Oracle)
   - Institutional Review Board data
   - Config: `IrbDatabaseConfig.java`
   - Mapper location: `mapper/irb/*.xml`
   - Package: `ai.planit.pev.domain.irb.**`

3. **Meta Database** (PostgreSQL)
   - Application metadata (users, reports, error logs, etc.)
   - Config: `MetaDatabaseConfig.java`
   - Mapper location: `mapper/meta/*.xml`
   - Package: `ai.planit.pev.domain.meta.**`

Each database has its own:
- DataSource bean
- SqlSessionFactory
- SqlSessionTemplate
- TransactionManager
- MyBatis mapper scanning configuration

### Backend Structure (Spring Boot)

```
src/main/java/ai/planit/pev/
├── config/              # Configuration classes
│   ├── database/        # Multi-database configs (ODS, IRB, Meta)
│   ├── idp/             # Identity Provider integration
│   └── resource/        # Resource handlers
├── core/                # Core utilities
│   ├── webclient/       # WebClient utilities
│   └── exception/       # Custom exceptions & handlers
├── domain/              # Domain-driven design structure
│   ├── ods/             # ODS database domain (23+ subdomains)
│   │   ├── patient/     # Patient data
│   │   ├── record/      # Medical records
│   │   ├── order/       # Medical orders
│   │   ├── inpatient/   # Inpatient info
│   │   └── ...          # anesthesia, bedsore, checkout, cpr, dialysis, etc.
│   ├── irb/             # IRB database domain
│   ├── meta/            # Meta database domain
│   │   ├── user/        # User management
│   │   ├── report/      # Error reporting
│   │   ├── record/      # Record metadata
│   │   └── env/         # Environment config
│   └── image/           # Image handling
├── strategy/            # Strategy patterns
└── utility/             # Utility classes
```

**Domain Layer Pattern** (consistent across all domains):
- `controller/` - REST endpoints (`@RestController`)
- `service/` - Business logic (`Service` interface + `ServiceImpl`)
- `dao/` - Data access (`DAO` interface + `DAOImpl` with MyBatis)
- `dto/` - Data transfer objects

### Frontend Structure (React + TypeScript)

```
src/main/webapp/src/
├── pev-component/       # Business components (Report, etc.)
├── pev-service/         # API service layer (RTK Query)
├── pev-interface/       # TypeScript interfaces (IPatient, IRecord, etc.)
├── pev-type/            # Type definitions
├── pev-utils/           # Utility functions
├── routes/              # Route configuration (React Router v6)
├── store/               # Redux state management
│   ├── slices/          # Generic slices
│   └── pev-slices/      # PEV-specific slices
├── layout/              # Layout components (MainLayout)
├── contexts/            # React contexts (JWTContext for auth)
├── themes/              # MUI theme customization
├── ui-component/        # Reusable UI components
├── menu-items/          # Menu configuration
└── hooks/               # Custom React hooks
```

**Key Frontend Patterns**:
- **API Layer**: Uses RTK Query in `pev-service/` directory (e.g., `PatientService.ts`, `ReportService.ts`)
- **State Management**: Redux Toolkit with slices in `store/`
- **Authentication**: JWT-based auth via `JWTContext` (see `contexts/JWTContext`)
- **Routing**: React Router v6 (very simple - only `/` and `/report` routes)
- **UI Framework**: Material-UI (MUI) v5 with custom theming

### Identity Provider Integration

The application uses a custom IDP (Identity Provider) SDK:
- SDK JAR: `src/main/libs/sdk-1.0.0-all.jar`
- Config: `IdpConfig.java`
- IDP URL and service provider configured in `application.yml`

### Branch Strategy

- `master` - Master branch
- `develop` - Development (main development branch)
- `ctdw` - CTDW-specific deployment
- `feat/styled-chart` - Chart redesign (pending merge)

## Important Implementation Notes

### Database Access
- All database queries use **MyBatis** with XML mappers
- DAO implementations are in separate `.java` files (not annotations)
- Transaction management is database-specific (three separate transaction managers)
- When modifying database queries, update corresponding XML mapper files in `src/main/resources/mapper/`

### Frontend Build Integration
The Gradle build process automatically:
1. Runs `yarn install` (frontend dependencies)
2. Runs `yarn build` (frontend production build)
3. Copies build output from `src/main/webapp/build/` to `src/main/resources/static/`
4. Deletes temporary build folder

This means **backend JAR includes the frontend** as static resources.

### Server Configuration
- Application runs on port `18083`
- PID file location: `/psdEx/pev/boot.pid`
- Spring profiles: `local`, `prod` (configured in `application-*.yml`)

### Error Handling
- Custom exception hierarchy: `BaseException` and `BaseExceptionHandler`
- Frontend error reporting via `ErrorLogger.ts` and `ReportService.ts`
- Report system tracks masking errors and chart errors

### Session Management
- Uses `HttpSession` for patient identifier (`pev-pid`)
- Session-based authentication state managed via IDP integration

## Medical Domain Context

The application handles various medical record types in the `ods` domain:
- Inpatient/outpatient records
- Medical orders (medications, procedures, labs)
- Nursing records (vital signs, I/O, observations)
- Specialized records (anesthesia, dialysis, CPR)
- Form data and images
- Pathology and specimen results
- Transfer and discharge summaries

Each type has its own subdomain under `domain/ods/` with the standard controller-service-dao-dto pattern.
