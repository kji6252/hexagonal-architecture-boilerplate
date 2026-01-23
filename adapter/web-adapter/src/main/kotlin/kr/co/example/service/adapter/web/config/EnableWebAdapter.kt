package kr.co.example.service.adapter.web.config

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(WebConfig::class)
annotation class EnableWebAdapter()
