package com.wengzhoujun.gstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() throws Exception {
        return "index";
    }

    @GetMapping("/")
    public String index2() throws Exception {
        return "index";
    }

}
