package com.feather.website.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feather.common4nutz.domain.NutzBaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-10-23 15:57
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table("website_article")
public class CmsArticle extends NutzBaseEntity {
    private static final long serialVersionUID = 1944045928714618191L;

    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @Column
    @Comment("文章类型(0:普通文章，1:外链文章，直接跳走)")
    @ColDefine(type = ColType.INT)
    private Integer type;

    @Column
    @Comment("文章标题")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String title;

    @Column
    @Comment("副标题")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String subTitle;

    @Column
    @Comment("栏目id")
    @ColDefine(type = ColType.INT)
    private Long articleColumnId;

    @Column
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String articleColumnName;

    @Column
    @Comment("文章简介")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String summary;

    @Column
    @Comment("文章关键字")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String keywords;

    @Column
    @Comment("来源")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String sourceFrom;

    @Column
    @Comment("文章作者")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String author;

    @Column
    @Comment("发布人")
    @ColDefine(type = ColType.VARCHAR, width = 50)
    private String publisher;

    @Column
    @Comment("发布时间")
    @ColDefine(type = ColType.DATETIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishAt;

    public String publishAtStr;

    @Column
    @Comment("发布人id")
    @ColDefine(type = ColType.INT)
    private Long publisherId;

    @Column("isTop")
    @Comment("是否置顶")
    @ColDefine(type = ColType.BOOLEAN)
    private Boolean isTop;

    @Column
    @Comment("外链")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String url;

    @Column
    @Comment("封面")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String coverImage;

    // 前台是否显示图片
    private Integer showCoverImage;

    @Column
    @Comment("附件是否可以下载,默认附件可以下载")
    @ColDefine(type = ColType.BOOLEAN)
    private boolean canDownload;

    @Column
    @Comment("文章内容")
    @ColDefine(type = ColType.TEXT)
    private String content;

    @Column
    @Comment("审核状态: 0 未审核 1 已通过 2 未通过")
    @ColDefine(type = ColType.INT)
    private Integer auditStatus;

    @Column
    @Comment("审核人")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String auditor;

    @Column
    @Comment("审核人id")
    @ColDefine(type = ColType.INT)
    private Long auditorId;

    @Column
    @Comment("审核说明(针对于不通过的情况)")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String auditRemark;

    /*
     * @Column
     * 
     * @Comment("可见范围，针对前台:0 都可见 1指定部门可见")
     * 
     * @ColDefine(type = ColType.INT) private Integer viewRange;
     * 
     * @Column
     * 
     * @Comment("可见范围部门id集合,用分号隔开")
     * 
     * @ColDefine(type = ColType.TEXT) private String viewRangeIds;
     * 
     * @Column
     * 
     * @Comment("可见范围人员id集合,用分号隔开")
     * 
     * @ColDefine(type = ColType.TEXT) private String viewRangeUserIds;
     */

    private String viewRangeNames = "";

    private String viewRangeUserNames = "";

    /*
     * @Column
     * 
     * @Comment("针对于文章分享的情况，分享到不同的栏目中")
     * 
     * @ColDefine(type = ColType.INT) private Long shareFrom;
     * 
     * private String shareArtileColumnIds; private String
     * shareArticleColumnNames;
     */

    @Column
    @Comment("阅读数")
    @ColDefine(type = ColType.INT)
    private Integer viewCount;

    @Column
    @Comment("排序字段")
    @ColDefine(type = ColType.INT)
    private Long orderNum;

}
