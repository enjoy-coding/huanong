package com.feather.lpscommunity.domain;

import java.util.List;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 党建信息对象 sc_partyBuild
 *
 * @author dufan
 * @date 2019-10-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ScPartyBuild extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long partyBuildId;

    /** null */
    @ApiModelProperty("党建标题")
    @Excel(name = "党建标题")
    private String partyBuildTitle;

    /** null */
    @Excel(name = "党建来源")
    @ApiModelProperty("党建来源")
    private String partyBuildSource;

    /** 党建发布时间 */
    @Excel(name = "党建发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("党建发布时间")
    private String partyBuildTime;

     /** null */
     @Excel(name = "封面图")
     @ApiModelProperty("封面图")
     private String partyBuildCover;

    /**
     * 内容
     */
    @Excel(name = "党建内容")
    @ApiModelProperty("党建内容")
    private String partyBuildContent;
    /**
     * 路径
     */
    @Excel(name = "党建文件路径")
    @ApiModelProperty("党建文件路径")
    private String partyBuildFile;

    /**
     * 类型
     */
    @Excel(name = "党建类型")
    @ApiModelProperty("党建类型")
    private String partyBuildType;

    /**
     * 视频上传类型
     */
    @Excel(name = "视频类型")
    @ApiModelProperty("视频类型")
    private String videoUpdateType;

    /**
     * 状态
     */
    @Excel(name = "党建状态")
    private String partyBuildStatus;

    /**
     * 照片集合
     */
    private List<ScFileInfo> files;

    public ScPartyBuild(String partyBuildType) {
        this.partyBuildType = partyBuildType;
    }

//    public  ScPartyBuild(Long partyBuildId,String partyBuildFile,String  )
}
