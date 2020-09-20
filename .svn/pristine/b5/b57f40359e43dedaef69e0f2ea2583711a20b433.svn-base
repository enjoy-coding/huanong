package com.feather.aupipes.service.impl;

import com.feather.aupipes.domain.AupFileInfo;
import com.feather.aupipes.domain.AupInspectDetail;
import com.feather.aupipes.domain.AupInspectService;
import com.feather.aupipes.mapper.AupFileInfoMapper;
import com.feather.aupipes.mapper.AupInspectDetailMapper;
import com.feather.aupipes.mapper.AupInspectServiceMapper;
import com.feather.aupipes.service.IAupInspectDetailService;
import com.feather.aupipes.utils.vo.AupInspectDetailVo;
import com.feather.aupipes.utils.vo.AupInspectServiceVo;
import com.feather.common.config.UidWorker;
import com.feather.common.core.domain.AjaxResult;
import com.feather.common.core.text.Convert;
import com.feather.common.json.JSONObject;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.file.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 巡检设备Service业务层处理
 *
 * @author fancy
 * @date 2020-06-04
 */
@Service
public class AupInspectDetailServiceImpl implements IAupInspectDetailService {

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private AupInspectDetailMapper aupInspectDetailMapper;

    @Autowired
    private AupInspectServiceMapper aupInspectServiceMapper;

    @Autowired
    private AupFileInfoMapper aupFileInfoMapper;

    /**
     * 查询巡检设备
     *
     * @param id
     *            巡检设备ID
     * @return 巡检设备
     */
    @Override
    public AupInspectDetail selectAupInspectDetailById(Long id) {
        return aupInspectDetailMapper.selectAupInspectDetailById(id);
    }

