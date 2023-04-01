package com.switchfully.eurder.order.api;

import com.switchfully.eurder.order.service.OrderService;
import com.switchfully.eurder.order.service.dtos.itemgroupdtos.CreateItemGroupDTO;
import com.switchfully.eurder.order.service.dtos.orderdtos.OrderDTO;
import com.switchfully.eurder.order.service.dtos.orderdtos.OrderReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /* POST */

    @PostMapping(path = "/order/{userId}", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderDTO> createNewOrder(@PathVariable String userId, @RequestBody ArrayList<CreateItemGroupDTO> createItemGroupDTOList){
        OrderDTO orderDTO = orderService.createNewOrder(userId, createItemGroupDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @PostMapping(path = "reorder/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderDTO> reorderAnExistingOrder(@PathVariable String orderId){
        OrderDTO orderDTO = orderService.reorderAnExistingOrder(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    /* GET */

    @GetMapping(path = "/allOrders", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestHeader String adminId){
        List<OrderDTO> allOrders = orderService.getAllOrders(adminId);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping(path = "/allOrders/{customerId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderReportDTO> getMyReportAsCustomer(@PathVariable String customerId){
        OrderReportDTO allOrdersByCustomer = orderService.getMyReportAsCustomer(customerId);
        return ResponseEntity.ok(allOrdersByCustomer);
    }
}
