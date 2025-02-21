package com.example.webframework.service;

import com.example.webframework.entity.PerformanceTicket;
import java.util.List;

public interface PerformanceTicketService {
    // 创建票档
    PerformanceTicket createTicket(PerformanceTicket ticket);

    // 更新票档信息
    PerformanceTicket updateTicket(Long id, PerformanceTicket ticket);

    // 删除票档
    void deleteTicket(Long id);

    // 获取单个票档详情
    PerformanceTicket getTicket(Long id);

    // 获取演出场次的所有票档
    List<PerformanceTicket> getTicketsByPerformanceAndSession(Long performanceId, Long sessionId);
}