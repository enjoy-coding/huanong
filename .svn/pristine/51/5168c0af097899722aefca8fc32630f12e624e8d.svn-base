package com.feather.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.system.domain.SysConfig;
import com.feather.system.mapper.SysConfigMapper;
import com.feather.system.service.ISysConfigService;

/**
 * 参数配置 服务层实现
 * 
 * @author feather
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询参数配置信息
     * 
     * @param configId
     *            参数配置ID
     * @return 参数配置信息
     */
    @Override
    public SysConfig selectConfigById(Long configId) {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey
     *            参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        return retConfig != null ? retConfig.getConfigValue() : "";
    }

    /**
     * 查询参数配置列表
     * 
     * @param config
     *            参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     * 
     * @param config
     *            参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config) {
        if (config.getConfigId() == null) {
            config.setConfigId(uidWorker.getNextId());
        }
        return configMapper.insertConfig(config);
    }

    /**
     * 修改参数配置
     * 
     * @param config
     *            参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        return configMapper.updateConfig(config);
    }

    /**
     * 批量删除参数配置对象
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(String ids) {
        return configMapper.deleteConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 校验参数键名是否唯一
     * 
     * @param config
     *            参数配置信息
     * @return 结果
     */
    @Override
    public boolean checkConfigKeyUnique(SysConfig config) {
        Long configId = config.getConfigId() == null ? -1L : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (info != null && info.getConfigId().longValue() != configId.longValue()) {
            return false;
        }
        return true;
    }
}
