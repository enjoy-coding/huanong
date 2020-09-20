package com.feather.patrol.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import com.feather.common.core.text.Convert;
import com.feather.system.domain.SysDictData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 巡点对象 ptr_card
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PtrCard extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long cardId;
    /** 所属部门 */
    private Long cardDept;
    private String deptIdPath;
    private String deptName;
    private String deptNamePath;
    /** 类型 */
    @Excel(name = "类型")
    private String cardType;
    /** 二维码1 */
    @Excel(name = "二维码1")
    private String cardQrcode1;
    /** 二维码2 */
    @Excel(name = "二维码2")
    private String cardQrcode2;
    /** 二维码3 */
    @Excel(name = "二维码3")
    private String cardQrcode3;
    /** 二维码4 */
    @Excel(name = "二维码4")
    private String cardQrcode4;
    /** 二维码5 */
    @Excel(name = "二维码5")
    private String cardQrcode5;
    /** 线名 */
    @Excel(name = "线名")
    private String cardLine;
    /** 里程 */
    @Excel(name = "里程")
    private String cardMileage;
    /** 行别 */
    @Excel(name = "行别")
    private String cardSide;

    @Excel(name = "坐标")
    private String cardCoordinate;
    /** 坐标（高德） */
    @Excel(name = "高德坐标")
    private String cardCoordinateGd;
    /** 地点 */
    @Excel(name = "地点")
    private String cardAddress;

    private List<PtrFacade> facades;

    @Excel(name = "巡检计划（天）")
    private String patrolPlan;
    @Excel(name = "最近巡检")
    private Date lastPatrol;
    @Excel(name = "逾期巡检（天）")
    private Integer overdueDay;

    @Excel(name = "说明")
    private String desc;

    private List<SysDictData> standards;

    /**
     * 获取经度
     */
    public Double getLongitude() {
        return getXY(0);
    }

    /**
     * 获取纬度
     */
    public Double getLatitude() {
        return getXY(1);
    }

    private Double getXY(int index) {
        if (StringUtils.isNotBlank(cardCoordinate)) {
            String[] arr = cardCoordinate.split(",");
            if (arr.length == 2) {
                return Convert.toDouble(arr[index]);
            }
        }
        return null;
    }
}
