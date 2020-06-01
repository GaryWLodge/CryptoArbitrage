package com.Lodge.Lodge.Exchanges.HitBTC

data class HitBTCPriceResponse(
        val last: Double,
        val symbol: String,
        val exchange: String = "HitBTC"

)