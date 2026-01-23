package kr.co.example.service.port.output

import kr.co.example.service.type.OsType

interface ProxyPort {
  fun getHexDualNumber(authorization: String, os: OsType): String?
}