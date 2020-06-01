package com.Lodge.Lodge.ArbitrageData

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
@ComponentScan("com.Lodge.Lodge.ArbitrageData")
class ArbitrageConfig : WebFluxConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
    }
}