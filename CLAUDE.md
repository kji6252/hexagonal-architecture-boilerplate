# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 필수 명령어

```bash
# 전체 프로젝트 빌드
./gradlew clean build

# 단위 테스트 실행 (integration 태그 제외됨)
./gradlew test

# 통합 테스트 실행 (TestContainers 사용)
./gradlew integrationTest

# 특정 테스트 클래스 실행
./gradlew :domain:test --tests "kr.co.uplus.service.specific.ClassName"

# 로컬에서 애플리케이션 실행 (defaultTasks 설정됨)
./gradlew
# 또는 명시적으로: ./gradlew :boilerplate-service-api:bootTestRun
# 그 후 boilerplate-service-api/src/test/kotlin/의 TestBoilerplateServiceApiApplication.kt 실행

# OpenAPI 스펙에서 API 코드 생성
./gradlew openApiGenerate

# Allure 테스트 리포트 생성
./gradlew allureAggregateReport
```

**참고**: `defaultTasks`가 `:boilerplate-service-api:bootTestRun`으로 설정되어 있어 `./gradlew`만 실행해도 됩니다.

## 프로젝트 아키텍처

헥사고날 아키텍처 (ports & adapters) 보일러플레이트 구현입니다. 의존성 흐름: `adapter → port-in/port-out → application → domain → core`

### 모듈 구조

```
boilerplate-service-api/          # Spring Boot 애플리케이션 진입점 (Test**Application.kt로 실행)
boilerplate-service-batch/         # 배치 처리 모듈

core/                        # 공통 유틸리티, 비즈니스 로직 없음
domain/                      # 순수 비즈니스 로직, 엔티티, 값 객체, 예외
application/                 # port-in 유스케이스 구현, 도메인 로직 오케스트레이션
port-in/                     # 유스케이스 인터페이스 (시스템이 할 수 있는 것)
port-out/                    # 레파지토리/서비스 인터페이스 (외부와 상호작용하는 방식)

adapter/
  ├── web-adapter/          # REST 컨트롤러 (port-in 구현)
  ├── persistence-adapter/   # JPA 레파지토리 (port-out 구현)
  ├── client-adapter/        # 외부 서비스 클라이언트 (port-out 구현)
  ├── cache-adapter/         # Redis 캐싱 (port-out 구현)
  ├── memory-adapter/        # 인메모리 저장소 (port-out 구현)
  ├── message-adapter/       # SMS/메시징 (port-out 구현)
  ├── stream-adapter/        # Kafka 스트리밍 (port-out 구현)
  └── storage-adapter/       # S3 스토리지 (port-out 구현)
```

### 의존성 규칙

- **web-adapter**는 **port-in**에 의존 (유스케이스 호출)
- 다른 **adapters**는 **port-out**에 의존 (레파지토리 구현)
- **application**은 **port-in**을 구현하고 **port-out**에 의존
- **application**은 **domain**에 의존
- **domain**은 **core**에만 의존

### 핵심 패턴

1. **포트를 인터페이스로**: `port-in`은 유즈케이스 정의, `port-out`은 레파지토리 계약 정의
2. **어댑터를 구현체로**: 각 어댑터는 하나 이상의 포트 구현
3. **도메인 로직의 순수성**: 도메인은 프레임워크 의존성 없음
4. **프로필을 통한 설정**: `local`, `dev`, `stg`, `prod`

## 기술 스택

- **Java 21** + **Kotlin 1.9.25**
- **Spring Boot 3.4.4** + Spring Cloud
- **JPA/Hibernate** with MySQL 8.0.36
- **Redis** 캐싱
- **Kafka** 메시징
- **TestContainers** 통합 테스트
- **Kotest** + **MockK** 테스트
- **MapStruct** 객체 매핑
- **Flyway** DB 마이그레이션

## API 개발

API 스펙은 `adapter/web-adapter/src/api-specs/specs/`에 YAML 파일로 정의됩니다. 새 엔드포인트 추가 시:

1. OpenAPI YAML 스펙 추가/수정
2. `./gradlew openApiGenerate` 실행으로 모델 생성
3. `application/`에 유스케이스 구현
4. `port-in/`에 포트 인터페이스 추가
5. 필요시 `port-out/`에 포트 아웃 인터페이스 추가
6. 필요한 어댑터 구현
7. `web-adapter/`에 컨트롤러 추가

## 테스트

- **단위 테스트**: `@Tag("unit")` 태그, `./gradlew test`로 실행
- **통합 테스트**: `@Tag("integration")` 태그, `./gradlew integrationTest`로 실행
- 통합 테스트는 **TestContainers**와 MySQL 사용
- 테스트 병렬 실행 가능

## 로컬 개발

