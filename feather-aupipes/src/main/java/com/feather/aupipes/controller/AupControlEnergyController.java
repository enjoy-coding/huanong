package com.feather.aupipes.controller;

import com.feather.aupipes.HttpClient.HttpClinetLights;
import com.feather.aupipes.domain.AupControlEnergy;
import com.feather.aupipes.domain.AupStreetlight;
import com.feather.aupipes.domain.AupStreetlightControl;
import com.feather.aupipes.service.IAupControlEnergyService;
import com.feather.aupipes.service.IAupStreetlightControlService;
import com.feather.aupipes.service.IAupStreetlightService;
import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器能源消耗Controller
 * 
 * @author fancy
 * @date 2020-06-03
 */
@Controller
@RequestMapping("/aupipes/energy")
public class AupControlEnergyController extends BaseController
{
    private String prefix = "aupipes/energy";

    @Autowired
    private IAupControlEnergyService aupControlEnergyService;

    @Autowired
    private IAupStreetlightControlService iAupStreetlightControlService;

    @Autowired
    private IAupStreetlightService iAupStreetlightService;


    @RequiresPermissions("aupipes:energy:view")
    @GetMapping()
    public String energy()
    {
        return prefix + "/energy";
    }

    /**
     * 查询控制器能源消耗列表
     */
    @RequiresPermissions("aupipes:energy:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(AupControlEnergy aupControlEnergy)
    {
        startPage();
        List<AupControlEnergy> list = aupControlEnergyService.selectAupControlEnergyList(aupControlEnergy);
        return getDataTable(list);
    }

    /**
     * 导出控制器能源消耗列表
     */
    @RequiresPermissions("aupipes:energy:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AupControlEnergy aupControlEnergy)
    {
        List<AupControlEnergy> list = aupControlEnergyService.selectAupControlEnergyList(aupControlEnergy);
        ExcelUtil<AupControlEnergy> util = new ExcelUtil<>(AupControlEnergy.class);
        return util.exportExcel(list, "energy");
    }

    /**
     * 新增控制器能源消耗
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        AupControlEnergy energy = new AupControlEnergy();
        mmap.put("energy", energy);
        return prefix + "/edit";
    }

    /**
     * 新增保存控制器能源消耗
     */
    @RequiresPermissions("aupipes:energy:add")
    @Log(title = "控制器能源消耗", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AupControlEnergy aupControlEnergy)
    {
        return toAjax(aupControlEnergyService.insertAupControlEnergy(aupControlEnergy), aupControlEnergy);
    }

    /**
     * 修改控制器能源消耗
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        AupControlEnergy energy = aupControlEnergyService.selectAupControlEnergyById(id);
        mmap.put("energy", energy);
        return prefix + "/edit";
    }

    /**
     * 修改保存控制器能源消耗
     */
    @RequiresPermissions("aupipes:energy:edit")
    @Log(title = "控制器能源消耗", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AupControlEnergy aupControlEnergy)
    {
        return toAjax(aupControlEnergyService.updateAupControlEnergy(aupControlEnergy));
    }

    /**
     * 删除控制器能源消耗
     */
    @RequiresPermissions("aupipes:energy:remove")
    @Log(title = "控制器能源消耗", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(aupControlEnergyService.deleteAupControlEnergyByIds(ids));
    }


    @RequestMapping( "/getAllLightStatus")
    @ResponseBody
    public List<Map<String,Object>> getAllLightStatus(){
        AupStreetlightControl entity = new AupStreetlightControl();

        List<AupStreetlightControl> aupStreetlightControls = iAupStreetlightControlService.selectAupStreetlightControlList(entity);


        List<Map<String,Object>> list = new ArrayList<>();
        for(AupStreetlightControl streetlightControl:aupStreetlightControls){
            String cuid = streetlightControl.getCuid();

            AupStreetlight aupStreetlight = new AupStreetlight();
            aupStreetlight.setCuid(cuid);
            List<AupStreetlight> aupStreetlights = iAupStreetlightService.selectAupStreetlightList(aupStreetlight);
            HttpClinetLights httpClinetLights = new HttpClinetLights();
            JSONObject[] lightStatus = httpClinetLights.getLightStatus(cuid);
            for (AupStreetlight aupStreetlight1 : aupStreetlights) {
                String statusName = "开启";
                Map<String,Object> map = new HashMap<>(16);
                if(lightStatus!=null) {
                    for (JSONObject lightStatus1 : lightStatus) {
                        String luid = lightStatus1.getString("luid");
                        String status = lightStatus1.getString("ls");
                        if (luid.equals(aupStreetlight1.getLuid())) {
                            statusName = "2".equals(status)?"关闭":statusName;
                        }

                    }
                }
                map.put("name", aupStreetlight1.getName());
                map.put("luid", aupStreetlight1.getLuid());
                map.put("status", statusName);
                list.add(map);

            }
        }
        return list;
    }
}
