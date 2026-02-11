# 헥사고날 아키텍처 보일러플레이트

Spring Boot 기반 헥사고날 아키텍처(Ports & Adapters) 보일러플레이트 프로젝트입니다.

## 개요

이 프로젝트는 헥사고날 아키텍처 패턴을 따르는 Spring Boot 애플리케이션의 템플릿입니다. 비즈니스 로직은 제거되었으며, 아키텍처 패턴과 프레임워크 구성만 포함되어 있습니다.

**특징:**
- **헥사고날 아키텍처**: Ports & Adapters 패턴 구현
- **멀티모듈 프로젝트**: Gradle 멀티모듈 구조
- **Java 21 + Kotlin 1.9**: 최신 Java/Kotlin 버전 지원
- **Spring Boot 3.4**: 최신 Spring Boot 프레임워크
- **TestContainers**: 통합 테스트 지원
- **Hello World 예제**: 빠른 시작을 위한 샘플 코드

## 목차

- [시작하기](#시작하기)
- [새 프로젝트 생성](#새-프로젝트-생성)
- [빌드 및 실행](#빌드-및-실행)
- [아키텍처](#아키텍처)
- [모듈 구성](#모듈-구성)
- [API 테스트](#api-테스트)

---

## 시작하기

### 사전 요구사항

- **JDK 21** 이상
- **Docker** (TestContainers용)

### 빌드

```bash
./gradlew clean build
```

### 로컬 실행

```bash
./gradlew :boilerplate-service-api:bootTestRun
```

그 후 `boilerplate-service-api/src/test/kotlin/kr/co/example/service/TestBoilerplateServiceApiApplication.kt`를 실행합니다.

> **참고**: Docker Desktop을 사용 중이고 Testcontainers 연결 오류가 발생할 경우, [이 StackOverflow 답변](https://stackoverflow.com/a/79860621)을 참조하여 해결하세요.

---

## 새 프로젝트 생성

이 보일러플레이트를 사용하여 새 프로젝트를 생성하는 방법:

### 방법 1: 스크립트 사용 (권장)

```bash
# 1. 보일러플레이트 복사
cp -r hexagonal-architecture-boilerplate my-new-service
cd my-new-service

# 2. 변수 치환 스크립트 실행
./scripts/replace-variables.sh my-service kr.co.mycompany kr.co.mycompany.myapp myapp MySchema
```

### 방법 2: 수동 설정

```bash
# 1. 보일러플레이트 복사
cp -r hexagonal-architecture-boilerplate my-new-service
cd my-new-service

# 2. settings.gradle.kts에서 프로젝트 이름 변경
# rootProject.name = "my-service"

# 3. 패키지 이름 검색/교체 (IDE 기능 활용)
# kr.co.example.service -> kr.co.mycompany.myapp

# 4. DB 스키마 변경
# SampleSchema -> MySchema
```

### 스크립트 파라미터

```bash
./scripts/replace-variables.sh [PROJECT_NAME] [PROJECT_GROUP] [PROJECT_PACKAGE] [PROJECT_DOMAIN] [DB_SCHEMA]
```

| 파라미터 | 설명 | 기본값 |
|---------|------|--------|
| PROJECT_NAME | 프로젝트 이름 | boilerplate-service |
| PROJECT_GROUP | 그룹 ID | kr.co.example |
| PROJECT_PACKAGE | 패키지명 | kr.co.example.service |
| PROJECT_DOMAIN | 도메인명 | sample |
| DB_SCHEMA | DB 스키마 | SampleSchema |

---

## 빌드 및 실행

### 빌드

```bash
./gradlew clean build
```

### 단위 테스트

```bash
./gradlew test
```

### 통합 테스트

```bash
./gradlew integrationTest
```

### 애플리케이션 실행

```bash
./gradlew :boilerplate-service-api:bootTestRun
```

---

## 아키텍처

헥사고날 아키텍처(Ports & Adapters) 패턴을 따릅니다.

```
┌─────────────────────────────────────────────────────────────┐
│                    boilerplate-service-api                  │
│                   (Spring Boot Application)                 │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       Web Adapter                           │
│                    (REST Controllers)                       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       Port-In                               │
│                     (UseCase Interfaces)                    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     Application                             │
│                  (UseCase Implementations)                  │
└─────────────────────────────────────────────────────────────┘
                              │
                 ┌────────────┴────────────┐
                 ▼                         ▼
         ┌─────────────┐           ┌─────────────┐
         │   Port-Out  │           │    Domain   │
         │  (Repository │           │  (Business  │
         │  Interfaces)│           │   Logic)    │
         └─────────────┘           └─────────────┘
                 │
                 ▼
         ┌─────────────────────────────────────┐
         │          Adapters                   │
         │  ┌──────────────────────────────┐  │
         │  │ Persistence Adapter (JPA)    │  │
         │  │ Cache Adapter (Redis)       │  │
         │  │ Client Adapter (External)   │  │
         │  │ Message Adapter (Kafka)     │  │
         │  │ Storage Adapter (S3)        │  │
         │  └──────────────────────────────┘  │
         └─────────────────────────────────────┘
```

### 의존성 방향

- **Web Adapter** → **Port-In** (유즈케이스 호출)
- **Application** → **Port-In** (구현), **Port-Out** (의존)
- **Adapters** → **Port-Out** (구현)
- **Application** → **Domain** (비즈니스 로직)

---

## 모듈 구성

### Endpoint Module

| 모듈 | 설명 |
|------|------|
| [boilerplate-service-api](boilerplate-service-api) | Spring Boot 애플리케이션 진입점 |
| [boilerplate-service-batch](boilerplate-service-batch) | 배치 처리 모듈 |

### Core Modules

| 모듈 | 설명 |
|------|------|
| [adapter](adapter) | Port-In/Port-Out 구현체들 |
| [port-in](port-in) | 유즈케이스 인터페이스 |
| [port-out](port-out) | 레파지토리/외부 서비스 인터페이스 |
| [application](application) | 유즈케이스 구현 |
| [domain](domain) | 순수 비즈니스 로직 |
| [core](core) | 공통 유틸리티 |

### Adapters

| Adapter | 설명 |
|---------|------|
| web-adapter | REST 컨트롤러 |
| persistence-adapter | JPA 레파지토리 |
| client-adapter | 외부 API 클라이언트 |
| cache-adapter | Redis 캐싱 |
| memory-adapter | 인메모리 저장소 |
| message-adapter | SMS/메시징 |
| stream-adapter | Kafka 스트리밍 |
| storage-adapter | S3 스토리지 |

---

## API 테스트

### Hello World 엔드포인트

```bash
curl "http://localhost:8080/api/v1/sample/hello?name=World"
```

**응답:**

```json
{
  "code": "SUCCESS_0001",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": "Hello, World!"
}
```

### 샘플 데이터 조회

```bash
curl "http://localhost:8080/api/v1/sample/1"
```

**응답:**

```json
{
  "code": "SUCCESS_0001",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {
    "id": 1,
    "message": "Hello, World!",
    "createdAt": "2025-01-23T00:00:00"
  }
}
```

---

## 개발 도구

### IDEA Plugin

**Testcontainers Port Updater** 플러그인 설치를 권장합니다:

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

---

## 라이선스

이 프로젝트는 보일러플레이트로 제공되며, 자유롭게 사용 및 수정 가능합니다.
