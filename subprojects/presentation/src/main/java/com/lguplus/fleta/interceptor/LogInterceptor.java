package com.lguplus.fleta.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class LogInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_TIME = "responseTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        MDC.put("saId", getSaIdParameter(request));
        MDC.put("stbMac", getStbMacParameter(request));
        log.info("[{}][{}][{}] Request", method, requestUri, queryString);
        return true;
    }

    private String getSaIdParameter(HttpServletRequest request) {
        String saId = request.getParameter("sa_id");
        if (StringUtils.hasText(saId)) {
            return saId;
        }
        saId = request.getParameter("SA_ID");
        if (StringUtils.hasText(saId)) {
            return saId;
        }
        return request.getParameter("saId");
    }

    private String getStbMacParameter(HttpServletRequest request) {
        String stbMac = request.getParameter("stb_mac");
        if (StringUtils.hasText(stbMac)) {
            return stbMac;
        }
        stbMac = request.getParameter("STB_MAC");
        if (StringUtils.hasText(stbMac)) {
            return stbMac;
        }
        return request.getParameter("stbMac");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        Long responseTime = (Long) request.getAttribute(RESPONSE_TIME);
        log.info("[{}][{}] Response {}ms", method, requestUri, responseTime);
        MDC.clear();
    }
}
