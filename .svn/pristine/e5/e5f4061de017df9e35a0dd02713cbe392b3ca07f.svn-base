package com.feather.website.base;

import org.nutz.dao.Condition;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.plugin.spring.boot.service.BaseService;
import org.nutz.plugin.spring.boot.service.entity.DataBaseEntity;

import java.util.List;

/**
 * @author nothing
 * @date 2019-11-01 17:13
 */
public class NutzBaseService<T extends DataBaseEntity> extends BaseService {

    /**
     * 查询获取部分字段
     * @param fieldName
     *            支持通配符 ^(a|b)$
     * @param cnd
     * @return
     */
    public List<T> query(String fieldName, Condition cnd) {
        return Daos
                .ext(this.dao(),
                        FieldFilter.create(this.getEntityClass(), fieldName))
                .query(this.getEntityClass(), cnd);
    }
}
