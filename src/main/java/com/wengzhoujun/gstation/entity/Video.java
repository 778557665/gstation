package com.wengzhoujun.gstation.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2019/6/24.
 *
 * @author WengZhoujun
 */
@Data
@Builder
@Entity
@Table(name = "gs_us_video")
public class Video implements Serializable{

    private static final long serialVersionUID = 731668395179800137L;

    @Id
    @GeneratedValue(generator = "id_generator")
    @GenericGenerator(name = "id_generator", strategy = "redis_id")
    private Long id;

    private String videoPath;

    private String videoCoverPath;

    private String videoType;

    private String title;

    private Date createTime;

    public Video() {
    }
}
