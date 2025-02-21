package com.example.webframework.service.impl;

import com.example.webframework.entity.PerformanceSession;
import com.example.webframework.repository.SessionRepository;
import com.example.webframework.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public PerformanceSession createSession(Long performanceId, PerformanceSession session) {
        session.setPerformanceId(performanceId);
        return sessionRepository.save(session);
    }

    @Override
    public PerformanceSession updateSession(Long performanceId, Long sessionId, PerformanceSession session) {
        PerformanceSession existingSession = sessionRepository.findByPerformanceIdAndId(performanceId, sessionId);

        existingSession.setTitle(session.getTitle());
        existingSession.setStartSaleTime(session.getStartSaleTime());
        existingSession.setEndSaleTime(session.getEndSaleTime());
        existingSession.setStartShowTime(session.getStartShowTime());
        existingSession.setEndShowTime(session.getEndShowTime());
        existingSession.setStatus(session.getStatus());

        return sessionRepository.save(existingSession);
    }

    @Override
    public void deleteSession(Long performanceId, Long sessionId) {
        sessionRepository.deleteByPerformanceIdAndId(performanceId, sessionId);
    }

    @Override
    public PerformanceSession getSession(Long performanceId, Long sessionId) {
        return sessionRepository.findByPerformanceIdAndId(performanceId, sessionId);
    }

    @Override
    public List<PerformanceSession> getSessionsByPerformance(Long performanceId) {
        return sessionRepository.findByPerformanceId(performanceId);
    }

    @Override
    public List<PerformanceSession> getSessionsByStatus(Long performanceId, String status) {
        return sessionRepository.findByPerformanceIdAndStatus(performanceId, status);
    }

    @Override
    public List<PerformanceSession> getSessionsByTimeRange(Long performanceId, String startTime, String endTime) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        return sessionRepository.findByPerformanceIdAndStartShowTimeBetween(performanceId, start, end);
    }
}