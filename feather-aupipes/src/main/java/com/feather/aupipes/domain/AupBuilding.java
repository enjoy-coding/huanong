package com.feather.aupipes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 楼栋信息对象 aup_building
 *
 * @author fancy
 * @date 2019-12-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AupBuilding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** null */
    @Excel(name = "null")
    private String name;
    /** null */
    @Excel(name = "null")
    private String no;
    /** null */
    @Excel(name = "null")
    private String typei;
    /** null */
    @Excel(name = "null")
    private String typen;
    /** 楼层 */
    @Excel(name = "楼层")
    private String floors;
    /** 单元 */
    @Excel(name = "单元")
    private String units;
    /** 区域编号 */
    @Excel(name = "区域编号")
    private String areano;
    /** 区域名称 */
    @Excel(name = "区域名称")
    private String areaname;
    /** 方位编号 */
    @Excel(name = "方位编号")
    private String sideno;
    /** 方位名称 */
    @Excel(name = "方位名称")
    private String sidename;
    /** null */
    @Excel(name = "null")
    private String waterno;
    /** null */
    @Excel(name = "null")
    private String watername;
    /** null */
    @Excel(name = "null")
    private String electricno;
    /** null */
    @Excel(name = "null")
    private String electricname;
    /** null */
    @Excel(name = "null")
    private String mapid;
    /** 经度 */
    @Excel(name = "经度")
    private String latitude;
    /** 纬度 */
    @Excel(name = "纬度")
    private String longitude;
    /** null */
    @Excel(name = "null")
    private String finishtime;
    /** 使用面积 */
    @Excel(name = "使用面积")
    private String usedarea;
    /** 建筑面积 */
    @Excel(name = "建筑面积")
    private String buildarea;
    /** null */
    @Excel(name = "null")
    private String modifyuserid;
    /** null */
    @Excel(name = "null")
    private String modifydate;
    /** null */
    @Excel(name = "null")
    private String createusername;
    /** null */
    @Excel(name = "null")
    private String createuserid;
    /** null */
    @Excel(name = "null")
    private String createdate;
    /** 详情 */
    @Excel(name = "详情")
    private String description;
    /** 是否能编辑 */
    @Excel(name = "是否能编辑")
    private String canwrite;
    /** 禁止标记 */
    @Excel(name = "禁止标记")
    private String enabledmark;
    /** 删除标记 */
    @Excel(name = "删除标记")
    private String deletemark;
    /** 序号 */
    @Excel(name = "序号")
    private String sortcode;
    /** 主键 */
    private String id;
    /** null */
    @Excel(name = "null")
    private String klassname;
    /** null */
    @Excel(name = "null")
    private String modifyusername;

    private String buildingType;//楼栋类型
}