package test.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.demo.entity.RedisTest;

public interface RedisTestService extends IService<RedisTest> {
    boolean persistenceHash(String key);
}
