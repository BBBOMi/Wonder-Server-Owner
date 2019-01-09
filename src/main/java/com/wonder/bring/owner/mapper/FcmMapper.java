package com.wonder.bring.owner.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by bomi on 2019-01-08.
 */

@Mapper
public interface FcmMapper {
    @Select("SELECT USERS.fcm_token FROM USERS JOIN ORDER_LISTS ON USERS.user_idx = ORDER_LISTS.user_idx WHERE order_idx = #{orderIdx}")
    String getFcmTokenByOrderIdx(@Param("orderIdx") final int orderIdx);

    @Select("SELECT name FROM STORES WHERE store_idx = #{storeIdx}")
    String getStoreNameByStoreIdx(@Param("storeIdx") final int StoreIdx);
}
