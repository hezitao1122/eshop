package com.eshop.inventory.cache.perwarm;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.ProductCacheApplication;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.enums.KafkaConstant;
import com.eshop.inventory.config.zk.ZooKeeperSession;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.feign.ItemFeign;
import com.eshop.inventory.manage.item.service.ItemCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author zeryts
 * @description: 缓存预热线程
 * ```````````````````````````
 * @title: CachePerwarmThread
 * @projectName inventory
 * @date 2019/9/22 19:25
 */
@Slf4j
public class CachePerwarmThread extends Thread {


    @Override
    public void run() {
        ZooKeeperSession session = ZooKeeperSession.getInstance();
        //获取task-id列表|
        String dataStr = session.getNodeData("/" + KafkaConstant.TASK_ID_LIST);
        log.info("[CachePerwarmThread获取到TaskId列表]taskIdList ->{}", dataStr);
        if (StringUtils.isNotEmpty(dataStr)) {
            String[] arr = dataStr.split("[,]");
            for (int i = 0; i < arr.length; i++) {
                //尝试拿所有分布式的锁
                String taskId = arr[i];
                String key = "/" + KafkaConstant.TASK_ID_LOCK + taskId;
                boolean b = session.acquireFastFaildDistributedLock(key);
                //没拿到就尝试下一个
                if (!b)
                    continue;
                //拿到了
                //检查预热状态
                session.acquireDistributedLock("/" + KafkaConstant.TASK_ID_STATUS_LOCK + taskId);
                String status = session.getNodeData("/" + KafkaConstant.TASK_ID_STATUS + taskId);
                log.info("[CachePerwarmThread获取task的预热状态]，taskId->{} , status -> {}", taskId, status);
                if (StringUtils.isEmpty(status)) {
                    //代表未进行过预热
                    String nodeData = session.getNodeData("/" + KafkaConstant.TASK_HOT + taskId);
//                    List<Map<String,Long>> maps = JSON.parse(nodeData, new TypeReference<HashMap<String,Long>>(){});
                    List<Long> itemIds = JSON.parseArray(nodeData, Long.class);

                    ItemCacheService itemCacheService = ProductCacheApplication.app.getBean(ItemCacheService.class);

                    log.info("[CachePerwarmThread获取到task热门商品列表], itemIdlist ->{}", itemIds);

                    ItemFeign itemFeign = ProductCacheApplication.app.getBean(ItemFeign.class);
                    //TODO 暂时先注释掉，缺少批量查询的逻辑
/*                    ItemDescCacheService itemDescCacheService = ProductCacheApplication.app.getBean(ItemDescCacheService.class);
                    ItemDescFeign descFeign = ProductCacheApplication.app.getBean(ItemDescFeign.class);
                    TbItemDesc desc = new TbItemDesc();
                    desc.setItemId();
                    descFeign.findByCondition()
*/

                    //查询出数据信息
                    ResultDto<TbItemDTO> items = itemFeign.findByIds(itemIds);
                    //存放至redis中
                    items.getDataLis().forEach(it -> {
                        log.info("[CachePerwarmThread将Item信息设置到缓存中] id ->{} , item->{}", it.getId(), JSON.toJSONString(it));
                        itemCacheService.saveLoadCache(it.getId(), it);
                        itemCacheService.saveLoadEhCache(it);
                    });

                    //预热完
                    session.setNodeData("/" + KafkaConstant.TASK_ID_STATUS + taskId, "success");
                }
                session.releaseDistributedLock("/" + KafkaConstant.TASK_ID_STATUS_LOCK + taskId);


                session.releaseDistributedLock(key);


            }


        }


    }
}
