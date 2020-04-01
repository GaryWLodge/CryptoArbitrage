package com.Lodge.Lodge.Huobi

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class HuobiService(

        private val huobiClient: HuobiClient
) {
    fun getPrice(): Flux<HuobiPriceResponse> {

        return huobiClient.getPrices()

    }


}