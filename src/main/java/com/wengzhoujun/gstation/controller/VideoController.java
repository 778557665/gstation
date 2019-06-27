package com.wengzhoujun.gstation.controller;

import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.entity.Video;
import com.wengzhoujun.gstation.service.JSoupService;
import com.wengzhoujun.gstation.service.VideoService;
import com.wengzhoujun.gstation.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private JSoupService jSoupService;

    @GetMapping("/getPage")
    public String getPage(HttpServletRequest request,
                          @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                          @RequestParam(required = false) String title) {
        Map<String, Object> map = new HashMap<>(1);
        if(StringUtils.isNotBlank(title)){
            map.put("title", title);
        }
        Page<Video> page = videoService.getPage(map, pageable);
        request.setAttribute("video", page.getContent());
        return "index";
    }

    @GetMapping("get")
    @ResponseBody
    public Result getVideo() throws IOException {
        jSoupService.crawlingPearVideo();
        return ResponseUtil.ok();
    }
}
