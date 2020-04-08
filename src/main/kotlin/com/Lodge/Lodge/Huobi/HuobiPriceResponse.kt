package com.Lodge.Lodge.Huobi

import reactor.core.publisher.Flux

data class HuobiPriceResponse(
        val `data`: List<Data>?
)

data class Data(
        val ask: Double,
        val symbol: String,
        val exchange: String = "Huobi"
)
