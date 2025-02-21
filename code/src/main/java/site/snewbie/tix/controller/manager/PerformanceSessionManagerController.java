package site.snewbie.tix.controller.manager;

import site.snewbie.tix.entity.manager.PerformanceSession;
import site.snewbie.tix.service.manager.PerformanceSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/api/performances/{performanceId}/sessions")
public class PerformanceSessionManagerController {

    @Autowired
    private PerformanceSessionService performanceSessionService;

    /**
     * 获取演出场次列表
     */
    @GetMapping
    public ResponseEntity<List<PerformanceSession>> getSessions(@PathVariable Long performanceId) {
        return ResponseEntity.ok(performanceSessionService.getSessionsByPerformance(performanceId));
    }

    /**
     * 获取单个场次详情
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<PerformanceSession> getSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId) {
        return ResponseEntity.ok(performanceSessionService.getSession(performanceId, sessionId));
    }

    /**
     * 创建场次
     */
    @PostMapping
    public ResponseEntity<PerformanceSession> createSession(
            @PathVariable Long performanceId,
            @RequestBody PerformanceSession session) {
        return ResponseEntity.ok(performanceSessionService.createSession(performanceId, session));
    }

    /**
     * 更新场次
     */
    @PutMapping("/{sessionId}")
    public ResponseEntity<PerformanceSession> updateSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId,
            @RequestBody PerformanceSession session) {
        return ResponseEntity.ok(performanceSessionService.updateSession(performanceId, sessionId, session));
    }

    /**
     * 删除场次
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId) {
        performanceSessionService.deleteSession(performanceId, sessionId);
        return ResponseEntity.ok().build();
    }
}