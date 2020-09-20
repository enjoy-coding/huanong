package com.feather.lpscommunity.domain;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.feather.common.annotation.Excel;
import com.feather.common.core.domain.BaseEntity;

/**
 * 志愿者风采对象 sc_volunteer_style
 * 
 * @author fancy
 * @date 2019-11-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScVolunteerStyle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 风采id */
    private Long styleId;
    /** 志愿者id */
    @Excel(name = "志愿者id")
    private Long volunteerId;
    /** 标题 */
    @Excel(name = "标题")
    private String styleTitle;
    /** 名称 */
    @Excel(name = "名称")
    private String styleName;
    /** 内容 */
    @Excel(name = "内容")
    private String styleContent;

    private List<ScFileInfo> files;

    private ScVolunteer volunteer;

    public ScVolunteerStyle(Long styleId, Long volunteerId, String styleTitle, String styleContent) {
        this.styleId = styleId;
        this.volunteerId = volunteerId;
        this.styleTitle = styleTitle;
        this.styleContent = styleContent;
    }

    public ScVolunteerStyle(String styleTitle, Long volunteerId) {
        this.styleTitle = styleTitle;
        this.volunteerId = volunteerId;

    }
}