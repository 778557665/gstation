package com.wengzhoujun.gstation.service;

import com.wengzhoujun.gstation.domain.Result;
import org.springframework.data.domain.Pageable;

import java.util.Map; /**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
public interface VideoService {
    Result getPageByParams(Map<String, Object> params);

    Result getPage(Pageable pageable);
}
