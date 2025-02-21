package com.example.webframework.controller;

import com.example.webframework.entity.PerformanceSession;
import com.example.webframework.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performances/{performanceId}/sessions")
public class PerformanceSessionController {

    @Autowired
    private SessionService sessionService;

    /** 获取演出场次列表 */
    @GetMapping
    public ResponseEntity<List<PerformanceSession>> getSessions(
            @PathVariable Long performanceId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        
        if (status != null) {
            return ResponseEntity.ok(sessionService.getSessionsByStatus(performanceId, status));
        } else if (startTime != null && endTime != null) {
            return ResponseEntity.ok(sessionService.getSessionsByTimeRange(performanceId, startTime, endTime));
        } else {
            return ResponseEntity.ok(sessionService.getSessionsByPerformance(performanceId));
        }
    }

    /** 获取单个场次详情 */
    @GetMapping("/{sessionId}")
    public ResponseEntity<PerformanceSession> getSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.getSession(performanceId, sessionId));
    }

    /** 创建场次 */
    @PostMapping
    public ResponseEntity<PerformanceSession> createSession(
            @PathVariable Long performanceId,
            @RequestBody PerformanceSession session) {
        return ResponseEntity.ok(sessionService.createSession(performanceId, session));
    }

    /** 更新场次 */
    @PutMapping("/{sessionId}")
    public ResponseEntity<PerformanceSession> updateSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId,
            @RequestBody PerformanceSession session) {
        return ResponseEntity.ok(sessionService.updateSession(performanceId, sessionId, session));
    }

    /** 删除场次 */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId) {
        sessionService.deleteSession(performanceId, sessionId);
        return ResponseEntity.ok().build();
    }
}