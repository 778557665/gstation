package com.wengzhoujun.gstation.repository;

import com.wengzhoujun.gstation.entity.User;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
public interface UserRepository extends CommonRepository<User, Long> {
    User findByPhone(String phone);
}
