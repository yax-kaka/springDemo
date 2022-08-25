package test.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("测试表传输层实体")
public class TestTableDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private long id;

    @ApiModelProperty("名称")
    @NotBlank(message = "名字不能为空")
    private String name;

    @ApiModelProperty("测试名称（没什么意义）")
    private String testName;

    @ApiModelProperty("介绍")
    private String introduction;
}
