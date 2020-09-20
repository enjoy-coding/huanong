package com.feather.aupipes.service;

import java.util.List;
import com.feather.aupipes.domain.NTAccountCategory;
import com.feather.common.core.domain.Ztree;

public interface NTAccountCategoryService {
    List<NTAccountCategory> selectCategoryList(NTAccountCategory category);

    NTAccountCategory selectCategoryById(String id);

    /**
     * 查询类型树
     * 
     * @param category 类型
     * @return 所有类型学信息
     */
    List<Ztree> selectCategoryTree(NTAccountCategory category);
    
}