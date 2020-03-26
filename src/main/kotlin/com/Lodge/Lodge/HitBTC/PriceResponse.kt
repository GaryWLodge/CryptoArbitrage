package com.Lodge.Lodge.HitBTC

data class PriceResponse (
    val symbol: String,
    val ask: Double,
    val exchange: String = "HitBTC"
)