package test.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestTableResponseVO {
    private long id;
//    private String name;

    @JsonProperty("englishName")
    private String testName;

    @JsonProperty("chineseName")
    private String name;

    @JsonProperty("介绍")
    private String introduction;
}
