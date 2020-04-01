package com.Lodge.Lodge.Binance

import com.Lodge.Lodge.ArbitrageData.priceModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BinanceService(

        private val binanceClient: BinanceClient

) {
    fun getPrice(): Flux<priceModel?> {


        return binanceClient.getPrices().map { binancePriceResponse ->
            priceModel(binancePriceResponse.symbol, binancePriceResponse.price, binancePriceResponse.exchange ) }


    }

//    fun BinancePriceResponse.toPriceModel() = binanceClient.getPrices().blockFirst()?.let {
//        priceModel(
//                symbol = it.symbol,
//                price = it.price,
//                exchange = it.exchange)
//    }




}