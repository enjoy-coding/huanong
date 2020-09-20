package com.feather.aupipes.mapper;

import java.util.List;

import com.feather.aupipes.domain.NTAccountCategory;

/**
 * 用能性质mapper
 * 20200731
 * dufan
 */
public interface NTAccountCategoryMapper {
    public List<NTAccountCategory> selectCategoryList(NTAccountCategory ntAccountCategory);

    public NTAccountCategory selectCategoryById(String id);
}