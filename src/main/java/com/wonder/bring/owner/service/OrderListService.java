package com.wonder.bring.owner.service;

import com.wonder.bring.owner.dto.OrderList;
import com.wonder.bring.owner.mapper.OrderListMapper;
import com.wonder.bring.owner.model.DefaultRes;
import com.wonder.bring.owner.utils.Message;
import com.wonder.bring.owner.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by bomi on 2019-01-04.
 */

@Slf4j
@Service
public class OrderListService {
    private final OrderListMapper orderListMapper;

    public OrderListService(final OrderListMapper orderListMapper) {
        this.orderListMapper = orderListMapper;
    }
    private static final Logger logger = LoggerFactory.getLogger(OrderListService.class);

    /**
     * 주문내역들 조회
     */
    public DefaultRes<List<OrderList>> getOrderList(final int storeIdx) {
        if(orderListMapper.checkStore(storeIdx) == 0) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_STORE_FOUND);
        }

        List<OrderList> list = orderListMapper.getAllOrderLists(storeIdx);

        if(list.isEmpty())
            return DefaultRes.res(Status.NO_CONTENT, "주문내역이 없습니다");

        return DefaultRes.res(Status.SUCCESS, "리스트 조회 성공", list);
    }

    /**
     * 상태 정보 변경
     * @param orderIdx
     * @param state
     * @return
     */
    @Transactional
    public DefaultRes updateOrderState(final int orderIdx, final Optional<Integer> state) {
       try {
           logger.info("state " + state.get());
           logger.info("orderIdx " + orderIdx);

           if(state.isPresent()) {
               if(orderListMapper.checkOrder(orderIdx) == 0) {
                   return DefaultRes.res(Status.NOT_FOUND, Message.NOT_ORDER_FOUND);
               }
               orderListMapper.changeState(state.get(),orderIdx);
               return DefaultRes.res(Status.SUCCESS, "상태 변경 성공");
           }
           else
               return DefaultRes.res(Status.BAD_REQUEST, Message.BAD_REQUEST);
       }catch(Exception e) {
           logger.info("DB에러 이유" + e.getMessage());
           return DefaultRes.res(Status.DB_ERROR, Message.DB_ERROR);
       }
    }

}
