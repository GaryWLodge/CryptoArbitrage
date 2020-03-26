package com.Lodge.Lodge.Binance

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.math.absoluteValue

@Service
class BinanceService(

        private val binanceClient: BinanceClient
) {
    fun getPrice(): Flux<PriceResponse> {

        return binanceClient.getPrices()

    }


}