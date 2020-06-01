package com.Lodge.Lodge.Exchanges.HitBTC

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@CrossOrigin
@Configuration
class HitBTCRouter {

    @Bean
    fun HitBTCRoute(handler: HitBTCRouterHandle, HitBTCService: HitBTCService) = router {
        contentType(MediaType.APPLICATION_JSON)
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