    /**
     * 查询巡检记录及其包含的设备
     *
     * @param id 巡检设备ID
     * @return 巡检设备
     */
    @Override
    public AupInspectDetailVo selectAupInspectDetailRecurById(Long id) {
        AupInspectDetail aupInspectDetail = aupInspectDetailMapper.selectAupInspectDetailById(id);

        AupInspectService aupInspectService = new AupInspectService();
        aupInspectService.setDetailId(id);

        List<AupInspectService> aupInspectServices = aupInspectServiceMapper.selectAupInspectServiceList(aupInspectService);

        List<AupInspectServiceVo> aupInspectServiceVoList = new ArrayList<>();
        for(AupInspectService temp : aupInspectServices){
            AupInspectServiceVo aupInspectServiceVo = new AupInspectServiceVo(temp.getServiceName(), temp.getServiceStatus(), temp.getServiceSituation());
            aupInspectServiceVoList.add(aupInspectServiceVo);
        }

       return new AupInspectDetailVo(aupInspectDetail.getServiceAddress(), aupInspectDetail.getServiceType(),
                DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, aupInspectDetail.getCreateTime()), aupInspectDetail.getServiceStatusText(), aupInspectServiceVoList);
    }

    /**
     * 查询巡检设备列表
     *
     * @param aupInspectDetail
     *            巡检设备
     * @return 巡检设备
     */
    @Override
    public List<AupInspectDetail> selectAupInspectDetailList(AupInspectDetail aupInspectDetail) {
        return aupInspectDetailMapper.selectAupInspectDetailList(aupInspectDetail);
    }

    /**
     * 新增巡检设备
     *
     * @param aupInspectDetail
     *            巡检设备
     * @return 结果
     */
    @Override
    public int insertAupInspectDetail(AupInspectDetail aupInspectDetail) {
        aupInspectDetail.setId(uidWorker.getNextId());
        aupInspectDetail.setCreateTime(new Date());
        return aupInspectDetailMapper.insertAupInspectDetail(aupInspectDetail);
    }

    /**
     * 修改巡检设备
     *
     * @param aupInspectDetail
     *            巡检设备
     * @return 结果
     */
    @Override
    public int updateAupInspectDetail(AupInspectDetail aupInspectDetail) {
        aupInspectDetail.setUpdateTime(new Date());
        return aupInspectDetailMapper.updateAupInspectDetail(aupInspectDetail);
    }

    /**
     * 删除巡检设备对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectDetailByIds(String ids) {
        return aupInspectDetailMapper.deleteAupInspectDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除巡检设备信息
     *
     * @param id
     *            巡检设备ID
     * @return 结果
     */
    @Override
    public int deleteAupInspectDetailById(Long id) {
        return aupInspectDetailMapper.deleteAupInspectDetailById(id);
    }

    @Override
    public AjaxResult insertAupInspectDetailAndSb(String xjjl, String xjsb) {

        // 巡检记录
        long detailId = uidWorker.getNextId();
        JSONObject xjjlJson = JSONObject.parse(xjjl);
        String inspectWorker = (String) xjjlJson.get("inspectWorker");
        String inspectWorkerId = (String) xjjlJson.get("inspectWorkerId");
        String serviceTypeId = (String) xjjlJson.get("serviceTypeId");
        String serviceName = (String) xjjlJson.get("serviceName");
        String taskName = (String) xjjlJson.get("taskName");
        String serviceAddress = (String) xjjlJson.get("serviceAddress");
        String serviceStatus = (String) xjjlJson.get("serviceStatus");
        String description = (String) xjjlJson.get("description");
        String taskId = (String) xjjlJson.get("taskId");
        AupInspectDetail aid = new AupInspectDetail();
        aid.setId(detailId);
        aid.setInspectWorker(inspectWorker);
        aid.setInspectWorkerId(inspectWorkerId);
        aid.setServiceTypeId(serviceTypeId);
        aid.setServiceName(serviceName);
        aid.setTaskName(taskName);
        aid.setServiceAddress(serviceAddress);
        aid.setServiceStatus(serviceStatus);
        aid.setDescription(description);
        aid.setTaskId(taskId);
        aid.setCreateTime(new Date());
        aupInspectDetailMapper.insertAupInspectDetail(aid);

        // 存入巡检设备
        JSONObject xjsbJson = JSONObject.parse(xjsb);
        String taskIdSb = (String) xjsbJson.get("taskId");
        long taskIdSbL = Long.parseLong(taskIdSb);
        String ids = (String) xjsbJson.get("ids");
        String serviceNames = (String) xjsbJson.get("serviceName");
        String serviceStatuss = (String) xjsbJson.get("serviceStatus");
        String serviceSituations = (String) xjsbJson.get("serviceSituation");
        String serviceIdss = (String) xjsbJson.get("serviceIds");
        String[] idArr = ids.split(",");
        String[] serviceNameArr = serviceNames.split(",");
        String[] serviceStatusArr = serviceStatuss.split(",");
        String[] serviceSituationArr = serviceSituations.split(",");
        String[] serviceIdsArr = serviceIdss.split(",");
        for (int i = 0; i < idArr.length; i++) {
            AupInspectService ais1 = new AupInspectService();
            ais1.setServiceName(serviceNameArr[i]);
            ais1.setServiceStatus(serviceStatusArr[i]);
            if ((serviceSituationArr[i]).equals("null")) {
                ais1.setServiceSituation("");
            } else {
                ais1.setServiceSituation(serviceSituationArr[i]);
            }
            ais1.setServiceId((Long.parseLong((serviceIdsArr[i]))));
            ais1.setTaskId(taskIdSbL);
            ais1.setDetailId(detailId);
            if (!(idArr[i].equals("null"))) {
                ais1.setId((Long.parseLong((idArr[i]))));
                aupInspectServiceMapper.updateAupInspectService(ais1);
            } else {
                ais1.setId(uidWorker.getNextId());
                ais1.setCreateTime(new Date());
                aupInspectServiceMapper.insertAupInspectService(ais1);
            }
        }

        return AjaxResult.success(detailId);
    }

    @Override
    public AjaxResult insertAupInspectDetailAndSbAndFile(String xjjl, String xjsb, MultipartFile[] multipartFiles) {

        // 巡检记录
        long detailId = uidWorker.getNextId();
        JSONObject xjjlJson = JSONObject.parse(xjjl);
        String inspectWorker = (String) xjjlJson.get("inspectWorker");
        String inspectWorkerId = (String) xjjlJson.get("inspectWorkerId");
        String serviceTypeId = (String) xjjlJson.get("serviceTypeId");
        String serviceName = (String) xjjlJson.get("serviceName");
        String taskName = (String) xjjlJson.get("taskName");
        String serviceAddress = (String) xjjlJson.get("serviceAddress");
        String serviceStatus = (String) xjjlJson.get("serviceStatus");
        String description = (String) xjjlJson.get("description");
        String taskId = (String) xjjlJson.get("taskId");
        AupInspectDetail aid = new AupInspectDetail();
        aid.setId(detailId);
        aid.setInspectWorker(inspectWorker);
        aid.setInspectWorkerId(inspectWorkerId);
        aid.setServiceTypeId(serviceTypeId);
        aid.setServiceName(serviceName);
        aid.setTaskName(taskName);
        aid.setServiceAddress(serviceAddress);
        aid.setServiceStatus(serviceStatus);
        aid.setDescription(description);
        aid.setTaskId(taskId);
        aid.setCreateTime(new Date());
        aupInspectDetailMapper.insertAupInspectDetail(aid);

        // 存入巡检设备
        JSONObject xjsbJson = JSONObject.parse(xjsb);
        String taskIdSb = (String) xjsbJson.get("taskId");
        long taskIdSbL = Long.parseLong(taskIdSb);
        String ids = (String) xjsbJson.get("ids");
        String serviceNames = (String) xjsbJson.get("serviceName");
        String serviceStatuss = (String) xjsbJson.get("serviceStatus");
        String serviceSituations = (String) xjsbJson.get("serviceSituation");
        String serviceIdss = (String) xjsbJson.get("serviceIds");
        String[] idArr = ids.split(",");
        String[] serviceNameArr = serviceNames.split(",");
        String[] serviceStatusArr = serviceStatuss.split(",");
        String[] serviceSituationArr = serviceSituations.split(",");
        String[] serviceIdsArr = serviceIdss.split(",");
        for (int i = 0; i < idArr.length; i++) {
            AupInspectService ais1 = new AupInspectService();
            ais1.setServiceName(serviceNameArr[i]);
            ais1.setServiceStatus(serviceStatusArr[i]);
            if ((serviceSituationArr[i]).equals("null")) {
                ais1.setServiceSituation("");
            } else {
                ais1.setServiceSituation(serviceSituationArr[i]);
            }
            ais1.setServiceId((Long.parseLong((serviceIdsArr[i]))));
            ais1.setTaskId(taskIdSbL);
            ais1.setDetailId(detailId);
            if (!(idArr[i].equals("null"))) {
                ais1.setId((Long.parseLong((idArr[i]))));
                aupInspectServiceMapper.updateAupInspectService(ais1);
            } else {
                ais1.setId(uidWorker.getNextId());
                ais1.setCreateTime(new Date());
                aupInspectServiceMapper.insertAupInspectService(ais1);
            }
        }
        // 存入图片
        List<AupFileInfo> aupFileInfos = new ArrayList<>();
        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];
            AupFileInfo aupFileInfo = new AupFileInfo();
            String url = null;
            try {
                url = FileUploadUtils.upload(multipartFile, null, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            aupFileInfo.setFileId(uidWorker.getNextId());
            aupFileInfo.setFileCode((detailId + ""));
            aupFileInfo.setFileTarget(new Long(888));
            aupFileInfo.setFileName(multipartFile.getOriginalFilename());
            aupFileInfo.setFileType(multipartFile.getContentType());
            aupFileInfo.setFilePath(url);
            aupFileInfo.setCreateTime(DateUtils.getNowDate());
            aupFileInfos.add(aupFileInfo);
        }
        aupFileInfoMapper.insertAupFileInfos(aupFileInfos);
        return AjaxResult.success(detailId);
    }
}
