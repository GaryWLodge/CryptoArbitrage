package com.Lodge.Lodge.Okex

data class OkexPriceResponse(
        val product_id: String?,
        val last: String?,
        val exchange: String = "Okex"
)
