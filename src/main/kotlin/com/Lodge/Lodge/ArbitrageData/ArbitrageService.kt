package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinanceService
import com.Lodge.Lodge.HitBTC.HitBTCService
import com.Lodge.Lodge.Huobi.HuobiService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ArbitrageService(

        private val binanceService: BinanceService,

        private val hitBTCService: HitBTCService,

        private val huobiService: HuobiService

) {

    fun combineExchanges(): Flux<priceModel> {

        return Flux.merge(hitBTCService.getPrice(),
                binanceService.getPrice())
    }


    fun getAllSymbols(): Flux<exchangeSymbols> {

        return combineExchanges().map { priceModel ->
            exchangeSymbols(priceModel.symbol)
        }.distinct()
    }


    fun getLikeSymbol(): Flux<ExchangeList> {

        return combineExchanges().flatMap { priceModel ->
            filterBySymbols(priceModel).map {
                ExchangeList(priceModel.symbol,
                        Exchange(priceModel.price, priceModel.exchange))
            }
        }
    }

    fun filterBySymbols(priceModel: priceModel): Flux<exchangeSymbols> {

        return getAllSymbols().filter { exchangeSymbols ->
            priceModel.symbol == exchangeSymbols.symbol
        }

    }

}

            
