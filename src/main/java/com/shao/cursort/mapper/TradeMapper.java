package com.shao.cursort.mapper;

import com.shao.cursort.base.BaseMapper;
import com.shao.cursort.pojo.Trade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeMapper extends BaseMapper<Trade> {
  public String getBalanceByUserId(@Param("userId")String userId) ;

}


