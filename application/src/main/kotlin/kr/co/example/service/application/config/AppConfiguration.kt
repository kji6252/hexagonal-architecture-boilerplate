package kr.co.example.service.application.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@Configuration
@ComponentScan(basePackages = ["kr.co.example.service.application"])
class AppConfiguration
