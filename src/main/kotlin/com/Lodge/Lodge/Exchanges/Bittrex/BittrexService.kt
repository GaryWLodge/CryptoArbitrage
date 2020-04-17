package com.Lodge.Lodge.Exchanges.Bittrex

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux

@Service
class BittrexService(

        private val BittrexClient: BittrexClient
) {
    fun getPrice(): Flux<priceModel> {

        return BittrexClient.getPrices().flatMap { BittrexPriceResponse ->
            BittrexPriceResponse.result.toFlux().map { result ->
                val symbol = result.MarketName.filter { c: Char -> c != '-' }
                result.Ask.toBigDecimal().let { priceModel(symbol, it, result.exchange) }
            }
        }

    }


}