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
 * @date 2019-10-23 15:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("website_site")
public class CmsSite extends NutzBaseEntity {
    private static final long serialVersionUID = 4289504676121988499L;

    @Id
    @ColDefine(type = ColType.INT)
    private Long id;

    @Column
    @Comment("名称(一般推荐为中文名称)")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String name;

    @Column
    @Comment("别名(一定为英文，具有唯一性)")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String alias;

    @Column
    @Comment("站点所属部门")
    @ColDefine(type = ColType.INT)
    private Long deptId;

    private String deptName = "";

    @Column
    @Comment("域名")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String realmName;

    @Column
    @Comment("模板实例id")
    @ColDefine(type = ColType.INT)
    private Long tplInstId;

    private String tplInstName = "";

    @Column
    @Comment("目录")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String folder;

    @Column
    @Comment("首页模板(后面栏目继承于站点)")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String indexTpl;

    @Column
    @Comment("列表模板(后面栏目继承于站点)")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String listTpl;

    @Column
    @Comment("内容模板(后面栏目继承于站点)")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String contentTpl;

    @Column
    @Comment("关键字")
    @ColDefine(type = ColType.VARCHAR, width = 250)
    private String keyword;

    @Column
    @Comment("描述")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String description;

    @Column
    @Comment("背景图")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String logo;

    @Column
    @Comment("排序")
    @ColDefine(type = ColType.INT)
    private Integer orderNum;

    @Column
    @Comment("状态")
    @ColDefine(type = ColType.INT)
    private Integer state;
}
