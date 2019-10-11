package com.alibaba.csp.sentinel.dashboard.datasource.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

/**
 * @author zeryts
 * @description: 持久化到数据库的Rule配置类
 * -----------------------------------
 * @title: RuleConfiEntity
 * @projectName amap
 * @date 2019/9/3 10:56
 */
@TableName("SYS_RULE_CONF")
public class RuleConfiEntity {
    /** 主键 **/
    @TableField("ID")
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 存储在redis中的key
     */
    @TableField("APP_NAME")
    private String appName;
    /**
     * 存储在redis中的授权内容
     */
    @TableField("CONTENT")
    private String content;
    /**
     * Rule的类型
     */
    @TableField("TYPE")
    private String type;
    /**
     * 删除标识
     */
    @TableField("DEL_FLAG")
    private Integer delFlag;

    public RuleConfiEntity() {
    }
    public RuleConfiEntity(String appName,String type){
        this.appName = appName;
        this.type = type;
    }
    public RuleConfiEntity(String id, String appName, String content, String type, Integer delFlag) {
        this.id = id;
        this.appName = appName;
        this.content = content;
        this.type = type;
        this.delFlag = delFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
