package com.Lodge.Lodge.HitBTC

import com.Lodge.Lodge.Binance.PriceResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class HitBTCService (
    private val hitBTCClient: HitBTCClient
) {
    fun getPrice(): Flux<PriceResponse> {
        return hitBTCClient.getPrices()
    }
}