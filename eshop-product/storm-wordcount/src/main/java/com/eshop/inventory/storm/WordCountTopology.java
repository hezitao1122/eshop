package com.eshop.inventory.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author zeryts
 * @description: 单词计数的拓扑的类
 * ```````````````````````````
 * @title: WordCountTopology
 * @projectName inventory
 * @date 2019/7/9 23:24
 */
public class WordCountTopology {


    /**
     * @author zeryts
     * @description: spout(数据源的代码组件)类，主要负责从数据源获取数据
     * 继承一个基类，暂时不从外部获取数据
     * ```````````````````````````
     * @title: RandomSentenceSpout
     * @projectName inventory
     * @date 2019/7/9 23:24
     */
    public static class RandomSentenceSpout extends BaseRichSpout {
        /**
         * 初始化时候传入进来的数据发射对象
         */
        private SpoutOutputCollector collector;
        /**
         * 随机对象
         */
        private Random random;


        /**
         * 功能描述: 对spout进行初始化的方法，如：<br>
         * 创建一个线程池、创建一个连接成、构建一个httpclient等
         * 〈〉
         *
         * @param conf      配置
         * @param context   在open方法初始化的时候会传入进来一个SpoutOutputCollector对象，然后这个对象用来发射数据出去的对象
         * @param collector 数据发射对象
         * @return: void
         * @since: 1.0.0
         * @Author: zeryts
         * @Date: 2019/7/9 23:31
         */
        public void open(Map conf,
                         TopologyContext context,
                         SpoutOutputCollector collector) {
            this.collector = collector;
            //构造一个随机数生产对象
            this.random = new Random();

        }

        /**
         * 功能描述: 这个spout最终会运行在task中，这段代码最终会放在某个worker进程的某个executor线程内部的task中<br>
         * task会负责去无限循环调用此方法<br>
         * 只要的话，无限循环调用，可以不断发射最新的数据出去，形成一个数据流<br>
         *
         * @since: 1.0.0
         * @Author: zeryts
         * @Date: 2019/7/9 23:37
         */
        public void nextTuple() {
            //避免发送数据过快
            Utils.sleep(100);
            String[] sentences = new String[]{"the cow jumped over the moon", "an apple a day keeps the doctor away",
                    "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature"};

            //从句子里面随机拿个句子出来
            final String sentence = sentences[random.nextInt(sentences.length - 1)];

            log.error("发射句子:[" + sentence + "]");
            //发射
            /**
             * values的作用为构建一个tuple的对象
             */
            collector.emit(new Values(sentence));

        }

        /**
         * 功能描述: 定义一个你发射出去的每个tuple中的每个field的名称是什么<br>
         * 〈〉
         *
         * @param declarer
         * @since: 1.0.0
         * @Author: zeryts
         * @Date: 2019/7/9 23:45
         */
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("sentence"));
        }
    }

    /**
     * @author zeryts
     * @description: Bolt -> 业务处理代码组件，spout将数据以tuple的形式传给它
     * 写个Bolt直接继承BaseRichBolt即可
     * ```````````````````````````
     * @title: SplitSentence
     * @projectName inventory
     * @date 2019/7/9 23:24
     */
    public static class SplitSentence extends BaseRichBolt {
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
            String sentence = tuple.getStringByField("sentence");
            String[] s = sentence.split(" ");
            for (String str : s) {
                //获取到数据然后逐条发射出去
                collector.emit(new Values(str));
            }
        }

        /**
         * 功能描述: 定义发送field的名称<br>
         * 〈〉
         *
         * @param outputFieldsDeclarer 获取到发射的对象
         * @return: void
         * @since: 1.0.0
         * @Author: zeryts
         * @Date: 2019/7/9 23:56
         */
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("word"));
        }
    }

    public static class WordCount extends BaseRichBolt {

        private OutputCollector collector;

        private Map<String, Integer> countMap = new HashMap<String, Integer>();

        public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        public void execute(Tuple tuple) {
            String sentence = tuple.getStringByField("word");
            Integer count = countMap.get(sentence);
            if (count == null) {
                count = 1;
            }
            count++;
            countMap.put(sentence, count);

            log.error("单词计数，单词[" + sentence + "]，的数量为[" + count + "]");
            //再发射出去
            collector.emit(new Values(sentence, count));
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }


    private static final Logger log = LoggerFactory.getLogger(WordCountTopology.class);

    /**
     * 功能描述: 在这个main方法中，会将spout和slot组合起来，构建成一个Topology拓扑<br>
     * 〈〉
     *
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/10 0:05
     */
    public static void main(String[] args) {
        //构建拓扑工厂类
        TopologyBuilder builder = new TopologyBuilder();

        //构建一个拓扑
        /**
         * @param 即给spout设置一个名称
         * @param 创建一个spout对象
         * @param 设置spout的executor逻辑有几个Field
         */
        builder.setSpout("RandomsSentence", new RandomSentenceSpout(), 2);

        /**
         * 构建一个blot
         * @param 设置Bolt的名称
         * @param 构建一个Bolt
         * @param 设置Bolt有几个Field
         */
        builder.setBolt("SplitSentence", new SplitSentence(), 5).
                /**
                 * 设置每个executoe的数量，，默认为1，不设置的情况下 executor的数量 = task的数量，即5*10代表并行度
                 */
                        setNumTasks(10).
                /**
                 * 设置spout的名称
                 */
                        shuffleGrouping("RandomsSentence");


        builder.setBolt("WordCount", new WordCount(), 10)
                .setNumTasks(20)
                /**
                 * 代表根据传入的字段的值进行流的分组，WordCount中的new Field中传入的field为字段名，第二个参数为值
                 */
                .fieldsGrouping("SplitSentence", new Fields("word"));

        /**
         * 下面为创建一个config
         */
        Config config = new Config();


        if (args != null && args.length > 0) {
            //说明在命令行执行，打算提交到storm集群上去
            /**
             * 此处提交到集群的storm要启动几个work
             */
            config.setNumWorkers(3);

            try {
                /**
                 * 其传入集群有多种方式，包括拓扑、jar
                 */
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            } catch (AuthorizationException e) {
                e.printStackTrace();
            }
        } else {
            //代表在idea里面进行执行
            config.setMaxTaskParallelism(20);

            LocalCluster cluster = new LocalCluster();
            try {
                cluster.submitTopology("WorkCountTopology", config, builder.createTopology());
            } catch (Exception e) {
                log.error(e.toString(), e);
            }


            Utils.sleep(60000);
            cluster.shutdown();
        }


    }

}
