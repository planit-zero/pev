#!/bin/bash

# DeView 개발 환경 시작 스크립트
# PostgreSQL Docker 컨테이너를 재시작하고 Spring Boot 애플리케이션을 실행합니다.

set -e # 에러 발생 시 스크립트 중단

DOCKER_COMPOSE_FILE="src/main/docker/postgres.yml"

echo "=========================================="
echo "DeView 개발 환경 시작"
echo "=========================================="
echo ""

# 1. 기존 Docker 컨테이너 종료 및 볼륨 삭제
echo "[1/3] Docker 컨테이너 종료 중..."
docker-compose -f "$DOCKER_COMPOSE_FILE" down -v
echo "✓ Docker 컨테이너 종료 완료"
echo ""

# 2. Docker 컨테이너 시작
echo "[2/3] Docker 컨테이너 시작 중..."
docker-compose -f "$DOCKER_COMPOSE_FILE" up -d
echo "✓ Docker 컨테이너 시작 완료"
echo ""

# 데이터베이스 초기화를 위한 대기 시간
echo "데이터베이스 초기화 대기 중... (5초)"
sleep 5
echo ""

# Note: bootRun은 포그라운드에서 실행되므로 Ctrl+C로 종료할 수 있습니다.
