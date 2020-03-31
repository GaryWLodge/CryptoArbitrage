package com.Lodge.Lodge.HitBTC

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class HitBTCService (
    private val hitBTCClient: HitBTCClient
) {
    fun getPrice(): Flux<HitBTCPriceResponse> {
        return hitBTCClient.getPrices()
    }
}