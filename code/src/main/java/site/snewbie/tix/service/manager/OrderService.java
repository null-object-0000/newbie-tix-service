package site.snewbie.tix.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.snewbie.tix.entity.manager.Order;
import site.snewbie.tix.repository.manager.OrderRepository;

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
}