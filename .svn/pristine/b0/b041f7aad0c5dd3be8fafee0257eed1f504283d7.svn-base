package com.feather.lpscommunity.service;

import org.springframework.web.multipart.MultipartFile;

import com.feather.lpscommunity.domain.ScFileInfo;

import java.util.List;
import java.util.Map;

/**
 * 智慧社区实体多图Service接口
 *
 * @author dufan
 * @date 2019-10-17
 */
public interface IScFileInfoService {
    /**
     * 查询智慧社区实体多图
     *
     * @param fileId 智慧社区实体多图ID
     * @return 智慧社区实体多图
     */
    public ScFileInfo selectScFileInfoById(Long fileId);

     /**
     * 查询智慧社区实体单图
     *
     * @param target 智慧社区实体多图
     * @return 智慧社区实体多图集合
     */
    public ScFileInfo selectScFileInfoByTarget(Long target);

    /**
     * 获取第一张图
     */
    public String selectFristScFileInfoByTarget(Long target,String fileCode,String headUrl);
    
    /**
     * 查询智慧社区实体多图列表
     *
     * @param scFileInfo 智慧社区实体多图
     * @return 智慧社区实体多图集合
     */
    public List<ScFileInfo> selectScFileInfoList(ScFileInfo scFileInfo);

    /**
     * 新增智慧社区实体多图
     *
     * @param scFileInfo 智慧社区实体多图
     * @return 结果
     */
    public int insertScFileInfo(ScFileInfo scFileInfo);

    /**
     * 根据源查询多个图片路径
     *
     * @param targetId 源
     * @param headUrl 请求网页地址
     * @return 智慧社区实体多图集合
     */
    public String [] selectFilePathByTarget(Long targetId,String headUrl);

    /**
     * 根据源查询多个缩略图图片路径
     *
     * @param targetId 源
     * @param headUrl 请求网页地址
     * @return 智慧社区实体多图集合
     */
    public String [] selectFileThumbPathByTarget(Long targetId,String headUrl,String code);

    /**
     * 批量新增智慧社区实体多图
     *
     * @param scFileInfos 智慧社区实体多图
     * @return 结果
     */
    public int insertScFileInfos(List<ScFileInfo> scFileInfos);

    /**
     * 添加指定单图
     * @param multipartFile
     * @param code
     * @param target
     * @return
     */
    public int insertScFileInfo(MultipartFile multipartFile, String code, Long target);

    /**
     * 添加指定多图
     * @param multipartFile
     * @param code
     * @param target
     * @return
     */
    public int insertScFileInfo(MultipartFile [] multipartFile, String code, Long target);

    /**
     * 修改智慧社区实体多图
     *
     * @param scFileInfo 智慧社区实体多图
     * @return 结果
     */
    public int updateScFileInfo(ScFileInfo scFileInfo);

    /**
     * 批量删除智慧社区实体多图
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScFileInfoByIds(String ids);

    /**
     * 删除智慧社区实体多图信息
     *
     * @param fileId 智慧社区实体多图ID
     * @return 结果
     */
    public int deleteScFileInfoById(Long fileId);


    /**
     * 批量删除多个实体对应的多图
     * @param targetId
     * @return
     */
    public int deleteScFileInfoByTarget(String [] targetId);

    /**
     * 图片预览
     * @param scFileInfoList 图片集合
     * @param title 标题
     * @param target 目标id
     * @return
     */
    public Map<String,Object> getImageViewData(List<ScFileInfo> scFileInfoList, String title, Long target, String headUrl);
}
