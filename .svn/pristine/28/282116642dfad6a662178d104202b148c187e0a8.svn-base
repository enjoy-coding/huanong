package com.feather.aupipes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.aupipes.domain.AupLeakCount;
import com.feather.aupipes.mapper.AupLeakCountMapper;
import com.feather.aupipes.service.IAupLeakCountService;
import com.feather.common.json.JSONObject;

/**
 * 探漏运行统计次数Service业务层处理
 *
 * @author fancy
 * @date 2020-01-13
 */
@Service
public class AupLeakCountServiceImpl implements IAupLeakCountService {
    @Autowired
    private AupLeakCountMapper aupLeakCountMapper;

    /**
     * 新增 探漏运行统计次数
     *
     * @param aupLeakCount
     *            探漏运行统计次数
     * @return 结果
     */
    @Override
    public int insertAupLeakCount(AupLeakCount aupLeakCount) {
        return aupLeakCountMapper.insertAupLeakCount(aupLeakCount);
    }

    @Override
    public int deleteAupLeakCount() {
        return aupLeakCountMapper.deleteAupLeakCount();
    }

    /**
     * 根据接口插入探漏数据
     *
     * @param result 参数
     * @return 结果
     */
    @Override
    public int insertLeakStatus(String result) {
        // {"Normal":99,"Leakage":1,"Lose":0,"Lnterfere":0,"Doubtleak":0}
        JSONObject obj = JSONObject.parse(result);
        if (obj != null) {
            this.deleteAupLeakCount();// 删除以前的
            AupLeakCount aupLeakCount = new AupLeakCount();
            aupLeakCount.setDoubtleak(obj.getInteger("Doubtleak"));
            aupLeakCount.setLeakage(obj.getInteger("Leakage"));
            aupLeakCount.setNormal(obj.getInteger("Normal"));
            aupLeakCount.setLose(obj.getInteger("Lose"));
            return this.insertAupLeakCount(aupLeakCount);
        }
        return 0;
    }

}