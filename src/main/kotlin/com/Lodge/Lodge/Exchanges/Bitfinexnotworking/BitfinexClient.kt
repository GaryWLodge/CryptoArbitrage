package com.Lodge.Lodge.Exchanges.Bitfinexnotworking

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
@Service
class BitfinexClient {

    fun getPrices() = WebClient
            .create("https://api-pub.bitfinex.com/v2/tickers?symbols=ALL")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(BitfinexPriceResponse::class.java)
            .delaySequence(Duration.ofSeconds(1))

}