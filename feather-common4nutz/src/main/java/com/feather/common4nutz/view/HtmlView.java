package com.feather.common4nutz.view;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Encoding;
import org.nutz.lang.Streams;
import org.springframework.web.servlet.View;

/**
 * @author nothing
 * @date 2019-11-15 8:16
 */
public class HtmlView implements View {
    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            Object mapValue = entry.getValue();
            sb.append(mapValue);
        }
        byte[] data = String.valueOf(sb).getBytes(Encoding.UTF8);
        httpServletResponse.setHeader("Content-Length", "" + data.length);
        OutputStream out = httpServletResponse.getOutputStream();
        Streams.writeAndClose(out, data);
    }
}