package com.Lodge.Lodge.Exchanges.Bitfinexnotworking

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BitfinexService(

        private val bitfinexClient: BitfinexClient
) {
    fun getPrice(): Flux<BitfinexPriceResponse> {

        return bitfinexClient.getPrices()
    }
}



