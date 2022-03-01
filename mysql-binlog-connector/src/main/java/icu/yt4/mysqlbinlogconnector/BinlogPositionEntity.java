package icu.yt4.mysqlbinlogconnector;

import lombok.Data;

/**
 * @author yt
 * @date 2022/3/1 11:15
 * 功能说明
 */
@Data
public class BinlogPositionEntity {
    /**
     * binlog文件的名字
     */
    private String binlogName;
    /**
     * binlog文件的位置
     */
    private Long position;
    /**
     * binlog的服务id
     */
    private Long serverId;
}
