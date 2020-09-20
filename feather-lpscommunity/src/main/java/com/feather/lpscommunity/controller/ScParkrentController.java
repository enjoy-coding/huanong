package com.feather.lpscommunity.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.domain.ScParkrent;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.service.IScFileInfoService;
import com.feather.lpscommunity.service.IScParkrentService;
import com.feather.lpscommunity.service.IScTaskVolunteerService;
import com.feather.system.service.ISysDictDataService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 租用信息发布Controller
 *
 * @author dufan
 * @date 2019-10-15
 */
@Api(tags = { "3-志愿者服务,2-心愿树,1-心愿树信息" })
@Controller
@RequestMapping("/sc/parkrent")
public class ScParkrentController extends BaseController {
    private String prefix = "lpscommunity/parkrent";

    @Autowired
    private IScParkrentService scParkrentService;

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private IScFileInfoService scFileInfoService;

    @Autowired
    private IScTaskVolunteerService scTaskVolunteerService;

    @Autowired
    private ISysDictDataService sysDicDataService;

    @RequiresPermissions("sc:parkrent:view")
    @GetMapping()
    public String parkrent() {
        return prefix + "/parkrent";
    }

    /**
     * 查询租用信息发布列表
     */
    @RequiresPermissions("sc:parkrent:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(ScParkrent scParkrent) {
        startPage().setOrderBy("update_time desc");
        List<ScParkrent> list = scParkrentService.selectScParkrentList(scParkrent);
        return getDataTable(list);
    }

    /**
     * 导出租用信息发布列表
     */
    @RequiresPermissions("sc:parkrent:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScParkrent scParkrent) {
        List<ScParkrent> list = scParkrentService.selectScParkrentList(scParkrent);
        ExcelUtil<ScParkrent> util = new ExcelUtil<ScParkrent>(ScParkrent.class);
        return util.exportExcel(list, "parkrent");
    }

    /**
     * 新增租用信息发布
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("scParkrent", new ScParkrent());
        return prefix + "/edit";
    }

    /**
     * 新增保存租用信息发布
     */
    @RequiresPermissions("sc:parkrent:add")
    @Log(title = "租用信息发布", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScParkrent scParkrent) {

        return toAjax(scParkrentService.insertScParkrent(scParkrent), scParkrent);
    }

    /**
     * 修改页面
     */
    @GetMapping("/edit/{parkrentId}")
    public String edit(@PathVariable("parkrentId") Long parkrentId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScParkrent scParkrent = scParkrentService.selectScParkrentFileById(parkrentId, basePath);
        mmap.put("scParkrent", scParkrent);
        mmap.put("scFileInfoList", scParkrent.getFiles());
        return prefix + "/edit";
    }

    /**
     * 修改保存租用信息发布
     */
    @RequiresPermissions("sc:parkrent:edit")
    @Log(title = "租用信息发布", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScParkrent scParkrent) {
        return toAjax(scParkrentService.updateScParkrent(scParkrent));
    }

    /**
     * 审核页面
     */
    @GetMapping("/audit/{parkrentId}")
    public String audit(@PathVariable("parkrentId") Long parkrentId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScParkrent scParkrent = scParkrentService.selectScParkrentFileById(parkrentId, basePath);
        mmap.put("scParkrent", scParkrent);
        mmap.put("scFileInfoList", scParkrent.getFiles());
        return prefix + "/audit";
    }

