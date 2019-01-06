package com.wonder.bring.owner.mapper;

import com.wonder.bring.owner.dto.OrderList;
import com.wonder.bring.owner.dto.OrderListDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by bomi on 2019-01-04.
 */

@Mapper
public interface OrderListMapper {

    /*
    // 모든 주문 내역
    @Select("SELECT ol.order_idx order_list_idx, ol.state, ol.time, om.size, om.order_count, om.total_price, om.memo, u.nick, m.name menu_name " +
            "FROM ORDER_LISTS ol JOIN ORDER_MENU om ON ol.order_idx = om.order_idx " +
            "JOIN USERS u ON ol.user_idx = u.user_idx " +
            "JOIN MENU m ON om.menu_idx = m.menu_idx " +
            "WHERE ol.store_idx = #{storeIdx} " +
            "ORDER BY ol.time")
    List<OrderList> getAllOrderLists(final int storeIdx);
    */

    // 모든 주문 내역
    @Select("SELECT ol.order_idx order_list_idx, ol.state, time, u.nick " +
            "FROM ORDER_LISTS ol JOIN USERS u ON ol.user_idx = u.user_idx " +
            "WHERE ol.store_idx = #{storeIdx} " +
            "ORDER BY ol.time")
    List<OrderList> getAllOrderLists(final int storeIdx);

    // 주문 상세 내역확인
    @Select("SELECT om.size, om.order_count, om.total_price menu_count_price, om.memo, m.name menu_name " +
            "FROM ORDER_MENU om JOIN MENU m ON om.menu_idx = m.menu_idx " +
            "WHERE om.order_idx = #{orderIdx} ")
    List<OrderListDetail> getAllOrderListDatails(final int orderIdx);

    //상태 변경
    @Update("UPDATE ORDER_LISTS SET state = #{state} WHERE order_idx = #{orderIdx}")
    void changeState(@Param("state")final int state, @Param("orderIdx") final int orderIdx);


    // 매장 존재 확인
    @Select("SELECT COUNT(*) FROM STORES WHERE store_idx = #{storeIdx}")
    int checkStore(final int storeIdx);

    // 주문 번호 확인
    @Select("SELECT COUNT(*) FROM ORDER_LISTS WHERE order_idx = #{orderIdx}")
    int checkOrder(final int orderIdx);

}
