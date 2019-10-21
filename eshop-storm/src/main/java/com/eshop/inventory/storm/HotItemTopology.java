package com.eshop.inventory.storm;

import com.eshop.inventory.storm.bolt.HotCountBolt;
import com.eshop.inventory.storm.bolt.LogAnalysisBolt;
import com.eshop.inventory.storm.config.KafkaConstant;
import com.eshop.inventory.storm.config.KafkaConsumerConfig;
import com.eshop.inventory.storm.spout.AccessLogKafkaSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @author zeryts
 * @description: 商品详情热数据统计的拓扑
 * ```````````````````````````
 * @title: HotItemTopology
 * @projectName inventory
 * @date 2019/8/1 22:56
 */
public class HotItemTopology {
    public static void main(String[] args) {

        KafkaConsumerConfig.init();

        TopologyBuilder builder = new TopologyBuilder();

        //构建一个拓扑
        /**
         * @param 即给spout设置一个名称
         * @param 创建一个spout对象
         * @param 设置spout的executor逻辑有几个Field
         */
        builder.setSpout(AccessLogKafkaSpout.class.getName(),new AccessLogKafkaSpout() , 1);
        /**
         * 构建一个传递blot
         * @param 设置Bolt的名称
         * @param 构建一个Bolt
         * @param 设置Bolt有几个Field
         */
        builder.setBolt(LogAnalysisBolt.class.getName(),new LogAnalysisBolt(),5)
                /**
                 * 设置每个executoe的数量，，默认为1，不设置的情况下 executor的数量 = task的数量，即5*10代表并行度
                 */
                .setNumTasks(5).
                /**
                 * 设置spout的名称
                 */
                shuffleGrouping(AccessLogKafkaSpout.class.getName());

        /**
         * 构建一个统计blot
         * @param 设置Bolt的名称
         * @param 构建一个Bolt
         * @param 设置Bolt有几个Field
         */
         builder.setBolt(HotCountBolt.class.getName(),new HotCountBolt(),5)

                 .setNumTasks(10).
                 /**
                  * 代表根据传入的字段的值进行流的分组，WordCount中的new Field中传入的field为字段名，第二个参数为值
                  */
                 fieldsGrouping(LogAnalysisBolt.class.getName(),new Fields(KafkaConstant.ID));
        /**
         * 下面为创建一个config
         */
        Config config = new Config();


        if(args != null && args.length > 0){
            //说明在命令行执行，打算提交到storm集群上去
            /**
             * 此处提交到集群的storm要启动几个work
             */
            config.setNumWorkers(3);

            try {
                StormSubmitter.submitTopology(args[0],config,builder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }


        }else{
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(HotItemTopology.class.getName(),config,builder.createTopology());
        }


    }
}
