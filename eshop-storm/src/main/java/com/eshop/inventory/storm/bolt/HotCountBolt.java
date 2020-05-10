package com.eshop.inventory.storm.bolt;

import com.eshop.inventory.storm.config.KafkaConstant;
import com.eshop.inventory.storm.processor.ItemCountThread;
import com.eshop.inventory.storm.zk.ZooKeeperSession;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.trident.util.LRUMap;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author zeryts
 * @description: 日志统计的Bolt
 * ```````````````````````````
 * @title: HotCountBolt
 * @projectName inventory
 * @date 2019/9/21 15:05
 */
public class HotCountBolt extends BaseRichBolt {
    private static final Logger log = LoggerFactory.getLogger(HotCountBolt.class);
    /**
     * 发射器
     */
    private OutputCollector collector;
    /**
     * 统计存储的MAP集合，初始值为可以存多少
     */
    private LRUMap<Long, Long> countMap = new LRUMap<Long, Long>(1000);
    /**
     * 实例化zookeeper单例
     */
    private ZooKeeperSession session = ZooKeeperSession.getInstance();

    /**
     * 功能描述: 对于blot来说此为第一个方法<br>
     * 〈〉
     *
     * @param conf      配置
     * @param context   在open方法初始化的时候会传入进来一个SpoutOutputCollector对象，然后这个对象用来发射数据出去的对象
     * @param collector blot的tuple发射对象
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/9 23:51
     */
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        new Thread(new ItemCountThread(countMap, session, context.getThisTaskId())).start();
        //1. 将自己的一个taskid写入一个zookeeper node中，形成taskid的列表
        //2. 然后每次都将自己的热门商品列表,写入自己的taskid对应的zookeeper节点
        //3. 并行的预热程序才能从第一步中知道,那些taskid
        //4. 然后并行预热程序根据每个taskid去获取一个锁,然后去对应的znode列表中拿具体数据
        initTaskId(context.getThisTaskId());
    }

    /**
     * 功能描述: 所有的HoltCountBolt启动的时候都会将自己的task写入到同一个node的值中，格式是 1,2<br>
     * 〈〉
     *
     * @param taskId 任务的id
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/9/22 17:03
     */
    private void initTaskId(int taskId) {
        //加锁
        session.acquireDistributedLock(KafkaConstant.TASK_ID_LIST);
        log.info("[Item获取到taskid list] taskid list = {}", taskId);
        String nodeData = session.getNodeData("/" + KafkaConstant.TASK_ID_LIST);
        if (StringUtils.isNotEmpty(nodeData)) {
            nodeData += "," + taskId;
        } else {
            nodeData = taskId + "";
        }
        session.setNodeData("/" + KafkaConstant.TASK_ID_LIST, nodeData);
        log.info("[ItemCountBlot设置taskId List] taskIdList = {}", nodeData);
        //释放锁
        session.releaseDistributedLock(KafkaConstant.TASK_ID_LIST);

    }

    /*
     * 功能描述: 每次接收到一条数据后就会交付给此方法来执行<br>
     * 〈〉
     *
     * @param tuple 接收数据的对象
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/9 23:53
     */
    public void execute(Tuple tuple) {
        //获取id
        Long id = tuple.getLongByField(KafkaConstant.ID);
        log.info("[接收到一个itemId] id ->[{}]", id);
        Long count = countMap.get(id);
        if (count == null)
            count = 0L;
        count++;
        //计算
        countMap.put(id, count);
        log.info("[ItemId 完成商品访问次数统计]， id->[{}] , count->[{}]", id, count);

    }

    /**
     * 功能描述: 定义发送field的名称<br>
     * 〈〉
     *
     * @param declarer 获取到发射的对象
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/9 23:56
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
