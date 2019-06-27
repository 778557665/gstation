package com.wengzhoujun.gstation.service.impl;

import com.wengzhoujun.gstation.domain.Result;
import com.wengzhoujun.gstation.entity.Video;
import com.wengzhoujun.gstation.repository.VideoRepository;
import com.wengzhoujun.gstation.service.VideoService;
import com.wengzhoujun.gstation.utils.DateUtils;
import com.wengzhoujun.gstation.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public Page<Video> getPage(Map<String, Object> params, Pageable pageable) {
        Page<Video> page = videoRepository.findAll((Specification<Video>) (Root<Video> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->{
            List<Predicate> list = new ArrayList<>();
            if(null != params.get("id")){
                list.add(criteriaBuilder.equal(root.get("id").as(Long.class), params.get("id")));
            }
            if(null != params.get("title")){
                list.add(criteriaBuilder.like(root.get("title").as(String.class), params.get("title").toString()));
            }
            if(null != params.get("createTimeStrat")){
                list.add(criteriaBuilder.greaterThan(root.get("createTime").as(Date.class), DateUtils.strToDate(params.get("createTimeStrat").toString())));
            }
            if(null != params.get("createTimeEnd")){
                list.add(criteriaBuilder.lessThan(root.get("createTime").as(Date.class), DateUtils.strToDate(params.get("createTimeEnd").toString())));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return criteriaQuery.where(list.toArray(predicates)).getRestriction();
            }, pageable);
        return page;
    }

    @Override
    public void save(Video video1) {
        videoRepository.save(video1);
    }
}
