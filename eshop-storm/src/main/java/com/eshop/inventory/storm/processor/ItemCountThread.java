package com.eshop.inventory.storm.processor;


import com.eshop.inventory.util.SoltUtil;
import org.apache.storm.trident.util.LRUMap;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author zeryts
 * @description: 用于向zookeeper交互的类
 * ```````````````````````````
 * @title: ItemCountThread
 * @projectName inventory
 * @date 2019/9/21 15:46
 */
public class ItemCountThread implements Runnable{
    /**
     * 需要传入一个Map
     */
    private LRUMap<Long,Long> countMap ;

    public ItemCountThread( LRUMap<Long,Long> countMap){
         this.countMap = countMap;
    }

    public void run() {

//        List<Map.Entry<Long,Long>> topList = new ArrayList<Map.Entry<Long, Long>>();
        Map<Long,Long> resourceMap = new LinkedHashMap<>(countMap);

        while(true){
            Map<Long, Long> topNMap = SoltUtil.soltByValue(resourceMap, false);


            //间隔性的
            Utils.sleep(60000);
        }
    }


    public static void main(String[] args) {
        Map<Integer,Integer> re = new HashMap<>();
        Random r = new Random(1999);
        for(int i =0; i< 100; i++)
            re.put(i,r.nextInt(199));

        Map<Integer, Integer> map= SoltUtil.soltByValue(re, false);

        map.forEach((k,v)-> System.out.println("k = "+ k + ", v = " + v));


    }
}
