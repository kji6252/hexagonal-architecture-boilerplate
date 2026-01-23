package kr.co.example.service.adapter.web.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan(basePackages = ["kr.co.example.service.adapter.web"], basePackageClasses = [WebConfig::class])
class WebConfig : WebMvcConfigurer {

  override fun addCorsMappings(registry: CorsRegistry) {
    registry
      .addMapping("/**")
      .allowedMethods("*")
      .allowedOriginPatterns("*")
      .exposedHeaders(
        "Content-Type",
        "Authorization",
        "X-AUTH-TOKEN",
        "Access-Control-Allow-Origin",
        "Access-Control-Allow-Credentials"
      )
      .allowCredentials(true)
      .maxAge(3000)
  }
}
