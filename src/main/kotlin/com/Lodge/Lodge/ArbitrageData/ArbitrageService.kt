package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinanceService
import com.Lodge.Lodge.HitBTC.HitBTCService
import com.Lodge.Lodge.Huobi.HuobiService
import org.springframework.stereotype.Service
import reactor.core.publisher.*
import java.math.BigDecimal

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


    fun getArbitrageData(): Mono<MutableList<Unit?>> {
        return getLikeSymbol().flatMap { priceModels: MutableList<priceModel?> ->
            getMaxMinPriceModel(priceModels).takeIf {
                priceModels[0]?.equals(priceModels[1])!!
            }?.map { val priceDiff: BigDecimal? = priceModels[1]?.price?.let { it1 -> priceModels[0]?.price?.rem(it1) }
                priceModels[0]?.symbol?.let { it1 ->
                    priceModels[0]?.let { it2 -> priceModels[1]?.let { it3 -> ExchangeModel(it2, it3) } }?.let { it3 ->
                        if (priceDiff != null) {
                            ArbitrageModel(it1, priceDiff, it3)
                        }
                    }
                }
            }

        }.collectList().log()
    }


    fun getMaxMinPriceModel(priceModels: MutableList<priceModel?>?): Mono<MutableList<priceModel>> {

        val maxPrice: priceModel? = priceModels?.maxBy { priceModel: priceModel? -> priceModel?.price!! }
        val minPrice: priceModel? = priceModels?.minBy { priceModel: priceModel? -> priceModel?.price!! }

        return Flux.concat(maxPrice?.toMono(), minPrice?.toMono()).collectList()
    }

}

            
