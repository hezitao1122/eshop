package cn.agree.amap.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zeryts
 * @description: 环境变量参数操作的切面
 * -----------------------------------
 * @title: EnvParamConfig
 * @projectName amap
 * @date 2019/8/19 10:52
 */
@Aspect
@Component
@Configuration
public class EnvParamConfig {

    @Autowired
    private JdbcTemplate jdbc;
    private final PropertiesResultSetExtractor extractor = new PropertiesResultSetExtractor();
    @Value("${spring.cloud.config.server.jdbc.evnSql}")
    private String envSQL;

    @Pointcut(value = "execution(public * org.springframework.cloud.config.server.environment.JdbcEnvironmentRepository.findOne(..) )")
    private void point() {
    }

    /**
     * description: 获取参数-改变结果额
     *
     * @param proceedingJoinPoint
     * @return: java.lang.Object
     * @Author: zeryts
     * @Date: 2019/8/19 10:57
     */
    @Around(value = "point()")
    public Object pointcut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        //获取环境
        Object profileO = args[1];

        //执行目标方法
        Object proceed = proceedingJoinPoint.proceed();
        if (profileO != null) {
            try {
                //appName
                String appName = args[0].toString();
                String profile = profileO.toString();
                if (StringUtils.isEmpty(profile)) {
                    profile = "default";
                }
                //去库里面查询当前环境的变量参数
                Environment environment = (Environment) proceed;
                Map<String, String> next = (Map<String, String>) jdbc.query(this.envSQL,
                        new Object[]{profile}, this.extractor);
                if (!next.isEmpty()) {
//                environment.add(new PropertySource( profile, next));
                    List<PropertySource> propertySources = environment.getPropertySources();
                    for (PropertySource pro : propertySources) {
                        String name = pro.getName();
                        if (appName != null && name != null && (appName + "-" + profile).equalsIgnoreCase(name)) {
                            Map source = pro.getSource();
                            for (Map.Entry<String, String> en : next.entrySet()) {
                                String key = en.getKey();
                                String value = en.getValue();
                                if (!source.containsKey(key)) {
                                    source.put(key, value);
                                }
                            }
                        }
                    }
                }
                return environment;
            } catch (Exception e) {

            }
        }
        return proceed;
    }


    class PropertiesResultSetExtractor implements ResultSetExtractor<Map<String, String>> {

        @Override
        public Map<String, String> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, String> map = new LinkedHashMap<>();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getString(2));
            }
            return map;
        }
    }

}
