package com.wengzhoujun.gstation.service;

import com.wengzhoujun.gstation.domain.User; /**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
public interface UserService {
    User save(User user);

    User findByPhone(String phone);
}
