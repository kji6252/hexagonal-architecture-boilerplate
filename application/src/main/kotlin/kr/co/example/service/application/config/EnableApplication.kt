package kr.co.example.service.application.config

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(AppConfiguration::class)
annotation class EnableApplication
