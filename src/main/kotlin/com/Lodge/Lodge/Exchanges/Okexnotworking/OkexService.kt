package com.Lodge.Lodge.Exchanges.Okexnotworking

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class OkexService(

        private val okexClient: OkexClient
) {
    fun getPrice(): Flux<priceModel> {

        return okexClient.getPrices().map { okexPriceResponse ->
            val symbol = okexPriceResponse.product_id?.filter { c: Char -> c != '-' }
            priceModel(symbol, okexPriceResponse.last?.toBigDecimal()!!,
                    okexPriceResponse.exchange)
        }

    }


}