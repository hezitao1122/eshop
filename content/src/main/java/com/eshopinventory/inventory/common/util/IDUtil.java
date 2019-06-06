package com.eshopinventory.inventory.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * ID主键生成工具类
 */
public class IDUtil {

    public static String nextUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static String getID() {
        String key = randomSalt(10,99) + "" + System.currentTimeMillis() + "" + randomSalt(1,9);
        return key;
    }

    public static int randomSalt(int a, int b) {
        int max=b;
        int min=a;
        Random random = new Random();
        int salt = random.nextInt(max)%(max-min+1) + min;
        return salt;
    }

}
