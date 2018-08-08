package com.cloudhelios.olympus.security.client.ip.whitelist.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author markfredchen
 * @date 2018/8/3
 */
@Component
public class ClientIPWhiteListFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SecurityContextHolder.getContext().getAuthentication();
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
