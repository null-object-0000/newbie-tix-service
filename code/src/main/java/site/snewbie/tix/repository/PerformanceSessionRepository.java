package site.snewbie.tix.repository;

import site.snewbie.tix.entity.PerformanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceSessionRepository extends JpaRepository<PerformanceSession, Long> {
    // 根据演出ID查询场次列表
    List<PerformanceSession> findByPerformanceId(Long performanceId);

    // 根据演出ID和场次ID查询单个场次
    PerformanceSession findByPerformanceIdAndId(Long performanceId, Long sessionId);

    // 删除指定演出的场次
    void deleteByPerformanceIdAndId(Long performanceId, Long sessionId);
}