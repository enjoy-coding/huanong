package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupPump;
import com.feather.aupipes.mapper.AupPumpMapper;
import com.feather.aupipes.service.IAupPumpService;
import com.feather.common.core.domain.Ztree;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AupPumpServiceImpl implements IAupPumpService {

    @Autowired
    private AupPumpMapper aupPumpMapper;

    /**
     * 获取泵房树结构
     *
     * @param id
     * @return
     */
    @Override
    public List<Ztree> getTree(Long id) {
        List<Ztree> ztreeList = new ArrayList<>();
        AupPump aupPump = new AupPump();
        // aupPump.setPLevel(1);
        // aupPump.setCachelevel((long) 1);
        List<AupPump> aupPumpList = this.selectAupPumpList(aupPump);
        for (AupPump pump : aupPumpList) {
            ztreeList.add(new Ztree(pump.getId(), pump.getPid(), pump.getName(), false, BeanUtils.beanToMap(pump)));
        }
        return ztreeList;
    }

    @Override
    public List<AupPump> getBaseTable(Long id) {
        AupPump pump = this.selectAupPumpById(id);
        List<AupPump> aupPumpList = new ArrayList<>();
        aupPumpList.add(new AupPump(1L, "名称", pump.getName()));
        aupPumpList.add(new AupPump(2L, "市政压力", "0.35Mpa"));
        return aupPumpList;
    }

    @Override
    public List<AupPump> getStateTable(Long id) {
        List<AupPump> aupPumpList = new ArrayList<>();
        // if(id ==1) {
        aupPumpList.add(new AupPump("一泵", "停止", "2020-04-20 12:33:40"));
        aupPumpList.add(new AupPump("二泵", "停止", "2020-04-20 12:33:40"));
        aupPumpList.add(new AupPump("三泵", "变频", "2020-04-20 12:33:40"));

        // }
        return aupPumpList;
    }

    /**
     * 仪表盘数据
     *
     * @param id id
     * @return 结果
     */
    @Override
    public Map<String, Object> getGaugeData(Long id) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (id == 0L) {
            map.put("num1", 80);
            map.put("num2", 20);
            map.put("num3", 100);
            map.put("num4", 60);

        } else {
            map.put("num1", 30);
            map.put("num2", 10);
            map.put("num3", 80);
            map.put("num4", 10);

        }
        return map;
    }

    /**
     * 查询泵房
     *
     * @param id
     *            泵房ID
     * @return 泵房
     */
    @Override
    public AupPump selectAupPumpById(Long id) {
        return aupPumpMapper.selectAupPumpById(id);
    }

    /**
     * 查询泵房列表
     *
     * @param aupPump
     *            泵房
     * @return 泵房
     */
    @Override
    public List<AupPump> selectAupPumpList(AupPump aupPump) {
        return aupPumpMapper.selectAupPumpList(aupPump);
    }

    /**
     * 新增泵房
     *
     * @param aupPump
     *            泵房
     * @return 结果
     */
    @Override
    public int insertAupPump(AupPump aupPump) {
        return aupPumpMapper.insertAupPump(aupPump);
    }

    /**
     * 修改泵房
     *
     * @param aupPump
     *            泵房
     * @return 结果
     */
    @Override
    public int updateAupPump(AupPump aupPump) {
        return aupPumpMapper.updateAupPump(aupPump);
    }

    /**
     * 删除泵房对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpByIds(String ids) {
        return aupPumpMapper.deleteAupPumpByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除泵房信息
     *
     * @param id
     *            泵房ID
     * @return 结果
     */
    @Override
    public int deleteAupPumpById(Long id) {
        return aupPumpMapper.deleteAupPumpById(id);
    }
}
