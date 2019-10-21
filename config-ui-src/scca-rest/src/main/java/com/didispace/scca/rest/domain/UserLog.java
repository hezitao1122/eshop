package com.didispace.scca.rest.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户操作日志
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "CONFIG_USER_LOG")
public class UserLog {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 日志类型
     */
    private Integer type;

    /**
     * 补充数据
     */
    private String data;

}