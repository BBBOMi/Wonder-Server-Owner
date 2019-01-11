package com.wonder.bring.owner.dto;

import lombok.Data;

/**
 * Created by bomi on 2019-01-05.
 */

@Data
public class OrderListDetail {
    private String menuName; // 메뉴 이름
    private int size; // 사이즈
    private int orderCount; // 메뉴 당 주문한 수량
    private int menuCountPrice; // 메뉴 하나당 전체 가격
    private String memo; // 요청 사항
}
