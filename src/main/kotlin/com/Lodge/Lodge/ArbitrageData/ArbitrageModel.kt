package com.Lodge.Lodge.ArbitrageData

import java.math.BigDecimal

data class ArbitrageModel(

        val symbol: String,
        val differencePercent: BigDecimal,
        val exchangeModel: ExchangeModel

)

data class ExchangeModel(
        val exchangeHigh: priceModel,
        val exchangeLow: priceModel
)