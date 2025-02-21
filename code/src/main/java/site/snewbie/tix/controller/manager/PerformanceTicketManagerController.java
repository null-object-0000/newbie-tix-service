package site.snewbie.tix.controller.manager;

import site.snewbie.tix.entity.manager.PerformanceTicket;
import site.snewbie.tix.service.manager.PerformanceTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/api/performances/{performanceId}/sessions/{sessionId}/tickets")
public class PerformanceTicketManagerController {

    @Autowired
    private PerformanceTicketService ticketService;

    @GetMapping
    public ResponseEntity<List<PerformanceTicket>> getTickets(@PathVariable Long performanceId,
                                                              @PathVariable Long sessionId) {
        return ResponseEntity.ok(ticketService.getTicketsByPerformanceAndSession(performanceId, sessionId));
    }

    @PostMapping
    public ResponseEntity<PerformanceTicket> createTicket(@RequestBody PerformanceTicket ticket) {
        return ResponseEntity.ok(ticketService.createTicket(ticket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceTicket> updateTicket(@PathVariable Long id,
                                                          @RequestBody PerformanceTicket ticket
    ) {
        return ResponseEntity.ok(ticketService.updateTicket(id, ticket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id
    ) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceTicket> getTicket(@PathVariable Long id
    ) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

}