package com.feather.aupipes.HttpClient;

import java.util.HashMap;
import java.util.Map;

import com.feather.aupipes.controller.PipeAnalysis;
import com.feather.common.config.Global;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.spring.SpringUtils;

/**
 * 拉闸限电关阀停水请求查询
 */
public class HttpClientFwPdf {

    private String aupipeService = Global.getConfig("spring.thymeleaf.staticVariables.aupipeService");

    // 拉闸请求
    private String url1 = aupipeService + "/pipeV2/pipe/getdatabyoid";
    // 关阀请求
    private String url2 = aupipeService + "/pipeV2/pipe/getValve3";

    public String getFwPdf(String type, String oid) throws Exception {
        String url = "";
        HashMap<String, String> params = new HashMap<String, String>();
        if (type.equals("lzxd")) {
            url = url1;
            params.put("oid", oid);
            params.put("type", "");
        } else if (type.equals("gfts")) {
            url = url2;
            params.put("oid", oid);
        }

        // String result = HttpClientUtil.httpGetWithJSON(url, params);
        PipeAnalysis analysis = SpringUtils.getBean(PipeAnalysis.class);
        AjaxResult valveByPoint = analysis.getValveByPoint(Integer.parseInt(oid));
        Map map = valveByPoint;
        JSONObject jsonObject = JSONObject.toJSONObject(map);

        return jsonObject.toString();
    }

}
