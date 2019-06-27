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
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
@Data
@Builder
@Entity
@Table(name = "gs_us_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "id_generator")
    @GenericGenerator(name = "id_generator", strategy = "redis_id")
    private Long id;

    private String nickname;

    private String password;

    private String salt;

    private String phone;

    private Integer gender;

    private Byte status;

    private String avatar;

    private Date createTime;

    private Date updateTime;

    private String regIp;

    public User() {
    }

    public User(String phone, String nickname, Byte status, Date createTime, String regIp) {
        this.phone = phone;
        this.nickname = nickname;
        this.status = status;
        this.createTime = createTime;
        this.regIp = regIp;
    }

    public enum StatusEnum {

        NORMAL((byte) 0, "正常"),
        FREEZING((byte) 1, "冻结");

        private static Map<Byte, StatusEnum> map = new HashMap<>();

        static {
            for (StatusEnum statusEnum : StatusEnum.values()) {
                map.put(statusEnum.getCode(), statusEnum);
            }
        }

        private Byte code;
        private String desc;

        StatusEnum(Byte code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static StatusEnum getEnumByCode(Byte code) {
            return map.get(code);
        }

        public Byte getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

}
