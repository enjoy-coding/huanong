package com.feather.aupipes.utils.vo;

import com.feather.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author sky
 * @date 2020/6/23 9:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectDetailVo implements Serializable {

    @Excel(name = "巡检地址")
    private String inspectAddress;
    /** 设备类型 */
    @Excel(name = "地点类型")
    private String addressType;
    @Excel(name = "巡检时间")
    private String inspectTime;
    @Excel(name = "设备状态")
    private String status;

    List<AupInspectServiceVo> aupInspectServiceVoList;
}
