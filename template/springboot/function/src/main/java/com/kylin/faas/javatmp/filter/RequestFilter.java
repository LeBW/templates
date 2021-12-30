package com.kylin.faas.javatmp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(filterName = "requestWrapperFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(RequestFilter.class);

    public static final String TRACE_ID = "trace";
    public static final String SPAN_ID = "span";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String remoteAddr = httpRequest.getRemoteAddr();
        String method = httpRequest.getMethod();

        String requestStr = httpRequest.getParameterMap().toString();
        ActiveSpan.tag("params", requestStr);
        log.info("url:{} , method:{}, clientIp:{}, params:{}", uri, method, remoteAddr, requestStr);

        chain.doFilter(request, response);
    }
}