package site.snewbie.tix.service.impl;

import site.snewbie.tix.entity.PerformanceSession;
import site.snewbie.tix.repository.PerformanceSessionRepository;
import site.snewbie.tix.service.PerformanceSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceSessionServiceImpl implements PerformanceSessionService {

    @Autowired
    private PerformanceSessionRepository performanceSessionRepository;

    @Override
    public PerformanceSession createSession(Long performanceId, PerformanceSession session) {
        session.setPerformanceId(performanceId);
        return performanceSessionRepository.save(session);
    }

    @Override
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

    @Override
    public void deleteSession(Long performanceId, Long sessionId) {
        performanceSessionRepository.deleteByPerformanceIdAndId(performanceId, sessionId);
    }

    @Override
    public PerformanceSession getSession(Long performanceId, Long sessionId) {
        return performanceSessionRepository.findByPerformanceIdAndId(performanceId, sessionId);
    }

    @Override
    public List<PerformanceSession> getSessionsByPerformance(Long performanceId) {
        return performanceSessionRepository.findByPerformanceId(performanceId);
    }

}