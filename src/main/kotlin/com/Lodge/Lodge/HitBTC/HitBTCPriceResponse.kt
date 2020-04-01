package com.Lodge.Lodge.HitBTC


data class HitBTCPriceResponse(
        val ask: Double,
        val symbol: String,
        val exchange: String = "HitBTC"

)