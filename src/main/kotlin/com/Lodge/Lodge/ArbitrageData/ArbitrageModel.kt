package com.Lodge.Lodge.ArbitrageData

data class ArbitrageModel(

        val symbol: String,
        val differencePercent: Double,
        val exchangeModel: ExchangeModel

)

data class ExchangeModel(
        val highLow: String,
        val price: Double,
        val exchange: String
)