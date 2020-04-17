package com.Lodge.Lodge.Exchanges.Okexnotworking

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
@Service
class OkexClient {

    fun getPrices() = WebClient
            .create("https://www.okex.com/api/spot/v3/instruments/ticker")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(OkexPriceResponse::class.java)
            .delayElements(Duration.ofSeconds(1))

}