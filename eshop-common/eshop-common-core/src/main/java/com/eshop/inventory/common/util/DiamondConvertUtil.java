package com.eshop.inventory.common.util;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author: ybc
 * @Date: 2018/6/2215:05
 * @Version: 1.0
 */
public class DiamondConvertUtil {
    /**
     * BigDecimal转换成Long去除小数点，只取整数
     * 钻石数量只取整数
     *
     * @return
     */
    public static Long diamondTakeRoundNumbers(BigDecimal b) {
        return b.longValue();
    }

    /**
     * BigDecimal转换成Long
     * 钻石转换成晶石
     *
     * @param b     钻石数量
     * @param ratio 转换比
     * @return
     */
    public static Long diamondConvertToCrystalTone(BigDecimal b, int ratio) {
        BigDecimal a = b.multiply(BigDecimal.valueOf(ratio));
        long l = a.longValue();
        return l;
    }

    /**
     * Long型数据转换成BigDecimal
     * 晶石转换成钻石
     *
     * @param crystalStoneNum 晶石数量
     * @param ratio           转换比
     * @return
     */
    public static BigDecimal crystalToneConvertToDiamond(long crystalStoneNum, int ratio) {
        BigDecimal a = new BigDecimal(crystalStoneNum);
        BigDecimal b = new BigDecimal(ratio);
        BigDecimal v = a.divide(b, 4, BigDecimal.ROUND_HALF_DOWN);
        return v;
    }


}
