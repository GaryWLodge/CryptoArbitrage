package com.Lodge.Lodge.Binance

data class BinancePriceResponse (
        val symbol: String,
        val price: Double,
        val exchange: String = "Binance"

)



