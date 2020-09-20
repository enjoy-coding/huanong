package com.feather.aupipes.service.impl;

import org.springframework.stereotype.Service;

import com.feather.aupipes.service.IAupDailyInspectService;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;

/**
 * @author sky
 * @date 2019/12/14 17:14
 */
@Service
public class AupDailyInspectServiceImpl implements IAupDailyInspectService {

    @Override
    public AjaxResult situation() {

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "正常");
        jsonObject1.put("value", 478);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "异常");
        jsonObject2.put("value", 204);

        JSONObject.JSONArray jsonArray = new JSONObject.JSONArray();
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        return AjaxResult.success(jsonArray);
    }

    @Override
    public AjaxResult personnelList() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("serial", i + 1);
            jsonObject1.put("name", "华农南苑");
            jsonObject1.put("personnel", "李云迪");
            jsonArray.add(jsonObject1);
        }

        return getAjaxResult(jsonArray);
    }

    @Override
    public AjaxResult siteList() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("serial", i + 1);
            jsonObject1.put("location", "阀门井1");
            jsonArray.add(jsonObject1);
        }

        return getAjaxResult(jsonArray);
    }

    @Override
    public AjaxResult exceptionDetails() {
        return null;
    }

    @Override
    public AjaxResult inspectRecord() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("serial", i + 1);
            jsonObject1.put("name", "华农西苑265号巡查");
            jsonObject1.put("personnel", "李云迪");
            jsonObject1.put("releaseTime", "2019/08/23 12:30:00");
            jsonObject1.put("startTime", "2019/08/23 12:30:00");
            jsonObject1.put("endTime", "2019/08/23 12:30:00");
            jsonObject1.put("duration", "1h30m21s");
            jsonObject1.put("yx", "20");
            jsonObject1.put("sx", "20");
            jsonObject1.put("lx", "1");
            jsonObject1.put("abnormal", "1");
            jsonObject1.put("type", "已处理");
            jsonArray.add(jsonObject1);
        }
        return getAjaxResult(jsonArray);
    }

    @Override
    public AjaxResult deviceProportion() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", 203);
        jsonObject.put("name", "监控");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("value", 178);
        jsonObject2.put("name", "监测");

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("value", 260);
        jsonObject3.put("name", "表计");

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("value", 378);
        jsonObject4.put("name", "电闸");

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("value", 478);
        jsonObject5.put("name", "路灯");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonArray.add(jsonObject5);
        return getAjaxResult(jsonArray);
    }

    @Override
    public AjaxResult areaProportion() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", 30);
        jsonObject.put("name", "梦泽园");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("value", 20);
        jsonObject2.put("name", "荟园");

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("value", 60);
        jsonObject3.put("name", "宝积苑");

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("value", 100);
        jsonObject4.put("name", "傅园");

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        return getAjaxResult(jsonArray);
    }

    @Override
    public AjaxResult exceptionNum() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(12);
        jsonArray.add(9);
        jsonArray.add(30);
        jsonArray.add(45);
        jsonArray.add(38);
        return getAjaxResult(jsonArray);
    }

    @Override
    public AjaxResult personnelStatistics() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(40);
        jsonArray.add(40);
        jsonArray.add(40);
        jsonArray.add(30);
        jsonArray.add(40);

        JSONArray jsonArray2 = new JSONArray();
        jsonArray2.add(45);
        jsonArray2.add(45);
        jsonArray2.add(40);
        jsonArray2.add(45);
        jsonArray2.add(42);

        JSONArray total = new JSONArray();
        total.add(jsonArray);
        total.add(jsonArray2);
        return getAjaxResult(total);
    }

    private AjaxResult getAjaxResult(JSONArray jsonArray) {
        JSONObject total = new JSONObject();
        total.put("total", jsonArray.size());
        total.put("page", (jsonArray.size() - 1) / 10 + 1);
        total.put("list", jsonArray);
        return AjaxResult.success(total);
    }
}