package site.snewbie.tix.service.manager;

import site.snewbie.tix.entity.manager.Performance;
import site.snewbie.tix.entity.manager.PerformanceStatus;
import site.snewbie.tix.repository.manager.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Transactional
    public Performance createPerformance(Performance performance) {
        return performanceRepository.save(performance);
    }

    @Transactional
    public Performance updatePerformance(Long id, Performance performance) {
        Performance existingPerformance = performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("演出不存在"));

        existingPerformance.setTitle(performance.getTitle());
        existingPerformance.setCoverUrl(performance.getCoverUrl());
        existingPerformance.setImages(performance.getImages());
        existingPerformance.setVenue(performance.getVenue());
        existingPerformance.setStatus(performance.getStatus());
        existingPerformance.setDescription(performance.getDescription());
        existingPerformance.setNotices(performance.getNotices());

        return performanceRepository.save(existingPerformance);
    }

    @Transactional
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }

    public Performance getPerformance(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("演出不存在"));
    }

    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    public List<Performance> getPerformancesByStatus(PerformanceStatus status) {
        return performanceRepository.findByStatus(status);
    }

    public List<Performance> getPerformancesByVenue(String venue) {
        return performanceRepository.findByVenueContaining(venue);
    }

    public List<Performance> getPerformancesByTitle(String title) {
        return performanceRepository.findByTitleContaining(title);
    }

    public List<Performance> getPerformancesByStatusAndVenue(PerformanceStatus status, String venue) {
        return performanceRepository.findByStatusAndVenueContaining(status, venue);
    }

    public List<Performance> getPerformancesByStatusAndTitle(PerformanceStatus status, String title) {
        return performanceRepository.findByStatusAndTitleContaining(status, title);
    }
}