package com.wonder.bring.owner.api;

import com.wonder.bring.owner.service.OrderListService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(OrderListService.class);

    public OrderListController(OrderListService orderListService) {
        this.orderListService = orderListService;
    }

    /**
     * 전체주문 내역 받아오기
     * @param storeIdx
     *      받아올 매장 인덱스
     * @return
     */
    @GetMapping("/stores/{storeIdx}/orderLists")
        public ResponseEntity getOrderLists(@PathVariable final int storeIdx) {
        try {
            return new ResponseEntity<>(orderListService.getOrderList(storeIdx), HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *  주문 상세 내역 받아오기
     * @param storeIdx
     *      주문서를 조회할 매장 고유 idx
     * @param orderIdx
     *      상세 내역을 조회할 주문서 고유 idx
     * @return
     */
    @GetMapping("/stores/{storeIdx}/orderLists/{orderIdx}")
    public ResponseEntity getOrderListDetails(@PathVariable final int storeIdx, @PathVariable final int orderIdx) {
        try {
            return new ResponseEntity<>(orderListService.getOrderListDetail(storeIdx, orderIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 주문 진행 상태 변경
     * @param storeIdx
     *      주문이 들어온 매장의 고유 idx
     * @param orderIdx
     *      상태를 변경할 주문서의 고유 idx
     * @param state
     *      변경할 상태 값
     * @param res
     *      주문 승인/거절 일 경우 소요시간/이유
     * @return
     */
    @PutMapping("/stores/{storeIdx}/orderLists/{orderIdx}")
    public ResponseEntity changeState(@PathVariable final int storeIdx,
                                      @PathVariable final int orderIdx,
                                      @RequestParam(value = "state", required = false) final Optional<Integer> state,
                                      @RequestParam(value = "res", required = false) final Optional<String> res) {
        try {
            return new ResponseEntity<>(orderListService.updateOrderState(storeIdx, orderIdx, state, res), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
