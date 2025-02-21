package site.snewbie.tix.entity.app;

import site.snewbie.tix.entity.manager.PerformanceSession;
import site.snewbie.tix.entity.manager.PerformanceTicket;

import java.util.List;

public class PerformanceSessionVO extends PerformanceSession {
    private List<PerformanceTicket> tickets;

    public List<PerformanceTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<PerformanceTicket> tickets) {
        this.tickets = tickets;
    }
}
