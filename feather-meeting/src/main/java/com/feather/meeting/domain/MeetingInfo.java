package com.feather.meeting.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.feather.common4nutz.domain.NutzBaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-11-07 15:46
 */
@ApiModel(value = "meetingInfo", description = "会议对象")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table("meeting_info")
public class MeetingInfo extends NutzBaseEntity {
    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @ApiModelProperty(value = "会议标题")
    @Column
    @Comment("会议标题")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String title;

    @ApiModelProperty(value = "会议编码")
    @Column
    @Comment("会议编码")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String code;

    @Column
    @Comment("会议开始时间")
    @ColDefine(type = ColType.DATETIME)
    private Date beginTime;

    @ApiModelProperty(value = "会议开始时间")
    private String beginTimeStr;

    @Column
    @Comment("会议结束时间")
    @ColDefine(type = ColType.DATETIME)
    private Date endTime;

    @ApiModelProperty(value = "会议结束时间")
    private String endTimeStr;

    @ApiModelProperty(value = "会议地点")
    @Column
    @Comment("会议地点")
    @ColDefine(type = ColType.DATETIME)
    private String place;

    @ApiModelProperty(value = "会议内容")
    @Column
    @Comment("会议内容")
    @ColDefine(type = ColType.VARCHAR)
    private String content;

    @ApiModelProperty(value = "附件")
    @Column
    @Comment("附件")
    @ColDefine(type = ColType.VARCHAR, width = 1000)
    private String attaches;

    @ApiModelProperty(value = "微信群二维码")
    @Column
    @Comment("微信群二维码")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String qrCode;

}
