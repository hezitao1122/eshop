package cn.agree.amap;

import cn.agree.amap.config.AmapConfigApplication;
import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvParam;
import com.didispace.scca.core.domain.EnvParamRepo;
import com.didispace.scca.core.domain.EnvRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author zeryts
 * @description: 加载yaml文件
 * -----------------------------------
 * @title: ImpportYamlFileTest
 * @projectName amap
 * @date 2019/9/26 19:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AmapConfigApplication.class})
public class ImpportYamlFileTest {

    @Autowired
    private EnvParamRepo paramRepo;
    @Autowired
    private EnvRepo repo;

    Env env = null;
    private List<EnvParam> list = new ArrayList<>();
    @Test
    public void test1(){
        Yaml yaml = new Yaml();
        repo.findById("408946816bd2115d016bd21b898b0001").get();
        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get("D:\\projects\\amap\\amap-config\\src\\main\\resources\\common-config-dev.yml"), StandardCharsets.UTF_8))
        {
            Map map = yaml.loadAs(reader, Map.class);
            prints(map,"");
            paramRepo.saveAll(list);
        } catch(
                IOException e)

        {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void prints(Map map,String path){

        map.forEach((k,v)->{
            if(v instanceof  Map){
                if(path.equals(""))
                    prints((Map) v,(String)k);
                else
                    prints((Map) v,path + "."+k);
            }else{
                String key = null;
                if(path.equals("")){
//                    System.out.println(k);
                    key = k.toString();
                }else{
//                    System.out.println(path+"."+k);
                    key = (path+"."+k);
                }
                String value = v.toString();

                EnvParam param = new EnvParam();
                param.setPKey(key);
                param.setPValue(value);
                param.setEnv(env);
                list.add(param);
            }
        });
    }

}
