package com.Lodge.Lodge.Exchanges.Binance

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
@Service
class BinanceClient {

    fun getPrices() = WebClient
            .create("https://api.binance.com/api/v3/ticker/price")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(BinancePriceResponse::class.java)
            .delaySequence(Duration.ofSeconds(2))

}