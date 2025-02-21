package site.snewbie.tix.entity.app;

import site.snewbie.tix.entity.manager.Performance;

import java.math.BigDecimal;
import java.util.List;

public class PerformanceVO extends Performance {
    private String showTime;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private List<PerformanceSessionVO> sessions;

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<PerformanceSessionVO> getSessions() {
        return sessions;
    }

    public void setSessions(List<PerformanceSessionVO> sessions) {
        this.sessions = sessions;
    }

}
