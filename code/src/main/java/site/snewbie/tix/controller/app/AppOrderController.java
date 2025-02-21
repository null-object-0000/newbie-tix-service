package site.snewbie.tix.controller.app;

import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.snewbie.tix.entity.app.CreateOrderRequest;
import site.snewbie.tix.entity.manager.*;
import site.snewbie.tix.service.manager.OrderService;
import site.snewbie.tix.service.manager.PerformanceService;
import site.snewbie.tix.service.manager.PerformanceSessionService;
import site.snewbie.tix.service.manager.PerformanceTicketService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/app/api/order")
public class AppOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private PerformanceSessionService performanceSessionService;
    @Autowired
    private PerformanceTicketService performanceTicketService;

    @PostMapping("/create")
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        // 生成订单号：时间戳 + UUID 后 8 位
        String orderNo = System.currentTimeMillis() + IdUtil.fastSimpleUUID().substring(24);

        // 根据演出、场次、票档查出对应实体
        Performance performance = performanceService.getPerformance(request.getPerformanceId());
        PerformanceSession session = performanceSessionService.getSession(request.getPerformanceId(), request.getSessionId());
        PerformanceTicket ticket = performanceTicketService.getTicket(request.getTicketId());

        if (performance == null || session == null || ticket == null) {
            throw new IllegalArgumentException("Invalid performance, session or ticket");
        }

        // 金额后端再算一遍，和前端算的金额比对，防止前端篡改
        BigDecimal totalAmount = ticket.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        if (totalAmount.compareTo(request.getTotalAmount()) != 0) {
            throw new IllegalArgumentException("Invalid total amount");
        }

        // 创建订单对象
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserUnionId(request.getUserUnionId());
        order.setPerformanceId(request.getPerformanceId());
        order.setSessionId(request.getSessionId());
        order.setTicketId(request.getTicketId());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(totalAmount);
        order.setFinalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setCreateTime(LocalDateTime.now());

        // 保存订单
        Order savedOrder = orderService.createOrder(order);

        // 模拟支付成功
        orderService.updateOrderStatus(savedOrder.getOrderNo(), OrderStatus.PAID, LocalDateTime.now());

        return savedOrder;
    }
}