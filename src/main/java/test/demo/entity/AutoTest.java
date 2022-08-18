package test.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("auto_test")
public class AutoTest implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("hash_key")
    private String hashKey;

    @TableField("redis_string")
    private  String redisString;

    @TableField("hash_value1")
    private String hashValue1;

    @TableField("hash_value2")
    private String hashValue2;
}
