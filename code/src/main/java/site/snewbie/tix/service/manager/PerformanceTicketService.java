package site.snewbie.tix.service.manager;

import site.snewbie.tix.entity.manager.PerformanceTicket;
import site.snewbie.tix.repository.manager.PerformanceTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerformanceTicketService {

    @Autowired
    private PerformanceTicketRepository ticketRepository;

    @Transactional
    public PerformanceTicket createTicket(PerformanceTicket ticket) {
        return ticketRepository.save(ticket);
    }

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

    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public PerformanceTicket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("票档不存在"));
    }

    public List<PerformanceTicket> getTicketsByPerformanceAndSession(Long performanceId, Long sessionId) {
        return ticketRepository.findByPerformanceIdAndSessionId(performanceId, sessionId);
    }
}