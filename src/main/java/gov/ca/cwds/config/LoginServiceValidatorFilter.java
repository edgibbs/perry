package gov.ca.cwds.config;


import gov.ca.cwds.rest.api.domain.PerryException;
import gov.ca.cwds.service.WhiteList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

import static gov.ca.cwds.config.Constants.CALLBACK_PARAM;
import static gov.ca.cwds.config.Constants.LOGIN_SERVICE_URL;

/**
 * Created by dmitry.rudenko on 9/14/2017.
 */
@Component
public class LoginServiceValidatorFilter extends GenericFilterBean {
  private WhiteList whiteList;

  private RequestMatcher requestMatcher = new AntPathRequestMatcher(LOGIN_SERVICE_URL);

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    if (requestMatcher.matches(httpServletRequest)) {
      validate(httpServletRequest);
      Logger.getLogger(this.getClass().getName()).fine("LOGIN SERVICE: Authentication process is started");
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  protected void validate(HttpServletRequest servletRequest) {
    required(CALLBACK_PARAM, servletRequest);
    registered(CALLBACK_PARAM, servletRequest);
  }

  protected void required(String name, HttpServletRequest servletRequest) {
    if (StringUtils.isBlank(servletRequest.getParameter(name))) {
      throw new PerryException("parameter is required: " + name);
    }
  }

  @Autowired
  public void setWhiteList(WhiteList whiteList) {
    this.whiteList = whiteList;
  }

  private void registered(String param, HttpServletRequest servletRequest) {
    String paramValue = servletRequest.getParameter(param);
    whiteList.validate(param, paramValue);
  }

  public void setRequestMatcher(RequestMatcher requestMatcher) {
    this.requestMatcher = requestMatcher;
  }
}
