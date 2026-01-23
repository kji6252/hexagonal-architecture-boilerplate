package kr.co.example.service.type

enum class OsType(val value: Int) {
    ALL(0),
    iOS(1),
    AOS(2);

    companion object {
        fun of(osType: Int): OsType {
            return when (osType) {
                0 -> ALL
                1 -> iOS
                2 -> AOS
                else -> throw IllegalArgumentException("Invalid osType: $osType")
            }
        }
    }
}
