package com.Lodge.Lodge.ArbitrageData

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class ArbitrageRouter {

    @Bean
    fun ArbitrageRoute(handler: ArbitrageRouterHandle, ArbitrageService: ArbitrageService) = router {
        GET("/api/Arbitrage/prices") { handler.getArbitragePricesHandler(it, ArbitrageService) }
    }

    @Configuration
    inner class ArbitrageRouterHandle(
    ) {
        fun getArbitragePricesHandler(request: ServerRequest, ArbitrageService: ArbitrageService): Mono<ServerResponse> {
            return ArbitrageService.getLikeSymbol()
                    .collectList()
                    .flatMap { response -> ServerResponse.ok().syncBody(response) }

        }
    }
}