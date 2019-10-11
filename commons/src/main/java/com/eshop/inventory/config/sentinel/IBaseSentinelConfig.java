package com.eshop.inventory.config.sentinel;

/**
 * @author zeryts
 * @description: 用于Sentinel配置的接口
 * -----------------------------------
 * @title: IBaseSentinelConfig
 * @projectName inventory
 * @date 2019/10/11 17:40
 */
public interface IBaseSentinelConfig {
    /** description: 初始化配置的方法，例如初始化redis配置和nacos配置等
    * @Author: zeryts
    * @Date: 2019/10/11 17:45
    */
   void initConfig();
    /** description: 初始化单机模式下的限流模式
    * @return:
    * @Author: zeryts
    * @Date: 2019/10/11 17:45
    */
    void standOnlyConfig();
    /** description: spring的init方法
    * @return:
    * @Author: zeryts
    * @Date: 2019/10/11 17:46
    */
    void init();
    /** description: 初始化客户端的配置信息
    * @return:
    * @Author: zeryts
    * @Date: 2019/10/11 17:46
    */
    void loadClientConfig();
    /** description: 初始化服务端的配置信息
    * @return:
    * @Author: zeryts
    * @Date: 2019/10/11 17:46
    */
    void loadServerConfig();
}
