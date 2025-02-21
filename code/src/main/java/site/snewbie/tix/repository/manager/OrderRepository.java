package site.snewbie.tix.repository.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.snewbie.tix.entity.manager.Order;
import site.snewbie.tix.entity.manager.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNo(String orderNo);
    
    List<Order> findByUserUnionIdOrderByCreateTimeDesc(String userUnionId);
    
    @Modifying
    @Query("UPDATE Order o SET o.status = :status, o.payTime = :payTime WHERE o.orderNo = :orderNo AND o.status = :currentStatus")
    int updateOrderStatus(String orderNo, OrderStatus currentStatus, OrderStatus status, LocalDateTime payTime);
    
    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.createTime < :expireTime")
    List<Order> findExpiredOrders(OrderStatus status, LocalDateTime expireTime);
}