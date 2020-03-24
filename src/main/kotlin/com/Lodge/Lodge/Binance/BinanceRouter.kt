package com.Lodge.Lodge.Binance

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class BinanceRouter {

    @Bean
    fun BinanceRoute(handler: BinanceRouterHandle, binanceService: BinanceService) = router {
        GET("/api/binance/price") { handler.getCurrentBinancePricesHandler(it, binanceService) }
    }

    @Configuration
    inner class BinanceRouterHandle(
    ) {
        fun getCurrentBinancePricesHandler(request: ServerRequest, binanceService: BinanceService): Mono<ServerResponse> {
            return binanceService.getPrice()
                    .collectList()
                    .flatMap { priceResponse ->
                        ServerResponse.ok().syncBody(priceResponse)
                    }
        }
    }
}
