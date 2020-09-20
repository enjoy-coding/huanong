package com.feather.hikvision;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.feather.common.json.JSONObject;
import com.feather.common.json.JSONObject.JSONArray;
import com.feather.hikvision.config.HikvisionConstants;
import com.feather.hikvision.util.HikvisionUtils;
import com.hikvision.artemis.sdk.constant.HttpMethod;

@Service
public class HikvisionService {
    /**
     * 增加字符串零
     */
    public final String ZERO_STR = "0";
    public final int ZER0 = 0;
    public final int ONE = 1;
    public final int TWO = 2;
    /**
     * 不可设置太高，设置太高会报错
     */
    public final int DEFAULT_PAGE_SIZE = 800;

    /**
     * 注册订阅事件
     */
    public String registerSubscribeEvents(Number[] eventTypes, String eventDest) {

        if (Objects.isNull(eventTypes)) {
            eventTypes = new Number[]{237681, 327941, 198400, 198657, 200453, 200455, 199708, 199710, 197121, 198145,
                    197137};
        }

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("eventTypes", eventTypes);
        jsonBody.put("eventDest", eventDest);

        return HikvisionUtils.doBaseArtemis(HikvisionConstants.EVENT_SUBSCRIPTION_BY_EVENT_TYPES, jsonBody,
                HttpMethod.POST);
    }

    /**
     * 获取在线的摄像头数量信息
     * 在海康平台中v1.4版本的事通过/api/irds/v2/resource/resourcesByParams，在v1.2平台中通过/api/resource/v1/cameras
     *
     * @return
     */
    public String getOnlineCamera(Integer pageSize) {
        String url = HikvisionConstants.RESOURCE_V1_CAMERAS;
        Integer[] arr = getOnlineFromUrl(url, pageSize);
        return arr[0] + "/" + arr[1];
    }

    /**
     * 获取门禁设备
     *
     * @param pageSize
     * @return
     **/
    public String getOnlineAcs(Integer pageSize) {
        String url = HikvisionConstants.RESOURCE_V1_DEVICE;
        Integer[] arr = getOnlineFromUrl(url, pageSize);
        return arr[0] + "/" + arr[1];
    }

    /**
     * 获取在线设备
     *
     * @param pageSize
     * @return
     */
    public String getOnlineDevice(Integer pageSize) {
        String url1 = HikvisionConstants.RESOURCE_V1_CAMERAS;
        String url2 = HikvisionConstants.RESOURCE_V1_DEVICE;
        Integer[] arr1 = getOnlineFromUrl(url1, pageSize);
        Integer[] arr2 = getOnlineFromUrl(url2, pageSize);

        return (arr1[0] + arr2[0]) + "/" + (arr1[1] + arr2[1]);
    }

    private Integer[] getOnlineFromUrl(String url, Integer pageSize) {
        int num = 0;
        Integer[] arr = new Integer[]{0, 0};
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        //pageSize设置过大会报错
        if (Objects.isNull(pageSize)) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        jsonBody.put("pageSize", pageSize);
        String result = HikvisionUtils.doBaseArtemis(url, jsonBody,
                HttpMethod.POST);
        JSONObject jsonObject = JSONObject.parse(result);
        String code = jsonObject.getString("code");
        if (ZERO_STR.equals(code)) {
            jsonObject = jsonObject.getJSONObject("data");
            int total = jsonObject.getInt("total");
            arr[1] = total;
            JSONObject.JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Object statusObj = jsonObject.get("status");
                // 如果该字段未定义，默认该摄像头是正常的
                if (Objects.isNull(statusObj)) {
                    num++;
                } else {
                    int status = (int) statusObj;
                    if (ONE == status) {
                        num++;
                    }
                }
            }
            arr[0] = num;
        }
        return arr;
    }
}
