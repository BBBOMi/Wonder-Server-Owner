package com.wonder.bring.owner.service;

import com.wonder.bring.owner.dto.OrderList;
import com.wonder.bring.owner.dto.OrderListDetail;
import com.wonder.bring.owner.mapper.OrderListMapper;
import com.wonder.bring.owner.model.DefaultRes;
import com.wonder.bring.owner.utils.Message;
import com.wonder.bring.owner.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        //상점번호 유효검사
        if(orderListMapper.checkStore(storeIdx) == 0) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_STORE_FOUND);
        }

        //주문내역 리스트 맵퍼에서 받아오기
        List<OrderList> list = orderListMapper.getAllOrderLists(storeIdx);

        if(list.isEmpty())
            return DefaultRes.res(Status.NO_CONTENT, "주문내역이 없습니다");

        //주문 리스트들
        for(OrderList orderList : list) {
            //주문번호 하나의 메뉴리스트 가져오기
            List<OrderListDetail> menuList = orderListMapper.getAllOrderListDatails(orderList.getOrderListIdx());

            //주문리스트의 가격 설정
            int totalPrice = 0;
            for(OrderListDetail orderListDetail : menuList) {
                totalPrice += orderListDetail.getMenuCountPrice() * orderListDetail.getOrderCount();
            }
            orderList.setTotalPrice(totalPrice);

            //주문리스트의 총주문 갯수 설정
            orderList.setTotalCount(menuList.size());

            //주문리스트의 첫번째 메뉴 설정
            orderList.setFirstMenu(menuList.get(0));
        }

        return DefaultRes.res(Status.SUCCESS, "주문내역 조회 성공", list);
    }


    /**
     * 주문 상세내역 조회
     * @param storeIdx
     * @return
     */

    public DefaultRes<List<OrderListDetail>> getOrderListDetail(final int storeIdx, final int orderIdx) {
        //상점번호 유효 검사
        if(orderListMapper.checkStore(storeIdx) == 0) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_STORE_FOUND);
        }
        //주문번호 유효 검사
        if(orderListMapper.checkOrder(orderIdx) == 0) {
            return DefaultRes.res(Status.NOT_FOUND, Message.NOT_ORDER_FOUND);
        }

        List<OrderListDetail> list = orderListMapper.getAllOrderListDatails(orderIdx);

        return DefaultRes.res(Status.SUCCESS, "주문내역 상세조회 성공", list);
    }

    /**
     * 상태 정보 변경
     * @param orderIdx
     * @param state
     * @return
     */
    @Transactional
    public DefaultRes updateOrderState(final int storeIdx, final int orderIdx, final Optional<Integer> state) {
       try {
            //상점 유효 검사
           if(orderListMapper.checkStore(storeIdx) == 0) {
               return DefaultRes.res(Status.NOT_FOUND, Message.NOT_STORE_FOUND);
           }

           //주문번호 유효 검사
           if(orderListMapper.checkOrder(orderIdx) == 0) {
               return DefaultRes.res(Status.NOT_FOUND, Message.NOT_ORDER_FOUND);
           }

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
