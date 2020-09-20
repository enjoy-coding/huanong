package com.feather.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feather.common.config.TopicMessageDelegate;

/**
 * @author feather
 */
@Controller
public class WebSocketDemoController {
    private String topic = "/myproject/meter/todaywater";

    @Autowired
    private TopicMessageDelegate topicMessageDelegate;

    @GetMapping("/system/demo/websocket")
    public String index() {
        return "system/demo/websocket/client";
    }

    @MessageMapping("/myproject/meter/view")
    @ResponseBody
    public void view(DemoMeter meter) throws Exception {
        topicMessageDelegate.getDelegate(topic).send(meter);
    }

    public static class DemoMeter {
        private String id;
        private String typename;
        private String sn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }
    }
}
