package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinancePriceResponse
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

    fun combineExchanges(): Flux<Any> {


        return Flux.merge(hitBTCService.getPrice(), binanceService.getPrice(), huobiService.getPrice())
    }


//    fun getLikeSymbol(): Flux<ExchangeList> {
//
//        return Flux.create()
//
//
//    }

}
