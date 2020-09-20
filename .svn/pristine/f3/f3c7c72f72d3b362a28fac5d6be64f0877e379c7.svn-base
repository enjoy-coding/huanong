package com.feather.aupipes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 巡检坐标对象 aup_inspect_coordinate
 *
 * @author fancy
 * @date 2020-03-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectCoordinate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 坐标id */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long coordinateId;
    /** 关联的任务id */
    @Excel(name = "关联的任务id")
    private Long taskId;
    /** 经度 */
    @Excel(name = "经度")
    private String longitude;
    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;
    @Excel(name = "高度")
    private String altitude;
}