package kr.co.example.service.domain

enum class FailDbCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  INTERNAL_DATABASE_ERROR(500, "DB_7001", "Something went wrong.[DB_7001]"),
  NOT_FOUND(404, "DB_7002", "Something went wrong.[DB_7002]"),
  INVALID_FORMAT(400, "DB_7003", "Something went wrong.[DB_7003]"),
  VALIDATION_ERROR(400, "DB_7004", "Something went wrong.[DB_7004]"),
  UNIQUE_CONSTRAINT_ERROR(400, "DB_7005", "Something went wrong.[DB_7005]"),
  FOREIGN_KEY_CONSTRAINT_ERROR(400, "DB_7006", "Something went wrong.[DB_7006]"),
  TIMEOUT_ERROR(503, "DB_7007", "Something went wrong.[DB_7007]"),
  CONNECTION_ERROR(500, "DB_7008", "Something went wrong.[DB_7008]"),
  HOST_NOT_FOUND_ERROR(500, "DB_7009", "Something went wrong.[DB_7009]"),
  HOST_NOT_REACHABLE_ERROR(500, "DB_7010", "Something went wrong.[DB_7010]"),
  INVALID_CONNECTION_ERROR(500, "DB_7011", "Something went wrong.[DB_7011]"),
  CONNECTION_REFUSED_ERROR(500, "DB_7012", "Something went wrong.[DB_7012]"),
  ACCESS_DENIED_ERROR(403, "DB_7013", "Something went wrong.[DB_7013]"),
  ROW_IS_REFERENCED(400, "DB_7014", "Something went wrong.[DB_7014]"),
  TABLE_NOT_FOUND(404, "DB_7015", "Something went wrong.[DB_7015]"),
  QUERY_ERROR(400, "DB_7016", "Something went wrong.[DB_7016]"),
  CONSTRAINT_VIOLATION(409, "DB_7017", "Something went wrong.[DB_7017]"),
  UNKNOWN_ERROR(500, "DB_7100", "Something went wrong.[DB_7100]"),
}
