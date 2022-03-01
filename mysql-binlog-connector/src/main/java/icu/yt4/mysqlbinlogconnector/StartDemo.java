package icu.yt4.mysqlbinlogconnector;

import com.alibaba.fastjson.JSONObject;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yt
 * @date 2022/3/1 9:35
 * 功能说明
 */
@Slf4j
public class StartDemo {

    public static final Map<Long, String> TABLE_ID_MAPPING = new HashMap<>();


    public static void main(String[] args) {
        BinaryLogClient client = new BinaryLogClient("121.4.170.25", 3306, "yt4", "sbyt0726");
        client.setServerId(2);

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                System.out.println("Table:");
                TableMapEventData tableMapEventData = (TableMapEventData) data;
                System.out.println(tableMapEventData.getTableId()+": ["+tableMapEventData.getDatabase() + "-" + tableMapEventData.getTable()+"]");
                TABLE_ID_MAPPING.put(tableMapEventData.getTableId(), tableMapEventData.getTable());
            }
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update:");
                System.out.println(data.toString());
                String sqlFormat = "UPDATE {} SET {} WHERE {}";
                String dataJsonFormat = "{\"test\":1,\"name\":\"aaa\",\"desc\":\"bb\",\"timestamp\":\"2022-03-01 10:48:00.0\"}";
                // 其实就是类名
                String tableName = TABLE_ID_MAPPING.get(((UpdateRowsEventData) data).getTableId());
                // 这个是字段变化值相当于 key是变化前 value是变化后
                List<Map.Entry<Serializable[], Serializable[]>> rows = ((UpdateRowsEventData) data).getRows();
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
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Insert:");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete:");
                System.out.println(data.toString());
            }
        });

        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
