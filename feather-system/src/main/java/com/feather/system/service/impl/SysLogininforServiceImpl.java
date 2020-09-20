package com.feather.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import com.feather.system.domain.SysLogininfor;
import com.feather.system.mapper.SysLogininforMapper;
import com.feather.system.service.ISysLogininforService;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author feather
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService {

    @Autowired
    private SysLogininforMapper logininforMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor
     *            访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        if (logininfor.getInfoId() == null) {
            logininfor.setInfoId(uidWorker.getNextId());
        }
        logininfor.setLoginTime(DateUtils.getNowDate());
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor
     *            访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        return logininforMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param ids
     *            需要删除的数据
     * @return
     */
    @Override
    public int deleteLogininforByIds(String ids) {
        return logininforMapper.deleteLogininforByIds(Convert.toStrArray(ids));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        logininforMapper.cleanLogininfor();
    }
}
