package com.feather.hikvision;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feather.common.config.UidWorker;
import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.http.HttpUtils;
import com.feather.hikvision.config.EventType;
import com.feather.hikvision.config.ResourceType;
import com.feather.hikvision.domain.AupWarring;
import com.feather.hikvision.service.IAupWarringService;
import com.feather.hikvision.vo.Events;
import com.feather.hikvision.vo.FieldDetectionData;
import com.feather.hikvision.vo.Fielddetection;
import com.feather.hikvision.vo.HikvisionEventBean;
import com.feather.hikvision.vo.Params;

@Controller
public class HikvisionController extends BaseController {
    final String FieldDetectionType = "131588";
    // final String[] Data_Replace_Arr = {"\"data\":\"消警\""};

    @Value("${hikvision.dataCenter}")
    private String dataCenter;

    @Autowired
    private HikvisionService hikvisionService;

    @Autowired
    private IAupWarringService aupWarringService;

    @Autowired
    private UidWorker uidWorker;

    @PostMapping("/eventRcv")
    @ResponseBody
    public AjaxResult eventRcv(@RequestBody String result) {
        // result =
        // "{\"method\":\"OnEventNotify\",\"params\":{\"ability\":\"event_rule\",\"events\":[{\"data\":{\"channelID\":1,\"dataType\":\"behavioralAnalysis\",\"dateTime\":\"2020-05-27T17:27:58.000+08:00\",\"eventDescription\":\"fielddetection\",\"eventType\":\"fielddetection\",\"fielddetection\":[{\"detectionTarget\":1,\"duration\":0,\"imageUrl\":\"http://10.163.132.200:6120/pic?3dd237i70-e*8f7406005175m9ep=t=i7p*i=d1s*i8d4d*=*8beib0950617734e2--a65579-e10s9c40z674i=4=\",\"rate\":0,\"regionCoordinatesList\":[{\"positionX\":0.43799999356269842,\"positionY\":0.0060000000521540642},{\"positionX\":0.95700001716613758,\"positionY\":0},{\"positionX\":0.91100001335144043,\"positionY\":0.99299997091293335},{\"positionX\":0,\"positionY\":0.99299997091293335}],\"sensitivityLevel\":0,\"targetAttrs\":{\"cameraIndexCode\":\"fc1456f17c8e48728ed2357db4af4942\",\"channelName\":\"总配电房操作区北\",\"deviceIndexCode\":\"643750f47d584017a2d98d840633b8c6\",\"imageServerCode\":\"ed992682-ec18-40d1-86c4-eaf9d2efcef1\"}}],\"ipAddress\":\"10.163.133.30\",\"portNo\":8000,\"recvTime\":\"2020-05-27T17:27:58.924+08:00\",\"sendTime\":\"2020-05-27T17:27:59.200+08:00\"},\"eventId\":\"CFCAEA51-A1F5-410C-B4E3-162D0721F088\",\"eventType\":131588,\"happenTime\":\"2020-05-27T17:27:58.000+08:00\",\"srcIndex\":\"fc1456f17c8e48728ed2357db4af4942\",\"srcName\":\"总配电房操作区北\",\"srcParentIndex\":\"643750f47d584017a2d98d840633b8c6\",\"srcType\":\"camera\",\"status\":0,\"timeout\":0}],\"sendTime\":\"2020-05-27T17:27:59.201+08:00\"}}";
        System.out.println("===监听到的事件===" + result);
        // todo 转换为eventBean以后，根据触发事件不同的事件源做相应的处理，如烟感、红外等相应的就应该增加
        try {
            List<AupWarring> aupWarringList = convertToAupWarring(result);
            if (!Objects.isNull(aupWarringList) && aupWarringList.size() > 0) {
                for (AupWarring aupWarring : aupWarringList) {
                    String id = UUID.randomUUID().toString();
                    aupWarring.setId(id);
                    aupWarringService.insertAupWarring(aupWarring);

                    sendMessage(aupWarring);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }

    @GetMapping("/registerSubscribeEvents")
    @ResponseBody
    public AjaxResult registerSubscribeEvents(@RequestParam(value = "requestUrl", required = false) String requestUrl,
            @RequestParam("eventTypes") String eventTypeStr, HttpServletRequest request) {
        String eventDest;
        Number[] eventTypes = null;
        try {
            if (Strings.isNotBlank(requestUrl)) {
                eventDest = requestUrl;
            } else {
                eventDest = getRequestUrl(request);
            }
            if (Strings.isNotBlank(eventTypeStr)) {
                eventTypes = getEventType(eventTypeStr);
            }
            eventDest += "/eventRcv";
            String result = hikvisionService.registerSubscribeEvents(eventTypes, eventDest);
            logger.info("===注册海康威视事件监听结束===,注册结果为:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    private Number[] getEventType(String eventTypeStr) {
        Number[] eventTypes = null;
        if (Strings.isNotBlank(eventTypeStr)) {
            String[] eventTypeArr = eventTypeStr.split(",");
            eventTypes = new Number[eventTypeArr.length];
            for (int i = 0; i < eventTypeArr.length; i++) {
                eventTypes[i] = Integer.parseInt(eventTypeArr[i]);
            }
        }
        return eventTypes;
    }

    private String getRequestUrl(HttpServletRequest request) {
        String requestUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort();
        return requestUrl;
    }

    @GetMapping("/getOnlineCamera")
    public String getOnlineCamera(@RequestParam("pageSize") Integer pageSize) {
        return hikvisionService.getOnlineCamera(pageSize);
    }

    @GetMapping("/getOnlineAcs")
    public String getOnlineAcs(@RequestParam("pageSize") Integer pageSize) {
        return hikvisionService.getOnlineAcs(pageSize);
    }

    @GetMapping("/getSecurityDevices")
    public String getSecurityDevices(@RequestParam("pageSize") Integer pageSize) {
        return hikvisionService.getOnlineDevice(pageSize);
    }

    /**
     * 将接收到的记录转化为预警记录信息对象
     *
     * @return
     */
    private List<AupWarring> convertToAupWarring(String result) throws Exception {
        List<AupWarring> aupWarringList = new ArrayList<AupWarring>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        result = checkAndReplaceIfNeed(result);
        HikvisionEventBean hikvisionEventBean = mapper.readValue(result, HikvisionEventBean.class);
        Params params = hikvisionEventBean.getParams();
        if (!Objects.isNull(params)) {
            List<Events> eventsList = params.getEvents();
            if (!Objects.isNull(eventsList) && eventsList.size() > 0) {
                AupWarring aupWarring = null;
                for (Events events : eventsList) {
                    aupWarring = new AupWarring();
                    aupWarring.setId(String.valueOf(uidWorker.getNextId()));

                    String happenTime = events.getHappenTime();
                    String srcTypeCode = events.getSrcType();
                    // 事件类型
                    String eventType = events.getEventType();
                    String srcName = events.getSrcName();
                    // 设备编码
                    String code = events.getSrcIndex();
                    if ("327941".equals(eventType)) {
                        aupWarringService.updateAupWarring(code);
                        continue;
                    }
                    String imageUrl = getImageUrl(events, aupWarring.getId());
                    String srcType = ResourceType.getName(srcTypeCode);
                    // 事件类型转换
                    String eventName = EventType.getName(eventType);
                    if (happenTime != null) {
                        int at = happenTime.indexOf('.');
                        if (at > -1) {
                            happenTime = happenTime.substring(0, at);
                        }
                    }
                    aupWarring.setDatetime(happenTime);
                    aupWarring.setCode(code);
                    aupWarring.setLevel("2");
                    aupWarring.setFormsysname("监控");
                    aupWarring.setPath(srcName);
                    aupWarring.setState("0");
                    aupWarring.setImageurl1(imageUrl);
                    if (Strings.isNotBlank(srcType)) {
                        aupWarring.setName(srcType);
                    } else {
                        aupWarring.setName(srcTypeCode);
                    }
                    if (Strings.isNotBlank(eventName)) {
                        aupWarring.setAuthor(eventName);
                    } else {
                        aupWarring.setAuthor(eventType);
                    }
                    String content = buildEventContent(events);
                    aupWarring.setContent(content);
                    aupWarringList.add(aupWarring);
                }
            }
        }
        return aupWarringList;
    }

    private String buildEventContent(Events events) {
        String srcName = events.getSrcName();
        String eventType = events.getEventType();
        String tmpEventType = EventType.getName(eventType);
        if (Strings.isNotBlank(tmpEventType)) {
            eventType = tmpEventType;
        }
        String content = srcName + "发生" + eventType + ",请排查!";
        return content;
    }

    private String getImageUrl(Events events, String id) throws IOException {
        StringBuilder sb = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String eventType = events.getEventType();
        Map<String, Object> map = events.getData();
        String jsonStr = mapper.writeValueAsString(map);
        if (FieldDetectionType.equals(eventType)) {
            FieldDetectionData data = mapper.readValue(jsonStr, FieldDetectionData.class);
            List<Fielddetection> fielddetections = data.getFielddetection();
            if (!Objects.isNull(fielddetections)) {
                if (fielddetections.size() == 1) {
                    Fielddetection fielddetection = fielddetections.get(0);
                    String imgUrl = fielddetection.getImageUrl();
                    sb.append(imgUrl);
                } else {
                    for (Fielddetection fielddetection : fielddetections) {
                        String imgUrl = fielddetection.getImageUrl();
                        sb.append(imgUrl).append(";");
                    }
                }
            }
        }

        String hkImageUrl = sb.toString();
        if (StringUtils.isNotBlank(hkImageUrl)) {
            try {
                String url = uploadLink(hkImageUrl);
                return url;
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return hkImageUrl;
    }

    private String uploadLink(String url) throws Exception {
        String server = dataCenter + "/prd/attachment/api/uploadLink";
        String param = "link=" + URLEncoder.encode(url, "utf-8") + "&ext=jpg";
        String request = server + "?" + param;
        String json = HttpUtils.sendGet(request);
        AjaxResult result = JSONObject.parse(json, AjaxResult.class);
        Map data = (Map) result.get(AjaxResult.DATA_TAG);
        return (String) data.get("filePath");
    }

    private void sendMessage(AupWarring aupWarring) throws Exception {
        String server = dataCenter + "/prd/msg/api/sendToRole";
        String param = "app=wx,sms&type=warning&title=" + URLEncoder.encode(aupWarring.getAuthor(), "utf-8")
                + "&content=" + URLEncoder.encode(aupWarring.getContent(), "utf-8") + "&roles=znaf_handler";
        HttpUtils.sendPost(server, param);
    }

    /**
     * 判断是否需要替换data中的数据为对象如果需要替换的话， 针对于海康返回的data为字符串的情况（傻X设计）
     *
     * @return
     */
    private String checkAndReplaceIfNeed(String result) {
        String lp = "{";
        // 使用非贪婪模式
        String regex = "\"data\":\"(.*?)\\\"";
        Matcher matcher = Pattern.compile(regex).matcher(result);
        if (matcher.find()) {
            String dataVal = matcher.group(1);
            if (Strings.isNotBlank(dataVal) && !dataVal.startsWith(lp)) {
                String deafultData = "\"data\":\"" + dataVal + "\"";
                String strObj = changeStrToObj(deafultData);
                result = result.replaceFirst(deafultData, strObj);
            }
        }
        return result;
    }

    /**
     * 将字符串转换为字符串对象
     *
     * @param str
     * @return
     */
    private String changeStrToObj(String str) {
        StringBuilder sb = new StringBuilder();
        if (Strings.isNotBlank(str)) {
            String[] arr = str.split(":");
            String key = arr[0];
            String value = arr[1];
            sb.append(key).append(":{\"value\":").append(value).append("}");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        /*
         * HikvisionController hikvisionController = new HikvisionController();
         * String result =
         * "{\"method\":\"OnEventNotify\",\"params\":{\"ability\":\"event_rule\",\"events\":[{\"data\":{\"channelID\":1,\"dataType\":\"behavioralAnalysis\",\"dateTime\":\"2020-05-27T17:27:58.000+08:00\",\"eventDescription\":\"fielddetection\",\"eventType\":\"fielddetection\",\"fielddetection\":[{\"detectionTarget\":1,\"duration\":0,\"imageUrl\":\"http://10.163.132.200:6120/pic?3dd237i70-e*8f7406005175m9ep=t=i7p*i=d1s*i8d4d*=*8beib0950617734e2--a65579-e10s9c40z674i=4=\",\"rate\":0,\"regionCoordinatesList\":[{\"positionX\":0.43799999356269842,\"positionY\":0.0060000000521540642},{\"positionX\":0.95700001716613758,\"positionY\":0},{\"positionX\":0.91100001335144043,\"positionY\":0.99299997091293335},{\"positionX\":0,\"positionY\":0.99299997091293335}],\"sensitivityLevel\":0,\"targetAttrs\":{\"cameraIndexCode\":\"fc1456f17c8e48728ed2357db4af4942\",\"channelName\":\"总配电房操作区北\",\"deviceIndexCode\":\"643750f47d584017a2d98d840633b8c6\",\"imageServerCode\":\"ed992682-ec18-40d1-86c4-eaf9d2efcef1\"}}],\"ipAddress\":\"10.163.133.30\",\"portNo\":8000,\"recvTime\":\"2020-05-27T17:27:58.924+08:00\",\"sendTime\":\"2020-05-27T17:27:59.200+08:00\"},\"eventId\":\"CFCAEA51-A1F5-410C-B4E3-162D0721F088\",\"eventType\":131588,\"happenTime\":\"2020-05-27T17:27:58.000+08:00\",\"srcIndex\":\"fc1456f17c8e48728ed2357db4af4942\",\"srcName\":\"总配电房操作区北\",\"srcParentIndex\":\"643750f47d584017a2d98d840633b8c6\",\"srcType\":\"camera\",\"status\":0,\"timeout\":0}],\"sendTime\":\"2020-05-27T17:27:59.201+08:00\"}}";
         * List<AupWarring> aupWarrings =
         * hikvisionController.convertToAupWarring(result); for (AupWarring
         * aupWarring : aupWarrings) { System.out.println(aupWarring); }
         */

        StringBuffer sb = new StringBuffer("http://lcoalhost:8080/env");
        System.out.println(sb.substring(0, sb.lastIndexOf("/")));
    }
}
