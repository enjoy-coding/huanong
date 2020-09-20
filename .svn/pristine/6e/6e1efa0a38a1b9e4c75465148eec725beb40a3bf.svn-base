package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupInspectService;
import com.feather.aupipes.mapper.AupInspectServiceMapper;
import com.feather.aupipes.service.IAupInspectServiceService;
import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 巡检记录设备Service业务层处理
 * 
 * @author fancy
 * @date 2020-06-08
 */
@Service
public class AupInspectServiceServiceImpl implements IAupInspectServiceService 
{

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private AupInspectServiceMapper aupInspectServiceMapper;

    /**
     * 查询巡检记录设备
     * 
     * @param id 巡检记录设备ID
     * @return 巡检记录设备
     */
    @Override
    public AupInspectService selectAupInspectServiceById(Long id)
    {
        return aupInspectServiceMapper.selectAupInspectServiceById(id);
    }

    /**
     * 查询巡检记录设备列表
     * 
     * @param aupInspectService 巡检记录设备
     * @return 巡检记录设备
     */
    @Override
    public List<AupInspectService> selectAupInspectServiceList(AupInspectService aupInspectService)
    {
        return aupInspectServiceMapper.selectAupInspectServiceList(aupInspectService);
    }

    /**
     * 新增巡检记录设备
     * 
     * @param aupInspectService 巡检记录设备
     * @return 结果
     */
    @Override
    public int insertAupInspectService(AupInspectService aupInspectService)
    {
        aupInspectService.setId(uidWorker.getNextId());
        aupInspectService.setCreateTime(new Date());
        return aupInspectServiceMapper.insertAupInspectService(aupInspectService);
    }

    /**
     * 修改巡检记录设备
     * 
     * @param aupInspectService 巡检记录设备
     * @return 结果
     */
    @Override
    public int updateAupInspectService(AupInspectService aupInspectService)
    {
        return aupInspectServiceMapper.updateAupInspectService(aupInspectService);
    }

    /**
     * 删除巡检记录设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectServiceByIds(String ids)
    {
        return aupInspectServiceMapper.deleteAupInspectServiceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除巡检记录设备信息
     * 
     * @param id 巡检记录设备ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectServiceById(Long id)
    {
        return aupInspectServiceMapper.deleteAupInspectServiceById(id);
    }

    @Override
    public int updateAppAupInspectService(AupInspectService aupInspectService) {

        String ids = aupInspectService.getIds();
        String serviceNames = aupInspectService.getServiceName();
        String serviceStatuss = aupInspectService.getServiceStatus();
        String serviceSituations = aupInspectService.getServiceSituation();
        String serviceIdss = aupInspectService.getServiceIds();
        //String serviceId = aupInspectService.getServiceIds();
        String[] idArr = ids.split(",");
        String[] serviceNameArr = serviceNames.split(",");
        String[] serviceStatusArr = serviceStatuss.split(",");
        String[] serviceSituationArr = serviceSituations.split(",");
        String[] serviceIdsArr = serviceIdss.split(",");

        for(int i =0;i<idArr.length;i++){
            AupInspectService ais = new AupInspectService();
            ais.setServiceName(serviceNameArr[i]);
            ais.setServiceStatus(serviceStatusArr[i]);
            if((serviceSituationArr[i]).equals("null")){
                ais.setServiceSituation("");
            }else{
                ais.setServiceSituation(serviceSituationArr[i]);
            }
            ais.setServiceId((Long.parseLong((serviceIdsArr[i]))));
            ais.setTaskId(aupInspectService.getTaskId());
            ais.setDetailId(aupInspectService.getDetailId());
            if(!(idArr[i].equals("null"))){
                ais.setId((Long.parseLong((idArr[i]))));
                aupInspectServiceMapper.updateAupInspectService(ais);
            }else{
                ais.setId(uidWorker.getNextId());
                ais.setCreateTime(new Date());
                aupInspectServiceMapper.insertAupInspectService(ais);
            }
        }

        return 1;
    }
}
