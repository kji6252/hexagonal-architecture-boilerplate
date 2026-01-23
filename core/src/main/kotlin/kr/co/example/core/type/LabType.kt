package kr.co.uplus.core.type

enum class LabType(val value: Int, val default: Boolean) {

    CALL_SEARCH(1, false),
    IN_CALL_AI(2, false),
    PUF_KMS(3, false),
    UNKNOWN(-1, false)
    ;

    companion object {
        val BY_VALUE = LabType.entries.associateBy { it.value }
    }
}
