package com.Lodge.Lodge.Huobi

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class HuobiRouter {

    @Bean
    fun HuobiRoute(handler: HuobiRouterHandle, huobiService: HuobiService) = router {
        GET("/api/Huobi/price") { handler.getCurrentHuobiPricesHandler(it, huobiService) }
    }

    @Configuration
    inner class HuobiRouterHandle(
    ) {
        fun getCurrentHuobiPricesHandler(request: ServerRequest, huobiService: HuobiService): Mono<ServerResponse> {
            return huobiService.getPrice()
                    .collectList()
                    .flatMap { priceResponse ->
                        ServerResponse.ok().bodyValue(priceResponse)
                    }
        }
    }
}
