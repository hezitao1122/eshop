package com.alibaba.csp.sentinel.dashboard.repository.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.RuleConfiEntity;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zeryts
 * @description: RuleConfig操作的类
 * -----------------------------------
 * @title: InDBRuleConfigMapper
 * @projectName amap
 * @date 2019/9/3 14:56
 */
@Service
public class InDBRuleConfigService {

    @Autowired
    private InDBRuleConfigRepository repository;

    @Transactional
    public boolean updateByAppAndType(String appName,String type,String content){
        RuleConfiEntity entity = new RuleConfiEntity(appName,type);
        EntityWrapper wr = new EntityWrapper(entity);
        List<RuleConfiEntity> list = repository.selectList(wr);
        Integer flag = 0;
        if(list ==null || list.size() ==0){
            //数据库中不存在
            entity.setContent(content);
            entity.setDelFlag(1);
            flag = repository.insert(entity);
        }else{
            RuleConfiEntity confiEntity = list.get(0);
            confiEntity.setContent(content);
            flag = repository.updateById(confiEntity);
        }
        if(flag == 0)
            return false;
        return true;
    }

    public List<RuleConfiEntity> findAll(){
        RuleConfiEntity entity = new RuleConfiEntity();
        entity.setDelFlag(1);
        EntityWrapper wr = new EntityWrapper(entity);
        return repository.selectList(wr);
    }
}
