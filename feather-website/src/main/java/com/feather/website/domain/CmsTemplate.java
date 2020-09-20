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
 * @date 2019-11-13 14:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("website_template")
public class CmsTemplate extends NutzBaseEntity {
    private static final long serialVersionUID = -4253221478038906887L;

    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @Column
    @Comment("模板名称")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String name;

    @Column
    @Comment("首页路径")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String indexTpl;

    @Column
    @Comment("模板页路径")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String listTpl;

    @Column
    @Comment("内容页路径")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String contentTpl;

    @Column
    @Comment("解析该模板的类名")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String className;

    @Column
    @Comment("解析该模板的类class字符串")
    @ColDefine(type = ColType.BINARY)
    private byte[] classInfo;

    @Column
    @Comment("封面")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String coverImage;

    @Column
    @Comment("序号")
    @ColDefine(type = ColType.INT)
    private int orderNum;
}
