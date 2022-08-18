package test.demo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 *     redis测试DTO
 * </p>
 * @author zhongchujie
 * @since 2022-8-9
 * */

@Data
@Accessors(chain = true)
public class RedisTestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String stringValue;

    private Map<Object, Object> hashMap;
}
