package com.wengzhoujun.gstation.domain;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 5919976584399575644L;

    @Transient
    private JSONObject other = new JSONObject();

    public BaseDomain() {
    }

    public JSONObject getOther() {
        return this.other;
    }

    public void setOther(JSONObject other) {
        this.other = other;
    }
}
