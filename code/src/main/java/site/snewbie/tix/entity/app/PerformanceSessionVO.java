package site.snewbie.tix.entity.app;

import site.snewbie.tix.entity.manager.PerformanceSession;

import java.util.List;

public class PerformanceSessionVO extends PerformanceSession {
    private List<PerformanceTicketVO> tickets;

    public List<PerformanceTicketVO> getTickets() {
        return tickets;
    }

    public void setTickets(List<PerformanceTicketVO> tickets) {
        this.tickets = tickets;
    }
}
