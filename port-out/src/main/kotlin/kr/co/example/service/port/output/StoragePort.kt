package kr.co.example.service.port.output

import kr.co.example.service.domain.ObjectMetadata
import kr.co.example.service.domain.PresignedUrl
import kr.co.example.service.domain.S3BucketType
import java.io.InputStream
import java.time.Duration

/**
 * 스토리지 포트
 * S3와 같은 오브젝트 스토리지 작업을 위한 포트 인터페이스
 */
interface StoragePort {

  /**
   * 프리사인드 GET URL 생성
   *
   * @param bucketType 버킷 타입
   * @param key 오브젝트 키
   * @param expiration URL 만료 시간 (기본값: 15분)
   * @return 프리사인드 URL 정보
   */
  fun generateDownloadPresignedUrl(
    bucketType: S3BucketType,
    key: String,
    expiration: Duration = Duration.ofMinutes(15)
  ): PresignedUrl

  /**
   * 프리사인드 PUT URL 생성
   *
   * @param bucketType 버킷 타입
   * @param key 오브젝트 키
   * @param contentType 콘텐츠 타입 (선택사항, null이면 제한 없음)
   * @param contentLength 콘텐츠 길이 (선택사항, null이면 제한 없음)
   * @param metadata 사용자 정의 메타데이터 (선택사항, x-amz-meta- 접두사로 저장됨)
   * @param expiration URL 만료 시간 (기본값: 15분)
   * @return 프리사인드 URL 정보
   */
  fun generateUploadPresignedUrl(
    bucketType: S3BucketType,
    key: String,
    contentType: String? = null,
    contentLength: Long? = null,
    metadata: Map<String, String> = emptyMap(),
    expiration: Duration = Duration.ofMinutes(15)
  ): PresignedUrl

  /**
   * 오브젝트 존재 여부 확인
   * 메타데이터를 조회하여 오브젝트가 존재하는지 확인합니다.
   *
   * @param bucketType 버킷 타입
   * @param key 오브젝트 키
   * @return 오브젝트 메타데이터 (존재하지 않으면 null)
   */
  fun getObjectMetadata(
    bucketType: S3BucketType,
    key: String
  ): ObjectMetadata?

  /**
   * 오브젝트 생성/업로드
   *
   * @param bucketType 버킷 타입
   * @param key 오브젝트 키
   * @param inputStream 업로드할 데이터 스트림
   * @param contentLength 콘텐츠 길이
   * @param contentType 콘텐츠 타입 (선택사항)
   */
  fun putObject(
    bucketType: S3BucketType,
    key: String,
    inputStream: InputStream,
    contentLength: Long,
    contentType: String? = null
  )

  /**
   * 오브젝트 이름 변경 (복사 후 원본 삭제)
   *
   * @param bucketType 버킷 타입
   * @param sourceKey 원본 오브젝트 키
   * @param destinationKey 대상 오브젝트 키
   */
  fun renameObject(
    bucketType: S3BucketType,
    sourceKey: String,
    destinationKey: String
  )

  /**
   * 오브젝트 삭제
   *
   * @param bucketType 버킷 타입
   * @param key 오브젝트 키
   */
  fun deleteObject(
    bucketType: S3BucketType,
    key: String
  )
}
