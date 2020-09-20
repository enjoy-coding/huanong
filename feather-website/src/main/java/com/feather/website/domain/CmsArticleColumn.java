package com.feather.website.domain;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.feather.common4nutz.domain.NutzBaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nothing
 * @date 2019-10-23 15:58
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table("website_article_column")
public class CmsArticleColumn extends NutzBaseEntity {
    private static final long serialVersionUID = 7426522899317389815L;

    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @Column
    @Comment("名称")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String name;

    @Column
    @Comment("别名,英文名称(具有唯一性，用于查找栏目和子栏目)")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String alias;

    @Column
    @Comment("类型，0为默认是本站的，1为链接,表明该栏目就是一个连接")
    @ColDefine(type = ColType.INT)
    private Integer type;

    @Column
    @Comment("所属层级，最顶级为1，其他的再次累加")
    @ColDefine(type = ColType.INT)
    private Long level;

    @Column
    @Comment("父级ID")
    @ColDefine(type = ColType.INT)
    private Long parentId;

    @Column
    @Comment("父级名称")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String parentName;

    @Column
    @Comment("所属站点id")
    @ColDefine(type = ColType.INT)
    private Long siteId;

    // 所属站点名(非数据库字段)
    private String siteName;

    @Column
    @Comment("栏目简介")
    @ColDefine(type = ColType.VARCHAR, width = 1000)
    private String summary;

    @Column
    @Comment("其他栏目简介")
    @ColDefine(type = ColType.VARCHAR, width = 1000)
    private String otherSummary;

    @Column
    @Comment("是否需要一次审核，0为默认,0是不需要，1为需要")
    @ColDefine(type = ColType.INT)
    private Integer publishAudit;

    @Column
    @Comment("是否需要二次审核，0为默认,0是不需要，1为需要")
    @ColDefine(type = ColType.INT)
    private Integer shareAudit;

    @Column
    @Comment("url,仅针对于type为外部链接类型")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String url;

    @Column
    @Comment("是否是导航(0:不是,1:是)，导航页一般显示在首页顶部")
    @ColDefine(type = ColType.INT)
    private Integer isNav;

    @Column
    @Comment("首页最大记录数")
    @ColDefine(type = ColType.INT)
    private Integer frontMaxSize;

    @Column
    @Comment("是否在首页上显示该栏目,1为显示,0为不显示")
    private Integer frontVisible;

    @Column
    @Comment("首页栏目内容获取方法")
    @ColDefine(type = ColType.INT)
    private Integer frontListMethod;

    @Column
    @Comment("列表模板（仅针对于非单页情况）")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String listTpl;

    @Column
    @Comment("内容模板（仅针对于非单页情况）")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String contentTpl;

    @Column
    @Comment("排序")
    @ColDefine(type = ColType.INT)
    private Integer orderNum;
}
