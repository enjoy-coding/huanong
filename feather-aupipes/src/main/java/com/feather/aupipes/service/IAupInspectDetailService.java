package com.feather.aupipes.service;

import com.feather.aupipes.domain.AupInspectDetail;
import com.feather.aupipes.utils.vo.AupInspectDetailVo;
import com.feather.common.core.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 巡检设备Service接口
 * 
 * @author fancy
 * @date 2020-06-04
 */
public interface IAupInspectDetailService
{
    /**
     * 查询巡检设备
     * 
     * @param id 巡检设备ID
     * @return 巡检设备
     */
    AupInspectDetail selectAupInspectDetailById(Long id);

    /**
     * 查询巡检记录及其包含的设备
     *
     * @param id 巡检设备ID
     * @return 巡检设备
     */
    AupInspectDetailVo selectAupInspectDetailRecurById(Long id);

    /**
     * 查询巡检设备列表
     * 
     * @param aupInspectDetail 巡检设备
     * @return 巡检设备集合
     */
    List<AupInspectDetail> selectAupInspectDetailList(AupInspectDetail aupInspectDetail);

    /**
     * 新增巡检设备
     * 
     * @param aupInspectDetail 巡检设备
     * @return 结果
     */
    int insertAupInspectDetail(AupInspectDetail aupInspectDetail);

    /**
     * 修改巡检设备
     * 
     * @param aupInspectDetail 巡检设备
     * @return 结果
     */
    int updateAupInspectDetail(AupInspectDetail aupInspectDetail);

    /**
     * 批量删除巡检设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAupInspectDetailByIds(String ids);

    /**
     * 删除巡检设备信息
     * 
     * @param id 巡检设备ID
     * @return 结果
     */
    int deleteAupInspectDetailById(Long id);

    AjaxResult insertAupInspectDetailAndSb(String xjjl, String xjsb);

    AjaxResult insertAupInspectDetailAndSbAndFile(String xjjl, String xjsb, MultipartFile[] files);
}
