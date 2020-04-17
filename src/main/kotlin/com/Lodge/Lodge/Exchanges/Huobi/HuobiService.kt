package com.Lodge.Lodge.Exchanges.Huobi

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux

@Service
class HuobiService(

        private val huobiClient: HuobiClient
) {
    fun getPrice(): Flux<priceModel> {

        return huobiClient.getPrices().flatMap { huobiPriceResponse ->
            huobiPriceResponse.data?.toFlux()?.map { data ->
                priceModel(data.symbol, data.bid.toBigDecimal(), data.exchange)
            }
        }

    }


}