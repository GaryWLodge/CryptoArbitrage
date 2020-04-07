package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinanceService
import com.Lodge.Lodge.HitBTC.HitBTCService
import com.Lodge.Lodge.Huobi.HuobiService
import kotlinx.coroutines.reactive.collect
import org.springframework.stereotype.Service
import reactor.core.Disposable
import reactor.core.publisher.*
import reactor.util.function.Tuple2
import java.util.logging.Level
import java.util.stream.Collectors

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


    fun getLikeSymbol(): Flux<List<priceModel>> {

        Hooks.onOperatorDebug()
        return getAllSymbols().flatMap { exchangeSymbols ->
            combineExchanges().filter { priceModel ->
                priceModel.symbol.contentEquals(exchangeSymbols.symbol)
            }.distinct()
                    .collect(Collectors.toList()).log()

        }.log()

    }

    fun filterBySymbols(priceModel: priceModel): Flux<exchangeSymbols> {

        return getAllSymbols().filter { exchangeSymbols ->
            priceModel.symbol == exchangeSymbols.symbol
        }

    }

}

            
