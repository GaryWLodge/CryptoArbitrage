package com.Lodge.Lodge.Bitfinex

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class BitfinexRouter {

    @Bean
    fun BitfinexRoute(handler: BitfinexRouterHandle, BitfinexService: BitfinexService) = router {
        GET("/api/Bitfinex/price") { handler.getCurrentBitfinexPricesHandler(it, BitfinexService) }
    }

    @Configuration
    inner class BitfinexRouterHandle(
    ) {
        fun getCurrentBitfinexPricesHandler(request: ServerRequest, BitfinexService: BitfinexService): Mono<ServerResponse> {
            return BitfinexService.getPrice()
                    .collectList()
                    .flatMap { priceResponse ->
                        ServerResponse.ok().bodyValue(priceResponse)
                    }
        }
    }
}
