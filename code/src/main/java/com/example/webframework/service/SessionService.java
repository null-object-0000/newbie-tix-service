package com.example.webframework.service;

import com.example.webframework.entity.PerformanceSession;
import java.util.List;

public interface SessionService {
    // 创建场次
    PerformanceSession createSession(Long performanceId, PerformanceSession session);

    // 更新场次信息
    PerformanceSession updateSession(Long performanceId, Long sessionId, PerformanceSession session);

    // 删除场次
    void deleteSession(Long performanceId, Long sessionId);

    // 获取单个场次详情
    PerformanceSession getSession(Long performanceId, Long sessionId);

    // 获取演出的所有场次列表
    List<PerformanceSession> getSessionsByPerformance(Long performanceId);

    // 根据状态查询场次列表
    List<PerformanceSession> getSessionsByStatus(Long performanceId, String status);

    // 根据时间范围查询场次列表
    List<PerformanceSession> getSessionsByTimeRange(Long performanceId, String startTime, String endTime);
}