package test.demo.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import test.demo.service.RedisTestService;
import test.demo.support.RedisService;
import test.demo.support.ReturnResponse;
import test.demo.util.IpUtil;
import test.demo.vo.RedisTestDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * redis测试接口
 * @author zhongchujie
 * @since 2022-8-9
 */

@Slf4j
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisTestController {

    private final RedisService redisService;

    private final RedisTestService redisTestService;
    /**
     * 保存redis hash数据
     * @param redisTestDTO
     * @return ReturnResponse
     */
    @PostMapping("/setSerializeHash")
    public ReturnResponse setSerializeHash(HttpServletRequest httpServletRequest, @RequestBody RedisTestDTO redisTestDTO) {
        String ip = IpUtil.getIp(httpServletRequest);
        redisService.pullSerializeMapToHash(redisTestDTO.getKey(),redisTestDTO.getHashMap());
        return ReturnResponse.makeOkMessage();
    }

    /**
     * 获取redis hash数据
     * @param key
     * @return ReturnResponse
     */
    @GetMapping("/getSerializeHash")
    public ReturnResponse getSerializeHash(@RequestParam("key") String key) {
        JSONObject result = new JSONObject();
        result.put("hashMap", redisService.getEntriesSerializeHash(key));
        return ReturnResponse.makeOkMessage(result);
    }

    /**
     * 持久化hash数据
     * @return
     */
    @GetMapping("/persistenceHash")
    public ReturnResponse persistenceHash(@RequestParam("key") String key) {
        return redisTestService.persistenceHash(key) ? ReturnResponse.makeOkMessage() : ReturnResponse.makeFailMessage("寄");
    }

    @GetMapping("/autoPersistenceHash")
    @Scheduled(fixedDelay = 60*1000)
    public ReturnResponse autoPersistenceHash(){
        log.warn("自动存储");
        return redisTestService.persistenceHash("auto") ? ReturnResponse.makeOkMessage() : ReturnResponse.makeFailMessage("寄");
    }
}
