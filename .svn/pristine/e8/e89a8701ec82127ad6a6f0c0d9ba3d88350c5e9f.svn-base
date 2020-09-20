package com.feather.aupipes.utils.vo;

import com.feather.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author sky
 * @date 2020/6/23 16:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupInspectVo implements Serializable {

    @Excel(name = "巡检任务名称")
    private String inspectName;
    @Excel(name = "巡检区域")
    private String inspectArea;
    @Excel(name = "发布人员")
    private String distributeMan;
    @Excel(name = "巡检人员")
    private String inspectWorker;
    @Excel(name = "下发时间")
    private String distributeTime;
    @Excel(name = "开始时间")
    private String startTime;
    @Excel(name = "结束时间")
    private String endTime;
    @Excel(name = "巡检状态")
    private String inspectStatus;

    private List<AupInspectDetailVo> aupInspectDetailVoList;

    public static void main(String[] args) throws ClassNotFoundException {
//        Class clazz =  Class.forName("com.feather.aupipes.utils.vo.AupInspectVo");
        Class clazz =  AupInspectVo.class;
        //获取所有成员变量
        Field[] fs = clazz.getDeclaredFields();
        for(int i = 0;i<fs.length;i++){
            System.out.println(fs[i]);
        }
    }
}
