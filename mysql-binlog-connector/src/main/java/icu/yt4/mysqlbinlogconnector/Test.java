package icu.yt4.mysqlbinlogconnector;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author yt
 * @date 2022/3/1 10:13
 * 功能说明
 */
@Data
public class Test {

    @JSONField(ordinal = 0)
    private Long test;
    @JSONField(ordinal = 1)
    private String name;
    @JSONField(ordinal = 2)
    private String desc;
    @JSONField(ordinal = 3)
    private Date timestamp;
}
