#!/bin/bash

###############################################################################
# 변수 치환 스크립트
#
# 헥사고날 아키텍처 보일러플레이트를 사용자 프로젝트로 변환합니다.
#
# 사용법:
#   ./scripts/replace-variables.sh [PROJECT_NAME] [PROJECT_GROUP] [PROJECT_PACKAGE] [PROJECT_DOMAIN] [DB_SCHEMA]
#
# 예시:
#   ./scripts/replace-variables.sh my-service kr.co.mycompany kr.co.myproject.myapp myapp MySchema
###############################################################################

set -e

# 기본값 설정
PROJECT_NAME="${1:-boilerplate-service}"
PROJECT_GROUP="${2:-kr.co.example}"
PROJECT_PACKAGE="${3:-kr.co.example.service}"
PROJECT_DOMAIN="${4:-sample}"
DB_SCHEMA="${5:-SampleSchema}"

# 현재 패키지 (보일러플레이트 기본값)
CURRENT_PACKAGE="kr.co.example.service"
CURRENT_SCHEMA="SampleSchema"

echo "=========================================="
echo "헥사고날 아키텍처 보일러플레이트 변수 치환"
echo "=========================================="
echo "프로젝트 이름:     $PROJECT_NAME"
echo "프로젝트 그룹:     $PROJECT_GROUP"
echo "프로젝트 패키지:    $PROJECT_PACKAGE"
echo "도메인명:          $PROJECT_DOMAIN"
echo "DB 스키마:         $DB_SCHEMA"
echo "=========================================="

# 확인 메시지
read -p "위 설정으로 변수 치환을 진행하시겠습니까? (y/N): " confirm
if [[ ! "$confirm" =~ ^[Yy]$ ]]; then
    echo "작업이 취소되었습니다."
    exit 0
fi

echo ""
echo "1. 파일 내용 치환 중..."

# 파일 내용 치환 (find + sed)
find . -type f \( -name "*.kt" -o -name "*.kts" -o -name "*.yml" -o -name "*.yaml" -o -name "*.sql" -o -name "*.md" \) -exec sed -i '' \
    -e "s|boilerplate-service|$PROJECT_NAME|g" \
    -e "s|BoilerplateService|${PROJECT_NAME^}Service|g" \
    -e "s|BOILERPLATE_SERVICE|$(echo $PROJECT_NAME | tr '[:lower:]' '[:upper:]' | tr '-' '_')|g" \
    -e "s|kr\.co\.example\.service|$PROJECT_PACKAGE|g" \
    -e "s|SampleSchema|$DB_SCHEMA|g" \
    -e "s|sample|$PROJECT_DOMAIN|g" \
    {} \;

echo "   완료"

echo ""
echo "2. 파일 이름 변경 중..."

# boilerplate-service-api -> {PROJECT_NAME}-api
if [ -d "boilerplate-service-api" ]; then
    mv "boilerplate-service-api" "${PROJECT_NAME}-api"
    echo "   boilerplate-service-api -> ${PROJECT_NAME}-api"
fi

# boilerplate-service-batch -> {PROJECT_NAME}-batch
if [ -d "boilerplate-service-batch" ]; then
    mv "boilerplate-service-batch" "${PROJECT_NAME}-batch"
    echo "   boilerplate-service-batch -> ${PROJECT_NAME}-batch"
fi

echo "   완료"

echo ""
echo "3. 디렉토리 구조 변경 중..."

# 패키지 디렉토리 구조 변경
OLD_BASE_DIR="src/main/kotlin/kr/co/example/service"
NEW_BASE_DIR="src/main/kotlin/$(echo $PROJECT_PACKAGE | tr '.' '/')"

# 변경된 패키지 구조로 디렉토리 이동
for module in "${PROJECT_NAME}-api" domain port-in port-out application adapter/web-adapter adapter/persistence-adapter adapter/client-adapter adapter/cache-adapter adapter/memory-adapter adapter/message-adapter adapter/stream-adapter adapter/storage-adapter; do
    if [ -d "$module/$OLD_BASE_DIR" ]; then
        mkdir -p "$module/$NEW_BASE_DIR"
        # 파일 이동 (디렉토리가 아닌 파일만)
        find "$module/$OLD_BASE_DIR" -maxdepth 1 -type f -exec mv {} "$module/$NEW_BASE_DIR/" \; 2>/dev/null || true
        # 빈 디렉토리 정리
        find "$module/src/main/kotlin" -type d -empty -delete 2>/dev/null || true
    fi
done

# 테스트 디렉토리도 처리
OLD_TEST_DIR="src/test/kotlin/kr/co/example/service"
for module in "${PROJECT_NAME}-api"; do
    if [ -d "$module/$OLD_TEST_DIR" ]; then
        mkdir -p "$module/$NEW_BASE_DIR"
        find "$module/$OLD_TEST_DIR" -maxdepth 1 -type f -exec mv {} "$module/$(echo $PROJECT_PACKAGE | tr '.' '/')/" \; 2>/dev/null || true
        find "$module/src/test/kotlin" -type d -empty -delete 2>/dev/null || true
    fi
done

echo "   완료"

echo ""
echo "4. Gradle 설정 파일 업데이트 중..."

# settings.gradle.kts 업데이트
if [ -f "settings.gradle.kts" ]; then
    sed -i '' \
        -e "s|boilerplate-service-api|${PROJECT_NAME}-api|g" \
        -e "s|boilerplate-service-batch|${PROJECT_NAME}-batch|g" \
        settings.gradle.kts
    echo "   settings.gradle.kts 업데이트 완료"
fi

echo "   완료"

echo ""
echo "=========================================="
echo "변수 치환이 완료되었습니다!"
echo "=========================================="
echo ""
echo "다음 단계:"
echo "1. 프로젝트 빌드: ./gradlew clean build"
echo "2. 애플리케이션 실행: ./gradlew :${PROJECT_NAME}-api:bootTestRun"
echo "3. README.md를 참고하여 프로젝트 설정을 완료하세요."
echo ""
