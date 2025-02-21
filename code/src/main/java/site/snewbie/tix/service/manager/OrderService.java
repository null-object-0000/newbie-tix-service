package site.snewbie.tix.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.snewbie.tix.entity.manager.Order;
import site.snewbie.tix.entity.manager.OrderStatus;
import site.snewbie.tix.repository.manager.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order getOrderByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void updateOrderStatus(String orderNo, OrderStatus status, LocalDateTime updateTime) {
        Order order = getOrderByOrderNo(orderNo);
        order.setStatus(status);
        if (status == OrderStatus.PAID) {
            order.setPayTime(updateTime);
        } else if (status == OrderStatus.CANCELLED) {
            order.setCancelTime(updateTime);
        }
        orderRepository.save(order);
    }
}