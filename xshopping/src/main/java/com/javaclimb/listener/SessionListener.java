package com.javaclimb.listener;

import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 通过session监听在线人数
 */
@WebListener
 public class SessionListener implements HttpSessionListener {
    private int onlineCount = 0;
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if (onlineCount > 0) {
            onlineCount--;
        }
        //将最新的onlineCount值存起来
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
   }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        onlineCount++;
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
    }

}
