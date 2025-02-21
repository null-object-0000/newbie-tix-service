package site.snewbie.tix.repository.manager;

import site.snewbie.tix.entity.manager.Performance;
import site.snewbie.tix.entity.manager.PerformanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    // 根据状态查询演出列表
    List<Performance> findByStatus(PerformanceStatus status);

    // 根据场馆名称查询演出列表
    List<Performance> findByVenueContaining(String venue);

    // 根据标题模糊查询演出列表
    List<Performance> findByTitleContaining(String title);

    // 根据状态和场馆查询演出列表
    List<Performance> findByStatusAndVenueContaining(PerformanceStatus status, String venue);

    // 根据状态和标题查询演出列表
    List<Performance> findByStatusAndTitleContaining(PerformanceStatus status, String title);
}