package com.feather.lpscommunity.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.domain.ScParkrent;
import com.feather.lpscommunity.domain.ScTaskVolunteer;
import com.feather.lpscommunity.domain.ScVolunteer;
import com.feather.lpscommunity.mapper.ScFileInfoMapper;
import com.feather.lpscommunity.mapper.ScParkrentMapper;
import com.feather.lpscommunity.mapper.ScVolunteerMapper;
import com.feather.lpscommunity.service.IScFileInfoService;
import com.feather.lpscommunity.service.IScParkrentService;
import com.feather.lpscommunity.service.IScTaskVolunteerService;
import com.feather.system.mapper.SysDictDataMapper;

/**
 * 租用信息发布Service业务层处理
 *
 * @author dufan
 * @date 2019-10-15
 */
@Service
@CacheConfig(cacheNames = "sc:parkrent")
public class ScParkrentServiceImpl implements IScParkrentService {

    private String code = "parkrent";

    @Autowired
    private ScParkrentMapper scParkrentMapper;

    @Autowired
    private ScFileInfoMapper scFileInfoMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private ScVolunteerMapper scVolunteerMapper;


    @Autowired
    private IScFileInfoService scFileInfoService;

    @Autowired
    private IScTaskVolunteerService scTaskVolunteerService;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询租用信息发布
     *
     * @param parkrentId 租用信息发布ID
     * @return 租用信息发布
     */
    @Override
    public ScParkrent selectScParkrentById(Long parkrentId) {
        return scParkrentMapper.selectScParkrentById(parkrentId);
    }

    /**
     * 查询租用信息发布列表
     *
     * @param scParkrent 租用信息发布
     * @return 租用信息发布
     */
    @Override
    public List<ScParkrent> selectScParkrentList(ScParkrent scParkrent) {
        return scParkrentMapper.selectScParkrentList(scParkrent);
    }

    /**
     * 新增租用信息发布
     *
     * @param scParkrent 租用信息发布
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int insertScParkrent(ScParkrent scParkrent) {
        scParkrent.setParkrentId(uidWorker.getNextId());
        scParkrent.setParkrentType("1");
        scParkrent.setParkentAuditState("0");
        return scParkrentMapper.insertScParkrent(scParkrent);
    }

    /**
     * 修改租用信息发布
     *
     * @param scParkrent 租用信息发布
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public int updateScParkrent(ScParkrent scParkrent) {
        scParkrent.setParkentAuditState("1");
        //如果通过
        if(scParkrent.getAuditPassState().equals("0")){
            //修改为招募中
            scParkrent.setParkrentType(sysDictDataMapper.selectDictValue("sc_task_state1", "招募中"));
            scParkrent.setAuditState("1");
        }
        return scParkrentMapper.updateScParkrent(scParkrent);
    }

    /**
     * 删除租用信息发布对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @CacheEvict(allEntries = true)
    public int deleteScParkrentByIds(String ids) {
        return scParkrentMapper.deleteScParkrentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除租用信息发布信息
     *
     * @param parkrentId 租用信息发布ID
     * @return 结果
     */
    @CacheEvict(allEntries = true)
    public int deleteScParkrentById(Long parkrentId) {
        return scParkrentMapper.deleteScParkrentById(parkrentId);
    }

    @Override
    @Cacheable(key = "#root.methodName +':'+ #root.args[0]", unless = "#result.size() == 0")
    public List<Map<String, Object>> screenScParkrentList(ScParkrent scParkrent, String domain) {
        List<ScParkrent> scParkrentList = this.selectScParkrentList(scParkrent);
        List<Map<String, Object>> scParkrentListMap = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < scParkrentList.size(); i++) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            //过滤掉已结项
            if("3".equals(scParkrentList.get(i).getParkrentType())){
                    continue;
            }
            String path = scFileInfoService.selectFristScFileInfoByTarget(scParkrentList.get(i).getParkrentId(),code,domain);
            dataMap.put("parkrentId", scParkrentList.get(i).getParkrentId());
            dataMap.put("parkrentTitle", scParkrentList.get(i).getParkrentTitle());
            dataMap.put("parkrentName", scParkrentList.get(i).getParkrentName());
            dataMap.put("parkrentType", sysDictDataMapper.selectDictLabel("sc_task_state1",scParkrentList.get(i).getParkrentType()));
            dataMap.put("parkrentTel", scParkrentList.get(i).getParkrentTel());
            dataMap.put("parkrentAuditType",
                    StringUtils.isEmpty(scParkrentList.get(i).getParkentAuditState()) ? "未审核"
                            : sysDictDataMapper.selectDictLabel("sc_audit_status",
                                    scParkrentList.get(i).getParkentAuditState()));
            dataMap.put("updateTime", scParkrentList.get(i).getUpdateTime());
            dataMap.put("updateBy", "");
            dataMap.put("indexShowPic", path);

