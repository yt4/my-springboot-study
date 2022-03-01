package icu.yt4.mysqlbinlogconnector;

/**
 * @author yt
 * @date 2022/3/1 11:15
 * 功能说明
 */
public interface IPositionHandler {

    BinlogPositionEntity getPosition(SyncConfig syncConfig);

    void savePosition(SyncConfig syncConfig, BinlogPositionEntity binlogPositionEntity);

}
