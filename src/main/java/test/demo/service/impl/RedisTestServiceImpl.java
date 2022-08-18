package test.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.stereotype.Service;
import test.demo.entity.RedisTest;
import test.demo.mapper.RedisTestMapper;
import test.demo.service.RedisTestService;
import test.demo.support.RedisService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor //这三个必加
public class RedisTestServiceImpl extends ServiceImpl<RedisTestMapper, RedisTest> implements RedisTestService {
    private final RedisService redisService;

    @Override
    public boolean persistenceHash(String key) {
        Cursor<Map.Entry<Object,Object>> cursor = redisService.scanSerializeHash(key);
        RedisTest redisTest = new RedisTest();
        redisTest.setHashKey(key);
        boolean haveData = false;
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> tmp = cursor.next();
            String field = (String) tmp.getKey();
            switch (field) {
                case "hash_value1" : {
                    redisTest.setHashValue1((String) tmp.getValue());
                    haveData = true;
                    redisService.deleteSerializeHashValue(key, tmp.getKey());
                    break;
                }
                case "hash_value2" : {
                    redisTest.setHashValue2((String) tmp.getValue());
                    haveData = true;
                    redisService.deleteSerializeHashValue(key, field);
                    break;
                }
                default : break;
            }
        }
        if(haveData){
            save(redisTest);
            log.warn("成功保存到数据库");
        }
        try {
            cursor.close();
        }catch (IOException e) {
            log.error("游标关闭异常：{}", e);
        }
        return true;
    }
}
