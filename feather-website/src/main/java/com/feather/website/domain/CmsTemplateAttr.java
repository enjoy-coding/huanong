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
 * @date 2019-11-13 15:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("website_template_attr")
public class CmsTemplateAttr extends NutzBaseEntity {
    private static final long serialVersionUID = -6091098089377582690L;

    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @Column
    @Comment("模板id")
    @ColDefine(type = ColType.INT)
    private Long tplId;

    private String tplName;

    @Column
    @Comment("块名")
    @ColDefine(type = ColType.VARCHAR)
    private String blockName;

    @Column
    @Comment("块类型")
    @ColDefine(type = ColType.VARCHAR)
    private String blockType;

    @Column
    @Comment("块解析方法")
    @ColDefine(type = ColType.VARCHAR)
    private String method;

    @Column
    @Comment("模块显示记录数")
    @ColDefine(type = ColType.INT)
    private int frontSize;

    @Column
    @Comment("模块文字长度")
    @ColDefine(type = ColType.INT)
    private int textLength;

    @Column
    @Comment("序号")
    @ColDefine(type = ColType.INT)
    private long orderNum;
}