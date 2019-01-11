package com.wonder.bring.owner.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by bomi on 2019-01-08.
 */

@Mapper
public interface FcmMapper {
    /**
     * 주문한 사람의 토큰값 조회
     * @param orderIdx
     *      주문서의 고유 idx
     * @return
     */
    @Select("SELECT USERS.fcm_token FROM USERS JOIN ORDER_LISTS ON USERS.user_idx = ORDER_LISTS.user_idx WHERE order_idx = #{orderIdx}")
    String getFcmTokenByOrderIdx(@Param("orderIdx") final int orderIdx);

    /**
     * 매장의 매장명 조회
     * @param StoreIdx
     *      매장명을 조회할 매장 고유 idx
     * @return
     */
    @Select("SELECT name FROM STORES WHERE store_idx = #{storeIdx}")
    String getStoreNameByStoreIdx(@Param("storeIdx") final int StoreIdx);
}
