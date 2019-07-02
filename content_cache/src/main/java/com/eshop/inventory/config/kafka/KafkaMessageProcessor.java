package com.eshop.inventory.config.kafka;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.ProductCacheApplication;
import com.eshop.inventory.common.dto.KafkaBaseVo;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.dto.ResultEnum;
import com.eshop.inventory.config.zk.ZooKeeperSession;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.feign.ItemFeign;
import com.eshop.inventory.manage.item.service.ItemCacheService;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * @author zeryts
 * @description: Kafka的消息处理线程
 * ```````````````````````````
 * @title: KafkaMessageProcessor
 * @projectName inventory
 * @date 2019/6/15 15:35
 */
@Slf4j
public class KafkaMessageProcessor implements Runnable {

    private KafkaStream kafkaStream;

    public KafkaMessageProcessor(KafkaStream kafkaStream) {
        this.kafkaStream = kafkaStream;
    }

    /**
     * 2、编写业务逻辑
     * <p>
     * （1）两种服务会发送来数据变更消息：商品信息服务，商品店铺信息服务，每个消息都包含服务名以及商品id
     * <p>
     * （2）接收到消息之后，根据商品id到对应的服务拉取数据，这一步，我们采取简化的模拟方式，就是在代码里面写死，会获取到什么数据，不去实际再写其他的服务去调用了
     * <p>
     * （3）商品信息：id，名称，价格，图片列表，商品规格，售后信息，颜色，尺寸
     * <p>
     * （4）商品店铺信息：其他维度，用这个维度模拟出来缓存数据维度化拆分，id，店铺名称，店铺等级，店铺好评率
     * <p>
     * （5）分别拉取到了数据之后，将数据组织成json串，然后分别存储到ehcache中，和redis缓存中
     */
    @Override
    public void run() {
        //拿到消息的迭代器，迭代器就可以拿到一条条的message
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            ////此处代表拿到具体的message消息
            String message = new String(it.next().message());
            //转化为对象
            KafkaBaseVo kafkaBaseVo = JSON.parseObject(message, KafkaBaseVo.class);
            /**
             * 主动监听：
             * 监听kafka队列，获取到一个商品变更的消息之后，去哪个源服务中调用接口拉取数据，更新到ehcache和redis中
             * 先获取分布式锁，然后才能更新redis，同时需要比较redis的版本
             */
            if (kafkaBaseVo.getServiceId().equals(RedisCachePrefixEnum.ITEM.getName())) {
                //获取service操作对象
                ItemFeign itemFeign = ProductCacheApplication.app.getBean(ItemFeign.class);
                ItemCacheService itemCacheService = ProductCacheApplication.app.getBean(ItemCacheService.class);
                //对象转换
                TbItem item = (TbItem) kafkaBaseVo.parseEntity(TbItem.class);
                //远程调用获取商品对象信息
                ResultDto<TbItemDTO> res = itemFeign.find(item.getId());
                //代表获取对象成功
                if (res.getCode().equals(ResultEnum.SUCCESS.getCode())) {
                    TbItemDTO dto = res.getData();
                    //写入缓存之前应该先获取分布式锁
                    String name = RedisCachePrefixEnum.ITEM.getName();
                    ZooKeeperSession instance = ZooKeeperSession.getInstance();
                    //线从redis中获取数据
                    TbItemDTO loadCache = itemCacheService.getLoadCache(dto.getId());

                    //主动重建-》如果没有获取到锁，则会等待
                    instance.acquireDistributedLock(name + dto.getId());
                    try {
                        if (loadCache != null) {
                            //比较当前数据的时间版本与已有数据的版本
                            Timestamp orderTime = loadCache.getUpdated();
                            Timestamp newTime = dto.getUpdated();
                            if (newTime.before(orderTime)) {//代表当前时间在老时间之前
                                log.info("此条新数据[{}]是在老数据[{}]之前!", newTime, orderTime);
                                return;
                            }
                        }
                        log.info("Item ------> 将获取的商品信息id->[{}]", dto.getId(), ",内容->[{}]", JSON.toJSONString(dto), "保存至缓存和ehcache中!");
                        //缓存到ehcache中
                        itemCacheService.saveLoadEhCache(dto);
                        //缓存到redis中
                        itemCacheService.saveLoadCache(dto.getId(), dto);
                    } catch (Exception e) {
                        log.info(e.toString(),e);
                    }finally {
                        //释放锁是有必要的
                        instance.releaseDistributedLock(name + dto.getId());
                    }
                }


            }

        }
    }
}
