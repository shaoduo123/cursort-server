package com.shao.cursort.service.impl;

import com.shao.cursort.mapper.TradeMapper;
import com.shao.cursort.pojo.Trade;
import com.shao.cursort.result.Result;
import com.shao.cursort.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;


public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Override
    public Result recharge(Trade trade) {

        tradeMapper.insert(trade) ;
        return new Result();
    }

    @Override
    public Result consume(Trade trade) {

        return null;
    }
}
