package site.snewbie.tix.service;

import site.snewbie.tix.entity.Performance;
import site.snewbie.tix.entity.PerformanceStatus;
import java.util.List;

public interface PerformanceService {
    // 创建演出
    Performance createPerformance(Performance performance);

    // 更新演出信息
    Performance updatePerformance(Long id, Performance performance);

    // 删除演出
    void deletePerformance(Long id);

    // 获取单个演出详情
    Performance getPerformance(Long id);

    // 获取所有演出列表
    List<Performance> getAllPerformances();

    // 根据状态查询演出列表
    List<Performance> getPerformancesByStatus(PerformanceStatus status);

    // 根据场馆查询演出列表
    List<Performance> getPerformancesByVenue(String venue);

    // 根据标题查询演出列表
    List<Performance> getPerformancesByTitle(String title);

    // 根据状态和场馆查询演出列表
    List<Performance> getPerformancesByStatusAndVenue(PerformanceStatus status, String venue);

    // 根据状态和标题查询演出列表
    List<Performance> getPerformancesByStatusAndTitle(PerformanceStatus status, String title);
}