package test.demo.support;

import org.springframework.data.redis.core.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class RedisService {
    @Resource //没有@Resoure注解，stringRedisTemplate会报空指针异常
    private StringRedisTemplate stringRedisTemplate;  //StringRedisTemplate使用的是StringRedisSerializer

    @Resource //没有@Resoure注解，redisTemplate会报空指针异常
    private RedisTemplate<Object, Object> redisTemplate;            //RedisTemplate使用的是JdkSerializationRedisSerializer    存入数据会将数据先序列化成字节数组然后在存入Redis数据库。

    /**
     * @param  key
     * redis string 的键
     * @param  value
     * redis string 的值
     * @return boolean
     */
    public boolean setString(String key, String value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
        return true;
    }

    /**
     * @param  key
     * redis string 的键
     * @param  value
     * redis string 的值
     * @return boolean
     */
    public boolean setString(String key, Object value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, JSONObject.toJSONString(value));
        return true;
    }

    /**
     * 序列化存储String
     * @param  key
     * redis string 的键
     * @param  value
     * redis string 的值
     * @return boolean
     */
    public boolean setSerializeString(Object key, Object value) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        return true;
    }
    /**
     * @param  key
     * redis hash 的键
     * @param  map
     * redis hash 值的 字段：值 对map集合
     * @return boolean
     */
    public boolean pullAllHash(String key, Map<String, String> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * 序列化存储hash
     * @param  key
     * redis hash 的键
     * @param  map
     * redis hash 值的 字段：值 对map集合
     * @return boolean
     */
    public boolean pullSerializeMapToHash(Object key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * @param  key
     * redis hash 的键
     * @param  hk
     * redis 加入hash集合 的值的字段
     * @param  hv
     * redis 加入hash集合 的值的值
     * @return boolean
     */
    public boolean pullHash(String key, Object hk, Object hv) {
        stringRedisTemplate.opsForHash().put(key, hk, hv);
        return true;
    }

    /**
     * @param  key
     * redis hash 的键
     * @param  hk
     * redis hash 的值的字段
     * @return boolean
     */
    public boolean hasHashKey(String key, Object hk) {
        return stringRedisTemplate.opsForHash().hasKey(key, hk);
    }

    /**
     * 匹配获取序列化hash的键值对
     * @param key
     * @return Cursor
     */
    public Cursor<Map.Entry<Object, Object>> scanSerializeHash(String key) {
        return redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
    }
    /**
     * @param  key
     * redis hash 的键
     * @return Map
     */
    public Map<Object, Object> getEntries(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取序列化hash的所有字段和值
     * @param  key
     * redis hash 的键
     * @return Map
     */
    public Map<Object, Object> getEntriesSerializeHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @param  key
     * redis string 的键
     * @return String
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取序列化的String
     * @param  key
     * redis string 的键
     * @return String
     */
    public Object getSerializeString(Object key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * @param  key
     * redis string 的键
     * @return Object
     */
    public Object getObject(String key) {
        return JSONObject.parse(stringRedisTemplate.opsForValue().get(key));
    }

    public void deleteSerializeHashValue(String key, Object... Objects) {
        redisTemplate.opsForHash().delete(key, Objects);
    }

}