    /**
     * 提交审核
     */
    @RequiresPermissions("sc:parkrent:audit")
    @Log(title = "租用信息审核", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    @ResponseBody
    public AjaxResult auditSave(ScParkrent scParkrent) {

        return toAjax(scParkrentService.updateScParkrent(scParkrent));
    }

    /**
     * 图片预览
     */
    @ResponseBody
    @RequestMapping("/audit/detail/pic")
    public Map<String, Object> showPic(@RequestParam Long parkrentId, HttpServletRequest request) {
        ScParkrent scParkrent = scParkrentService.selectScParkrentById(parkrentId);
        List<ScFileInfo> scFileInfoList = scFileInfoService
                .selectScFileInfoList(new ScFileInfo(scParkrent.getParkrentId(), "parkrent"));
        String basePath = getBasePath();
        Map<String, Object> map = scFileInfoService.getImageViewData(scFileInfoList, scParkrent.getParkrentTitle(),
                scParkrent.getParkrentId(), basePath);
        return map;
    }

    /**
     * 根据任务查询志愿者信息列表
     */
    @PostMapping("/volunteer/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo selectlistByParkrent(Long parkrentId) {
        startPage();
        List<ScVolunteer> scVolunteers = scParkrentService.selectVolunteerByParkrent(parkrentId);
        return getDataTable(scVolunteers);
    }

    /**
     * 结项页面
     */
    @GetMapping("/over/{parkrentId}")
    public String over(@PathVariable("parkrentId") Long parkrentId, ModelMap mmap, HttpServletRequest request) {
        String basePath = getBasePath();
        ScParkrent scParkrent = scParkrentService.selectScParkrentFileById(parkrentId, basePath);
        mmap.put("scParkrent", scParkrent);
        return prefix + "/over";
    }

    /**
     * 结项操作
     */
    @RequiresPermissions("sc:parkrent:edit")
    @Log(title = "租用信息发布", businessType = BusinessType.UPDATE)
    @PostMapping("/over")
    @ResponseBody
    public AjaxResult overSave(ScParkrent scParkrent) {
        // 结项
        return toAjax(scParkrentService.overScParkrent(scParkrent));
    }

    /**
     * 删除租用信息发布
     */
    @RequiresPermissions("sc:parkrent:remove")
    @Log(title = "租用信息发布", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        // 判断是否存在志愿者报名任务
        if (scTaskVolunteerService.checkScTaskVolunteerByParkrentId(ids)) {
            return AjaxResult.error("删除失败！存在已报名的任务无法删除!");
        }
        return toAjax(scParkrentService.updateScParkrentDelFlagById(ids));
    }

    /**
     * 移动端获取已审核信息发布列表
     */
    @ApiOperation(value = "信息列表([志愿者查询心愿树 audit_state  = 1 ])")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "pageNum", value = "当前页数", required = true), //
            @ApiImplicitParam(name = "pageSize", value = "每页条目数", required = true), //
            @ApiImplicitParam(name = "audit_state", value = "审核状态(,0未审核,1已审核,''所有)", required = false), //
            @ApiImplicitParam(name = "equipment_id", value = "设备id", required = false) //
    })
    @RequestMapping(value = "/api/list", method = RequestMethod.GET)
    @ClearPage
    @ResponseBody
    public AjaxResult restList(@RequestParam(value = "pageNum", required = true) int pageNum,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "audit_state", required = false) String auditState,
            @RequestParam(value = "equipment_id", required = false) String equipmentId, HttpServletRequest request) {

        String basePath = getBasePath();
        ScParkrent scParkrent = new ScParkrent();
        scParkrent.setDelFlag("0");
        scParkrent.setAuditState(auditState);
        scParkrent.setEquipmentId(equipmentId);
        if (auditState != null && auditState.equals("1")) {
            scParkrent.setAuditPassState("0");// 已通过
        }
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize, "update_time desc");
        List<Map<String, Object>> scParkrentListMap = scParkrentService.screenScParkrentList(scParkrent, basePath);
        PageInfo<Map<String, Object>> pageInfo = page.toPageInfo();
        pageInfo.setList(scParkrentListMap);
        return AjaxResult.success(pageInfo);
    }

    /**
     * 移动端获取信息发布列表
     */
    @ApiOperation(value = "发布信息")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "parkrent_title", value = "标题", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "parkrent_name", value = "姓名", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "parkrent_tel", value = "联系电话", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "parkrent_content", value = "内容", required = false, dataType = "String"), //
            @ApiImplicitParam(name = "parkrent_file", value = "多个图片集合", required = false, dataType = "MultipartFile"), //
            @ApiImplicitParam(name = "equipment_id", value = "设备id", required = true, dataType = "String") //
    })
    @PostMapping(value = "/api/add")
    @ResponseBody
    public AjaxResult restAdd(@RequestParam(value = "parkrent_title", required = false) String parkrentTitle,
            @RequestParam(value = "parkrent_name", required = false) String parkrentName,
            @RequestParam(value = "parkrent_tel", required = false) String parkrentTel,
            @RequestParam(value = "parkrent_content", required = false) String parkrentContent,
            @RequestParam(value = "parkrent_file", required = false) MultipartFile[] parkrentFile,
            @RequestParam(value = "equipment_id", required = true) String equipmentId) {
        ScParkrent scParkrent = new ScParkrent(uidWorker.getNextId(), parkrentName, parkrentTitle,
                sysDicDataService.selectDictValue("sc_task_state1", "招募待启动"), parkrentTel, parkrentContent, "0", "0",
                equipmentId);
        scParkrentService.insertScParkrentAndPic(scParkrent, parkrentFile);
        AjaxResult result = AjaxResult.success();
        result.put("parkrentId", scParkrent.getParkrentId());
        return result;
    }

    /**
     * 获取信息详情
     */
    @ApiOperation(value = "发布信息详情")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "parkrent_id", value = "发布信息id", required = true) //
    })
    @RequestMapping(value = "/api/detail", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult restDetail(@RequestParam(name = "parkrent_id", required = true) Long parkrentId,
            HttpServletRequest request) {
        String basePath = getBasePath();
        Map<String, Object> data = scParkrentService.screenScParkrentDetail(parkrentId, basePath);
        return AjaxResult.success(data);
    }

}