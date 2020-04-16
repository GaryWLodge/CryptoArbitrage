package com.Lodge.Lodge.Huobi

import reactor.core.publisher.Flux

data class HuobiPriceResponse(
        val `data`: List<Data>?
)

data class Data(
        val bid: String,
        val symbol: String,
        val exchange: String = "Huobi"
)
