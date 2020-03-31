package com.Lodge.Lodge.ArbitrageData

data class ExchangeList(val symbol: String, val exchangePrices: ExchangePrices )

data class ExchangePrices(val exchangeMap: List<HashMap<String, Double>> )
