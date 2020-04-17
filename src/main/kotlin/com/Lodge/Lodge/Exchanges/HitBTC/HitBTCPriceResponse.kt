package com.Lodge.Lodge.Exchanges.HitBTC


data class HitBTCPriceResponse(
        val ask: String,
        val symbol: String,
        val exchange: String = "HitBTC"

)