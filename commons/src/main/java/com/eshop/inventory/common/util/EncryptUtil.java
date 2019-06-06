package com.eshop.inventory.common.util;


import java.math.BigDecimal;

public class EncryptUtil {

    /**
     * 加密
     * @author
     * @date 2017年4月19日 上午9:40:53
     * @param data
     * @return
     * @throws Exception
     */
    public String encode(String data) throws Exception {
        BigDecimal result = new BigDecimal(data).add(new BigDecimal(1000)).multiply(new BigDecimal(2));
        String result1 = new StringBuffer(result+"").reverse().toString();
        return result1;
    }

    /**
     * 解密
     * @author ershuai
     * @date 2017年4月19日 上午9:41:01
     * @param data
     * @return
     * @throws Exception
     */
    public String decode(String data)    throws Exception {
        String result1 = new StringBuffer(data).reverse().toString();
        BigDecimal  result = new BigDecimal(result1).divide(new BigDecimal(2)).subtract(new BigDecimal(1000));
        return result+"";
    }

}
