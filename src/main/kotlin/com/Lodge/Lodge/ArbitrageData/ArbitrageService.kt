package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinanceService
import com.Lodge.Lodge.HitBTC.HitBTCService
import com.Lodge.Lodge.Huobi.HuobiService
import org.springframework.stereotype.Service
import reactor.core.publisher.*
import java.math.BigDecimal
import java.time.Duration

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
            }.collectList().log()
        }
    }


    fun getArbitrageData(): Flux<MutableList<priceModel>> {

        var minMax = getLikeSymbol().flatMap { priceModelList: MutableList<priceModel?>? ->
            getMaxMinPriceModel(priceModelList)
        }

        var filterOutDoubles = minMax.filter { priceModels: MutableList<priceModel>? ->
            !priceModels?.get(0)?.exchange.equals(priceModels?.get(0)?.exchange) ||
                    !priceModels.isNullOrEmpty()}

        var getDeferencePercent = filterOutDoubles.map { priceModelList: MutableList<priceModel>? ->
            priceModelList?.get(1)?.price?.let { priceModelList.get(0).price?.div(it)?.times(BigDecimal(100)) }
        }

        return

    }


    fun getMaxMinPriceModel(priceModels: MutableList<priceModel?>?): Mono<MutableList<priceModel>> {

        val maxPrice: priceModel? = priceModels?.maxBy { priceModel: priceModel? -> priceModel?.price!! }
        val minPrice: priceModel? = priceModels?.minBy { priceModel: priceModel? -> priceModel?.price!! }

        return Flux.concat(maxPrice?.toMono(), minPrice?.toMono()).collectList()
    }

}

            
