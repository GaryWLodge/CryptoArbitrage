package com.Lodge.Lodge.HitBTC

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux

@Service
class HitBTCService(
        private val hitBTCClient: HitBTCClient
) {
    fun getPrice(): Flux<priceModel> {

        return hitBTCClient.getPrices().map { hitBTCPriceResponse ->
            priceModel(hitBTCPriceResponse.symbol
                    , hitBTCPriceResponse.ask
                    , hitBTCPriceResponse.exchange)
        }

    }
}