package com.Lodge.Lodge.Binance

data class BinancePriceResponse (
        val symbol: String,
        val price: String,
        val exchange: String = "Binance"

)



