package com.wengzhoujun.gstation.service.impl;

import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.entity.Video;
import com.wengzhoujun.gstation.repository.VideoRepository;
import com.wengzhoujun.gstation.service.VideoService;
import com.wengzhoujun.gstation.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Result getPageByParams(Map<String, Object> params) {
        return null;
    }

    @Override
    public Result getPage(Pageable pageable) {
        Page<Video> page = videoRepository.findAll((Specification<Video>) (Root<Video> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->{return null;}, pageable);
        return ResponseUtil.ok(page);
    }
}
