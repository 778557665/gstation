package com.wengzhoujun.gstation.controller;

import com.wengzhoujun.gstation.cache.UserOnlineCache;
import com.wengzhoujun.gstation.domain.ErrorCode;
import com.wengzhoujun.gstation.entity.RequestKeys;
import com.wengzhoujun.gstation.entity.UserOnline;
import com.wengzhoujun.gstation.filter.AuthFilter;
import com.wengzhoujun.gstation.utils.ResponseUtil;
import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.domain.User;
import com.wengzhoujun.gstation.service.UserService;
import com.wengzhoujun.gstation.utils.PasswordUtil;
import com.wengzhoujun.gstation.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserOnlineCache userOnlineCache;

    @GetMapping("/signUp")
    public Result signUp(HttpServletRequest request,
                         @RequestParam String phone,
                         @RequestParam String password) throws Exception {
        User user = UserUtils.initUser(phone, request);
        String salt = PasswordUtil.salt(phone);
        user.setSalt(salt);
        user.setPassword(PasswordUtil.encode(salt, password));
        userService.save(user);
        return ResponseUtil.ok();
    }

    @GetMapping("/signIn")
    public Result signIn(HttpServletResponse response,
                         HttpServletRequest request,
                         @RequestParam String phone,
                         @RequestParam String password) throws Exception {
        User user = userService.findByPhone(phone);
        if (null == user) {
            return ResponseUtil.error(ErrorCode.USER_NOT_PRESENCE);
        }
        if (!PasswordUtil.isPasswordValid(user.getPassword(), password, user.getSalt())) {
            return ResponseUtil.error(ErrorCode.WRONG_PHONE_OR_WRONG_PASSWORD);
        }
        Cookie cookie = new Cookie(RequestKeys.USER_ID, user.getId().toString());
        response.addCookie(cookie);
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
        }
        session.setAttribute(RequestKeys.SESSION_UID, user.getId());
        session.setAttribute(RequestKeys.SESSION_NICKNAME, URLDecoder.decode(user.getNickname(), "UTF-8"));
        userOnlineCache.set(new UserOnline(user.getId(), session.getId(), System.currentTimeMillis()));
        return ResponseUtil.ok();
    }

    @GetMapping("/test")
    public Result test(HttpServletResponse response,
                       HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        Result outResult = ResponseUtil.ok("溜了");
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("userId".equals(cookie.getName())) {
                Long userId = Long.parseLong(cookie.getValue());
                UserOnline userOnline = userOnlineCache.get(userId);
                if(null != userOnline){
                    return ResponseUtil.ok("登录着呢");
                }
                return outResult;
            }else {
                return outResult;
            }
        }
        return ResponseUtil.ok();
    }

    @GetMapping("/needLogin")
    public Result needLogin() throws Exception {
        return ResponseUtil.error("need_login");
    }

    @GetMapping("/test1")
    public Result test1() throws Exception {
        return ResponseUtil.ok("login");
    }

    @GetMapping("/loginOut")
    public Result loginOut(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        AuthFilter.clearAuth(req, res);
        session.invalidate();
        return ResponseUtil.ok("login out!");
    }
}
