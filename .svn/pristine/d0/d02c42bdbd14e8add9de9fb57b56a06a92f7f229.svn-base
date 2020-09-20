package com.feather.aupipes.controller;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.service.IAupWarringService;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.http.HttpUtils;

@Controller
public class HikvisionPicController extends BaseController {

    @Autowired
    private IAupWarringService aupWarringService;

    @GetMapping("/api/hik/trimpic")
    @ResponseBody
    public AjaxResult trimpic(HttpServletRequest request) {
        int count = 0;
        try {
            String basePath = getBasePath();
            List<AupWarring> list = aupWarringService.selectAllWithOuterPic();
            for (AupWarring aupWarring : list) {
                try {
                    String pic = uploadLink(basePath, aupWarring.getImageurl1());
                    AupWarring aw = new AupWarring();
                    aw.setId(aupWarring.getId());
                    aw.setImageurl1(pic);
                    aupWarringService.updateAupWarring(aw);
                    count++;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return AjaxResult.success("成功转换：" + count + " 个");
    }

    private String uploadLink(String basePath, String url) throws Exception {
        String server = basePath + "/prd/attachment/api/uploadLink";
        String param = "link=" + URLEncoder.encode(url, "utf-8") + "&ext=jpg";
        String request = server + "?" + param;
        String json = HttpUtils.sendGet(request);
        AjaxResult result = JSONObject.parse(json, AjaxResult.class);
        Map data = (Map) result.get(AjaxResult.DATA_TAG);
        return (String) data.get("filePath");
    }
}
