package com.example.demo.service;

import com.example.demo.conf.RedisKeyPrefix;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class UserService {

    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    UserMapper userMapper;
    @Resource
    private RedisTemplate<String, User> redisTemplate;

    public User selectUserByName(String name){
        return userMapper.selectUserByName(name);
    }

    /**
     * 通过name查询，如果查询到则进行缓存
     * @param name 实体类name
     * @return 查询到的实现类
     */
    public User findUserByName(String name) {
        String key = RedisKeyPrefix.USER + name;
        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) { // 从缓存中取
            User user = redisTemplate.opsForValue().get(key);
            logger.info("从缓存中获取了用户！");
            return user;
        }
        // 从数据库取，并存回缓存
        User user = userMapper.selectUserByName(name);
        // 放入缓存，并设置缓存时间
        redisTemplate.opsForValue().set(key, user, 60, TimeUnit.SECONDS);
        return user;
    }

    /**
     * 更新用户
     * 如果缓存存在，删除
     * 如果缓存不存在，不操作
     *
     * @param user 用户
     */
    public void updateUser(User user) {
        logger.info("更新用户start...");
        userMapper.updateById(user);
        int userId = user.getId();
        // 缓存存在，删除缓存
        String key = "user_" + userId;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
            logger.info("更新用户时候，从缓存中删除用户 >> " + userId);
        }
    }

    /**
     * 删除用户
     * 如果缓存中存在，删除
     */
    public void deleteById(int id) {
        logger.info("删除用户start...");
        userMapper.deleteById(id);

        // 缓存存在，删除缓存
        String key = "user_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
            logger.info("删除用户时候，从缓存中删除用户 >> " + id);
        }
    }
}
