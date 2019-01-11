package com.wonder.bring.owner.service;

import com.wonder.bring.owner.dto.OrderList;
import com.wonder.bring.owner.dto.OrderListDetail;
import com.wonder.bring.owner.mapper.FcmMapper;
import com.wonder.bring.owner.mapper.OrderListMapper;
import com.wonder.bring.owner.model.DefaultRes;
import com.wonder.bring.owner.utils.Message;
import com.wonder.bring.owner.utils.Status;
import lombok.extern.slf4j.Slf4j;

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
    private final FcmService fcmService;
    private final FcmMapper fcmMapper;

    public OrderListService(final OrderListMapper orderListMapper, final FcmService fcmService, final FcmMapper fcmMapper) {
        this.orderListMapper = orderListMapper;
        this.fcmService = fcmService;
        this.fcmMapper = fcmMapper;
    }

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

        //주문 리스트들
        for(OrderList orderList : list) {
            //주문번호 하나의 메뉴리스트 가져오기
            List<OrderListDetail> menuList = orderListMapper.getAllOrderListDetails(orderList.getOrderListIdx());

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

        if(list.isEmpty())
            return DefaultRes.res(Status.NO_CONTENT, "주문내역이 없습니다");

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

        List<OrderListDetail> list = orderListMapper.getAllOrderListDetails(orderIdx);

        return DefaultRes.res(Status.SUCCESS, "주문내역 상세조회 성공", list);
    }

    /**
     * 상태 정보 변경
     * @param orderIdx
     * @param state
     * @return
     */
    @Transactional
    public DefaultRes updateOrderState(final int storeIdx, final int orderIdx, final Optional<Integer> state, final Optional<String> str) {
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
               String storeName = fcmMapper.getStoreNameByStoreIdx(storeIdx);
               String title = storeName;
               String message = "";

               if(state.get() == 1) {
                   if(str.isPresent() && !str.get().equals("")) {
                       message = "요청하신 주문이 접수 되었습니다. \n"
                               + str.get() + "분 이내에 완성될 예정입니다";
                   } else {
                       return DefaultRes.res(Status.BAD_REQUEST, Message.NOT_RESPONSE);
                   }
               } else if(state.get() == 2) {
                   message = "주문하신 메뉴가 완성되었습니다. \n"
                           + "매장에서 메뉴를 수령해 주세요!";
               } else if(state.get() == 3) {

               } else if(state.get() == 4) {
                   if(str.isPresent() && !str.get().equals("")) {
                       message = "요청하신 주문이 거절 되었습니다. \n"
                               + "거절 사유: " + str.get();
                   } else {
                       return DefaultRes.res(Status.BAD_REQUEST, Message.NOT_RESPONSE);
                   }
               } else {
                   return DefaultRes.res(Status.BAD_REQUEST, Message.BAD_REQUEST);
               }

               final String fcmToken = fcmMapper.getFcmTokenByOrderIdx(orderIdx);
               //주문번호로 fcmToken값을 찾아 전송
               fcmService.sendPush(fcmToken, title, message);
               orderListMapper.changeState(state.get(),orderIdx);
               return DefaultRes.res(Status.SUCCESS, "상태 변경 성공");
           }
           else
               return DefaultRes.res(Status.BAD_REQUEST, Message.BAD_REQUEST);
       } catch(Exception e) {
           log.info(e.getMessage());
           return DefaultRes.res(Status.DB_ERROR, Message.DB_ERROR);
       }
    }

}
