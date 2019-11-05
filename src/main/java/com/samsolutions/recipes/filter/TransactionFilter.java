package com.samsolutions.recipes.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Transaction filter.
 *
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Component
@Order(1)
@Log4j2
public class TransactionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        log.info("Starting a transaction for req : {}", req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("Committing a transaction for req : {}", req.getRequestURI());
    }
}
