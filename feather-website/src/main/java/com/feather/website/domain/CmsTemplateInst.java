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
 * @date 2019-11-13 15:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("website_template_inst")
public class CmsTemplateInst extends NutzBaseEntity {

    private static final long serialVersionUID = -6227225746335888181L;

    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @Column
    @Comment("模板实例名称")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String name;

    @Column
    @Comment("模板id")
    @ColDefine(type = ColType.INT)
    private Long tplId;

    @Column
    @Comment("模板名称")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String tplName;

    @Column
    @Comment("站点id")
    @ColDefine(type = ColType.INT)
    private Long siteId;

    @Column
    @Comment("模板名称")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String siteName;

    @Column
    @Comment("模板实例值,一个json字符串")
    @ColDefine(type = ColType.VARCHAR, width = 2000)
    private String value;
}
