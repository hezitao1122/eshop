package com.eshopinventory.inventory.config.listener;

import com.eshopinventory.inventory.config.thread.RequestProcessorThreadPool;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zeryts
 * @description: 系统初始化监听器
 * ```````````````````````````
 * @title: InitListener
 * @projectName inventory
 * @date 2019/6/1 1:17
 */
@Component
public class InitListener{
    @PostConstruct
    public void init() {
        // 初始化工作线程池和内存队列
        RequestProcessorThreadPool.init();
    }

}
