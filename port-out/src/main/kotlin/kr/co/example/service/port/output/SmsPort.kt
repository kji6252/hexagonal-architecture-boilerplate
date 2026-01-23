package kr.co.example.service.port.output

interface SmsPort {
  fun send(senderCtn: String, targetCtn: String, message: String)
}
