package com.Lodge.Lodge.Binance

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BinanceService(

        private val binanceClient: BinanceClient
) {
    fun getPrice(): Flux<BinancePriceResponse> {

        return binanceClient.getPrices()

    }


}