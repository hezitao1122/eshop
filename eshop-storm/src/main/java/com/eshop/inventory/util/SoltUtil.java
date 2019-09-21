package com.eshop.inventory.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author zeryts
 * @description: 排序操作的工具类
 * ```````````````````````````
 * @title: SoltUtil
 * @projectName inventory
 * @date 2019/9/21 17:53
 */
public class SoltUtil {

    /**
     * 功能描述: 对Map的value进行排序<br>
     * 〈〉
     *
     * @param resourceMap 需要排序的map
     * @param asc         true升序，false降序
     * @return: java.util.Map<K, V>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/9/21 17:54
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> soltByValue(Map<K, V> resourceMap, boolean asc) {
        Map<K, V> resultMap = new LinkedHashMap<K, V>();
        if (resourceMap == null)
            return resourceMap;

        Stream<Map.Entry<K, V>> stream = resourceMap.entrySet().stream();

        if (asc) {
            stream.sorted(Map.Entry.<K, V>comparingByValue()).
                    forEachOrdered(e -> resultMap.put(e.getKey(), e.getValue()));
        } else {
            stream.sorted(Map.Entry.<K, V>comparingByValue().reversed())
                    .forEachOrdered(e -> resultMap.put(e.getKey(), e.getValue()));
        }
        return resultMap;
    }

}
