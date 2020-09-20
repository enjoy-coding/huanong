package com.feather.aupipes.controller;

import com.feather.aupipes.domain.AupBuildingArea;
import com.feather.aupipes.service.IAupBuildingAreaService;
import com.feather.aupipes.service.IAupStreetlightControlService;
import com.feather.aupipes.service.IAupWarringCategoryService;
import com.feather.common.core.domain.Ztree;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author sky
 * @date 2019/12/21 11:15
 */
@Api(tags = "树接口")
@Controller
@CacheConfig(cacheNames = "sys:tree")
@RequestMapping("/api/tree")
public class AupTreeController {

    @Autowired
    private IAupWarringCategoryService aupWarringCategoryService;

    @Autowired
    private IAupStreetlightControlService aupStreetlightControlService;

    @Autowired
    private IAupBuildingAreaService aupBuildingAreaService;

    /**
     * 加载控制器列表树
     */
    @GetMapping("/streetlight")
    @Cacheable(key = "#root.methodName")
    @ResponseBody
    public List<Ztree> streetlight() {
        return aupStreetlightControlService.selectAupStreetlightTree();
    }

    /**
     * 加载预警列表树
     */
    @GetMapping("/warringCategory")
    @Cacheable(key = "#root.methodName")
    @ResponseBody
    public List<Ztree> warringCategory(String name) {
        return aupWarringCategoryService.selectWarringCategoryTree(name);
    }

    /**
     * 加载区域列表树
     */
    @GetMapping("/buildingArea")
//    @Cacheable(key = "#root.methodName +':'+ #root.args[0]")
    @ResponseBody
    public List<Ztree> buildingArea(AupBuildingArea aupBuildingArea) {
        return aupBuildingAreaService.selectAupBuildingAreaTree(aupBuildingArea);
    }
}