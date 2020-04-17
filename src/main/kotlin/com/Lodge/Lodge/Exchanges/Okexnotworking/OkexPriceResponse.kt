package com.Lodge.Lodge.Exchanges.Okexnotworking

data class OkexPriceResponse(
        val product_id: String?,
        val last: String?,
        val exchange: String = "Okex"
)
