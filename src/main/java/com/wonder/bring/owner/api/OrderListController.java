package com.wonder.bring.owner.api;

import com.wonder.bring.owner.service.OrderListService;
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
@RestController
public class OrderListController {
    public final OrderListService orderListService;

    public OrderListController(OrderListService orderListService) {
        this.orderListService = orderListService;
    }

    /**
       주문내역 얻어오기
     */
    @GetMapping("/stores/{storeIdx}/orderLists")
        public ResponseEntity getOrderLists(@PathVariable final int storeIdx) {
        try {
            return new ResponseEntity<>(orderListService.getOrderList(storeIdx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/orderLists/{orderIdx}")
    public ResponseEntity changeState( @PathVariable final int orderIdx,
                                       @RequestParam(value = "state", required = false) final Optional<Integer> state) {
        try {
            return new ResponseEntity<>(orderListService.updateOrderState(orderIdx, state), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
