package com.Lodge.Lodge.Exchanges.HitBTC

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import java.math.MathContext
import java.math.RoundingMode

@Service
class HitBTCService(
        private val hitBTCClient: HitBTCClient
) {
    fun getPrice(): Flux<priceModel> {

        return hitBTCClient.getPrices().map { hitBTCPriceResponse ->
            priceModel(hitBTCPriceResponse.symbol
                    , hitBTCPriceResponse.last.toBigDecimal()
                    , hitBTCPriceResponse.exchange)
        }

    }
}