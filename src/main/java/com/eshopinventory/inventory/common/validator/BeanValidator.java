package com.eshopinventory.inventory.common.validator;

import com.eshopinventory.inventory.config.exception.ParamException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @author: hzt
 * @date: 2018-11-04 15:22
 * @version: 1.0
 * @description: 用于校验bean的工具类
 */
public class BeanValidator {
    //定义校验工厂
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * @param t      校验的类
     * @param groups 校验的集合
     * @description: 校验类的发膜护发
     * @author: hzt
     * @return: java.util.Map<java.lang.String                                                               ,                                                               java.lang.String>
     * @date: 2018/11/4 15:42
     */
    public static <T> Map<String, String> validate(T t, Class... groups) {

        //获取校验类
        Validator validator = validatorFactory.getValidator();
        //获取校验结果
        Set validatorResult = validator.validate(t, groups);

        if (validatorResult.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        LinkedHashMap errors = new LinkedHashMap();
        Iterator iterator = validatorResult.iterator();
        while (iterator.hasNext()) {
            //取出封装好的错误值
            ConstraintViolation c = (ConstraintViolation) iterator.next();
            //放到error中
            errors.put(c.getPropertyPath().toString(), c.getMessage());
        }
        return errors;
    }

    /**
     * @param collection 校验的集合
     * @description: 校验集合的方法
     * @author: hzt
     * @return: java.util.Map<java.lang.String               ,               java.lang.String>
     * @date: 2018/11/4 15:43
     */
    public static Map<String, String> valisatorList(Collection<?> collection) {
        if (collection == null) throw new RuntimeException("集合为空");
        Iterator iterator = collection.iterator();
        Map<String, String> errors;
        do {
            if (!iterator.hasNext()) {
                return Collections.EMPTY_MAP;
            }
            Object error = iterator.next();
            errors = validate(error, new Class[0]);
        } while (errors.isEmpty());
        return errors;
    }

    /**
     * @param first   传入此值则校验类
     * @param objects 传入此值则校验集合
     * @description: 校验object的方法 进行封装
     * @author: hzt
     * @return: java.util.Map<java.lang.String               ,               java.lang.String>
     * @date: 2018/11/4 15:45
     * @throws:
     */
    public static Map<String, String> validatorObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return valisatorList(Arrays.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }

    /**
     * @param param 校验的数据
     * @description: 校验的方法, 如果有错误抛出异常
     * @author: hzt
     * @date: 2018/11/4 15:58
     */
    public static void check(Object param) {
        Map<String, String> validate = validate(param);
        if (MapUtils.isNotEmpty(validate)) {
            throw new ParamException(param.toString());
        }

    }

}
