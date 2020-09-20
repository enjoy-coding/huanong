package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupPlansType;
import com.feather.aupipes.mapper.AupPlansTypeMapper;
import com.feather.aupipes.service.IAupPlansTypeService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 预案类型Service业务层处理
 *
 * @author fancy
 * @date 2020-01-02
 */
@Service
public class AupPlansTypeServiceImpl implements IAupPlansTypeService {
    @Autowired
    private AupPlansTypeMapper aupPlansTypeMapper;

    /**
     * 查询预案类型
     *
     * @param id 预案类型ID
     * @return 预案类型
     */
    @Override
    public AupPlansType selectAupPlansTypeById(Long id) {
        return aupPlansTypeMapper.selectAupPlansTypeById(id);
    }

    /**
     * 查询预案类型列表
     *
     * @param aupPlansType 预案类型
     * @return 预案类型
     */
    @Override
    public List<AupPlansType> selectAupPlansTypeList(AupPlansType aupPlansType) {
        return aupPlansTypeMapper.selectAupPlansTypeList(aupPlansType);
    }

    /**
     * 新增预案类型
     *
     * @param aupPlansType 预案类型
     * @return 结果
     */
    @Override
    public int insertAupPlansType(AupPlansType aupPlansType) {
        return aupPlansTypeMapper.insertAupPlansType(aupPlansType);
    }

    /**
     * 修改预案类型
     *
     * @param aupPlansType 预案类型
     * @return 结果
     */
    @Override
    public int updateAupPlansType(AupPlansType aupPlansType) {
        return aupPlansTypeMapper.updateAupPlansType(aupPlansType);
    }

    /**
     * 删除预案类型对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupPlansTypeByIds(String ids) {
        return aupPlansTypeMapper.deleteAupPlansTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预案类型信息
     *
     * @param id 预案类型ID
     * @return 结果
     */
    @Override
    public int deleteAupPlansTypeById(Long id) {
        return aupPlansTypeMapper.deleteAupPlansTypeById(id);
    }

    @Override
    public List<Ztree> selectWarringCategoryTree() {
        List<Ztree> ztrees = new ArrayList<>();
        List<AupPlansType> aupPlansTypes = aupPlansTypeMapper.selectAupPlansTypeList(null);
        for (AupPlansType aupPlansType : aupPlansTypes) {
            Ztree ztree = new Ztree();
            ztree.setId(aupPlansType.getId());
            ztree.setpId(Long.parseLong(aupPlansType.getParetid()));
            ztree.setName(aupPlansType.getYatype());
            ztree.setCode("");
            ztree.setTitle("");

            ztrees.add(ztree);
        }

        return ztrees;
    }
}
