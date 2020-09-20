package com.feather.patrol.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.ServletUtils;
import com.feather.common.utils.StringUtils;
import com.feather.framework.util.ShiroUtils;
import com.feather.patrol.domain.PtrCard;
import com.feather.patrol.domain.forexmobi.Facility;
import com.feather.patrol.mapper.FacilityMapper;
import com.feather.patrol.mapper.PtrCardMapper;
import com.feather.patrol.service.IPtrLogService;
import com.feather.system.domain.SysUser;

@Configuration
public class XfResourcesConfig implements WebMvcConfigurer {

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    private PtrCardMapper ptrCardMapper;
    @Autowired
    private IPtrLogService ptrLogService;

    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(new AllRequest()).addPathPatterns("/**");

        registry.addInterceptor(new RsisToken(facilityMapper)).addPathPatterns("/**/Token.app.xf");

        registry.addInterceptor(new RsisLogin()).addPathPatterns("/**/Token.login.xf");

        registry.addInterceptor(new RsisLog(ptrCardMapper, ptrLogService))
                .addPathPatterns("/**/RSI_Facility.addLog.xf");
    }

    private static AjaxResult getAjaxResult() {
        return getAjaxResult(200, null, null);
    }

    private static AjaxResult getAjaxResult(Object data) {
        return getAjaxResult(200, null, data);
    }

    private static AjaxResult getAjaxResult(String msg) {
        return getAjaxResult(500, msg, null);
    }

    private static AjaxResult getAjaxResult(int status, String msg, Object data) {
        AjaxResult result = new AjaxResult();
        result.put("status", status);
        result.put("message", msg);
        result.put("data", data);
        return result;
    }

    static class AllRequest extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            System.out.println(request.getRequestURL() + ": " + JSONObject.marshal(request.getParameterMap()));
            Enumeration<String> head = request.getHeaderNames();
            while (head.hasMoreElements()) {
                String name = head.nextElement();
                System.out.println(name + ":" + request.getHeader(name));
            }

            return super.preHandle(request, response, handler);
        }
    }

    static class RsisToken extends HandlerInterceptorAdapter {
        private final Logger logger = LoggerFactory.getLogger(RsisToken.class);

        private FacilityMapper facilityMapper;

        public RsisToken(FacilityMapper facilityMapper) {
            this.facilityMapper = facilityMapper;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String head = request.getHeader("_pkg");
            if (StringUtils.isNotEmpty(head)) {
                switch (head) {
                case "pVersion.appa_find": {
                    // String pkg = request.getParameter("package");
                    String version = request.getParameter("client");

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("_token", request.getSession().getId());
                    data.put("version", version);

                    AjaxResult result = getAjaxResult(data);
                    ServletUtils.renderString(response, JSONObject.marshal(result));
                    break;
                }
                case "RSI_Facility.app_find": {
                    String qrcode = request.getParameter("code");

                    Facility data = null;
                    String message = null;
                    try {
                        List<Facility> list = facilityMapper.selectFacilityByQrcode(qrcode);
                        if (list == null || list.size() == 0) {
                            message = "不能识别的二维码";
                            logger.error(message + ":" + qrcode);
                        } else if (list.size() > 1) {
                            message = "此二维码冲突，请联系系统管理员。";
                            logger.error(message + ":" + qrcode);
                        } else {
                            data = list.get(0);
                        }
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                        message = ex.getMessage();
                    }
                    AjaxResult result = message == null ? getAjaxResult(data) : getAjaxResult(message);
                    ServletUtils.renderString(response, JSONObject.marshal(result));
                    break;
                }
                }
            }
            return false;
        }
    }

    static class RsisLogin extends HandlerInterceptorAdapter {
        private final Logger logger = LoggerFactory.getLogger(RsisLogin.class);

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            // String agents = request.getParameter("agents");

            HashMap<String, Object> data = new HashMap<>();
            String message = null;
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(account, password, false);
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                SysUser sysUser = ShiroUtils.getSysUser();

                Date loginDate = sysUser.getLoginDate();
                if (loginDate == null) {
                    loginDate = new Date();
                }

                data.put("org_root", sysUser.getDeptId());
                data.put("person_name", sysUser.getUserName());
                data.put("person_applogindt", DateFormatUtils.format(loginDate, "yyyy-MM-dd HH:mm"));
                data.put("_token", request.getSession().getId());
            } catch (AuthenticationException e) {
                logger.error(e.getMessage(), e);
                message = "用户或密码错误";
                if (StringUtils.isNotEmpty(e.getMessage())) {
                    message = e.getMessage();
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                message = ex.getMessage();
            }

            AjaxResult result = message == null ? getAjaxResult(data) : getAjaxResult(message);
            ServletUtils.renderString(response, JSONObject.marshal(result));
            return false;
        }
    }

    static class RsisLog extends HandlerInterceptorAdapter {
        private final Logger logger = LoggerFactory.getLogger(RsisLog.class);

        private PtrCardMapper ptrCardMapper;
        private IPtrLogService ptrLogService;

        public RsisLog(PtrCardMapper ptrCardMapper, IPtrLogService ptrLogService) {
            this.ptrCardMapper = ptrCardMapper;
            this.ptrLogService = ptrLogService;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String message = null;
            try {
                MultipartResolver resolver = new StandardServletMultipartResolver();
                MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
                Map<String, MultipartFile> files = multipartRequest.getFileMap();
                List<MultipartFile> picList = new ArrayList<>();
                for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
                    String key = entry.getKey();
                    if (key.indexOf(".pic") > 0) {
                        continue;
                    }
                    picList.add(entry.getValue());
                }
                MultipartFile[] pics = new MultipartFile[picList.size()];
                picList.toArray(pics);

                String id = request.getParameter("facility_id");
                String altitude = request.getParameter("log_altitude");
                String standard = request.getParameter("log_standard").replaceAll(";", ",");
                String longitude = request.getParameter("log_longitude");
                String latitude = request.getParameter("log_latitude");
                String address = request.getParameter("log_address");
                String issue = request.getParameter("log_issue");
                String remark = request.getParameter("log_remark");

                Long cardId = Long.parseLong(id);
                PtrCard ptrCard = ptrCardMapper.selectPtrCardById(cardId);

                String coordinate = longitude + "," + latitude;
                String issueStr = issue + remark;
                Long[] standards = Convert.toLongArray(standard);
                ptrLogService.insertPtrLog(ptrCard.getCardQrcode1(), coordinate, altitude, address, issueStr, standards,
                        pics);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                message = ex.getMessage();
            }

            AjaxResult result = message == null ? getAjaxResult() : getAjaxResult(message);
            ServletUtils.renderString(response, JSONObject.marshal(result));
            return false;
        }
    }
}
