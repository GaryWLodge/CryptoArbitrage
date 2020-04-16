package com.Lodge.Lodge.ArbitrageData

data class ArbitrageModel(

        val symbol: String?,
        val differencePercent: String,
        val exchangeModel: ExchangeModel?

)

data class ExchangeModel(
        val exchangeHigh: priceModel?,
        val exchangeLow: priceModel?
)