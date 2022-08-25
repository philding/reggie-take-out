package com.dqf.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.dqf.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经登录
 *
 * @author qf.Ding
 * @date 2022/8/25 23:44
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1. 获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}" + requestURI);
        String[] urls = new String[]{"/employee/login", "/employee/logout", "/backend/**","/front/**"};
        //2. 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //3. check
        if (check) {
            log.info("本次请求 {}",requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        //4. 判断登录状态，如果已经登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已经登录，ID为 {}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }
        //5. 如果已经登录则返回登录结果,通过输出流的方式来响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

        return;


    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = antPathMatcher.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
