package com.example.webframework.service.impl;

import com.example.webframework.entity.Performance;
import com.example.webframework.entity.PerformanceStatus;
import com.example.webframework.repository.PerformanceRepository;
import com.example.webframework.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Override
    @Transactional
    public Performance createPerformance(Performance performance) {
        return performanceRepository.save(performance);
    }

    @Override
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

    @Override
    @Transactional
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }

    @Override
    public Performance getPerformance(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("演出不存在"));
    }

    @Override
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    @Override
    public List<Performance> getPerformancesByStatus(PerformanceStatus status) {
        return performanceRepository.findByStatus(status);
    }

    @Override
    public List<Performance> getPerformancesByVenue(String venue) {
        return performanceRepository.findByVenueContaining(venue);
    }

    @Override
    public List<Performance> getPerformancesByTitle(String title) {
        return performanceRepository.findByTitleContaining(title);
    }

    @Override
    public List<Performance> getPerformancesByStatusAndVenue(PerformanceStatus status, String venue) {
        return performanceRepository.findByStatusAndVenueContaining(status, venue);
    }

    @Override
    public List<Performance> getPerformancesByStatusAndTitle(PerformanceStatus status, String title) {
        return performanceRepository.findByStatusAndTitleContaining(status, title);
    }
}