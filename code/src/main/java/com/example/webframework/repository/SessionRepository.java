package com.example.webframework.repository;

import com.example.webframework.entity.PerformanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<PerformanceSession, Long> {
    // 根据演出ID查询场次列表
    List<PerformanceSession> findByPerformanceId(Long performanceId);

    // 根据演出ID和场次ID查询单个场次
    PerformanceSession findByPerformanceIdAndId(Long performanceId, Long sessionId);

    // 根据演出ID和状态查询场次列表
    List<PerformanceSession> findByPerformanceIdAndStatus(Long performanceId, String status);

    // 根据演出ID和时间范围查询场次列表
    @Query("SELECT s FROM PerformanceSession s WHERE s.performanceId = :performanceId AND s.startSaleTime >= :startTime AND s.startSaleTime <= :endTime")
    List<PerformanceSession> findByPerformanceIdAndTimeRange(
        @Param("performanceId") Long performanceId,
        @Param("startTime") String startTime,
        @Param("endTime") String endTime
    );

    // 根据演出ID和开始时间范围查询场次列表
    List<PerformanceSession> findByPerformanceIdAndStartShowTimeBetween(
        Long performanceId,
        LocalDateTime startShowTime,
        LocalDateTime endShowTime
    );

    // 删除指定演出的场次
    void deleteByPerformanceIdAndId(Long performanceId, Long sessionId);
}