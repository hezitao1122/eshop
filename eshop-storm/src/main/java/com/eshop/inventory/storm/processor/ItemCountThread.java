package com.eshop.inventory.storm.processor;


import com.alibaba.fastjson.JSONArray;
import com.eshop.inventory.storm.config.KafkaConstant;
import com.eshop.inventory.storm.zk.ZooKeeperSession;
import com.eshop.inventory.util.SoltUtil;
import org.apache.storm.trident.util.LRUMap;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author zeryts
 * @description: 用于向zookeeper交互的类
 * ```````````````````````````
 * @title: ItemCountThread
 * @projectName inventory
 * @date 2019/9/21 15:46
 */
public class ItemCountThread implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 需要传入一个Map
     */
    private LRUMap<Long, Long> countMap;

    /**
     * 和一个zookeeper工具类
     */
    private ZooKeeperSession session;

    /**
     * 任务的id
     */
    private int taskId;

    public ItemCountThread(LRUMap<Long, Long> countMap, ZooKeeperSession session, int taskId) {
        this.countMap = countMap;
        this.session = session;
        this.taskId = taskId;
    }

    public void run() {

//        List<Map.Entry<Long,Long>> topList = new ArrayList<Map.Entry<Long, Long>>();


        while (true) {

            Map<Long, Long> resourceMap = new LinkedHashMap<>(countMap);
            log.info("[ItemCountThread打印topNItemList的长度] size ->[{}]", resourceMap.size());
            if (resourceMap == null || resourceMap.isEmpty()) {
                Utils.sleep(100);
                continue;
            }
            Map<Long, Long> topNMap = SoltUtil.soltByValue(resourceMap, false);
            List<Map<String, Long>> topNList = new ArrayList<>();

            topNMap.forEach((k, v) -> {
                Map<String, Long> map = new HashMap<>();
                map.put("key", k);
                map.put("value", v);
                topNList.add(map);
            });
            String str = JSONArray.toJSONString(topNList);
            log.info("[ItemCountThread计算出一份topN热门商品数据]， TopN信息为-》[" + str + "]");
            session.setNodeData("/" + KafkaConstant.TASK_HOT + taskId, str);
            log.info("task hot node taskId -> [{}] , value -> [{}]", taskId, str);
            //间隔性的
            Utils.sleep(60000);
        }
    }


    public static void main(String[] args) {
        Map<Integer, Integer> re = new HashMap<>();
        Random r = new Random(1999);
        for (int i = 0; i < 100; i++)
            re.put(i, r.nextInt(199));

        Map<Integer, Integer> map = SoltUtil.soltByValue(re, false);

        map.forEach((k, v) -> System.out.println("k = " + k + ", v = " + v));

    }
}
