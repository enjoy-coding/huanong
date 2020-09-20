package com.feather.patrol.domain;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;
import com.feather.common.core.text.Convert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 巡检日志对象 ptr_log
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PtrLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**  */
    private Long logId;
    private Long logCard;
    /** 二维码 */
    @Excel(name = "二维码")
    private String logQrcode;
    /** 坐标 */
    @Excel(name = "坐标")
    private String logCoordinate;
    /** 坐标（高德） */
    @Excel(name = "高德坐标")
    private String logCoordinateGd;
    /** 地点 */
    @Excel(name = "地点")
    private String logAddress;
    /** 高度 */
    @Excel(name = "高度")
    private String logAltitude;
    /** 静态地图 */
    @Excel(name = "静态地图")
    private String logMap;

    @Excel(name = "偏移（米）")
    private Integer logOffset;

    @Excel(name = "状态")
    private Integer logIssue;

    private PtrCard card;
    private List<PtrIssue> issues;
    private List<PtrFacade> facades;

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
        if (StringUtils.isNotBlank(logCoordinate)) {
            String[] arr = logCoordinate.split(",");
            if (arr.length == 2) {
                return Convert.toDouble(arr[index]);
            }
        }
        return null;
    }
}