package icu.yt4.mysqlbinlogconnector.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import icu.yt4.mysqlbinlogconnector.BinlogPositionEntity;
import icu.yt4.mysqlbinlogconnector.SyncConfig;
import icu.yt4.mysqlbinlogconnector.Test;
import icu.yt4.mysqlbinlogconnector.config.DbConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author yt
 * @date 2022/3/1 13:13
 * 功能说明
 */
@Component
@Slf4j
public class MysqlBinlogEventListener {

    @Autowired
    IPositionHandler iPositionHandler;
    @Autowired
    DbConfig dbConfig;

    private static SyncConfig syncConfig;
    private static IPositionHandler positionHandler;

    @PostConstruct
    public void init() {
        if (syncConfig == null) {
            syncConfig = new SyncConfig();
            syncConfig.setHost(dbConfig.getHost());
            syncConfig.setPort(dbConfig.getPort());
            syncConfig.setUserName(dbConfig.getUsername());
            syncConfig.setPassword(dbConfig.getPassword());
        }
        if (positionHandler == null) {
            positionHandler = iPositionHandler;
        }
    }

    public static void start() throws IOException {
        BinaryLogClient client = new BinaryLogClient(syncConfig.getHost(), syncConfig.getPort(), syncConfig.getUserName(), syncConfig.getPassword());
        EventDeserializer eventDeserializer = new EventDeserializer();
        //时间反序列化的格式
//        eventDeserializer.setCompatibilityMode(
//                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
//        );
        client.setEventDeserializer(eventDeserializer);
        //设置serverId，不同的集群，机器的serverId不能相同。
        client.setServerId(2);
        //获取position的位置（创建client时，读取当前记录的postion）
        BinlogPositionEntity binlogPositionEntity = positionHandler.getPosition(syncConfig);
        if (binlogPositionEntity != null &&
                binlogPositionEntity.getBinlogName() != null &&
                binlogPositionEntity.getPosition() != null) {
            client.setBinlogFilename(binlogPositionEntity.getBinlogName());
            client.setBinlogPosition(binlogPositionEntity.getPosition());
        }

        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {

                EventHeader header = event.getHeader();

                EventType eventType = header.getEventType();
                System.out.println("监听的事件类型:" + eventType);

                /*
                 * 不计入position更新的事件类型
                 * FORMAT_DESCRIPTION类型为binlog起始时间
                 * HEARTBEAT为心跳检测事件，不会写入master的binlog，记录该事件的position会导致重启时报错
                 */
                List<EventType> excludePositionEventType = new ArrayList<>();
                excludePositionEventType.add(EventType.FORMAT_DESCRIPTION);
                excludePositionEventType.add(EventType.HEARTBEAT);
                if (!excludePositionEventType.contains(eventType)) {
                    BinlogPositionEntity binlogPositionEntity = new BinlogPositionEntity();
                    //处理rotate事件,这里会替换调binlog fileName
                    if (event.getHeader().getEventType().equals(EventType.ROTATE)) {
                        RotateEventData rotateEventData = (RotateEventData) event.getData();
                        binlogPositionEntity.setBinlogName(rotateEventData.getBinlogFilename());
                        binlogPositionEntity.setPosition(rotateEventData.getBinlogPosition());
                        binlogPositionEntity.setServerId(event.getHeader().getServerId());
                    } else { //统一处理事件对应的binlog position
                        //在Redis中获取获取binlog的position配置
                        binlogPositionEntity = positionHandler.getPosition(syncConfig);
                        EventHeaderV4 eventHeaderV4 = (EventHeaderV4) event.getHeader();
                        binlogPositionEntity.setPosition(eventHeaderV4.getPosition());
                        binlogPositionEntity.setServerId(event.getHeader().getServerId());
                    }
                    //将最新的配置保存到Redis中
                    log.info("保存的数据{}", JSON.toJSONString(binlogPositionEntity));
                    positionHandler.savePosition(syncConfig, binlogPositionEntity);
                }

                //解析
                if (EventType.isWrite(eventType)) {
                    //对应insert
                    WriteRowsEventData data = event.getData();
                    log.info(JSON.toJSONString(data));
                } else if (EventType.isUpdate(eventType)) {
                    //对应update
                    UpdateRowsEventData data = event.getData();
                    log.info(JSON.toJSONString(data));
                    System.out.println("Update:");
                    System.out.println(data.toString());
                    String sqlFormat = "UPDATE {} SET {} WHERE {}";
                    String dataJsonFormat = "{\"test\":1,\"name\":\"aaa\",\"desc\":\"bb\",\"timestamp\":\"2022-03-01 10:48:00.0\"}";
                    // 其实就是类名
                    // 这个是字段变化值相当于 key是变化前 value是变化后
                    List<Map.Entry<Serializable[], Serializable[]>> rows = data.getRows();
                    log.info("update affect rows:{}", rows.size());
                    JSONObject jsonObject = JSONObject.parseObject(dataJsonFormat);
                    Object[] keys = jsonObject.keySet().toArray();
                    for (Map.Entry<Serializable[], Serializable[]> row : rows) {
                        Object[] values = Arrays.stream(row.getValue()).toArray();
                        boolean match = keys.length == values.length;
                        System.out.println("k-v match?=" + match);
                        if (match) {
                            jsonObject = new JSONObject();
                            for (int i = 0; i < keys.length; i++) {
                                jsonObject.put(String.valueOf(keys[i]), values[i]);
                            }
                            log.info("更新后的json:{} model:{}", jsonObject, jsonObject.toJavaObject(Test.class));
                        }
                    }
                } else if (EventType.isDelete(eventType)) {
                    //对应delete
                    DeleteRowsEventData data = event.getData();
                    log.info(JSON.toJSONString(data));
                }
            }
        });
        client.connect();
    }
}
