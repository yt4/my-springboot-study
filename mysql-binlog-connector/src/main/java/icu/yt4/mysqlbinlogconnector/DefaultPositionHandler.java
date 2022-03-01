package icu.yt4.mysqlbinlogconnector;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yt
 * @date 2022/3/1 11:16
 * 功能说明
 */
public class DefaultPositionHandler implements IPositionHandler {

    private Map<String, String> cache = new ConcurrentHashMap<>();

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public BinlogPositionEntity getPosition(SyncConfig syncConfig) {
        return JSON.parseObject(cache.get(generateKey(syncConfig)), BinlogPositionEntity.class);
    }

    @Override
    public void savePosition(SyncConfig syncConfig, BinlogPositionEntity binlogPositionEntity) {
        cache.put(generateKey(syncConfig), JSON.toJSONString(binlogPositionEntity));
    }


    /**
     * @param syncConfig 参数配置
     * @return 生成的key
     */
    private String generateKey(SyncConfig syncConfig) {
        return syncConfig.getHost() + ":" + syncConfig.getPort();
    }

}
