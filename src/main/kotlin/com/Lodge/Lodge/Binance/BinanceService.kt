package com.Lodge.Lodge.Binance

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BinanceService(

        private val priceClient: PriceClient
) {
    fun getPrice(): Flux<PriceResponse> {

        return priceClient.getPrices()

    }


}