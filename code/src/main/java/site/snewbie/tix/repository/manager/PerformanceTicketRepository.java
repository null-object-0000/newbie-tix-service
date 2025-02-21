package site.snewbie.tix.repository.manager;

import site.snewbie.tix.entity.manager.PerformanceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceTicketRepository extends JpaRepository<PerformanceTicket, Long> {
    List<PerformanceTicket> findByPerformanceIdAndSessionId(Long performanceId, Long sessionId);
}