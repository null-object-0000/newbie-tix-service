package com.example.webframework.service.impl;

import com.example.webframework.entity.PerformanceTicket;
import com.example.webframework.repository.PerformanceTicketRepository;
import com.example.webframework.service.PerformanceTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerformanceTicketServiceImpl implements PerformanceTicketService {

    @Autowired
    private PerformanceTicketRepository ticketRepository;

    @Override
    @Transactional
    public PerformanceTicket createTicket(PerformanceTicket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public PerformanceTicket updateTicket(Long id, PerformanceTicket ticket) {
        PerformanceTicket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("票档不存在"));

        existingTicket.setPerformanceId(ticket.getPerformanceId());
        existingTicket.setSessionId(ticket.getSessionId());
        existingTicket.setTitle(ticket.getTitle());
        existingTicket.setPrice(ticket.getPrice());
        existingTicket.setTotalQuantity(ticket.getTotalQuantity());

        return ticketRepository.save(existingTicket);
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public PerformanceTicket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("票档不存在"));
    }

    @Override
    public List<PerformanceTicket> getTicketsByPerformanceAndSession(Long performanceId, Long sessionId) {
        return ticketRepository.findByPerformanceIdAndSessionId(performanceId, sessionId);
    }
}