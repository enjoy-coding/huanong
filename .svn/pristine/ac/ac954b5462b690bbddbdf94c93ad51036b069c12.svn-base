package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.mapper.AupBuildingAreaMapper;
import com.feather.aupipes.mapper.AupCountMapper;
import com.feather.aupipes.service.IAupNhjgService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: hmzhu
 * @Date: 2019/12/27 16:00
 * @Description: 能耗监管
 */
@Service
public class AupNhjgServiceImpl implements IAupNhjgService {

    @Value("classpath:/sdtemp.json")
    private Resource jsondata;//临时用下本地的json文件


    @Autowired
    private AupCountMapper aupCountMapper;

    @Autowired
    private AupBuildingAreaMapper aupBuildingAreaMapper;


    @Override
    public List<Ztree> selectZtreeBuildingByLessThanLd(String important) {
        AupBuildingArea aupBuildingArea = new AupBuildingArea();
        aupBuildingArea.setImportant(important);
        aupBuildingArea.setLevel(3);
        List<AupBuildingArea> aupBuildingAreaList = this.selectAupBuildingAreaList(aupBuildingArea);
        return initZtree(aupBuildingAreaList);
    }


    /**
     * 对象转部门树
     *
     * @param areaList 部门列表
     * @return 树结构列表
     */

    private List<Ztree> initZtree(List<AupBuildingArea> areaList) {
        return initZtree(areaList, null);
    }

    /**
     * 对象转部门树
     *
     * @param areaList     部门列表
     * @param roleAreaList 角色已存在菜单列表
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<AupBuildingArea> areaList, List<String> roleAreaList) {
        List<Ztree> ztrees = new ArrayList<>();
        boolean isCheck = roleAreaList != null;
        for (AupBuildingArea area : areaList) {
            Ztree ztree = new Ztree();
            ztree.setId(area.getId());
            ztree.setpId(area.getMapid());
            ztree.setName(area.getName());
            ztree.setCode(area.getAreaNo());
            if (area.getLevel() < 3) {
                ztree.setIsParent(true);
            } else {
                ztree.setIsParent(false);
            }
            ztree.setTitle(area.getName());
            ztree.setMaps(BeanUtils.beanToMap(area));
            if (isCheck) {
                ztree.setChecked(roleAreaList.contains(area.getId() + area.getName()));
            }
            ztrees.add(ztree);

        }
        return ztrees;
    }

    /**
     * 查询区域树结构列表
     *
     * @param aupBuildingArea 区域树结构
     * @return 区域树结构
     */
    @Override
    public List<AupBuildingArea> selectAupBuildingAreaList(AupBuildingArea aupBuildingArea) {
        return aupBuildingAreaMapper.selectAupBuildingAreaListSd(aupBuildingArea);
    }


    @Override
    public List<Map<String,Object>> queryAreaUseNumber(Map<String,Object> map){
        return aupCountMapper.queryAreaUseNumber(map);
    }

}
