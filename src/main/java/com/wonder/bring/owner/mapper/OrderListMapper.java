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

    /**
     * 모든 주문 내역
     * @param storeIdx
     *      주문을 조회할 매장 고유 idx
     * @return
     */
    @Select("SELECT ol.order_idx order_list_idx, ol.state, time, u.nick " +
            "FROM ORDER_LISTS ol JOIN USERS u ON ol.user_idx = u.user_idx " +
            "WHERE ol.store_idx = #{storeIdx} " +
            "ORDER BY ol.time")
    List<OrderList> getAllOrderLists(final int storeIdx);

    /**
     * 주문 상세 내역 확인
     * @param orderIdx
     *      조회할 주문서의 고유 idx
     * @return
     */
    @Select("SELECT om.size, om.order_count, om.total_price menu_count_price, om.memo, m.name menu_name " +
            "FROM ORDER_MENU om JOIN MENU m ON om.menu_idx = m.menu_idx " +
            "WHERE om.order_idx = #{orderIdx} ")
    List<OrderListDetail> getAllOrderListDetails(final int orderIdx);

    /**
     * 주문 진행 상태 변경
     * @param state
     *      변경할 상태의 값
     * @param orderIdx
     *      진행 상태를 변경할 주문서의 고유 idx
     */
    @Update("UPDATE ORDER_LISTS SET state = #{state} WHERE order_idx = #{orderIdx}")
    void changeState(@Param("state")final int state, @Param("orderIdx") final int orderIdx);

    /**
     * 매장 유무 확인
     * @param storeIdx
     *      확인할 매장의 고유 idx
     * @return
     */
    @Select("SELECT COUNT(*) FROM STORES WHERE store_idx = #{storeIdx}")
    int checkStore(final int storeIdx);

    /**
     * 주문서 유무 확인
     * @param orderIdx
     *      확인할 주문서의 고유 idx
     * @return
     */
    @Select("SELECT COUNT(*) FROM ORDER_LISTS WHERE order_idx = #{orderIdx}")
    int checkOrder(final int orderIdx);

    @Select("SELECT COUNT(*) FROM ORDER_MENU JOIN ORDER_LISTS ON ORDER_LISTS.order_idx = ORDER_MENU.order_idx WHERE ORDER_LISTS.store_idx = #{storeIdx} AND ORDER_LISTS.order_idx = #{orderIdx}")
    int checkOrderListByStoreIdxAndOrderIdx(@Param("storeIdx") final int storeIdx, @Param("orderIdx") final int orderIdx);

}
