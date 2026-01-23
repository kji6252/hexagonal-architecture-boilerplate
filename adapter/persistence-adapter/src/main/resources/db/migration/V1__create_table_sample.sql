-- V1__create_table_sample.sql
-- 샘플 테이블 생성을 위한 Flyway 마이그레이션 스크립트

-- 스키마 생성 (없는 경우)
CREATE SCHEMA IF NOT EXISTS SampleSchema;

-- 샘플 테이블 생성
CREATE TABLE IF NOT EXISTS SampleSchema.sample (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '샘플 ID',
    message VARCHAR(255) NOT NULL COMMENT '메시지',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    created_by VARCHAR(100) COMMENT '생성자',
    updated_by VARCHAR(100) COMMENT '수정자'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='샘플 테이블';

-- 초기 데이터 삽입 (선택사항)
INSERT INTO SampleSchema.sample (id, message, created_at, updated_at)
VALUES
    (1, 'Hello, World!', NOW(), NOW()),
    (2, 'Hexagonal Architecture Boilerplate', NOW(), NOW()),
    (3, 'Sample Data', NOW(), NOW())
ON DUPLICATE KEY UPDATE message = VALUES(message);
