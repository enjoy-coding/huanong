package com.feather.lpscommunity.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.feather.common.config.UidWorker;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.common.utils.file.ThumbnailFactory;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.mapper.ScFileInfoMapper;
import com.feather.lpscommunity.service.IScFileInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.core.text.Convert;

import org.springframework.web.multipart.MultipartFile;

/**
 * 智慧社区实体多图Service业务层处理
 *
 * @author dufan
 * @date 2019-10-17
 */
@Service
public class ScFileInfoServiceImpl implements IScFileInfoService {

    @Autowired
    private ScFileInfoMapper scFileInfoMapper;

    @Autowired
    private UidWorker uidWorker;

    /**
     * 查询智慧社区实体多图
     *
     * @param fileId
     *            智慧社区实体多图ID
     * @return 智慧社区实体多图
     */
    @Override
    public ScFileInfo selectScFileInfoById(Long fileId) {
        return scFileInfoMapper.selectScFileInfoById(fileId);
    }

    /**
     * 查询智慧社区实体多图列表
     *
     * @param scFileInfo
     *            智慧社区实体多图
     * @return 智慧社区实体多图
     */
    @Override
    public List<ScFileInfo> selectScFileInfoList(ScFileInfo scFileInfo) {
        return scFileInfoMapper.selectScFileInfoList(scFileInfo);
    }

    /**
     * 根据源查询多个图片路径
     *
     * @param targetId
     *            源
     * @param headUrl
     *            请求网页地址
     * @return 智慧社区实体多图集合
     */
    public String[] selectFilePathByTarget(Long targetId, String headUrl) {
        ScFileInfo scFileInfo = new ScFileInfo();
        scFileInfo.setFileTarget(targetId);
        List<ScFileInfo> scFileInfoList = selectScFileInfoList(scFileInfo);
        String[] filePaths = new String[scFileInfoList.size()];
        for (int i = 0; i < scFileInfoList.size(); i++) {
            filePaths[i] = headUrl + scFileInfoList.get(i).getFilePath();
        }
        return filePaths;
    }

     /**
     * 查询智慧社区实体单图
     *
     * @param target 智慧社区实体单图
     * @return 智慧社区实体多图集合
     */
    public ScFileInfo selectScFileInfoByTarget(Long target){
        return scFileInfoMapper.selectScFileInfoByTarget(target);
    }

    /**
     * 根据源查询多个缩略图片路径
     *
     * @param targetId
     *            源
     * @param headUrl
     *            请求网页地址
     * @return 智慧社区实体多图集合
     */
    public String[] selectFileThumbPathByTarget(Long targetId, String headUrl, String code) {
        ScFileInfo scFileInfo = new ScFileInfo();
        scFileInfo.setFileTarget(targetId);
        scFileInfo.setFileCode(code);
        List<ScFileInfo> scFileInfoList = selectScFileInfoList(scFileInfo);
        String[] fileThumbPaths = new String[scFileInfoList.size()];
        for (int i = 0; i < scFileInfoList.size(); i++) {
            // String filePath = scFileInfoList.get(i).getFilePath();
            // String thumbPath =
            // filePath.substring(0,filePath.lastIndexOf("."))+"_t";
            // String sufix =
            // filePath.substring(filePath.lastIndexOf("."),filePath.length());
            // fileThumbPaths[i] = headUrl+thumbPath+sufix;
            fileThumbPaths[i] = headUrl + scFileInfoList.get(i).getFilePath();
        }
        return fileThumbPaths;
    }

    @Override
    public int insertScFileInfo(ScFileInfo scFileInfo){
        return scFileInfoMapper.insertScFileInfo(scFileInfo);
    }
    /**
     * 新增智慧社区实体单图
     *
     * @param multipartFile 智慧社区实体多图
     * @param code 编码
     * @param target 目标
     * @return 结果
     */
    @Override
    public int insertScFileInfo(MultipartFile multipartFile,String code,Long target) {
        ScFileInfo scFileInfo = new ScFileInfo();
        try {
            String url = FileUploadUtils.upload(multipartFile, null, true);
            scFileInfo.setFileId(uidWorker.getNextId());
            scFileInfo.setFileCode(code);
            scFileInfo.setFileTarget(target);
            scFileInfo.setFileName(multipartFile.getOriginalFilename());
            scFileInfo.setFileType(multipartFile.getContentType());
            scFileInfo.setFilePath(url);

        }catch (Exception e){
            e.printStackTrace();
        }
        return scFileInfoMapper.insertScFileInfo(scFileInfo);
    }


