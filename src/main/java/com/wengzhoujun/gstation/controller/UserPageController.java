package com.wengzhoujun.gstation.controller;

import com.wengzhoujun.gstation.cache.UserOnlineCache;
import com.wengzhoujun.gstation.domain.ErrorCode;
import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.entity.RequestKeys;
import com.wengzhoujun.gstation.entity.User;
import com.wengzhoujun.gstation.entity.UserOnline;
import com.wengzhoujun.gstation.filter.AuthFilter;
import com.wengzhoujun.gstation.service.UserService;
import com.wengzhoujun.gstation.utils.PasswordUtil;
import com.wengzhoujun.gstation.utils.ResponseUtil;
import com.wengzhoujun.gstation.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Controller
@RequestMapping("/user")
public class UserPageController {

    @GetMapping("/toLogin")
    public String toLogin() throws Exception {
        return "login";
    }

}
