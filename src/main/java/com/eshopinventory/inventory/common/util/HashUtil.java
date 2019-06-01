package com.eshopinventory.inventory.common.util;

/**
 * @author zeryts
 * @description: 用户hash操作的工具类
 * ```````````````````````````
 * @title: HashUtil
 * @projectName inventory
 * @date 2019/5/31 22:11
 */
public class HashUtil {
    /**
     * 功能描述: 根据传入的值求hash码的方法<br>
     * 〈〉
     * @param key
     * @return: int
     * @since: 1.0.0
     * @Author: Administrator
     * @Date: 2019/5/31 22:14
     */
    public static int getHash(Object key){

       if(key == null) return 0;
        int h = key.hashCode();
       int hash = h ^ (h >>> 16);

        return hash;
    }
}
