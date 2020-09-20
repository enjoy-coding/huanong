package com.feather.hikvision.config;

import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.feather.framework.util.RedisUtils;
import com.feather.hikvision.HikvisionService;

/**
 * 启动的时候注册事件订阅事件，同一个用户重复订阅相同的事件，接口内部逻辑自动去重，确保不重复投递事件。
 */
@Component
public class HikvisionApplicationRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(HikvisionApplicationRunner.class);

    @Autowired
    HikvisionService hikvisionService;

    @Value("${hikvision.reqestUrl}")
    String requestUrl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String eventDest;
        logger.info("===注册海康威视事件监听开始===");
        Object eventDestObj = RedisUtils.get(HikvisionConstants.EVENT_DEST);
        if (!Objects.isNull(eventDestObj)) {
            eventDest = eventDestObj.toString();
        } else {
            eventDest = requestUrl + "/eventRcv";
        }
        logger.info("===事件注册地址为%s==", eventDest);
        if (Strings.isBlank(eventDest))
            return;
        String result = hikvisionService.registerSubscribeEvents(null, eventDest);
        logger.info("===注册海康威视事件监听结束===,注册结果为:" + result);
    }
}
