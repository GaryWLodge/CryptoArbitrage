package com.Lodge.Lodge.Exchanges.Digifinex

data class DigifinexPriceResponse(
    val ticker: List<Ticker>?
)

data class Ticker(
        val last: Double?,
        val symbol: String?,
        val exchange: String = "Digifinex"
)