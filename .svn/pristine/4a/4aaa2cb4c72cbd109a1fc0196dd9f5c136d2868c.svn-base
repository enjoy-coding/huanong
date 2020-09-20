package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 巡检任务微信消息推送日志对象 aup_inspect_log
 * 
 * @author fancy
 * @date 2020-06-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 微信openid */
    @Excel(name = "微信openid")
    private String openId;
    /** 巡检任务id */
    @Excel(name = "巡检任务id")
    private Long taskId;
    /** 巡检人员id */
    @Excel(name = "巡检人员id")
    private Long userId;
    private String username;
    /** 巡检任务标题 */
    @Excel(name = "巡检任务标题")
    private String title;
    /** 巡检任务内容 */
    @Excel(name = "巡检任务内容")
    private String content;
}
