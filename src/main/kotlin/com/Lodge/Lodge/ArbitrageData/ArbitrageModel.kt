package com.Lodge.Lodge.ArbitrageData

data class ArbitrageModel(

        val symbol: String?,
        val differencePercent: String,
        val exchangeModel: ExchangeModel?

)

data class ExchangeModel(
        val exchangeSell: priceModel?,
        val exchangeBuy: priceModel?
)