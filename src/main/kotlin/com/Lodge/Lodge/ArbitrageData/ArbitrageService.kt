package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinanceService
import com.Lodge.Lodge.HitBTC.HitBTCService
import com.Lodge.Lodge.Huobi.HuobiService
import org.springframework.stereotype.Service
import reactor.core.publisher.*
import java.math.BigDecimal
import java.text.NumberFormat

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
            }.collectList()
        }
    }


    fun getArbitrageData(): Flux<ArbitrageModel> {

        var minMax = getLikeSymbol().flatMap { priceModelList: MutableList<priceModel?>? ->
            getMaxMinPriceModel(priceModelList)
        }

        return minMax.filter { priceModels: MutableList<priceModel>? ->
            priceModels?.get(0)?.equals(priceModels.get(1))!! ||
                    !priceModels.isNullOrEmpty()
        }.map { priceModelList: MutableList<priceModel>? ->

         var getDifference = priceModelList?.get(0)?.price?.let {
                priceModelList.get(1).price?.div(it)
            }

         var getAverage = priceModelList?.get(0)?.price?.let {
             priceModelList.get(1).price!!.plus(it).div(BigDecimal(2)) }

         var differencePercentage = getDifference?.let {
             getAverage?.div(it)?.multiply(BigDecimal(100))
         }

            ArbitrageModel(priceModelList?.get(0)?.symbol, differencePercentage ,
                    ExchangeModel(priceModelList?.get(0), priceModelList?.get(1)) )
    }.log()

}


fun getMaxMinPriceModel(priceModels: MutableList<priceModel?>?): Mono<MutableList<priceModel>> {

    val maxPrice: priceModel? = priceModels?.maxBy { priceModel: priceModel? -> priceModel?.price!! }
    val minPrice: priceModel? = priceModels?.minBy { priceModel: priceModel? -> priceModel?.price!! }

    return Flux.concat(maxPrice?.toMono(), minPrice?.toMono()).collectList()
}

}

            
