package com.Lodge.Lodge.ArbitrageData

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.collect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.http.codec.ServerSentEventHttpMessageWriter
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.net.ServerSocket

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
            return ArbitrageService.getArbitrageData()
                    .flatMap { response -> ok().sse().bodyValue(response!!) }.toMono()


        }
    }
}