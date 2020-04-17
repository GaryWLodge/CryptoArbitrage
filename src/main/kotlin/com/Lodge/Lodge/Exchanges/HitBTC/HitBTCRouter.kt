package com.Lodge.Lodge.Exchanges.HitBTC

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class HitBTCRouter {

    @Bean
    fun HitBTCRoute(handler: HitBTCRouterHandle, HitBTCService: HitBTCService) = router {
        GET("/api/HitBTC/price") { handler.getCurrentHitBTCPricesHandler(it, HitBTCService) }
    }

    @Configuration
    inner class HitBTCRouterHandle(
    ) {
        fun getCurrentHitBTCPricesHandler(request: ServerRequest, HitBTCService: HitBTCService): Mono<ServerResponse> {
            return HitBTCService.getPrice()
                    .collectList()
                    .flatMap { priceResponse ->
                        ServerResponse.ok().bodyValue(priceResponse)
                    }
        }
    }
}