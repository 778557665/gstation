package com.wengzhoujun.gstation.filter;

import com.wengzhoujun.gstation.entity.RequestKeys;
import com.wengzhoujun.gstation.utils.UrlUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
@Component
public class AuthFilter implements Filter {

    private String[] noAuthKeys = new String[]{"/user/signIn", "/user/needLogin"};

    public static final String TEMP_COOKIE = "TEMP_COOKIE_";

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String reqUrl = req.getRequestURI();
        if (isAuthUrl(reqUrl)) {
            filterChain.doFilter(req, res);
            return;
        }
        String sessionId = getFromCookie(req, RequestKeys.SESSION_UID);
        if (isExpired(sessionId)) {
            redirectLogin(res);
        }
        filterChain.doFilter(req, res);
    }

    private boolean isAuthUrl(String path) {
        //根路径不用处理
        if ("".equals(path) || "/".equals(path)) {
            return true;
        }
        for (String noAuthKey : noAuthKeys) {
            if (path.contains(noAuthKey)) {
                return true;
            }
        }
        return false;
    }

    private void redirectLogin(HttpServletResponse res) throws IOException {
        res.sendRedirect("/user/needLogin");
    }

    @Override
    public void destroy() {

    }

    private String getFromCookie(HttpServletRequest req, String key) {
        Object attriValue = req.getRequestedSessionId();
        if (attriValue != null) {
            return attriValue.toString();
        }
        Cookie[] cs = req.getCookies();
        if (cs != null && cs.length > 0) {
            for (Cookie c : cs) {
                if (key.equalsIgnoreCase(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    private boolean isExpired(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return true;
        }
        Session session = sessionRepository.findById(sessionId);
        if (null != session) {
            return false;
        }
        return true;
    }

    public static void clearAuth(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession(false);
        if (null != session) {
            session.removeAttribute(RequestKeys.SESSION_UID);
            session.removeAttribute(RequestKeys.SESSION_NICKNAME);
        }
        setCookie(req, res, RequestKeys.USER_ID, "", 1);
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
        value = UriUtils.encode(value, "UTF-8");
        Cookie cookie = new Cookie(name, value);
        String topDomain = UrlUtils.getTopDomainWithoutSubDomain(request.getRequestURL().toString());
        if (StringUtils.isNotBlank(topDomain)) {
            cookie.setDomain(topDomain);
        }
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
        //某些情况下cooikie还没来得及返回到浏览器程序就要使用，临时存放到请求中，是的第一次请求能够获取到相关的属性
        request.setAttribute(TEMP_COOKIE + name, value);
    }

}
