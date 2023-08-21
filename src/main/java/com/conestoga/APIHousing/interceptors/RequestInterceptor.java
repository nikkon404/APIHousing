package com.conestoga.APIHousing.interceptors;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {

  Logger logger = Logger.getLogger(RequestInterceptor.class.getName());

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    String apiPath = request.getRequestURI();

    logger.info("------------------");
    // print path and method and other info
    logger.info(
        "API Path: " + apiPath + " - " + request.getMethod() + " - " + response.getStatus());
    // print agent
    logger.info("Agent: " + request.getHeader("User-Agent"));
    // print IP
    logger.info("IP: " + request.getRemoteAddr());

    return true;
  }
}
