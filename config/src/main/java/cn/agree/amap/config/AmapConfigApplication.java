package cn.agree.amap.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yangjian
 * @date 2018年06月22日
 * 配置中心
 */
@EnableConfigServer
@SpringCloudApplication
@ComponentScan
public class AmapConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmapConfigApplication.class, args);
	}
}