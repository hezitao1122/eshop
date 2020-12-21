package com.eshop.inventory.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: ReflectUtil
 * @Title: ReflectUtil
 * @ProjectName: inventory
 * @Description: 反射操作的工具类
 * @Author: zeryts
 * @Date: Create in 2019/6/1815:01
 */
public class ReflectUtil {
    /**
     * @param
     * @Description: 通过反射获取类的各个属性名称和对应的Field
     * ----------------------------
     * @param: clazz类的类型
     * @param:
     * @Method: getClassField
     * @Author: zeryts
     * @Return: Map<String, Field>
     * @Date: Create in 2019/6/18 15:12
     */
    public static Map<String, Field> getClassField(Class<?> clazz, HashMap<String, Field> attrMap) {
        //如果到了Object这级就返回null,不做操作
        if (clazz == Object.class)
            return null;
        if (attrMap == null)
            attrMap = new HashMap<String, Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            attrMap.put(field.getName(), field);
        }
        Class<?> superClazz = clazz.getSuperclass();
        //递归获取其超类的,即父类的属性
        getClassField(superClazz, attrMap);
        return attrMap;
    }

    /**
     * @param object         操作的实体类
     * @param methodName     方法名
     * @param parameterTypes 参数是 Class 对象的一个数组，它按声明顺序标识该方法的形参类型。用于区分方法重载
     * @Description: 根据方法名获取具体的方法对象
     * ----------------------------
     * @Method: getMethod
     * @Author: zeryts
     * @Return: java.lang.reflect.Method
     * @Date: Create in 2019/6/18 15:53
     */
    public static Method getMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * @param object    操作的对象
     * @param fieldName 需要获取的字段名
     * @Description: 根据字段名获取对象的字段信息
     * ----------------------------
     * @Method: getFiledByFiledName
     * @Author: zeryts
     * @Return: java.lang.reflect.Field
     * @Date: Create in 2019/6/18 16:02
     */
    public static Field getFieldByFieldName(Object object, String fieldName) throws SecurityException, IllegalArgumentException {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * @param object    需要操作的对象
     * @param fieldName 字段名
     * @Description: 根据字段名获取对象的字段属性
     * ----------------------------
     * @Method: getValueByFieldName
     * @Author: zeryts
     * @Return: java.lang.Object
     * @Date: Create in 2019/6/18 16:21
     */
    public static Object getValueByFieldName(Object object, String fieldName) throws IllegalAccessException {
        Field field = getFieldByFieldName(object, fieldName);
        Object ret = null;
        if (field != null) {
            if (field.isAccessible()) {
                ret = field.get(object);
            } else {
                field.setAccessible(true);
                ret = field.get(object);
                field.setAccessible(false);
            }
        }
        return ret;
    }

    /**
     * @param object 操作的对象
     * @param clazz  注解的类型
     * @Description: 根据注解获取对象的该字段, 只会获取到其中的一个字段
     * ----------------------------
     * @Method: getFieldByAnnotation
     * @Author: zeryts
     * @Return: java.lang.reflect.Field
     * @Date: Create in 2019/6/18 16:23
     */
    public static Field getFieldByAnnotation(Object object, Class<? extends Annotation> clazz) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                for (Field field : superClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(clazz))
                        return field;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * @param object 操作的对象
     * @param clazz  注解的类型
     * @Description: 根据注解获取对象的该字段集合
     * ----------------------------
     * @Method: getFieldsByAnnotation
     * @Author: zeryts
     * @Return: java.util.List<java.lang.reflect.Field>
     * @Date: Create in 2019/6/18 16:28
     */
    public static List<Field> getFieldsByAnnotation(Object object, Class<? extends Annotation> clazz) {
        List<Field> retList = new ArrayList<>();
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                for (Field field : superClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(clazz))
                        retList.add(field);
                }
            } catch (Exception e) {
            }
        }
        return retList.size() == 0 ? null : retList;
    }

    /**
     * @param object    操作的对象
     * @param fieldName 操作的字段名
     * @param value     赋值的值
     * @Description: 给对象的指定字段赋值指定的值
     * ----------------------------
     * @Method: setValueByFieldName
     * @Author: zeryts
     * @Date: Create in 2019/6/18 16:34
     */
    public static void setValueByFieldName(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        //判断是否可操作性
        if (field.isAccessible()) {
            field.set(object, value);
        } else {
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        }
    }

    /**
     * @param clazz 操作的类
     * @param set   所有字段的集合
     * @Description: 获取类的所有字段的集合
     * ----------------------------
     * @Method: getFieldName
     * @Author: zeryts
     * @Return: java.util.Set<java.lang.String>
     * @Date: Create in 2019/6/18 16:47
     */
    public static Set<String> getFieldName(Class<?> clazz, Set<String> set) {
        if (clazz == Object.class)
            return null;
        if (set == null)
            set = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
            set.add(field.getName());
        Class<?> superClazz = clazz.getSuperclass();
        getFieldName(superClazz, set);
        return set;
    }

    /**
     * @param object     操作的对象
     * @param methodName 方法名
     * @param types      参数列表
     * @Description: 根据方法名和参数列表去执行制定的方法，并获取返回值
     * ----------------------------
     * @Method: invokeMethod
     * @Author: zeryts
     * @Return: java.lang.Object
     * @Date: Create in 2019/6/18 17:02
     */
    public static Object invokeMethod(Object object, String methodName, Object... types) throws InvocationTargetException, IllegalAccessException {

        Class<?>[] clazzs = new Class<?>[types.length];
        for (int i = 0; i < types.length; i++) {
            clazzs[i] = types.getClass();
        }
        Method method = getMethod(object, methodName, clazzs);

        if (method == null)
            return null;
        method.setAccessible(true);
        return method.invoke(object, types);
    }
}
