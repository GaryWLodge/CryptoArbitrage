package com.Lodge.Lodge.ArbitrageData

import reactor.core.publisher.Flux

data class ExchangeList(
        val symbols: String?,
        val price: Double?,
        val exchange: String?
)
