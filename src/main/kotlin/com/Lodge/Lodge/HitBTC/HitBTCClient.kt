package com.Lodge.Lodge.HitBTC;

import com.Lodge.Lodge.Binance.PriceResponse
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Service
class HitBTCClient {

    fun getPrices() = WebClient
            .create("https://api.binance.com/api/v3/ticker/price")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(PriceResponse::class.java)

}
