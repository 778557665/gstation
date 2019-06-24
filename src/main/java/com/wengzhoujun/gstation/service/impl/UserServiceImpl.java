package com.wengzhoujun.gstation.service.impl;

import com.wengzhoujun.gstation.entity.User;
import com.wengzhoujun.gstation.repository.UserRepository;
import com.wengzhoujun.gstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2019/6/19.
 *
 * @author WengZhoujun
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
       return userRepository.save(user);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
