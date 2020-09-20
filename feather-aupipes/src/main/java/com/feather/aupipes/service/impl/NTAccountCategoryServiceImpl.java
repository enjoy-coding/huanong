package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.feather.aupipes.domain.NTAccountCategory;
import com.feather.aupipes.mapper.NTAccountCategoryMapper;
import com.feather.aupipes.service.NTAccountCategoryService;
import com.feather.common.core.domain.Ztree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NTAccountCategoryServiceImpl implements NTAccountCategoryService {

    @Autowired
    NTAccountCategoryMapper categoryMapper;
    
    @Override
    public List<NTAccountCategory> selectCategoryList(NTAccountCategory category) {
        return categoryMapper.selectCategoryList(category);
    }

    @Override
    public NTAccountCategory selectCategoryById(String id) {
        return categoryMapper.selectCategoryById(id);
    }

    /**
     * 查询类型树
     * 
     * @param category 类型
     * @return 所有类型学信息
     */
    @Override
    public List<Ztree> selectCategoryTree(NTAccountCategory category) {
        List<NTAccountCategory> categoryList = categoryMapper.selectCategoryList(category);
        return initZtree(categoryList);
    }

 

    /**
     * 对象转用能性质树
     *
     * @param categoryList     用能性质列表
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<NTAccountCategory> categoryList) {
        List<Ztree> ztrees = new ArrayList<>();
        for (NTAccountCategory category : categoryList) {
            Ztree ztree = new Ztree();
            ztree.setId_(category.getId());
            ztree.setPid_(category.getParent());
            ztree.setLevel(category.getLevel() == null ? 0 : Integer.parseInt(category.getLevel()));
            ztree.setName(category.getName());
            ztree.setNamePath(category.getPath());
            ztree.setTitle(category.getName());

            ztree.setChecked(true);

            ztrees.add(ztree);

        }
        return ztrees;
    }

}