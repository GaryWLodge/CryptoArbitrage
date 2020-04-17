package com.Lodge.Lodge.Exchanges.Digifinex

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class DigifinexRouter {

    @Bean
    fun DigifinexRoute(handler: DigifinexRouterHandle, digifinexService: DigifinexService) = router {
        GET("/api/Digifinex/price") { handler.getCurrentDigifinexPricesHandler(it, digifinexService) }
    }

    @Configuration
    inner class DigifinexRouterHandle(
    ) {
        fun getCurrentDigifinexPricesHandler(request: ServerRequest, digifinexService: DigifinexService): Mono<ServerResponse> {
            return digifinexService.getPrice()
                    .collectList()
                    .flatMap { priceResponse ->
                        ServerResponse.ok().bodyValue(priceResponse)
                    }
        }
    }
}
