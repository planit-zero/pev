#!/bin/bash

# 3. Spring Boot 애플리케이션 실행
echo "[3/3] Spring Boot 애플리케이션 실행 중..."
echo "=========================================="
./gradlew bootRun -x deleteWebApp
