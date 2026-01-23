package kr.co.example.service.adapter.persistence

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(DbConfig::class)
annotation class EnablePersistenceAdapter
