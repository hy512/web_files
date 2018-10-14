package me.web.files.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Encoding implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        chain.doFilter(req, resp);
    }
    public void destroy() {
        
    }
}