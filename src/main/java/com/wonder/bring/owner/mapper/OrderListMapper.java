package com.wonder.bring.owner.mapper;

import com.wonder.bring.owner.dto.OrderList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by bomi on 2019-01-04.
 */

@Mapper
public interface OrderListMapper {
    @Select("SELECT ol.order_idx order_lists_idx, ol.state, ol.time, om.size, om.order_count, om.total_price, om.memo, u.nick, m.name menu_name " +
            "FROM ORDER_LISTS ol JOIN ORDER_MENU om ON ol.order_idx = om.order_idx" +
            "JOIN USERS u ON ol.user_idx = u.user_idx" +
            "JOIN ORDER_MENU ON om.menu_idx = m.menu_idx" +
            "WHERE Ol.store_idx = #{storeIdx}" +
            "ORDER BY ol.time")
    List<OrderList> getAllOrderLists(final int storeIdx);

    @Update("UPDATE ORDER_LISTS SET state = #{state} WHERE order_idx = #{orderIdx}")
    void changeState(final int state, final int orderIdx);
}
