package com.Lodge.Lodge.Bittrex

data class BittrexPriceResponse(
    val result: List<Result>
)

data class Result(
        val Ask: Double,
        val MarketName: String,
        val exchange: String = "Bittrex"
)