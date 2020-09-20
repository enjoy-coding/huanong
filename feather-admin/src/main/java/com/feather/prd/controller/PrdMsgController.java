package com.feather.prd.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.core.text.Convert;
import com.feather.common.enums.BusinessType;
import com.feather.common.utils.poi.ExcelUtil;
import com.feather.prd.domain.PrdMsg;
import com.feather.prd.domain.PrdMsgUser;
import com.feather.prd.service.IPrdMsgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 消息发送日志Controller
 */
@Api(tags = "消息发送日志")
@Controller
@RequestMapping("/prd/msg")
public class PrdMsgController extends BaseController {
    private String prefix = "prd/msg";

    @Autowired
    private IPrdMsgService prdMsgService;

    @RequiresPermissions("prd:msg:view")
    @GetMapping()
    public String msg() {
        return prefix + "/msg";
    }

    /**
     * 查询消息发送日志列表
     */
    @RequiresPermissions("prd:msg:list")
    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(PrdMsg prdMsg) {
        startPage();
        List<PrdMsg> list = prdMsgService.selectPrdMsgList(prdMsg);
        return getDataTable(list);
    }

    @RequiresPermissions("prd:msg:view")
    @GetMapping("/users/{msgId}")
    public String users(@PathVariable("msgId") Long msgId, ModelMap mmap) {
        mmap.put("msgId", msgId);
        return prefix + "/users";
    }

    /**
     * 查询接收人列表
     */
    @RequiresPermissions("prd:msg:list")
    @PostMapping("/listUsers")
    @ClearPage
    @ResponseBody
    public TableDataInfo listUsers(PrdMsgUser prdMsgUser) {
        startPage();
        List<PrdMsgUser> list = prdMsgService.selectPrdMsgUser(prdMsgUser);
        return getDataTable(list);
    }

    /**
     * 导出消息发送日志列表
     */
    @RequiresPermissions("prd:msg:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PrdMsg prdMsg) {
        List<PrdMsg> list = prdMsgService.selectPrdMsgList(prdMsg);
        ExcelUtil<PrdMsg> util = new ExcelUtil<PrdMsg>(PrdMsg.class);
        return util.exportExcel(list, "msg");
    }

    /**
     * 发送消息（进入表单）
     */
    @GetMapping("/send")
    public String send(ModelMap mmap) {
        return prefix + "/send";
    }

    /**
     * 发送消息
     */
    @RequiresPermissions("prd:msg:send")
    @Log(title = "发送消息", businessType = BusinessType.INSERT)
    @PostMapping("/send")
    @ResponseBody
    public AjaxResult send(//
            @RequestParam("app") String app, //
            @RequestParam("type") String type, //
            @RequestParam("title") String title, //
            @RequestParam("content") String content, //
            @RequestParam("url") String url, //
            @RequestParam("users") String users //
    ) {
        PrdMsg prdMsg = new PrdMsg();
        prdMsg.setMsgApp(app);
        prdMsg.setMsgType(type);
        prdMsg.setMsgTitle(title);
        prdMsg.setMsgContent(content);
        prdMsg.setMsgUrl(url);
        Long[] userIds = Convert.toLongArray(users);
        return toAjax(prdMsgService.sendToUser(prdMsg, userIds, false));
    }

    /**
     * 查看消息发送日志详情
     */
    @GetMapping("/detail/{msgId}")
    public String detail(@PathVariable("msgId") Long msgId, ModelMap mmap) {
        PrdMsg msg = prdMsgService.selectPrdMsgById(msgId);
        mmap.put("msg", msg);
        return prefix + "/detail";
    }

    /**
     * 删除消息发送日志
     */
    @RequiresPermissions("prd:msg:remove")
    @Log(title = "消息发送日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(prdMsgService.deletePrdMsgByIds(ids));
    }

    /**
     * 发送消息到用户
     */
    @Log(title = "消息发送日志", businessType = BusinessType.INSERT)
    @ApiOperation(value = "发送消息到用户")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "app", value = "应用类型", required = false), //
            @ApiImplicitParam(name = "type", value = "消息分类", required = false), //
            @ApiImplicitParam(name = "title", value = "消息标题", required = false), //
            @ApiImplicitParam(name = "content", value = "消息内容", required = true), //
            @ApiImplicitParam(name = "url", value = "消息链接", required = false), //
            @ApiImplicitParam(name = "users", value = "用户，多个逗号分隔", required = true), //
            @ApiImplicitParam(name = "synch", value = "是否同步", required = false) //
    })
    @PostMapping("/api/sendToUser")
    @ResponseBody
    public AjaxResult sendToUser(String app, String type, String title, String content, String url, String users,
            Boolean synch) {
        PrdMsg prdMsg = new PrdMsg();
        prdMsg.setMsgApp(app);
        prdMsg.setMsgType(type);
        prdMsg.setMsgTitle(title);
        prdMsg.setMsgContent(content);
        prdMsg.setMsgUrl(url);
        return toAjax(prdMsgService.sendToUser(prdMsg, users, synch));
    }

    /**
     * 发送消息到角色
     */
    @Log(title = "消息发送日志", businessType = BusinessType.INSERT)
    @ApiOperation(value = "发送消息到角色")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "app", value = "应用类型", required = false), //
            @ApiImplicitParam(name = "type", value = "消息分类", required = false), //
            @ApiImplicitParam(name = "title", value = "消息标题", required = false), //
            @ApiImplicitParam(name = "content", value = "消息内容", required = true), //
            @ApiImplicitParam(name = "url", value = "消息链接", required = false), //
            @ApiImplicitParam(name = "roles", value = "角色，多个逗号分隔", required = true), //
            @ApiImplicitParam(name = "synch", value = "是否同步", required = false) //
    })
    @PostMapping("/api/sendToRole")
    @ResponseBody
    public AjaxResult sendToRole(String app, String type, String title, String content, String url, String roles,
            Boolean synch) {
        PrdMsg prdMsg = new PrdMsg();
        prdMsg.setMsgApp(app);
        prdMsg.setMsgType(type);
        prdMsg.setMsgTitle(title);
        prdMsg.setMsgContent(content);
        prdMsg.setMsgUrl(url);
        return toAjax(prdMsgService.sendToRole(prdMsg, roles, synch));
    }
}
