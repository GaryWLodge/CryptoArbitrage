package com.Lodge.Lodge.Bittrex

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class BittrexRouter {

    @Bean
    fun BittrexRoute(handler: BittrexRouterHandle, BittrexService: BittrexService) = router {
        GET("/api/Bittrex/price") { handler.getCurrentBittrexPricesHandler(it, BittrexService) }
    }

    @Configuration
    inner class BittrexRouterHandle(
    ) {
        fun getCurrentBittrexPricesHandler(request: ServerRequest, BittrexService: BittrexService): Mono<ServerResponse> {
            return BittrexService.getPrice()
                    .collectList()
                    .flatMap { priceResponse ->
                        ServerResponse.ok().bodyValue(priceResponse)
                    }
        }
    }
}
