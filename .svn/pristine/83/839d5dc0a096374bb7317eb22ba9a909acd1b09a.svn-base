package com.feather.framework.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.feather.common.annotation.DataScope;
import com.feather.common.config.Global;
import com.feather.common.config.datasource.DynamicDataSourceContextHolder;
import com.feather.common.core.domain.BaseEntity;
import com.feather.common.utils.StringUtils;
import com.feather.framework.util.ShiroUtils;
import com.feather.system.domain.SysRole;
import com.feather.system.domain.SysUser;

/**
 * 数据过滤处理
 * 
 * @author feather
 */
@Aspect
@Component
public class DataScopeAspect {
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    public static final int DATASOURCE_TYPE_UNKNOWN = 0;
    public static final int DATASOURCE_TYPE_SQLSERVER = 1;
    public static final int DATASOURCE_TYPE_MYSQL = 2;
    public static final int DATASOURCE_TYPE_ORACLE = 3;
    private static int defaultDatasourceType = -1;

    // 配置织入点
    @Pointcut("@annotation(com.feather.common.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        DataScope controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null) {
            return;
        }
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(),
                        controllerDataScope.userAlias());
            }
        }
    }

    /**
     * 数据范围过滤
     * 
     * @param joinPoint
     *            切点
     * @param user
     *            用户
     * @param alias
     *            别名
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();

        for (SysRole role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
                        role.getRoleId()));
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                switch (getDefaultDatasourceType()) {
                case DATASOURCE_TYPE_SQLSERVER: {
                    sqlString.append(StringUtils.format(
                            " OR {}.id_path like ( (SELECT id_path FROM sys_dept WHERE dept_id = {}) +'%')", deptAlias,
                            user.getDeptId(), user.getDeptId()));
                    break;
                }
                case DATASOURCE_TYPE_MYSQL: {
                    sqlString.append(StringUtils.format(
                            " OR {}.id_path like concat( (SELECT id_path FROM sys_dept WHERE dept_id = {}), '%')",
                            user.getDeptId(), user.getDeptId()));
                    break;
                }
                case DATASOURCE_TYPE_ORACLE: {
                    sqlString.append(StringUtils.format(
                            " OR {}.id_path like ( (SELECT id_path FROM sys_dept WHERE dept_id = {}) ||'%')",
                            user.getDeptId(), user.getDeptId()));
                    break;
                }
                }
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (StringUtils.isNotBlank(userAlias)) {
                    sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
                } else {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(" OR 1=0 ");
                }
            }
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[0];
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }

    private static int getDefaultDatasourceType() {
        if (defaultDatasourceType == -1) {
            defaultDatasourceType = DATASOURCE_TYPE_UNKNOWN;

            String dst = DynamicDataSourceContextHolder.getDataSourceType();
            if (dst == null) {
                dst = "default";
            }
            String url = Global.getConfig("spring.datasource.druid." + (dst.toLowerCase()) + ".url");
            if (url != null) {
                if (url.indexOf(":sqlserver:") != -1) {
                    defaultDatasourceType = DATASOURCE_TYPE_SQLSERVER;
                } else if (url.indexOf(":mysql:") != -1) {
                    defaultDatasourceType = DATASOURCE_TYPE_MYSQL;
                } else if (url.indexOf(":oracle:") != -1) {
                    defaultDatasourceType = DATASOURCE_TYPE_ORACLE;
                }
            }
        }
        return defaultDatasourceType;
    }
}
