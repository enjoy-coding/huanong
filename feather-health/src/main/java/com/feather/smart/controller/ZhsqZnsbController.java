package com.feather.smart.controller;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.json.JSONObject;
import com.feather.smart.config.rabbitmqConfig;
import com.feather.smart.domain.ZhsqZnsb;

import com.feather.smart.service.IZhsqZnsbService;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class ZhsqZnsbController extends BaseController {

    private final static String URL="http://api.jiai.pro:8080/jiai/";

    private final static String CPID="1279";

    private final static String KEY="fca3b9d3e31afb3e90e8bc67dc8fdb85";

    //手表串号
    private final static String IMEI="356201199014211";

    private final static String JSON="{\"imei\":\"356201199014211\"}";

    @Autowired
    IZhsqZnsbService zhsqZnsbService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 获取心率测量请求
     * @return
     */
    @PostMapping("/{name}")
    @ResponseBody
    public AjaxResult health (@PathVariable("name")String name,@RequestBody String requestBody){
        Map map = new HashMap();
        map.put("name",name);
        map.put("requestBody",requestBody);
        AjaxResult ajaxResult = (AjaxResult) rabbitTemplate.convertSendAndReceive(rabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.sb", map);
        return ajaxResult;
    }


}