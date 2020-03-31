package com.Lodge.Lodge.Huobi

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@Service
class HuobiClient {

    fun getPrices() = WebClient
            .create("https://api.huobi.pro/market/tickers")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(HuobiPriceResponse::class.java)

}