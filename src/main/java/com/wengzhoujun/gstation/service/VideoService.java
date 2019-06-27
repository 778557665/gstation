package com.wengzhoujun.gstation.service;

import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map; /**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
public interface VideoService {
    Result getPageByParams(Map<String, Object> params);

    Page<Video> getPage(Map<String, Object> params, Pageable pageable);

    void save(Video video1);
}
