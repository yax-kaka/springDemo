package test.demo.controller;

import org.apache.ibatis.annotations.Param;
import test.demo.service.LocalTestService;
import test.demo.support.RedisService;
import test.demo.vo.TestTableDTO;
import test.demo.vo.TestTableRequestVO;
import test.demo.support.ReturnResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 测试接口
 */

@RestController
@RequestMapping("/localTest")
@RequiredArgsConstructor
public class LocalTestController {

    private final LocalTestService localTestService;

    private final RedisService redisService;

    @RequestMapping("/hello")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/testTable/{id}")
    public ReturnResponse testList(TestTableRequestVO requestVO) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", "1");
        return ReturnResponse.makeOkMessage(localTestService.pageList(requestVO));
    }
    @PostMapping("/testTable2/")
    public ReturnResponse testPostList(TestTableRequestVO requestVO) {
        return ReturnResponse.makeOkMessage(localTestService.pageList(requestVO));
    }

    @PostMapping("/addRecord")
    public ReturnResponse addRecord(@RequestBody TestTableDTO testTableDTO) {
        boolean result = localTestService.addRecord(testTableDTO);
        return result ? ReturnResponse.makeOkMessage() : ReturnResponse.makeFailMessage("保存失败");
        //System.console().printf(testTableDTO);
        //return ReturnResponse.makeFailResponse("保存失败");
    }

    @PostMapping("/setRedisString")
    public ReturnResponse setRedisData(@RequestBody TestTableDTO testTableDTO) {
        boolean result = redisService.setString(testTableDTO.getName(), testTableDTO.getTestName());
        return result ? ReturnResponse.makeOkMessage() : ReturnResponse.makeFailMessage("保存失败");
    }

    @PostMapping("/setRedisSerializeString")
    public ReturnResponse setRedisSerializeString(@RequestBody TestTableDTO testTableDTO) {
        boolean result = redisService.setSerializeString(testTableDTO.getName(), testTableDTO);
        return result ? ReturnResponse.makeOkMessage() : ReturnResponse.makeFailMessage("保存失败");
    }

    @GetMapping("/getRedisString")
    public ReturnResponse getRedisString(@RequestParam("key") String key) {
        String value = redisService.getString(key);
        return ReturnResponse.makeOkMessage(value);
    }

    @GetMapping("/getRedisSerializeString")
    public ReturnResponse getRedisSerializeString(@RequestParam("key") String key) {
        Object value = redisService.getSerializeString(key);
        return ReturnResponse.makeOkMessage(value);
    }

    @PostMapping("/setRedisObject")
    public ReturnResponse setRedisObject(@RequestBody TestTableDTO testTableDTO) {
        boolean result = redisService.setString(testTableDTO.getName(),testTableDTO);
        return result ? ReturnResponse.makeOkMessage() : ReturnResponse.makeFailMessage("保存失败");
    }

    @GetMapping("/getRedisObject")
    public ReturnResponse getRedisObject(@RequestParam("key") String key) {
        Object value = redisService.getObject(key);
        return ReturnResponse.makeOkMessage(value);
    }

    @PostMapping("/setRedisHash")
    public ReturnResponse setRedisHash(@RequestBody TestTableDTO testTableDTO) {
        Map<String, String> map = new HashMap<>();
        map.put(String.valueOf(testTableDTO.getId()),testTableDTO.getName());
        map.put(testTableDTO.getTestName(),testTableDTO.getIntroduction());
        if(redisService.pullAllHash(String.valueOf(testTableDTO.getId()), map)) {
            return ReturnResponse.makeOkMessage(redisService.getEntries(String.valueOf(testTableDTO.getId())));
        }
        return ReturnResponse.makeFailMessage("寄");
    }


}
