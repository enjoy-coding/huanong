package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.domain.AupipeJcbzWoker;
import com.feather.aupipes.mapper.AupBuildingAreaMapper;
import com.feather.aupipes.mapper.AupipeJcbzWokerMapper;
import com.feather.aupipes.service.IAupDecisionSecurityService;
import com.feather.aupipes.service.IAupipeJcbzWokerService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by NieCheng
 * Time   2020/7/6 17:24
 */
@Service
public class AupipeJcbzWokerServiceImpl implements IAupipeJcbzWokerService {
    @Autowired
    private AupipeJcbzWokerMapper aupipeJcbzWokerMapper;
    @Autowired
    private AupBuildingAreaMapper aupBuildingAreaMapper;
    @Autowired
    IAupDecisionSecurityService iAupDecisionSecurityService;

    /**
     * 查询决策保障管理员
     *
     * @param id
     *            决策保障管理员ID
     * @return 决策保障管理员
     */
    @Override
    public AupipeJcbzWoker selectAupJcbzManagerById(Long id) {
        return aupipeJcbzWokerMapper.selectAupJcbzManagerById(id);
    }

    /**
     * 查询决策保障管理员列表
     *
     * @param aupJcbzManager
     *            决策保障管理员
     * @return 决策保障管理员
     */
    @Override
    public List<AupipeJcbzWoker> selectAupJcbzManagerList(AupipeJcbzWoker aupJcbzManager) {
        return aupipeJcbzWokerMapper.selectAupJcbzManagerList(aupJcbzManager);
    }

    /**
     * 新增决策保障管理员
     *
     * @param aupJcbzManager
     *            决策保障管理员
     * @return 结果
     */
    @Override
    public int insertAupJcbzManager(AupipeJcbzWoker aupJcbzManager) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        aupJcbzManager.setEdittime(sdf.format(date));
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String format1 = sdf1.format(date);
        long timeId = Long.parseLong(format1);
        aupJcbzManager.setManagerUuid(timeId);

        int i = aupipeJcbzWokerMapper.insertAupJcbzManager(aupJcbzManager);
        if (i > 0) {
            // RedisUtils.del("dept");
            /*
             * String type="";
             * iAupDecisionSecurityService.selectLzxdUserTree(type);
             */
        }
        return i;
    }

    /**
     * 修改决策保障管理员
     *
     * @param aupJcbzManager
     *            决策保障管理员
     * @return 结果
     */
    @Override
    public int updateAupJcbzManager(AupipeJcbzWoker aupJcbzManager) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        aupJcbzManager.setEdittime(sdf.format(date));
        int i = aupipeJcbzWokerMapper.updateAupJcbzManager(aupJcbzManager);
        if (i > 0) {
            // RedisUtils.del("dept");
            /*
             * String type="";
             * iAupDecisionSecurityService.selectLzxdUserTree(type);
             */
        }
        return i;
    }

    /**
     * 删除决策保障管理员对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupJcbzManagerByIds(String ids) {
        int i = aupipeJcbzWokerMapper.deleteAupJcbzManagerByIds(Convert.toStrArray(ids));
        if (i > 0) {
            // RedisUtils.del("dept");
            /*
             * String type="";
             * iAupDecisionSecurityService.selectLzxdUserTree(type);
             */
        }
        return i;
    }

    /**
     * 删除决策保障管理员信息
     *
     * @param id
     *            决策保障管理员ID
     * @return 结果
     */
    @Override
    public int deleteAupJcbzManagerById(Long id) {

        int i = aupipeJcbzWokerMapper.deleteAupJcbzManagerById(id);
        if (i > 0) {
            // RedisUtils.del("dept");
            /*
             * String type="";
             * iAupDecisionSecurityService.selectLzxdUserTree(type);
             */
        }
        return i;
    }

    /**
     * 查询区域树结构列表
     *
     * @param area
     *            区域树结构
     * @return 区域树结构
     */
    @Override
    public List<Ztree> selectAupBuildingAreaTree(AupBuildingArea area) {
        // List<AupBuildingArea> areaList =
        // aupBuildingAreaMapper.selectAupBuildingAreaList(area);
        List<AupBuildingArea> areaList = aupBuildingAreaMapper.selectAupBuildingAreaViewList(area);
        List<Ztree> ztrees = initZtree(areaList);
        return ztrees;
    }

    @Override
    public AjaxResult selectOptions(AupBuildingArea area) {
        // List<AupBuildingArea> areaList =
        // aupipeJcbzWokerMapper.selectAupBuildingAreaList(area);
        List<AupBuildingArea> areaList = aupBuildingAreaMapper.selectAupBuildingAreaViewList(area);
        for (AupBuildingArea a : areaList) {
            if (a.getParentCode().equals("root")) {
                a.setLevel(2);
            } else {
                a.setLevel(3);
            }
            a.setName(a.getBuildName());
        }

        return AjaxResult.success(areaList);
    }

    @Override
    public void updateData() {
        // RedisUtils.del("dept");
        String type = "";
        iAupDecisionSecurityService.selectLzxdUserTree(type);

    }

    /**
     * 对象转部门树
     *
     * @param areaList
     *            部门列表
     * @return 树结构列表
     */

    private List<Ztree> initZtree(List<AupBuildingArea> areaList) {
        return initZtree(areaList, null);
    }

    /**
     * 对象转部门树
     *
     * @param areaList
     *            部门列表
     * @param roleareaList
     *            角色已存在菜单列表
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<AupBuildingArea> areaList, List<String> roleareaList) {
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = roleareaList != null;
        for (AupBuildingArea area : areaList) {
            /*
             * if (area.getLevel() <= 3) { Ztree ztree = new Ztree();
             * ztree.setId(area.getId()); ztree.setpId(area.getMapid());
             * ztree.setName(area.getName()); ztree.setCode(area.getAreaNo());
             * if (area.getLevel() < 3) { ztree.setIsParent(true); } else {
             * ztree.setIsParent(false); } ztree.setTitle(area.getName());
             * ztree.setMaps(BeanUtils.beanToMap(area)); if (isCheck) {
             * ztree.setChecked(roleareaList.contains(area.getId() +
             * area.getName())); } ztrees.add(ztree); }
             */
            Ztree ztree = new Ztree();
            ztree.setId_(area.getCode());
            ztree.setPid_(area.getParentCode());
            ztree.setId(area.getId());
            ztree.setpId(area.getMapid());
            ztree.setName(area.getBuildName());
            ztree.setCode(area.getNo());
            ztree.setIsParent(false);
            ztree.setTitle(area.getBuildName());
            ztree.setMaps(BeanUtils.beanToMap(area));
            ztree.setOpen(false);
            if (isCheck) {
                ztree.setChecked(roleareaList.contains(area.getId() + area.getName()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
