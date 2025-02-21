DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS performance_tickets;
DROP TABLE IF EXISTS performance_sessions;
DROP TABLE IF EXISTS performances;

-- 创建演出表
CREATE TABLE IF NOT EXISTS performances (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL COMMENT '演出标题',
    cover_url VARCHAR(1024) DEFAULT '' COMMENT '封面图片URL',
    images TEXT COMMENT '图片URL列表',
    venue VARCHAR(255) NOT NULL COMMENT '演出场馆',
    status ENUM('ON_SALE', 'OFFLINE') NOT NULL DEFAULT 'OFFLINE' COMMENT '演出状态',
    description TEXT COMMENT '演出描述',
    notices TEXT COMMENT '观演须知',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_status (status),
    INDEX idx_venue (venue)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='演出信息表';

-- 创建场次表
CREATE TABLE IF NOT EXISTS performance_sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    performance_id BIGINT NOT NULL COMMENT '关联的演出ID',
    title VARCHAR(255) DEFAULT '' COMMENT '场次标题',
    start_sale_time DATETIME NOT NULL DEFAULT '1970-01-01' COMMENT '售票开始时间',
    end_sale_time DATETIME NOT NULL DEFAULT '1970-01-01' COMMENT '售票结束时间',
    start_show_time DATETIME NOT NULL DEFAULT '1970-01-01' COMMENT '演出开始时间',
    end_show_time DATETIME NOT NULL DEFAULT '1970-01-01' COMMENT '演出结束时间',
    status ENUM('ON_SALE', 'OFFLINE') NOT NULL DEFAULT 'OFFLINE' COMMENT '场次状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (performance_id) REFERENCES performances(id) ON DELETE CASCADE,
    INDEX idx_performance_id (performance_id),
    INDEX idx_status (status),
    INDEX idx_show_time (start_show_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='演出场次表';

-- 创建票档表
CREATE TABLE IF NOT EXISTS performance_tickets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    performance_id BIGINT NOT NULL COMMENT '关联的演出ID',
    session_id BIGINT NOT NULL COMMENT '关联的场次ID',
    title VARCHAR(255) NOT NULL COMMENT '票档标题',
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '票价',
    total_quantity INT NOT NULL DEFAULT 0 COMMENT '总票数',
    status ENUM('ON_SALE', 'OFFLINE') NOT NULL DEFAULT 'OFFLINE' COMMENT '票档状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (performance_id) REFERENCES performances(id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES performance_sessions(id) ON DELETE CASCADE,
    INDEX idx_performance_session (performance_id, session_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票档信息表';

-- 创建订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
    user_union_id VARCHAR(50) NOT NULL COMMENT '用户ID',
    performance_id BIGINT NOT NULL COMMENT '演出ID',
    session_id BIGINT NOT NULL COMMENT '场次ID',
    ticket_id BIGINT NOT NULL COMMENT '票档ID',
    quantity INT NOT NULL DEFAULT 0 COMMENT '购买数量',
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '票面总价',
    final_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
    status ENUM('pending_payment', 'paid', 'cancelled', 'refunded') NOT NULL DEFAULT 'pending_payment' COMMENT '订单状态',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    pay_time DATETIME NULL COMMENT '支付时间',
    cancel_time DATETIME NULL COMMENT '取消时间',
    refund_time DATETIME NULL COMMENT '退款时间',
    FOREIGN KEY (performance_id) REFERENCES performances(id),
    FOREIGN KEY (session_id) REFERENCES performance_sessions(id),
    FOREIGN KEY (ticket_id) REFERENCES performance_tickets(id),
    INDEX idx_order_no (order_no),
    INDEX idx_user_union_id (user_union_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';