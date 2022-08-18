package test.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * <p>
 *     测试表
 * </p>
 * @author zhongchujie
 * @since 2022-8-5
 * */

@Data
@Accessors(chain = true)
public class TestTableDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    @NotBlank(message = "名字不能为空")
    private String name;

    private String testName;

    private String introduction;
}
