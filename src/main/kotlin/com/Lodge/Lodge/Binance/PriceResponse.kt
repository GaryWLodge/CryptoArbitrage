package com.Lodge.Lodge.Binance

data class PriceResponse (
        val symbol: String,
        val price: Double,
        val exchange: String = "Binance"

)



