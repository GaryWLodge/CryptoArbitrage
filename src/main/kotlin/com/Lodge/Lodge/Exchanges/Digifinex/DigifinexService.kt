package com.Lodge.Lodge.Exchanges.Digifinex

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux

@Service
class DigifinexService(

        private val digifinexClient: DigifinexClient
) {
    fun getPrice(): Flux<priceModel> {

        return digifinexClient.getPrices().flatMap { digifinexPriceResponse ->
            digifinexPriceResponse.ticker?.toFlux()?.map { ticker ->
                val symbol = ticker.symbol?.filter { c: Char -> c != '_' }
                priceModel(symbol, ticker.last?.toBigDecimal()!!, ticker.exchange)
            }
        }
    }
}