package com.feather.framework.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;

@Aspect
@Component
public class ClearPageAspect {

    @Pointcut("@annotation(com.feather.common.annotation.ClearPage)")
    public void clearPointCut() {

    }

    @After("clearPointCut()")
    public void doAfter(JoinPoint joinPoint) {
        PageHelper.clearPage();
    }
}
