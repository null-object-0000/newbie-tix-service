package site.snewbie.tix.service.app;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.snewbie.tix.entity.app.PerformanceSessionVO;
import site.snewbie.tix.entity.app.PerformanceVO;
import site.snewbie.tix.entity.manager.Performance;
import site.snewbie.tix.entity.manager.PerformanceSession;
import site.snewbie.tix.entity.manager.PerformanceTicket;
import site.snewbie.tix.service.manager.PerformanceSessionService;
import site.snewbie.tix.service.manager.PerformanceTicketService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppPerformanceService {
    @Autowired
    private PerformanceSessionService performanceSessionService;
    @Autowired
    private PerformanceTicketService performanceTicketService;

    private String buildShowTime(List<LocalDateTime> showTimes) {
        if (showTimes == null || showTimes.isEmpty()) {
            return StrUtil.EMPTY;
        }

        // 找到演出的最早开始时间和最晚结束时间
        LocalDateTime minShowTime = showTimes.stream().min(LocalDateTime::compareTo).get();
        LocalDateTime maxShowTime = showTimes.stream().max(LocalDateTime::compareTo).get();

        // 如果开始和结束时间是同一天，只显示一天
        if (minShowTime.toLocalDate().isEqual(maxShowTime.toLocalDate())) {
            return minShowTime.toLocalDate().toString();
        }
        // 如果开始和结束时间是同一年，则结束时间只显示月份和日期
        if (minShowTime.getYear() == maxShowTime.getYear()) {
            return minShowTime.toLocalDate() + " ~ " + maxShowTime.toLocalDate().format(DateTimeFormatter.ofPattern("MM-dd"));
        }

        return minShowTime.toLocalDate() + " ~ " + maxShowTime.toLocalDate();
    }

    public PerformanceVO fullPerformanceVO(Performance performance) {
        PerformanceVO performanceVO = new PerformanceVO();
        BeanUtil.copyProperties(performance, performanceVO);

        List<PerformanceSession> sessions = performanceSessionService.getSessionsByPerformance(performance.getId());
        if (CollUtil.isNotEmpty(sessions)) {
            // 取出所有演出开始时间和结束时间
            List<LocalDateTime> showTimes = sessions.stream()
                    .flatMap(session -> CollUtil.newArrayList(session.getStartShowTime(), session.getEndShowTime()).stream())
                    .collect(Collectors.toList());
            // 组装成 showTime 字符串
            performanceVO.setShowTime(this.buildShowTime(showTimes));

            performanceVO.setSessions(sessions.stream().map(this::fullPerformanceSessionVO).collect(Collectors.toList()));

            // 计算最低和最高价格
            List<PerformanceTicket> tickets = performanceVO.getSessions().stream()
                    .flatMap(session -> session.getTickets().stream())
                    .collect(Collectors.toList());

            if (!tickets.isEmpty()) {
                performanceVO.setMinPrice(tickets.stream().map(PerformanceTicket::getPrice).min(BigDecimal::compareTo).get());
                performanceVO.setMaxPrice(tickets.stream().map(PerformanceTicket::getPrice).max(BigDecimal::compareTo).get());
            }
        }

        return performanceVO;
    }

    private PerformanceSessionVO fullPerformanceSessionVO(PerformanceSession session) {
        PerformanceSessionVO sessionVO = new PerformanceSessionVO();
        BeanUtil.copyProperties(session, sessionVO);

        List<PerformanceTicket> tickets = performanceTicketService.getTicketsByPerformanceAndSession(session.getPerformanceId(), session.getId());
        sessionVO.setTickets(tickets);

        return sessionVO;
    }
}
