package com.wonder.bring.owner.service;

import com.wonder.bring.owner.dto.OrderList;
import com.wonder.bring.owner.mapper.OrderListsMapper;
import com.wonder.bring.owner.model.DefaultRes;
import com.wonder.bring.owner.utils.Message;
import com.wonder.bring.owner.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bomi on 2019-01-04.
 */

@Slf4j
@Service
public class OrderListsService {
    private final OrderListsMapper orderListsMapper;

    public OrderListsService(final OrderListsMapper orderMapper) {
        this.orderListsMapper = orderMapper;
    }

    /**
     * 주문내역들 조회
     */
    private static List<OrderList> list = new LinkedList<>();

    public DefaultRes<List<OrderList>> getOrderList(final int storeIdx) {
        list = orderListsMapper.getAllOrderLists(storeIdx);
        if(list.isEmpty())
            return DefaultRes.res(Status.NOT_FOUND, "주문내역이 없습니다");

        return DefaultRes.res(Status.SUCCESS, "리스트 조회 성공", list);
    }

    /**
     *
     * @param orderIdx
     * @param state
     * @return
     */
    public DefaultRes updateOrderState(final int orderIdx, final int state) {
       try {
           orderListsMapper.changeState(orderIdx, state);
           return DefaultRes.res(Status.SUCCESS, "상태 변경 성공");
       }catch(Exception e) {
           DefaultRes.res(Status.DB_ERROR, Message.DB_ERROR);
       }
    }
}