    /**
     * 新增智慧社区实体单图
     *
     * @param multipartFiles 智慧社区实体多图
     * @param code 编码
     * @param target 目标
     * @return 结果
     */
    @Override
    public int insertScFileInfo(MultipartFile [] multipartFiles,String code,Long target) {
        List<ScFileInfo> scFileInfos = new ArrayList<ScFileInfo>();
        try {
            for (int i = 0; i < multipartFiles.length; i++) {
                MultipartFile multipartFile = multipartFiles[i];
                ScFileInfo scFileInfo = new ScFileInfo();
                String url = FileUploadUtils.upload(multipartFile, null, true);
                scFileInfo.setFileId(uidWorker.getNextId());
                scFileInfo.setFileCode(code);
                scFileInfo.setFileTarget(target);
                scFileInfo.setFileName(multipartFile.getOriginalFilename());
                scFileInfo.setFileType(multipartFile.getContentType());
                scFileInfo.setFilePath(url);
                scFileInfos.add(scFileInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return this.insertScFileInfos(scFileInfos);
    }

    /**
     * 新增智慧社区实体多图
     *
     * @param scFileInfos
     *            智慧社区实体多图
     * @return 结果
     */
    @Override
    public int insertScFileInfos(List<ScFileInfo> scFileInfos) {
        if (scFileInfos.size() > 0) {
            return scFileInfoMapper.insertScFileInfos(scFileInfos);
        } else {
            return 1;
        }
    }

    /**
     * 修改智慧社区实体多图
     *
     * @param scFileInfo
     *            智慧社区实体多图
     * @return 结果
     */
    @Override
    public int updateScFileInfo(ScFileInfo scFileInfo) {
        return scFileInfoMapper.updateScFileInfo(scFileInfo);
    }

    /**
     * 删除智慧社区实体多图对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScFileInfoByIds(String ids) {
        return scFileInfoMapper.deleteScFileInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除智慧社区实体多图信息
     *
     * @param fileId
     *            智慧社区实体多图ID
     * @return 结果
     */
    public int deleteScFileInfoById(Long fileId) {
        return scFileInfoMapper.deleteScFileInfoById(fileId);
    }

    /**
     * 批量删除多个实体对应的多图
     * 
     * @param targetId
     * @return
     */
    public int deleteScFileInfoByTarget(String[] targetId) {
        return scFileInfoMapper.deleteScFileInfoByTarget(targetId);
    }

    @Override
    public String selectFristScFileInfoByTarget(Long target,String fileCode,String headUrl) {
        List<ScFileInfo> scFileInfoList = selectScFileInfoList(new ScFileInfo(target, fileCode));
        String filePath = "";
        if(scFileInfoList.size()>0){
            filePath = headUrl+scFileInfoList.get(0).getFilePath();
        }
        return filePath;
    }

    /**
     * 获取多图片轮番预览格式
     * @param scFileInfoList 图片集合
     * @param title 标题
     * @param target 目标id
     * @param headUrl
     * @return
     */
    @Override
    public Map<String,Object> getImageViewData(List<ScFileInfo> scFileInfoList, String title, Long target,String headUrl)
    {
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        List<Map<String,Object>> picJsonList = new ArrayList<Map<String,Object>>();
        map.put("title", title);
        map.put("id", target);
        map.put("start", "0");
        for (int i = 0; i < scFileInfoList.size(); i++) {
            Map<String,Object> pmap = new LinkedHashMap<String,Object>();
            pmap.put("alt", scFileInfoList.get(i).getFileName());
            pmap.put("pid", scFileInfoList.get(i).getFileId());
            pmap.put("src", StringUtils.isEmpty(scFileInfoList.get(i).getFilePath()) ? ""
                    : ThumbnailFactory.getUrlSeries(scFileInfoList.get(i).getFilePath()).getOriginal());
            pmap.put("thumb", StringUtils.isEmpty(scFileInfoList.get(i).getFilePath()) ? ""
                    : headUrl + scFileInfoList.get(i).getFilePath());
            picJsonList.add(pmap);
        }
        map.put("data", picJsonList);
        return map;
    }
}
