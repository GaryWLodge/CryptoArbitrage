package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinanceService
import com.Lodge.Lodge.HitBTC.HitBTCService
import com.Lodge.Lodge.Huobi.HuobiService
import kotlinx.coroutines.reactive.collect
import org.springframework.stereotype.Service
import reactor.core.Disposable
import reactor.core.publisher.*
import reactor.netty.ReactorNetty
import reactor.util.function.Tuple2
import java.time.Duration
import java.util.logging.Level
import java.util.stream.Collectors

@Service
class ArbitrageService(

        private val binanceService: BinanceService,

        private val hitBTCService: HitBTCService,

        private val huobiService: HuobiService

) {

    fun combineExchanges(): Flux<priceModel?> {

        return Flux.merge(binanceService.getPrice(),
                hitBTCService.getPrice(),
        huobiService.getPrice())
    }


    fun getAllSymbols(): Flux<exchangeSymbols?> {

        return combineExchanges().map { priceModel ->
            priceModel?.symbol?.let { exchangeSymbols(it) }
        }.distinct()
    }


    fun getLikeSymbol(): Flux<MutableList<priceModel?>> {

        Hooks.onOperatorDebug()
        Hooks.enableContextLossTracking()
        return getAllSymbols().flatMap { exchangeSymbols ->
             combineExchanges().filter { priceModel ->
                priceModel?.symbol?.toUpperCase().equals(
                        exchangeSymbols?.symbol?.toUpperCase())
             }.collectSortedList().log()
        }
    }

//    fun getArbitrageData(): Flux<ArbitrageModel?>  {
//
//        getLikeSymbol().flatMap { priceModel: MutableList<priceModel?>? ->
//            priceModel
//        }
//
//
//    }


}

            
