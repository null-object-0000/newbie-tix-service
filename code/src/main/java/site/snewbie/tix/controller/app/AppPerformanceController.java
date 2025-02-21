package site.snewbie.tix.controller.app;

import cn.hutool.core.util.ObjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.snewbie.tix.entity.app.PerformanceVO;
import site.snewbie.tix.entity.manager.Performance;
import site.snewbie.tix.entity.manager.PerformanceStatus;
import site.snewbie.tix.service.app.AppPerformanceService;
import site.snewbie.tix.service.manager.PerformanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/api/performances")
public class AppPerformanceController {

    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private AppPerformanceService appPerformanceService;

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceVO> getPerformance(@PathVariable Long id) {
        Performance performance = performanceService.getPerformance(id);
        if (performance == null) {
            return ResponseEntity.notFound().build();
        }

        PerformanceVO performanceVO = appPerformanceService.fullPerformanceVO(performance);

        return ResponseEntity.ok(performanceVO);
    }

    @GetMapping
    public ResponseEntity<List<PerformanceVO>> getPerformances(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Performance> performances = performanceService.getAllPerformances();

        // 过滤已下架的
        performances = performances.stream().filter(p -> ObjUtil.notEqual(p.getStatus(), PerformanceStatus.OFFLINE)).collect(Collectors.toList());

        // 分页处理
        int start = (page - 1) * size;
        int end = Math.min(start + size, performances.size());
        if (start >= performances.size()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        performances = performances.subList(start, end);

        List<PerformanceVO> performanceVO = performances.stream().map(appPerformanceService::fullPerformanceVO).collect(Collectors.toList());

        return ResponseEntity.ok(performanceVO);
    }

}