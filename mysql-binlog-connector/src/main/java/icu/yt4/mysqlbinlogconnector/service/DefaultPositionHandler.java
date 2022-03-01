package icu.yt4.mysqlbinlogconnector.service;

import com.alibaba.fastjson.JSON;
import icu.yt4.mysqlbinlogconnector.BinlogPositionEntity;
import icu.yt4.mysqlbinlogconnector.SyncConfig;
import icu.yt4.mysqlbinlogconnector.config.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yt
 * @date 2022/3/1 11:16
 * 功能说明
 */
@Component
public class DefaultPositionHandler implements IPositionHandler {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Override
    public BinlogPositionEntity getPosition(SyncConfig dbConfig) {
        String s = stringRedisTemplate.opsForValue().get(generateKey(dbConfig));
        return JSON.parseObject(s, BinlogPositionEntity.class);
    }

    @Override
    public void savePosition(SyncConfig dbConfig, BinlogPositionEntity binlogPositionEntity) {
        stringRedisTemplate.opsForValue().set(generateKey(dbConfig), JSON.toJSONString(binlogPositionEntity));
    }


    /**
     * @param dbConfig 参数配置
     * @return 生成的key
     */
    private String generateKey(SyncConfig dbConfig) {
        return dbConfig.getHost() + ":" + dbConfig.getPort();
    }

}
