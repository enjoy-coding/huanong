package com.feather.hikvision.util;

import com.feather.common.json.JSONObject;
import com.feather.hikvision.config.HikvisionConstants;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.hikvision.artemis.sdk.constant.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class HikvisionUtils {

    public static String doBaseArtemis(String url, JSONObject jsonBody, String method) {
        String result = "";

        ArtemisConfig.host = HikvisionConstants.HOST;
        ArtemisConfig.appKey = HikvisionConstants.APP_KEY;
        ArtemisConfig.appSecret = HikvisionConstants.APP_SECRET;

        final String ARTEMIS_PATH = HikvisionConstants.ARTEMIS_PATH;

        final String urlsApi = ARTEMIS_PATH + url;

        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("http://", urlsApi);// 根据现场环境部署确认是http还是https
            }
        };

        String contentType = "application/json";

        if (HttpMethod.POST.equals(method)) {
            result = ArtemisHttpUtil.doPostStringArtemis(path, jsonBody.toString(), null, null, contentType, null);
        }
        return result;
    }
}
