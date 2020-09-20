package com.feather.napo.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author nothing
 * @date 2020-06-30 16:18
 */
public class RequestFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String INFO_TYPE = "infoType";
    private final String ORDER_BY_COLUMN = "orderByColumn";
    private final String IS_ASC = "isAsc";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("---init---");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String infoType = request.getParameter(INFO_TYPE);
        String orderByColumn = request.getParameter(ORDER_BY_COLUMN);
        String isAsc = request.getParameter(IS_ASC);

        if (Strings.isNotBlank(infoType)) {
            if (Strings.isBlank(orderByColumn) || Strings.isBlank(isAsc)) {
                //todo 根据类型来设置排序规则
                Map parameter = new HashMap(16);
                parameter.put(ORDER_BY_COLUMN, "createTime");
                parameter.put(IS_ASC, "desc");
                ParameterRequestWrapper wrapper = new ParameterRequestWrapper(request, parameter);
                filterChain.doFilter(wrapper, servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy(){
        logger.info("---destroy---");
    }
}
