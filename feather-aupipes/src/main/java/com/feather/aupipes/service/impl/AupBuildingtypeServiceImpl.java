package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupBuildingtype;
import com.feather.aupipes.mapper.AupBuildingtypeMapper;
import com.feather.aupipes.service.IAupBuildingtypeService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼栋类型Service业务层处理
 * 
 * @author fancy
 * @date 2020-07-03
 */
@Service
public class AupBuildingtypeServiceImpl implements IAupBuildingtypeService 
{
    @Autowired
    private AupBuildingtypeMapper aupBuildingtypeMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询楼栋类型
     * 
     * @param id 楼栋类型ID
     * @return 楼栋类型
     */
    @Override
    public AupBuildingtype selectAupBuildingtypeById(Long id)
    {
        return aupBuildingtypeMapper.selectAupBuildingtypeById(id);
    }

    /**
     * 查询楼栋类型列表
     * 
     * @param aupBuildingtype 楼栋类型
     * @return 楼栋类型
     */
    @Override
    public List<AupBuildingtype> selectAupBuildingtypeList(AupBuildingtype aupBuildingtype)
    {
        return aupBuildingtypeMapper.selectAupBuildingtypeList(aupBuildingtype);
    }

    /**
     * 新增楼栋类型
     * 
     * @param aupBuildingtype 楼栋类型
     * @return 结果
     */
    @Override
    public int insertAupBuildingtype(AupBuildingtype aupBuildingtype)
    {
        aupBuildingtype.setId(uidWorker.getNextId());
        return aupBuildingtypeMapper.insertAupBuildingtype(aupBuildingtype);
    }

    /**
     * 修改楼栋类型
     * 
     * @param aupBuildingtype 楼栋类型
     * @return 结果
     */
    @Override
    public int updateAupBuildingtype(AupBuildingtype aupBuildingtype)
    {
        return aupBuildingtypeMapper.updateAupBuildingtype(aupBuildingtype);
    }

    /**
     * 删除楼栋类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupBuildingtypeByIds(String ids)
    {
        return aupBuildingtypeMapper.deleteAupBuildingtypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除楼栋类型信息
     * 
     * @param id 楼栋类型ID
     * @return 结果
     */
    @Override
    public int deleteAupBuildingtypeById(Long id)
    {
        return aupBuildingtypeMapper.deleteAupBuildingtypeById(id);
    }
}
