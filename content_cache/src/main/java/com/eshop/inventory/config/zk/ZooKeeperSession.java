package com.eshop.inventory.config.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author zeryts
 * @description: 初始化zookeeper的类
 * ```````````````````````````
 * @title: ZooKeeperSession
 * @projectName inventory
 * @date 2019/6/30 17:26
 */
public class ZooKeeperSession {

    private static final Logger log = LoggerFactory.getLogger(ZooKeeperSession.class);

    /**
     * java多线程同步的一个工具类，传递进入一些数字，比如 1,2,3
     * 然后await ，如果数字不是0则就卡住等待
     * 其他线程调用countDown（）则让数字减1
     * 如果到0，则会让之前的等待线程进入非阻塞状态
     */
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;


    public ZooKeeperSession() {
        try {
            /**
             * @param zk的地址
             * @param 连接的等待时长
             * @param 去连接zk server 是异步去进行的，所以要给一个监听时长，说告诉我们什么时候真正完成了与zk server的连接
             */
            this.zooKeeper = new ZooKeeper("192.168.31.226:2181,192.168.31.225:2181,192.168.31.224:2181",
                    50000,
                    new ZookeeperWatcher());

            log.info("zk status [{}]",zooKeeper.getState().toString());

            try{
                connectedSemaphore.await();
            }catch (Exception e){
                log.info(e.toString(),e);
            }
            log.info("ZK session established ...... ");
        } catch (IOException e) {
            log.info(e.toString(),e);
        }
    }

    /**
     * 功能描述: 分布式加锁的逻辑<br>
     * 〈〉
     * @param path 需要加锁的节点路径
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/1 23:08
     */
    public void acquireDistributedLock(String path){
        create(path);
    }

    /**
     * 功能描述: 分布式加锁的逻辑,没获取到锁直接失败<br>
     * 〈〉
     * @param path 需要加锁的节点路径
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/1 23:08
     */
    public boolean acquireFastFaildDistributedLock(String path){
        try{
            String s = zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            log.info("sucess to path acquire lock for [{}]  " , path);
            return true;
        }catch (Exception e){
            log.info("get "+path+" is fail");
            return false;
        }
    }

    /**
     * 功能描述: 根据路径释放分布式锁的逻辑<br>
     * 〈〉
     * @param path
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/1 23:10
     */
    public void releaseDistributedLock(String path){
        try{
            zooKeeper.delete("/"+path,-1);
            log.info("release distributed lock success , path []","/"+ path);
        }catch (Exception e){
            log.info("release distributed lock fail , path []","/"+ path);
            log.info(e.toString(),e);
        }
    }


    /**
     * 功能描述: 建立zk server的watcher<br>
     * 〈〉
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/1 23:08
     */
    private class ZookeeperWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            log.info("Receive watched event : [{}]",event.getType());
            if(Event.KeeperState.SyncConnected == event.getState()){
                connectedSemaphore.countDown();
            }

        }
    }

    private static class Singleton{
        private static ZooKeeperSession install ;

        static {
            install = new ZooKeeperSession();
        }
        public static ZooKeeperSession getInstance() {
            return install;
        }
    }

    /**
     * 功能描述: 获取ZooKeeper单例的方法<br>
     * 〈〉
     *
     * @return: com.eshop.inventory.config.zk.ZooKeeperSession
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/30 17:29
     */
    public static ZooKeeperSession getInstance(){
        return Singleton.getInstance();
    }
    /**
     * 初始化单例的便捷方法
     */
    public static void init() {
        getInstance();
    }
    private void create(String path){
        //如果已经被别人加锁了，就会报错
        int count = 0;
        while (true){
            try{
                zooKeeper.create("/"+path,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                log.info("创建临时节点成功!节点路径为：[{}]",("/"+path));
            }catch (Exception ex){
                count ++ ;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    log.info(e.toString(),e);
                }
                if(count >= 20)break;
                continue;
            }
            log.info("创建临时节点[{}],[{}]次后成功！",path,count);
            break;
        }
    }
    /**
     * 功能描述: 往zookeeper的中读取数据的方法<br>
     * 〈〉
     *
     * @param path 节点路径
     * @return: java.lang.String
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/9/22 16:55
     */
    public String getNodeData(String path){

        try {
            String data = new String(zooKeeper.getData(path,false,new Stat()));
            return data;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 功能描述: 往zookeeper中写数据的方法<br>
     * 〈〉
     *
     * @param path 节点路径
     * @param data
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/9/22 16:54
     */
    public void setNodeData(String path ,String data){
        try {
            zooKeeper.setData(path,data.getBytes(),-1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
