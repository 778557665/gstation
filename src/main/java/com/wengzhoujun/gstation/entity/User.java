package com.wengzhoujun.gstation.entity;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
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
