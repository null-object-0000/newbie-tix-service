package site.snewbie.tix.service.app;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.snewbie.tix.entity.app.PerformanceSessionVO;
import site.snewbie.tix.entity.app.PerformanceTicketVO;
import site.snewbie.tix.entity.app.PerformanceVO;
import site.snewbie.tix.entity.manager.Performance;
import site.snewbie.tix.entity.manager.PerformanceSession;
import site.snewbie.tix.entity.manager.PerformanceStatus;
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
        // 过滤掉已下架的场次
        sessions = sessions.stream().filter(session -> !PerformanceStatus.OFFLINE.equals(session.getStatus())).collect(Collectors.toList());
        if (CollUtil.isEmpty(sessions)) {
            // 如果场次为空，则把演出的状态设置为未开始售票
            performanceVO.setStatus(PerformanceStatus.COMING_SOON);
            return performanceVO;
        }

        performanceVO.setSessions(sessions.stream().map(this::fullPerformanceSessionVO).collect(Collectors.toList()));

        List<PerformanceTicketVO> allTickets = performanceVO.getSessions().stream()
                .filter(session -> CollUtil.isNotEmpty(session.getTickets()))
                .flatMap(session -> session.getTickets().stream()).collect(Collectors.toList());

        // 如果票档为空，则把演出的状态设置为未开始售票
        if (CollUtil.isEmpty(allTickets)) {
            performanceVO.setStatus(PerformanceStatus.COMING_SOON);
        }

        // 取出所有演出开始时间和结束时间
        List<LocalDateTime> showTimes = sessions.stream()
                .flatMap(session -> CollUtil.newArrayList(session.getStartShowTime(), session.getEndShowTime()).stream())
                .collect(Collectors.toList());
        // 组装成 showTime 字符串
        performanceVO.setShowTime(this.buildShowTime(showTimes));

        // 计算最低和最高价格
        if (!allTickets.isEmpty()) {
            performanceVO.setMinPrice(allTickets.stream().map(PerformanceTicketVO::getPrice).min(BigDecimal::compareTo).get());
            performanceVO.setMaxPrice(allTickets.stream().map(PerformanceTicketVO::getPrice).max(BigDecimal::compareTo).get());
        }

        // 先看看是不是所有的场次都未开始售票，是的话把演出的状态设置为未开始售票
        boolean allSessionsNotStartSale = sessions.stream().allMatch(session -> session.getStartSaleTime().isAfter(LocalDateTime.now()));
        if (allSessionsNotStartSale) {
            performanceVO.setStatus(PerformanceStatus.COMING_SOON);
        }

        return performanceVO;
    }

    private PerformanceSessionVO fullPerformanceSessionVO(PerformanceSession session) {
        PerformanceSessionVO sessionVO = new PerformanceSessionVO();
        BeanUtil.copyProperties(session, sessionVO);

        // 看场次时间是否未开始售票，是的话把场次状态设置为未开始售票
        if (session.getStartSaleTime().isAfter(LocalDateTime.now())) {
            sessionVO.setStatus(PerformanceStatus.COMING_SOON);
        }

        List<PerformanceTicket> tickets = performanceTicketService.getTicketsByPerformanceAndSession(session.getPerformanceId(), session.getId());
        // 过滤掉已下架的票档
        tickets = tickets.stream().filter(ticket -> !PerformanceStatus.OFFLINE.equals(ticket.getStatus())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(tickets)) {
            sessionVO.setTickets(tickets.stream().map(this::fullPerformanceTicketVO).collect(Collectors.toList()));

            // 如果场次时间是未开始售票，把票的状态设置为未开始售票
            if (PerformanceStatus.COMING_SOON.equals(sessionVO.getStatus())) {
                sessionVO.getTickets().forEach(ticket -> ticket.setStatus(PerformanceStatus.COMING_SOON));
            }
        }

        return sessionVO;
    }

    private PerformanceTicketVO fullPerformanceTicketVO(PerformanceTicket ticket) {
        PerformanceTicketVO ticketVO = new PerformanceTicketVO();
        BeanUtil.copyProperties(ticket, ticketVO);
        // todo: 后面再处理是否有库存
        ticketVO.setHasStock(true);
        return ticketVO;
    }
}
