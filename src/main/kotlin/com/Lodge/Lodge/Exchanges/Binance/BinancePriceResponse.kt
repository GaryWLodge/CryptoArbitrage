package com.Lodge.Lodge.Exchanges.Binance

data class BinancePriceResponse (
        val symbol: String,
        val price: String,
        val exchange: String = "Binance"

)



