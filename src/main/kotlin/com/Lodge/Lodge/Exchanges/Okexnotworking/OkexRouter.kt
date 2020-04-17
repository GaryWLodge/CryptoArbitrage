package com.Lodge.Lodge.Exchanges.Okexnotworking

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class OkexRouter {

    @Bean
    fun OkexRoute(handler: OkexRouterHandle, okexService: OkexService) = router {
        GET("/api/okex/price") { handler.getCurrentOkexPricesHandler(it, okexService) }
    }

    @Configuration
    inner class OkexRouterHandle(
    ) {
        fun getCurrentOkexPricesHandler(request: ServerRequest, okexService: OkexService): Mono<ServerResponse> {
            return okexService.getPrice()
                    .collectList()
                    .flatMap { priceResponse ->
                        ServerResponse.ok().bodyValue(priceResponse)
                    }
        }
    }
}
