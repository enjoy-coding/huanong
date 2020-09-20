package com.feather.meeting.domain;

import org.apache.commons.io.FilenameUtils;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Strings;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.feather.common4nutz.domain.NutzBaseEntity;

import cn.afterturn.easypoi.entity.ImageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-11-07 16:15
 */
@ApiModel(value = "meetingPersonnel", description = "会议参会人员信息")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table("meeting_personnel")
public class MeetingPersonnel extends NutzBaseEntity {
    private final int WIDTH = 150;
    private final int HEIGHT = 220;


    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @ApiModelProperty(value = "会议id")
    @Column
    @Comment("会议id")
    @ColDefine(type = ColType.INT)
    private Long meetingId;

    @ApiModelProperty(value = "会议标题")
    private String meetingTitle;

    @ApiModelProperty(value = "姓名")
    @Column
    @Comment("姓名")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String userName;

    @ApiModelProperty(value = "学校")
    @Column
    @Comment("学校")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String school;

    @ApiModelProperty(value = "工作单位")
    @Column
    @Comment("工作单位")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String unit;

    @ApiModelProperty(value = "职务")
    @Column
    @Comment("职务")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String job;

    @ApiModelProperty(value = "地址")
    @Column
    @Comment("地址")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String address;

    @ApiModelProperty(value = "编码")
    @Column
    @Comment("编码")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String code;

    @ApiModelProperty(value = "手机号")
    @Column
    @Comment("手机号")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String mobile;

    @ApiModelProperty(value = "电话")
    @Column
    @Comment("电话")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String telephone;

    @ApiModelProperty(value = "照片")
    @Column
    @Comment("照片")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String photo;

    @ApiModelProperty(value = "参会人员唯一id")
    @Column
    @Comment("参会人员唯一id")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String uniqueId;

    @ApiModelProperty(hidden = true)
    private PictureRenderData picture;

    @ApiModelProperty(hidden = true)
    private ImageEntity image;

    private String openId;

    public void setPicture(String basePath) {
        setPicture(WIDTH, HEIGHT, basePath);
    }

    public void setPicture(int width, int height, String basePath) {
        if (Strings.isNotBlank(photo)) {
            String ext = "." + FilenameUtils.getExtension(photo);
            this.picture = new PictureRenderData(width, height, ext,
                    BytePictureUtils.getUrlBufferedImage(basePath + photo));
        }
    }

    public void setImage(String basePath) {
        setImage(WIDTH, HEIGHT, basePath);
    }

    public void setImage(int width, int height, String basePath) {
        if (Strings.isNotBlank(photo)) {
            ImageEntity image = new ImageEntity();
            image.setWidth(width);
            image.setHeight(height);
            image.setUrl(basePath + photo);
            this.image = image;
        }
    }
}
