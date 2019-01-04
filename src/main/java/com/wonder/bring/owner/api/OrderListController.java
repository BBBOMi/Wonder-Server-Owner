package com.wonder.bring.owner.api;

import com.wonder.bring.owner.service.OrderListsService;
import com.wonder.bring.owner.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.wonder.bring.owner.model.DefaultRes.FAIL_DEFAULT_RES;

/**
 * Created by bomi on 2019-01-04.
 */

@Slf4j
@RequestMapping("")
@RestController
public class OrderListController {
    public final OrderListsService orderListsService;

    public OrderListController(OrderListsService orderListsService) {
        this.orderListsService = orderListsService;
    }

    /**
       주문내역 얻어오기
     */
    @GetMapping("/stores/{storeIdx}/orderlists")
        public ResponseEntity getOrderLists(@PathVariable final int storeIdx) {
        try {
            return new ResponseEntity<>(orderListsService.getOrderList(storeIdx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/orderlists/{orderIdx}")
    public ResponseEntity changeState( @PathVariable final int orderIdx,
                                       @RequestParam(value = "state", required = false) final int state) {
        try {
            return new ResponseEntity<>(orderListsService.updateOrderState(orderIdx, state), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
