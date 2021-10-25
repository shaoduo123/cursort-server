package com.shao.cursort.service;

import com.shao.cursort.pojo.Trade;
import com.shao.cursort.result.Result;

import java.math.BigDecimal;

public interface TradeService {

    public Result recharge(Trade trade) ;

    public Result consume(Trade trade) ;
}
