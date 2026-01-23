package kr.co.uplus.core

import mu.KLogger
import mu.KotlinLogging


class CoreBase {}

inline fun logger(): KLogger {
  return KotlinLogging.logger {}
}

inline fun tryLog(description: String, block: () -> Unit) {
  try {
    block()
  } catch (e: Exception) {
    logger().error("Error while $description", e)
  }
}

