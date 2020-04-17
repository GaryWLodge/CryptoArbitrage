package com.Lodge.Lodge.Bitfinex

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux

@Service
class BitfinexService(

        private val bitfinexClient: BitfinexClient
) {
    fun getPrice(): Flux<BitfinexPriceResponse> {

        return bitfinexClient.getPrices()
    }
}



