package site.snewbie.tix.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "performance_sessions")
public class PerformanceSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "performance_id", nullable = false)
    private Long performanceId;

    @Column(name = "title")
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_sale_time", nullable = false)
    private LocalDateTime startSaleTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_sale_time", nullable = false)
    private LocalDateTime endSaleTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_show_time", nullable = false)
    private LocalDateTime startShowTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_show_time", nullable = false)
    private LocalDateTime endShowTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PerformanceStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(Long performanceId) {
        this.performanceId = performanceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartSaleTime() {
        return startSaleTime;
    }

    public void setStartSaleTime(LocalDateTime startSaleTime) {
        this.startSaleTime = startSaleTime;
    }

    public LocalDateTime getEndSaleTime() {
        return endSaleTime;
    }

    public void setEndSaleTime(LocalDateTime endSaleTime) {
        this.endSaleTime = endSaleTime;
    }

    public LocalDateTime getStartShowTime() {
        return startShowTime;
    }

    public void setStartShowTime(LocalDateTime startShowTime) {
        this.startShowTime = startShowTime;
    }

    public LocalDateTime getEndShowTime() {
        return endShowTime;
    }

    public void setEndShowTime(LocalDateTime endShowTime) {
        this.endShowTime = endShowTime;
    }

    public PerformanceStatus getStatus() {
        return status;
    }

    public void setStatus(PerformanceStatus status) {
        this.status = status;
    }
}