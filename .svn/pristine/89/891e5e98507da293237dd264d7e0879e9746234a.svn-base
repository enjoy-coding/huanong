package com.feather.aupipes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.mapper.AupBuildingAreaMapper;
import com.feather.aupipes.service.IAupBuildingAreaService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.bean.BeanUtils;

/**
 * 区域树结构Service业务层处理
 *
 * @author fancy
 * @date 2019-12-11
 */
@Service
public class AupBuildingAreaServiceImpl implements IAupBuildingAreaService {
    @Autowired
    private AupBuildingAreaMapper aupBuildingAreaMapper;


    /**
     * 查询区域树结构
     *
     * @param id
     *            区域树结构ID
     * @return 区域树结构
     */
    @Override
    public AupBuildingArea selectAupBuildingAreaById(Long id) {
        return aupBuildingAreaMapper.selectAupBuildingAreaById(id);
    }

    @Override
    public AupBuildingArea selectAupBuildingAreaByPid(Long pid) {
        return aupBuildingAreaMapper.selectAupBuildingAreaByPid(pid);
    }

    /**
     * 根据id查询树结构异步加载
     *
     * @param pid
     * @return
     */
    @Override
    public List<Ztree> selectBuildingListByPid(Long pid) {
        if (pid == null) {
            pid = 0L;
        }
        AupBuildingArea aupBuildingArea = new AupBuildingArea();
        aupBuildingArea.setMapid(pid);
        List<AupBuildingArea> aupBuildingAreaList = aupBuildingAreaMapper.selectAupBuildingAreaList(aupBuildingArea);
        List<Ztree> ztrees = initZtree(aupBuildingAreaList);
        return ztrees;
    }

    /**
     * 通过parentId找到树结构
     */
    @Override
    public List<Ztree> selectZtreeDataByPid(Long pid) {
        List<Ztree> ztrees = new ArrayList<>();
        AupBuildingArea aupBuildingArea = new AupBuildingArea();
        aupBuildingArea.setMapid(pid);
        List<AupBuildingArea> aupBuildingAreas = aupBuildingAreaMapper.selectAupBuildingAreaList(aupBuildingArea);
        for (AupBuildingArea area : aupBuildingAreas) {
            Ztree ztree = new Ztree();
            ztree.setId(area.getId());
            ztree.setpId(area.getMapid());
            ztree.setName(area.getName());
            ztree.setCode(area.getAreaNo());
            ztree.setTitle(area.getName());
            ztree.setMaps(BeanUtils.beanToMap(area));
            ztrees.add(ztree);

        }
        return ztrees;
    }

    @Override
    public AupBuildingArea selectAupBuildingAreaByCode(String code) {
        return aupBuildingAreaMapper.selectAupBuildingAreaByCode(code);
    }

    @Override
    public AupBuildingArea selectAupBuildingAreaByAreaNo(String areaNo) {
        return aupBuildingAreaMapper.selectAupBuildingAreaByAreaNo(areaNo);
    }

    /**
     * 查询区域树结构列表
     *
     * @param aupBuildingArea
     *            区域树结构
     * @return 区域树结构
     */
    @Override
    public List<AupBuildingArea> selectAupBuildingAreaList(AupBuildingArea aupBuildingArea) {
        return aupBuildingAreaMapper.selectAupBuildingAreaList(aupBuildingArea);
    }

    /**
     * 通过code去查询
     *
     * @param code
     * @return
     */
    @Override
    public List<AupBuildingArea> selectAupBuildingAreaList(String code) {
        AupBuildingArea aupBuildingArea = new AupBuildingArea();
        aupBuildingArea.setCode(code);
        List<AupBuildingArea> aupBuildingAreaList = aupBuildingAreaMapper.selectAupBuildingAreaList(aupBuildingArea);
        return aupBuildingAreaList;
    }


    public List<AupBuildingArea> selectBuildingAppointParentCodeList(AupBuildingArea aupBuildingArea) {
        return aupBuildingAreaMapper.selectBuildingAppointParentCodeList(aupBuildingArea);
    }

    /**
     * 查询区域树
     *
     * @param area
     *            部门信息
     * @return 所有部门信息
     */
    @Override
    public List<Ztree> selectAupBuildingAreaTree(AupBuildingArea area) {
        List<AupBuildingArea> areaList = aupBuildingAreaMapper.selectAupBuildingAreaList(area);
        List<Ztree> ztrees = initZtree(areaList);
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param areaList
     *            部门列表
     * @return 树结构列表
     */

    public List<Ztree> initZtree(List<AupBuildingArea> areaList) {
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
    public List<Ztree> initZtree(List<AupBuildingArea> areaList, List<String> roleareaList) {
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = roleareaList != null;
        for (AupBuildingArea area : areaList) {
            Ztree ztree = new Ztree();
            ztree.setId(area.getId());
            ztree.setpId(area.getMapid());
            ztree.setName(area.getName());
            ztree.setCode(area.getAreaNo());
            if (area.getLevel() < 5) {
                ztree.setIsParent(true);
            } else {
                ztree.setIsParent(false);
            }
            ztree.setTitle(area.getName());
            ztree.setMaps(BeanUtils.beanToMap(area));
            if (isCheck) {
                ztree.setChecked(roleareaList.contains(area.getId() + area.getName()));
            }
            ztrees.add(ztree);

        }
        return ztrees;
    }

}