package cn.agree.amap.cluster.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 * @date 2018年06月21日
 * 网关应用
 */
@SpringCloudApplication
@RestController
@EnableDiscoveryClient
public class AmapGatewayRedisApplication {
	@Autowired
	private DiscoveryClient discoveryClient;
	public static void main(String[] args) {
		SpringApplication.run(AmapGatewayRedisApplication.class, args);
	}
	@RequestMapping(value = "index")
	public Map<String,Object> index(){
		List<ServiceInstance> instances = discoveryClient.getInstances("AMAP-GATEWAY-REDIS");
		instances.forEach(in ->{
			URI uri = in.getUri();
			System.out.println(uri);
		});
		Map<String,Object> map = new HashMap<>();
		map.put("code",200);
		map.put("msg","success");
		return map;
	}



}
