package com.eshop.inventory.common.base;

/**
 * @author zeryts
 * @description: 用于EhCache缓存的service接口
 * ```````````````````````````
 * @title: ICacheService
 * @projectName inventory
 * @date 2019/6/1223:29
 */
public interface ICacheService<T,ID> {
    /**
     * 功能描述: 将对应的对象通过前缀+id的形式存在redis中<br>
     * 〈〉
     * @param id id
     * @param t 存储的对象
     * @return: T
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 22:08
     */
    T saveLoadCache(ID id,T t);
    /**
     * 功能描述: 通过前缀+id取出存在redis中的对象<br>
     * 〈〉
     * @param id id
     * @return: T
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 22:08
     */
    T getLoadCache(ID id);
    /**
     * 功能描述: 通过前缀+id删除redis中的对象<br>
     * 〈〉
     * @param id id
     * @return: Boolean 删除成功与否的标志
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/28 0:21
     */
    Boolean deleteLoadCache(ID id);

    /**
     * 功能描述: 获取到存储到redis中的前缀<br>
     * 〈〉
     * @return: String 前缀
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 22:08
     */
    String prefix();
    /**
     * 功能描述: 将对象存储到ehcache中的方法，由于此为通过注解方式动态加载，故所有动作延迟到实现类<br>
     * 〈〉
     * @param t 对象
     * @return: t 对象
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 22:08
     */
    T saveLoadEhCache(T t);

    /**
     * 功能描述: 通过id去ehcache中取的方法，由于此为通过注解方式动态加载，故所有动作延迟到实现类<br>
     * 〈〉
     * @param id 主键
     * @return: t 对象
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 22:08
     */
    T getLoadEhCache(ID id);

    /**
     * 功能描述: 通过id去ehcache中删除的方法，由于此为通过注解方式动态加载，故所有动作延迟到实现类<br>
     * 〈〉
     * @param id 主键
     * @return: Boolean 删除成功与否的标志
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 22:08
     */
    void delLoadEhCache(ID id);
}
