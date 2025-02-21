package site.snewbie.tix.service.manager;

import site.snewbie.tix.entity.manager.PerformanceSession;
import site.snewbie.tix.repository.manager.PerformanceSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceSessionService {

    @Autowired
    private PerformanceSessionRepository performanceSessionRepository;

    public PerformanceSession createSession(Long performanceId, PerformanceSession session) {
        session.setPerformanceId(performanceId);
        return performanceSessionRepository.save(session);
    }

    public PerformanceSession updateSession(Long performanceId, Long sessionId, PerformanceSession session) {
        PerformanceSession existingSession = performanceSessionRepository.findByPerformanceIdAndId(performanceId, sessionId);

        existingSession.setTitle(session.getTitle());
        existingSession.setStartSaleTime(session.getStartSaleTime());
        existingSession.setEndSaleTime(session.getEndSaleTime());
        existingSession.setStartShowTime(session.getStartShowTime());
        existingSession.setEndShowTime(session.getEndShowTime());
        existingSession.setStatus(session.getStatus());

        return performanceSessionRepository.save(existingSession);
    }

    public void deleteSession(Long performanceId, Long sessionId) {
        performanceSessionRepository.deleteByPerformanceIdAndId(performanceId, sessionId);
    }

    public PerformanceSession getSession(Long performanceId, Long sessionId) {
        return performanceSessionRepository.findByPerformanceIdAndId(performanceId, sessionId);
    }

    public List<PerformanceSession> getSessionsByPerformance(Long performanceId) {
        return performanceSessionRepository.findByPerformanceId(performanceId);
    }

}