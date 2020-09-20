package com.feather.hikvision.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 海康平台事件记录日志
 *
 * @author nothing
 * @date 2020/3/3 9:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HikvisionEventLog {
    private Long id;

    /**
     * 事件源
     */
    private String sourceFrom;

    /**
     * 创建事件
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
