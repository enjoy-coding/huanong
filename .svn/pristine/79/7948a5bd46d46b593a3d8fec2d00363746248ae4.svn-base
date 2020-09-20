package com.feather.napo.controller;

import com.feather.common.annotation.ClearPage;
import com.feather.common.annotation.Log;
import com.feather.common.config.Global;
import com.feather.common.constant.Constants;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.page.TableDataInfo;
import com.feather.common.enums.BusinessType;
import com.feather.framework.util.ShiroUtils;
import com.feather.napo.config.InfoType;
import com.feather.napo.domain.NpInfo;
import com.feather.napo.service.INpInfoService;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

/**
 * @author nothing
 * @date 2020-06-17 8:53
 */

@Controller
@RequestMapping("/np/info")
public class NpInfoController extends BaseController {
    private String prefix = "napo/info";

    private String index = "index.html";

    @Autowired
    INpInfoService npInfoService;

    @GetMapping()
    public String index(@RequestParam("infoType") Integer infoType, ModelMap mmap) {
        mmap.put("infoType", infoType);
        return prefix + "/list";
    }

    /**
     * 新增
     *
     * @param mmap
     * @return
     */
    @GetMapping("/add/{infoType}")
    public String add(@PathVariable("infoType") int infoType, ModelMap mmap) {
        NpInfo npInfo = new NpInfo();
        npInfo.setInfoType(infoType);
        mmap.put("npInfo", npInfo);
        return retFormStyle(infoType);
    }

    /**
     * 跳转编辑页面
     *
     * @param infoId
     * @param mmap
     * @return
     */
    @GetMapping("/edit/{infoId}")
    public String edit(@PathVariable("infoId") Long infoId, ModelMap mmap) {
        NpInfo npInfo = npInfoService.selectNpInfoById(infoId);
        int infoType = npInfo.getInfoType();
        mmap.put("npInfo", npInfo);
        return retFormStyle(infoType);
    }

    /**
     * 返回跳转的编辑地址
     */
    private String retFormStyle(int infoType) {
        //吃美食 住酒店
        if (infoType == InfoType.I_1.getCode() || infoType == InfoType.I_3.getCode()
                || infoType == InfoType.I_4.getCode() || infoType == InfoType.I_8.getCode()) {
            return prefix + "/formStyle1";
        } else if (infoType == InfoType.I_2.getCode()) {
            return prefix + "/formStyle2";
            //服务问答
        } else if (infoType == InfoType.I_9.getCode()) {
            return prefix + "/formStyle4";
        } else if (infoType == InfoType.I_10.getCode()) {
            return prefix + "/formStyle6";
        } else if (infoType == InfoType.I_11.getCode()) {
            return prefix + "/formStyle5";
            //景点管理
        } else if (infoType == InfoType.I_12.getCode()) {
            return prefix + "/formStyle7";
        }
        //跳转新闻公告编辑页面
        return prefix + "/formStyle3";
    }

    /**
     * 信息详情
     */
    @GetMapping("/showShops/{infoId}")
    public String showShopes(@PathVariable("infoId") Long infoId, ModelMap mmap) {
        mmap.put("npInfo", npInfoService.selectNpInfoById(infoId));
        return prefix + "/showShops";
    }

    /**
     * 景点列表
     *
     * @param infoId
     * @param mmap
     * @return
     */
    @GetMapping("/showSpots/{infoId}")
    public String showSpots(@PathVariable("infoId") Long infoId, ModelMap mmap) {
        mmap.put("npInfo", npInfoService.selectNpInfoById(infoId));
        return prefix + "/showSpots";
    }


    /**
     * 主键查询
     */
    @GetMapping("/selectNpInfoById/{infoId}")
    @ResponseBody
    public NpInfo selectNpInfoById(@PathVariable("infoId") Long infoId) {
        NpInfo npInfo = npInfoService.selectNpInfoById(infoId);
        return npInfo;
    }

    @PostMapping("/list")
    @ClearPage
    @ResponseBody
    public TableDataInfo list(NpInfo npInfo) {
        startPage();
        List<NpInfo> list = npInfoService.selectNpInfoList(npInfo);
        return getDataTable(list);
    }

    @Log(title = "那坡信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated NpInfo npInfo) {
        ShiroUtils.clearCachedAuthorizationInfo();
        int rows = 0;
        Integer hot = npInfo.getHot();
        Integer viewCount = npInfo.getViewCount();
        if (Objects.isNull(hot)) {
            hot = 0;
            npInfo.setHot(hot);
        }
        if (Objects.isNull(viewCount)) {
            viewCount = 0;
            npInfo.setViewCount(viewCount);
        }
        /**
         * 当类型为实景的时候
         */
        try {
            handleLive(npInfo);
            rows = npInfoService.insertNpInfo(npInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        return toAjax(rows, npInfo);
    }

    /**
     * 处理为实景的情况
     *
     * @param npInfo
     * @return
     */
    private NpInfo handleLive(NpInfo npInfo) throws ZipException {
        int infoType = npInfo.getInfoType();
        String addInfo1 = npInfo.getAddInfo1();
        if (infoType == InfoType.I_11.getCode() && Strings.isNotBlank(addInfo1)) {
            String realPath = addInfo1.substring(Constants.RESOURCE_PREFIX.length());
            String destPath = unzip(realPath);
            npInfo.setAddInfo2(Constants.RESOURCE_PREFIX + destPath + "/" + index);
        }
        return npInfo;
    }

    @Log(title = "那坡信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated NpInfo npInfo) {
        ShiroUtils.clearCachedAuthorizationInfo();
        int rows = 0;
        try {
            handleLive(npInfo);
            rows = npInfoService.updateNpInfo(npInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
        //为实景的时候需要重新加载页面显示实景信息，所以这里返回的是对象
        if (npInfo.getInfoType() == InfoType.I_11.getCode()) {
            return toAjax(rows, npInfo);
        }
        return AjaxResult.success(rows);
    }

    @Log(title = "那坡信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(npInfoService.deleteNpInfoByIds(ids));
    }

    private String unzip(String zipFile) throws ZipException {
        ZipFile zFile = new ZipFile(Global.getProfile() + zipFile);
        zFile.setCharset(Charset.forName("GBK"));
        String destPath = zipFile.substring(0, zipFile.lastIndexOf("."));
        String destFullPath = Global.getProfile() + destPath;
        if (!new File(destPath).exists()) {
            zFile.extractAll(destFullPath);
            File destDir = new File(destFullPath);
            File[] files = destDir.listFiles();
            if (files.length == 1 && files[0].isDirectory()) {
                String from = files[0].getPath();
                boolean isMove = fileMove(from, destFullPath);
                if (isMove) {
                    files[0].delete();
                }
            }
        }
        return destPath;
    }

    private boolean fileMove(String from, String to) {
        boolean flag = false;
        try {
            File dir = new File(from);
            File[] files = dir.listFiles();
            if (files == null) {
                return flag;
            }
            File moveDir = new File(to);
            if (!moveDir.exists()) {
                moveDir.mkdirs();

            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    fileMove(files[i].getPath(),
                            to + "/" + files[i].getName());
                    files[i].delete();
                }
                File moveFile = new File(moveDir.getPath() + "/"
                        + files[i].getName());
                if (moveFile.exists()) {
                    moveFile.delete();
                }
                files[i].renameTo(moveFile);
            }

        } catch (Exception e) {
            return flag;
        }
        flag = true;
        return flag;
    }
}