- `src/test/kotlin/` 디렉토리의 `Test**Application.kt` 파일로 애플리케이션 실행
- 기본 태스크는 `:boilerplate-service-api:bootTestRun` 실행
- TestContainers DB 비밀번호: `test`

### IntelliJ IDEA DB 설정 (선택사항)

TestContainers로 실행 중인 DB에 IDE에서 연결하려면 **Testcontainers Port Updater** 플러그인을 설치하세요:

1. [Testcontainers Port Updater](https://plugins.jetbrains.com/plugin/17116-testcontainers-port-updater) 설치
2. 설정에서 `Everything`으로 설정
3. 아래 DataSource를 IDE DB Section에 붙여넣기
4. 비밀번호: `test`

```
#DataSourceSettings#
#LocalDataSource: SampleSchema@localhost
#BEGIN#
<data-source source="LOCAL" name="SampleSchema@localhost" uuid="04c7086f-5bc9-49e2-8282-70b19efdd57f"><database-info product="MySQL" version="8.0.36" jdbc-version="4.2" driver-name="MySQL Connector/J" driver-version="mysql-connector-j-8.2.0 (Revision: 06a1f724497fd81c6a659131fda822c9e5085b6c)" dbms="MYSQL" exact-version="8.0.36" exact-driver-version="8.2"><extra-name-characters>#@</extra-name-characters><identifier-quote-string>`</identifier-quote-string><jdbc-catalog-is-schema>true</jdbc-catalog-is-schema></database-info><case-sensitivity plain-identifiers="exact" quoted-identifiers="exact"/><driver-ref>mysql.8</driver-ref><synchronize>true</synchronize><jdbc-driver>com.mysql.cj.jdbc.Driver</jdbc-driver><jdbc-url>jdbc:mysql://localhost:32841/SampleSchema</jdbc-url><secret-storage>master_key</secret-storage><user-name>test</user-name><schema-mapping><introspection-scope><node kind="schema"><name qname="@"/><name qname="information_schema"/><name qname="performance_schema"/></node></introspection-scope></schema-mapping><working-dir>$ProjectFileDir$</working-dir></data-source>
#END#
```

## DB 마이그레이션

Flyway 마이그레이션은 `adapter/persistence-adapter/src/main/resources/db/migration/`에 위치합니다.

## 코드 스타일

- 2칸 들여쓰기
- 최대 줄 길이: 120자
- UTF-8 인코딩

## Hello World 예제

이 보일러플레이트는 다음 Hello World 예제를 포함합니다:

### API 엔드포인트

- `GET /api/v1/sample/hello?name=World` - Hello World 메시지 반환
- `GET /api/v1/sample/{id}` - 샘플 데이터 조회

### 핵심 파일 (패키지: kr.co.uplus.service)

| 파일 | 경로 |
|------|------|
| UseCase 인터페이스 | `port-in/src/main/kotlin/kr/co/uplus/service/port/input/SampleUseCase.kt` |
| 서비스 구현 | `application/src/main/kotlin/kr/co/uplus/service/application/service/SampleService.kt` |
| 컨트롤러 | `adapter/web-adapter/src/main/kotlin/kr/co/uplus/service/adapter/web/input/SampleController.kt` |
| 도메인 엔티티 | `domain/src/main/kotlin/kr/co/uplus/service/domain/Sample.kt` |
| JPA 엔티티 | `adapter/persistence-adapter/src/main/kotlin/kr/co/uplus/service/adapter/persistence/sample/jpa/entity/SampleEntity.kt` |
| 리파지토리 | `adapter/persistence-adapter/src/main/kotlin/kr/co/uplus/service/adapter/persistence/sample/jpa/SampleRepository.kt` |
| 리파지토리 어댑터 | `adapter/persistence-adapter/src/main/kotlin/kr/co/uplus/service/adapter/persistence/sample/SampleRepositoryAdapter.kt` |
| DB 마이그레이션 | `adapter/persistence-adapter/src/main/resources/db/migration/V1__create_table_sample.sql` |

## 새 프로젝트 생성

이 보일러플레이트를 사용하여 새 프로젝트를 생성하려면:

```bash
# 1. 보일러플레이트 복사
cp -r hexagonal-architecture-boilerplate my-new-service
cd my-new-service

# 2. 변수 치환 스크립트 실행
./scripts/replace-variables.sh my-service kr.co.mycompany kr.co.mycompany.myapp myapp MySchema
```

스크립트 파라미터:
- `PROJECT_NAME`: 프로젝트 이름 (예: my-service)
- `PROJECT_GROUP`: 그룹 ID (예: kr.co.mycompany)
- `PROJECT_PACKAGE`: 패키지명 (예: kr.co.mycompany.myapp)
- `PROJECT_DOMAIN`: 도메인명 (예: myapp)
- `DB_SCHEMA`: DB 스키마 (예: MySchema)
