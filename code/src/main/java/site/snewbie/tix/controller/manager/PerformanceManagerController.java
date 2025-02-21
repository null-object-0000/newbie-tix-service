package site.snewbie.tix.controller.manager;

import site.snewbie.tix.entity.manager.Performance;
import site.snewbie.tix.entity.manager.PerformanceStatus;
import site.snewbie.tix.service.manager.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/api/performances")
public class PerformanceManagerController {

    @Autowired
    private PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<Performance> createPerformance(@RequestBody Performance performance) {
        Performance createdPerformance = performanceService.createPerformance(performance);
        return ResponseEntity.ok(createdPerformance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(
            @PathVariable Long id,
            @RequestBody Performance performance) {
        Performance updatedPerformance = performanceService.updatePerformance(id, performance);
        return ResponseEntity.ok(updatedPerformance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable Long id) {
        performanceService.deletePerformance(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Performance> getPerformance(@PathVariable Long id) {
        Performance performance = performanceService.getPerformance(id);
        return ResponseEntity.ok(performance);
    }

    @GetMapping
    public ResponseEntity<List<Performance>> getPerformances(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String venue,
            @RequestParam(required = false) PerformanceStatus status) {
        
        List<Performance> performances;
        
        if (status != null) {
            if (venue != null) {
                performances = performanceService.getPerformancesByStatusAndVenue(status, venue);
            } else if (title != null) {
                performances = performanceService.getPerformancesByStatusAndTitle(status, title);
            } else {
                performances = performanceService.getPerformancesByStatus(status);
            }
        } else if (venue != null) {
            performances = performanceService.getPerformancesByVenue(venue);
        } else if (title != null) {
            performances = performanceService.getPerformancesByTitle(title);
        } else {
            performances = performanceService.getAllPerformances();
        }
        
        return ResponseEntity.ok(performances);
    }
}