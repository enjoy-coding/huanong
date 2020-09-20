package com.feather.aupipes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.AupWaterSide;
import com.feather.aupipes.service.IAupBuildingSideService;
import com.feather.aupipes.service.IAupElectricitySideService;
import com.feather.aupipes.service.IAupNhjgService;
import com.feather.aupipes.service.IAupWaterSideService;
import com.feather.common.core.domain.Ztree;

/**
 * @Auther: hmzhu
 * @Date: 2019/12/30 15:17
 * @Description:
 */
@Controller
@RequestMapping("/aupipes/nhjg")
public class AupNhjgController {

    @Autowired
    private IAupNhjgService iAupNhjgService;

    /**
     * 重点建筑
     */
    @Autowired
    IAupBuildingSideService aupBuildingSideService;

    /**
     * 用水平衡结构表
     */
    @Autowired
    private IAupWaterSideService aupWaterSideService;

    /**
     * 用电平衡结构表
     */
    @Autowired
    private IAupElectricitySideService aupElectricitySideService;

    /**
     * 耗水,耗电,重点建筑
     */
    @ResponseBody
    @GetMapping("/jdxx/building/tree")
    public List<Ztree> buildingTree(@Param("pid") Long pid, @Param("important") String important) {
        return iAupNhjgService.selectZtreeBuildingByLessThanLd(important);
    }

    /**
     * 电表树
     */
    @ResponseBody
    @GetMapping("/jdxx/electricity/tree")
    public List<Ztree> electricityTree(@Param("pid") Long pid, @Param("important") String important) {
        if (pid == null) {
            pid = 0L;
        }
        return aupElectricitySideService.selectZtreeAupElectricitySide(pid);
    }

    /**
     * 水表树
     */
    @ResponseBody
    @GetMapping("/jdxx/water/tree")
    public List<Ztree> waterTree(@Param("pid") Long pid) {
        if (pid == null) {
            pid = 0L;
        }
        int level = 1;
        AupWaterSide aupWaterSide = aupWaterSideService.selectAupWaterSideById(pid);
        if (aupWaterSide != null) {
            level = aupWaterSide.getLevel();
        }
        return aupWaterSideService.selectZtreeAupWaterSide(pid, level);
    }

    /**
     * 重点建筑树
     */
    @ResponseBody
    @GetMapping("/jdxx/waterAndElectricity/tree")
    public List<Ztree> waterAndElectricity(@Param("pid") Long pid, @Param("level") Long level,
            @Param("important") String important, @Param("elementById") String elementById, ModelMap mmap) {
        int pid_ = 0;
        int level_ = 0;
        if (pid != null && level != null) {
            pid_ = pid.intValue();
            level_ = level.intValue();
        }
        return aupBuildingSideService.initWaterAndElectricityZtrees(pid_, level_, important, elementById);
    }

    // 查询水电cacheid
    @ResponseBody
    @GetMapping("/getAupSideList")
    public <M> M getAupSideList(String type, String typeno) {
        List<M> list = new ArrayList<>();
        if (type.equals("water")) {
            AupWaterSide aupWaterSide = new AupWaterSide();
            aupWaterSide.setTypeno(typeno);
            list = (List<M>) aupWaterSideService.selectAupWaterSideList(aupWaterSide);
            return (M) list;
        } else {
            list = (List<M>) aupBuildingSideService.getBuildSide(typeno);
            return (M) list;

        }
    }
}
