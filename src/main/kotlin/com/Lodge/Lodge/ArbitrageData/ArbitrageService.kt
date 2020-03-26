package com.Lodge.Lodge.ArbitrageData;

import com.Lodge.Lodge.Binance.BinanceService
import com.Lodge.Lodge.Binance.PriceResponse
import com.Lodge.Lodge.HitBTC.HitBTCService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ArbitrageService(

        private val binanceService: BinanceService,

        private val hitBTCService: HitBTCService

) {

    fun comparePriceBySymbol(): Flux<PriceResponse> {

        return


    }

}
