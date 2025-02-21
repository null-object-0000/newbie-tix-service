package site.snewbie.tix.entity.manager;

public enum OrderStatus {
    PENDING_PAYMENT,  // 待支付
    PAID,            // 已支付
    CANCELLED,       // 已取消
    REFUNDED         // 已退款
}