package com.eshop.inventory.common.hystrix.redis;

import com.eshop.inventory.common.enums.RedisOperateEnums;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author zeryts
 * @description: Redis操作的command
 * ```````````````````````````
 * @title: RedisOperateCommand
 * @projectName inventory
 * @date 2020/6/6 16:12
 */
public class RedisOperateCommand<V> extends HystrixCommand<V> {

    public static final HystrixCommandGroupKey GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("RedisGroupKey");
    public static final HystrixCommandKey COMMAND_KEY = HystrixCommandKey.Factory.asKey("RedisCommand");

    private String key;

    private V value;
    private RedisOperateEnums enums;

    private RedisTemplate template ;

    private TimeUnit unit;
    private long time;
    boolean flag = false;


    public RedisOperateCommand(String key, V value, RedisTemplate template, RedisOperateEnums enums ) {
        //绑定一个线程池 ， 默认为线程池模式
        super(Setter.withGroupKey(GROUP_KEY)
                .andCommandKey(COMMAND_KEY)
        );
        this.key = key;
        this.value = value;
        this.enums = enums;
        this.template = template;
    }

    public RedisOperateCommand(String key, V value, RedisTemplate template, RedisOperateEnums enums, TimeUnit unit, long time) {
        //绑定一个线程池 ， 默认为线程池模式
        super(Setter.withGroupKey(GROUP_KEY)
                .andCommandKey(COMMAND_KEY)
        );
        this.key = key;
        this.value = value;
        this.enums = enums;
        this.unit = unit;
        this.time = time;
        this.template = template;
        flag = true;
    }

    @Override
    protected V run() throws Exception {
        if (enums == RedisOperateEnums.ADD) {
            if (flag) {
                template.opsForValue().set(key, value, time, unit);
            } else {
                template.opsForValue().set(key, value);
            }
        } else if (enums == RedisOperateEnums.DELETE) {
            template.delete(key);
        } else if (enums == RedisOperateEnums.SELECT) {
            template.opsForValue().get(key);
        }
        return value;
    }
}
