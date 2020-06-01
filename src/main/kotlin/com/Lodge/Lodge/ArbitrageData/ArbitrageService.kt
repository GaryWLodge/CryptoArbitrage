package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Exchanges.Binance.BinanceService
import com.Lodge.Lodge.Exchanges.Bittrex.BittrexService
import com.Lodge.Lodge.Exchanges.Digifinex.DigifinexService
import com.Lodge.Lodge.Exchanges.HitBTC.HitBTCService
import com.Lodge.Lodge.Exchanges.Huobi.HuobiService
import org.springframework.stereotype.Service
import reactor.core.publisher.*
import java.math.BigDecimal

@Service
class ArbitrageService(

        private val binanceService: BinanceService,
        private val hitBTCService: HitBTCService,
        private val huobiService: HuobiService,
        private val bittrexService: BittrexService,
        private val digifinexService: DigifinexService

) {

    fun combineExchanges(): Flux<priceModel?> {

        return Flux.merge(binanceService.getPrice(),
                hitBTCService.getPrice(),
                huobiService.getPrice(),
                bittrexService.getPrice(),
                digifinexService.getPrice())
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
                priceModel?.symbol?.equals(
                        exchangeSymbols?.symbol, true)!!
            }.collectList()
        }
    }


    fun getMaxMinPriceModel(priceModels: MutableList<priceModel?>?): Mono<MutableList<priceModel>> {

        val maxPrice: priceModel? = priceModels?.maxBy { priceModel: priceModel? -> priceModel?.price!! }
        val minPrice: priceModel? = priceModels?.minBy { priceModel: priceModel? -> priceModel?.price!! }

        return Flux.concat(maxPrice?.toMono(), minPrice?.toMono()).collectList()
    }


    fun getArbitrageData(): Flux<ArbitrageModel?> {

        val minMax = getLikeSymbol().flatMap { priceModelList: MutableList<priceModel?>? ->
            getMaxMinPriceModel(priceModelList)
        }

        return minMax.filter { priceModels: MutableList<priceModel>? ->
            !priceModels?.get(0)?.exchange?.contentEquals(priceModels.get(1).exchange!!)!!
        }.map { priceModelList: MutableList<priceModel>? ->

            val getDifference = priceModelList?.get(0)?.price?.let {
                priceModelList.get(1).price?.subtract(it)
            }

            val getAverage = priceModelList?.get(0)?.price?.let {
                priceModelList.get(1).price!!.plus(it).div(BigDecimal(2))
            }

            val differencePercentage = getDifference?.div(getAverage!!)?.times(BigDecimal(100))

            differencePercentage?.toPlainString()?.let {
                ArbitrageModel(priceModelList.get(0).symbol, it,
                        ExchangeModel(priceModelList.get(0), priceModelList.get(1)))
            }
        }.log()

    }

}

            
