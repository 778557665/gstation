package com.wengzhoujun.gstation.controller;

import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    public Result getPage(@PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return videoService.getPage(pageable);
    }
}
