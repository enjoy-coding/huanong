package com.feather.aupipes.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupPlansManage;
import com.feather.aupipes.domain.AupWarringCategory;
import com.feather.aupipes.mapper.AupPlansManageMapper;
import com.feather.aupipes.service.IAupPlansManageService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.text.Convert;

/**
 * 预案管理Service业务层处理
 *
 * @author niecheng
 * @date 2019-12-30
 */
@Service
public class AupPlansManageServiceImpl implements IAupPlansManageService {
    @Autowired
    private AupPlansManageMapper aupPlansManageMapper;

    /**
     * 查询预案管理
     *
     * @param id
     *            预案管理ID
     * @return 预案管理
     */
    @Override
    public AupPlansManage selectAupPlansManageById(Long id) {
        return aupPlansManageMapper.selectAupPlansManageById(id);
    }

    @Override
    public Long selectAupPlansManageCount(AupPlansManage aupPlansManage) {
        return aupPlansManageMapper.selectAupPlansManageCount(aupPlansManage);
    }

    /**
     * 查询预案管理列表
     *
     * @param aupPlansManage
     *            预案管理
     * @return 预案管理
     */
    @Override
    public List<AupPlansManage> selectAupPlansManageList(AupPlansManage aupPlansManage) {
        return aupPlansManageMapper.selectAupPlansManageList(aupPlansManage);
    }

    /**
     * 新增预案管理
     *
     * @param aupPlansManage
     *            预案管理
     * @return 结果
     */
    @Override
    public int insertAupPlansManage(AupPlansManage aupPlansManage) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        aupPlansManage.setEditTime(sdf.format(date));
        return aupPlansManageMapper.insertAupPlansManage(aupPlansManage);
    }

    /**
     * 修改预案管理
     *
     * @param aupPlansManage
     *            预案管理
     * @return 结果
     */
    @Override
    public int updateAupPlansManage(AupPlansManage aupPlansManage) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        aupPlansManage.setEditTime(sdf.format(date));
        return aupPlansManageMapper.updateAupPlansManage(aupPlansManage);
    }

    /**
     * 删除预案管理对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupPlansManageByIds(String ids) {
        return aupPlansManageMapper.deleteAupPlansManageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预案管理信息
     *
     * @param id
     *            预案管理ID
     * @return 结果
     */
    @Override
    public int deleteAupPlansManageById(Long id) {
        return aupPlansManageMapper.deleteAupPlansManageById(id);
    }

    @Override
    public AjaxResult selectInitSelect(String categoryparentid) {

        AupWarringCategory aupWarringCategory = new AupWarringCategory();
        String zreo = "0";
        String one =" 1";
        if (categoryparentid.equals(zreo)) {
            aupWarringCategory.setPid(categoryparentid);
        } else if (categoryparentid.equals(one)) {
            aupWarringCategory.setPid(categoryparentid);
        } else {
            aupWarringCategory.setPid(categoryparentid);
        }
        List<AupWarringCategory> aupWarringCategories = aupPlansManageMapper
                .selectAupWarringCategoryList(aupWarringCategory);

        return AjaxResult.success(aupWarringCategories);
    }

    @Override
    public List<Map> selectAupUsersListArr(String ldIds) {
        String character = ",";
        String[] lzArr = ldIds.split(character);
        return aupPlansManageMapper.selectAupUsersListArr(lzArr);
    }

    @Override
    public List<Map> selectJcbaManagers(String ldIds) {
        String character = ",";
        String[] lzArr = ldIds.split(character);
        return aupPlansManageMapper.selectJcbaManagers(lzArr);
    }

    @Override
    public List<Map> selectJcbaManager(String ld) {
        return aupPlansManageMapper.selectJcbaManager(ld);
    }

    @Override
    public List<Map> selectAupUsersListArrAll() {
        return aupPlansManageMapper.selectAupUsersListArrAll();
    }
}
