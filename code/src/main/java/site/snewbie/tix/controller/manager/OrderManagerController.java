package site.snewbie.tix.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.snewbie.tix.entity.manager.Order;
import site.snewbie.tix.service.manager.OrderService;

import java.util.List;

@RestController
@RequestMapping("/manager/api/orders")
public class OrderManagerController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表
     */
    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * 获取订单详情
     */
    @GetMapping("/{orderNo}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderNo) {
        Order order = orderService.getOrderByOrderNo(orderNo);
        return ResponseEntity.ok(order);
    }

}