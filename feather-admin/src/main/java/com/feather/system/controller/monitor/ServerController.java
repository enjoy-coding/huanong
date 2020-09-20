package com.feather.system.controller.monitor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feather.common.core.controller.BaseController;
import com.feather.framework.web.domain.Server;

/**
 * 服务器监控
 * 
 * @author feather
 */
@Controller
@RequestMapping("/system/monitor/server")
public class ServerController extends BaseController {
    private String prefix = "system/monitor/server";

    @RequiresPermissions("monitor:server:view")
    @GetMapping()
    public String server(ModelMap mmap) throws Exception {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        return prefix + "/server";
    }
}
