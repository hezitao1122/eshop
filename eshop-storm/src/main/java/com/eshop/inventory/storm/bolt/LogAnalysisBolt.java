package com.eshop.inventory.storm.bolt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eshop.inventory.storm.config.KafkaConstant;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author zeryts
 * @description: 日志解析的Bolt
 * ```````````````````````````
 * @title: LogAnalysisBolt
 * @projectName inventory
 * @date 2019/9/21 14:54
 */
public class LogAnalysisBolt extends BaseRichBolt {

    private static final Logger log = LoggerFactory.getLogger(LogAnalysisBolt.class);

    /**
     * 发射器
     */
    private OutputCollector collector;

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
    }
    /**
     * 功能描述: 每次接收到一条数据后就会交付给此方法来执行<br>
     * 〈〉
     *
     * @param tuple 接收数据的对象
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/9 23:53
     */
    public void execute(Tuple tuple) {
        //拿到一条message
        String message = tuple.getStringByField(KafkaConstant.MESSAGE);

        log.info("[LogAnalysisBolt接收到一条日志]");

        JSONObject obje = JSON.parseObject(message);
        //获取到这条数据的id
        Long id = obje.getLong(KafkaConstant.ID);
        if(id != null)
            //发射出去
            collector.emit(new Values(id));


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
        declarer.declare(new Fields(KafkaConstant.ID));
    }
}
