package com.Lodge.Lodge.Huobi

import com.fasterxml.jackson.annotation.JsonAlias

data class HuobiPriceResponse(
        val `data`: List<Data>?
)

data class Data(
        @JsonAlias("Price")
        val ask: Double?,
        val symbol: String?,
        val exchange: String = "Huobi"
)
