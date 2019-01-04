package com.wonder.bring.owner.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bomi on 2019-01-04.
 */

@Slf4j
@RequestMapping("orderlists")
@RestController
public class OrderListController {

    @GetMapping("")
    public ResponseEntity getOrderLists() {
        return new ResponseEntity();
    }

    @PutMapping("")
    public ResponseEntity changeState(@RequestParam(value = "state", required = false) final String state) {
        return new ResponseEntity();
    }
}
