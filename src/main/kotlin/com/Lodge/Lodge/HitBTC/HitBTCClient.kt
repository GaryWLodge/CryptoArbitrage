package com.Lodge.Lodge.HitBTC;

import com.Lodge.Lodge.Binance.BinancePriceResponse
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration

@Configuration
@Service
class HitBTCClient {

    fun getPrices() = WebClient
            .create("https://api.hitbtc.com/api/2/public/ticker")
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(HitBTCPriceResponse::class.java)
            .delaySequence(Duration.ofSeconds(1))

}