            scParkrentListMap.add(dataMap);
        }

        return scParkrentListMap;
    }

    /**
     * 信息跟照片同时增加
     */
    @Override
    @CacheEvict(allEntries = true)
    public ScParkrent insertScParkrentAndPic(ScParkrent scParkrent, MultipartFile[] parkrentFile) {
        this.insertScParkrent(scParkrent);
        // 循环增加图片
        if (StringUtils.isEmpty(parkrentFile)) {
            return scParkrent;
        }
        for (int i = 0; i < parkrentFile.length; i++) {
            MultipartFile multipartFile = parkrentFile[i];
            ScFileInfo scFileInfo = new ScFileInfo();
            scFileInfo.setFileId(uidWorker.getNextId());
            scFileInfo.setFileTarget(scParkrent.getParkrentId());
            String url = "";
            try {
                url = FileUploadUtils.upload(multipartFile, null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            scFileInfo.setFilePath(url);
            scFileInfo.setFileName(multipartFile.getOriginalFilename());
            scFileInfo.setFileType(multipartFile.getContentType());
            scFileInfo.setFileCode(code);
            scFileInfoMapper.insertScFileInfo(scFileInfo);
        }

        return scParkrent;
    }

    /**
     * 获取信息发布详情
     */
    @Override
    @Cacheable(key = "#root.methodName +':'+ #root.args[0]")
    public Map<String, Object> screenScParkrentDetail(Long parkrentId, String domain) {
        ScParkrent scParkrent  = this.selectScParkrentById(parkrentId);
        Map<String, Object> data = new HashMap<String, Object>();
        String[] fileThumbPath = scFileInfoService.selectFileThumbPathByTarget(parkrentId, domain, code);
        data.put("parkrent_id", scParkrent.getParkrentId());
        data.put("parkrent_title", scParkrent.getParkrentTitle() == null?"":scParkrent.getParkrentTitle());
        data.put("parkrent_name", scParkrent.getParkrentName() == null?"":scParkrent.getParkrentName());
        data.put("parkrent_tel", scParkrent.getParkrentTel() == null?"":scParkrent.getParkrentTel() );
        data.put("parkrent_content", scParkrent.getParkrentContent());
        data.put("parkrent_audit_state", StringUtils.isEmpty(scParkrent.getParkentAuditState()) ? "未审核"
                : sysDictDataMapper.selectDictLabel("sc_audit_status", scParkrent.getParkentAuditState()));
        data.put("parkrent_cover", fileThumbPath);
        return data;
    }

    /**
     * 设置任务图片
     */
    @Override
    public ScParkrent selectScParkrentFileById(Long parkrentId, String headUrl) {
        ScParkrent scParkrent = scParkrentMapper.selectScParkrentFileById(parkrentId);
        if (scParkrent.getFiles() != null) {
            for (int i = 0; i < scParkrent.getFiles().size(); i++) {
                scParkrent.getFiles().get(i)
                        .setFilePath(StringUtils.isEmpty(scParkrent.getFiles().get(i).getFilePath()) ? ""
                                : headUrl + scParkrent.getFiles().get(i).getFilePath());
            }
        }
        return scParkrent;
    }

    
    /**
     * 根据任务获取志愿者列表
     */
    @Override
    public List<ScVolunteer> selectVolunteerByParkrent(Long parkrentId) {
        ScTaskVolunteer scTaskVolunteer  = new ScTaskVolunteer();
        scTaskVolunteer.setTaskId(parkrentId);
        scTaskVolunteer.setAuditState("1"); //已审核
        scTaskVolunteer.setAuditPassState("0");//已通过
        List<ScTaskVolunteer> scTaskVolunteers = scTaskVolunteerService.selectScTaskVolunteerList(scTaskVolunteer);
        List<ScVolunteer> scVolunteers = new ArrayList<ScVolunteer>(); 
        for (int i = 0; i < scTaskVolunteers.size(); i++) {
            ScTaskVolunteer scTaskVolunteer1 = scTaskVolunteers.get(i);
            if(scTaskVolunteer1.getTaskId().equals(parkrentId)){
                scVolunteers.add(scTaskVolunteer1.getVolunteer());
            }
        }
        return scVolunteers;
    }

    /**
     * 结束任务
     * @param scParkrent
     *            租用信息发布
     * @return
     */
    @Override
    @Transactional
    public int overScParkrent(ScParkrent scParkrent) {
        //修改类型未已结项
        scParkrent.setParkrentType(sysDictDataMapper.selectDictValue("sc_task_state1", "已结项"));

        //根据任务获取志愿者
        List<ScVolunteer> scVolunteers = this.selectVolunteerByParkrent(scParkrent.getParkrentId());
        double score = scParkrent.getParkrentScore();
        int volunteerNum = scVolunteers.size();
        for (ScVolunteer scVolunteer : scVolunteers) {
            double volunteerScore = Double.parseDouble(scVolunteer.getVolunteerScore());
            double awardScore = compluteScore(score,volunteerNum);
            scVolunteer.setVolunteerScore(Double.toString(volunteerScore+awardScore));
            //修改积分
            scVolunteerMapper.updateScVolunteer(scVolunteer);
        }
        //修改任务
        return scParkrentMapper.updateScParkrent(scParkrent);
    }

     /**
     * 平分积分
     */
    public double compluteScore(double Score,int num){
        double volunteerScore = Score/num;
        return volunteerScore;
    }

    @Override
    public int updateScParkrentDelFlagById(String ids) {
        return scParkrentMapper.updateScParkrentDelFlagById(Convert.toStrArray(ids));
    }
}
