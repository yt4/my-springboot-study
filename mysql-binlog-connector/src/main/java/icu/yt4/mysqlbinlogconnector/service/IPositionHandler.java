package icu.yt4.mysqlbinlogconnector.service;

import icu.yt4.mysqlbinlogconnector.BinlogPositionEntity;
import icu.yt4.mysqlbinlogconnector.SyncConfig;
import icu.yt4.mysqlbinlogconnector.config.DbConfig;

/**
 * @author yt
 * @date 2022/3/1 11:15
 * 功能说明
 */
public interface IPositionHandler {

    BinlogPositionEntity getPosition(SyncConfig dbConfig);

    void savePosition(SyncConfig dbConfig, BinlogPositionEntity binlogPositionEntity);

}
