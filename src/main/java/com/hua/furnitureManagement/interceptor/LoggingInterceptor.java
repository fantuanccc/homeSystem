package com.hua.furnitureManagement.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前执行
        System.out.println("请求URL: " + request.getRequestURL());
        System.out.println("请求方法: " + request.getMethod());
        System.out.println("请求时间: " + System.currentTimeMillis());

        // 返回true表示继续处理请求，返回false则表示中断请求处理
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后，视图渲染之前执行
        System.out.println("请求完成时间: " + System.currentTimeMillis());
        if (modelAndView != null) {
            System.out.println("视图名称: " + modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求处理完成，视图渲染之后执行
        System.out.println("请求处理完成时间: " + System.currentTimeMillis());
        if (ex != null) {
            System.out.println("请求处理中发生异常: " + ex.getMessage());
        }
    }
}
