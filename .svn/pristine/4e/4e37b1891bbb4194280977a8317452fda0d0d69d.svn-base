package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupWarringCategory;
import com.feather.aupipes.mapper.AupWarringCategoryMapper;
import com.feather.aupipes.service.IAupWarringCategoryService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.text.Convert;

/**
 * 预警类型Service业务层处理
 *
 * @author fancy
 * @date 2019-12-20
 */
@Service
public class AupWarringCategoryServiceImpl implements IAupWarringCategoryService {
    @Autowired
    private AupWarringCategoryMapper aupWarringCategoryMapper;

    /**
     * 查询预警类型
     *
     * @param id
     *            预警类型ID
     * @return 预警类型
     */
    @Override
    public AupWarringCategory selectAupWarringCategoryById(Long id) {
        return aupWarringCategoryMapper.selectAupWarringCategoryById(id);
    }

    /**
     * 查询预警类型列表
     *
     * @param aupWarringCategory
     *            预警类型
     * @return 预警类型
     */
    @Override
    public List<AupWarringCategory> selectAupWarringCategoryList(AupWarringCategory aupWarringCategory) {
        return aupWarringCategoryMapper.selectAupWarringCategoryList(aupWarringCategory);
    }

    private Ztree getZtree(AupWarringCategory aupWarringCategorie) {

        Ztree ztree = new Ztree();
        ztree.setId(aupWarringCategorie.getId());
        ztree.setpId(Long.parseLong(aupWarringCategorie.getPid()));
        ztree.setName(aupWarringCategorie.getName());
        ztree.setCode(aupWarringCategorie.getCategoryid());
        ztree.setTitle("");
        return ztree;
    }

    @Override
    public List<Ztree> selectWarringCategoryTree(String name) {
        AupWarringCategory category = new AupWarringCategory();
        category.setName(name);
        List<Ztree> ztrees = new ArrayList<>();
        List<AupWarringCategory> aupWarringCategories = aupWarringCategoryMapper.selectAupWarringCategoryList(category);
        category.setId(0L);
        category.setName("全部");
        category.setCode("qb");
        category.setCategoryid("qb");
        category.setPid("0");
        aupWarringCategories.add(category);
        String zreo = "0";
        for (AupWarringCategory aupWarringCategorie : aupWarringCategories) {
            ztrees.add(getZtree(aupWarringCategorie));
            String pid = aupWarringCategorie.getPid();
            if (!"".equals(name)) {
                if (!pid.equals(zreo)) {
                    AupWarringCategory entity = aupWarringCategoryMapper.getWarringListByPid(pid);
                    ztrees.add(getZtree(entity));
                    if (!entity.getPid().equals(zreo)) {
                        AupWarringCategory entity1 = aupWarringCategoryMapper.getWarringListByPid(entity.getPid());
                        ztrees.add(getZtree(entity1));
                    }
                }
            }

        }
        return ztrees;
    }

    /**
     * 新增预警类型
     *
     * @param aupWarringCategory
     *            预警类型
     * @return 结果
     */
    @Override
    public int insertAupWarringCategory(AupWarringCategory aupWarringCategory) {
        return aupWarringCategoryMapper.insertAupWarringCategory(aupWarringCategory);
    }

    /**
     * 修改预警类型
     *
     * @param aupWarringCategory
     *            预警类型
     * @return 结果
     */
    @Override
    public int updateAupWarringCategory(AupWarringCategory aupWarringCategory) {
        return aupWarringCategoryMapper.updateAupWarringCategory(aupWarringCategory);
    }

    /**
     * 删除预警类型对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupWarringCategoryByIds(String ids) {
        return aupWarringCategoryMapper.deleteAupWarringCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预警类型信息
     *
     * @param id
     *            预警类型ID
     * @return 结果
     */
    @Override
    public int deleteAupWarringCategoryById(Long id) {
        return aupWarringCategoryMapper.deleteAupWarringCategoryById(id);
    }
}