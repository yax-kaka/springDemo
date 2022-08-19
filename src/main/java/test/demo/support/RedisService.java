package test.demo.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
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
    public List<Map.Entry<Object,Object>> scanSerializeHash(String key) {
        List<Map.Entry<Object,Object>> result = new ArrayList<>();
        try {
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();
                result.add(map);
            }
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

    public List<Object> getSerializeKeys(String hashKey, String filed){
        long start = System.currentTimeMillis();
        //需要匹配的key
        String patternKey =  "".equals(filed) ? "*" : "xx_1";
        ScanOptions options = ScanOptions.scanOptions()
                //这里指定每次扫描key的数量(很多博客瞎说要指定Integer.MAX_VALUE，这样的话跟        keys有什么区别？)
                .count(10000)
                .match(patternKey).build();
//        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
//        Cursor cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(hashKey+"*", options);
        List<Object> result = new ArrayList<>();
        while(cursor.hasNext()){
            result.add(cursor.next());
        }
        //切记这里一定要关闭，否则会耗尽连接数。报Cannot get Jedis connection; nested exception is redis.clients.jedis.exceptions.JedisException: Could not get a
        try{
            cursor.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        log.info("scan扫描共耗时：{} ms key数量：{}",System.currentTimeMillis()-start,result.size());
        return result;
    }
}
