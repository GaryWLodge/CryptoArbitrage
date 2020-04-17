package com.Lodge.Lodge.Exchanges.Digifinex

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
@Service
class DigifinexClient {

    fun getPrices() = WebClient
            .create("https://openapi.digifinex.com/v3/ticker")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(DigifinexPriceResponse::class.java)
            .delaySequence(Duration.ofSeconds(2))

}