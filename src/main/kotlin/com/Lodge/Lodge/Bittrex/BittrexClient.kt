package com.Lodge.Lodge.Bittrex

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Configuration
@Service
class BittrexClient {

    fun getPrices() = WebClient
            .create("https://api.bittrex.com/api/v1.1/public/getmarketsummaries")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(BittrexPriceResponse::class.java)
            .delaySequence(Duration.ofSeconds(2))

}