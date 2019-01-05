package com.wonder.bring.owner.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by bomi on 2019-01-04.
 */

@Data
public class OrderList {
    private int orderListIdx;
    private String nick;
    private int state; // 상태
    private Date time; // 주문 시각
    private List<OrderListDetail> menuList;
}
