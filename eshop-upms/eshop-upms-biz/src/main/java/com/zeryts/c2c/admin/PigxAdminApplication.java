package com.zeryts.c2c.admin;

import com.zeryts.c2c.common.feign.annotation.EnableSocialFeignClients;
import com.zeryts.c2c.common.security.annotation.EnableSocialResourceServer;
import com.zeryts.c2c.common.swagger.annotation.EnableSocialSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author zeryts
 * @date 2018年06月21日
 * <p>
 * 用户统一管理系统
 */
@EnableSocialSwagger2
@SpringCloudApplication
@EnableSocialFeignClients
@EnableSocialResourceServer
public class PigxAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigxAdminApplication.class, args);
	}

}
