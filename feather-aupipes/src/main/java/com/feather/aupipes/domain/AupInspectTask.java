package com.feather.aupipes.domain;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【请填写功能名称】对象 aup_inspect_task
 *
 * @author fancy
 * @date 2020-01-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectTask extends BaseEntity
{
  private static final long serialVersionUID = 1L;

  /** $column.columnComment */
  private Long taskId;
  /** 任务名称 */
  @Excel(name = "任务名称")
  private String taskName;
  /** 任务执行人 */
  @Excel(name = "任务执行人")
  private String taskWorker;
  /** 下发时间 */
  @Excel(name = "下发时间")
  private String allottime;
  /** 开始时间 */
  @Excel(name = "开始时间")
  private String starttime;
  /** 结束时间 */
  @Excel(name = "结束时间")
  private String endtime;
  /** 巡检时长 */
  @Excel(name = "巡检时长")
  private String duration;
  /** 应巡任务数 */
  @Excel(name = "应巡任务数")
  private Long tasknum;
  /** 已巡任务数 */
  @Excel(name = "已巡任务数")
  private Long donum;
  /** 漏巡任务数 */
  @Excel(name = "漏巡任务数")
  private Long undonum;
  /** 异常任务数 */
  @Excel(name = "异常任务数")
  private Long excepnum;
  /** 处理状态 */
  @Excel(name = "处理状态")
  private String state;
}