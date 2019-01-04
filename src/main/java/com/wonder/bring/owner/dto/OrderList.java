package com.wonder.bring.owner.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by bomi on 2019-01-04.
 */

@Data
public class OrderList {
    private int orderListIdx;
    private String nick;
    private String menuName;
    private int size; // 사이즈
    private int orderCount; // 수량
    private int totalPrice; // 메뉴 하나당 전체 가격
    private String memo; // 요청 사항
    private int state; // 상태
    private Date time; // 주문 시각
}
