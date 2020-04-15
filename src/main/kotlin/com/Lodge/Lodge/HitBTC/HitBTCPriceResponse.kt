package com.Lodge.Lodge.HitBTC


data class HitBTCPriceResponse(
        val ask: String,
        val symbol: String,
        val exchange: String = "HitBTC"

)