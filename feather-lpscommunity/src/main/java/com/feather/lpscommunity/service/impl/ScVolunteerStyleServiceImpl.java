package com.feather.lpscommunity.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.StringUtils;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.domain.ScVolunteerStyle;
import com.feather.lpscommunity.mapper.ScFileInfoMapper;
import com.feather.lpscommunity.mapper.ScVolunteerStyleMapper;
import com.feather.lpscommunity.service.IScVolunteerStyleService;

/**
 * 志愿者风采Service业务层处理
 * 
 * @author fancy
 * @date 2019-11-21
 */
@Service
public class ScVolunteerStyleServiceImpl implements IScVolunteerStyleService {
    private String code = "style";

    @Autowired
    private ScVolunteerStyleMapper scVolunteerStyleMapper;

    @Autowired
    private UidWorker uidWorker;

    @Autowired
    private ScFileInfoMapper scFileInfoMapper;

    /**
     * 查询志愿者风采
     * 
     * @param styleId 志愿者风采ID
     * @return 志愿者风采
     */
    @Override
    public ScVolunteerStyle selectScVolunteerStyleById(Long styleId) {
        return scVolunteerStyleMapper.selectScVolunteerStyleById(styleId);
    }

    /**
     * 查询志愿者风采列表
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 志愿者风采
     */
    @Override
    public List<ScVolunteerStyle> selectScVolunteerStyleList(ScVolunteerStyle scVolunteerStyle) {
        return scVolunteerStyleMapper.selectScVolunteerStyleList(scVolunteerStyle);
    }

    /**
     * 新增志愿者风采
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 结果
     */
    @Override
    public int insertScVolunteerStyle(ScVolunteerStyle scVolunteerStyle) {
        return scVolunteerStyleMapper.insertScVolunteerStyle(scVolunteerStyle);
    }

    /**
     * 修改志愿者风采
     * 
     * @param scVolunteerStyle 志愿者风采
     * @return 结果
     */
    @Override
    public int updateScVolunteerStyle(ScVolunteerStyle scVolunteerStyle) {
        return scVolunteerStyleMapper.updateScVolunteerStyle(scVolunteerStyle);
    }

    /**
     * 删除志愿者风采对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScVolunteerStyleByIds(String ids) {
        return scVolunteerStyleMapper.deleteScVolunteerStyleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除志愿者风采信息
     * 
     * @param styleId 志愿者风采ID
     * @return 结果
     */
    public int deleteScVolunteerStyleById(Long styleId) {
        return scVolunteerStyleMapper.deleteScVolunteerStyleById(styleId);
    }

    @Override
    public ScVolunteerStyle insertScVolunteerStyle(ScVolunteerStyle scVolunteerStyle, MultipartFile[] MultipartFile) {
        this.insertScVolunteerStyle(scVolunteerStyle);
         // 循环增加图片
         if (StringUtils.isEmpty(MultipartFile)) {
            return scVolunteerStyle;
        }
        //上传图片
        for (MultipartFile multipartFile2 : MultipartFile) {
            ScFileInfo scFileInfo = new ScFileInfo();
            scFileInfo.setFileId(uidWorker.getNextId());
            scFileInfo.setFileTarget(scVolunteerStyle.getStyleId());
            String url = "";
            try {
                url = FileUploadUtils.upload(multipartFile2, null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            scFileInfo.setFilePath(url);
            scFileInfo.setFileName(multipartFile2.getOriginalFilename());
            scFileInfo.setFileType(multipartFile2.getContentType());
            scFileInfo.setFileCode(code);
            scFileInfoMapper.insertScFileInfo(scFileInfo);
        }
        return scVolunteerStyle;
    }

    @Override
    public ScVolunteerStyle selectScVolunteerStyleFileById(Long styleId, String headUrl) {
        ScVolunteerStyle scVolunteerStyle = scVolunteerStyleMapper.selectScVolunteerStyleFileById(styleId);
        for (ScFileInfo f : scVolunteerStyle.getFiles()) {
            f.setFilePath(f.getFilePath()==null?"":headUrl+f.getFilePath());
        }
        return scVolunteerStyle;
    }

    @Override
    public List<Map<String,Object>> screenScVolunteerStyleList(ScVolunteerStyle scVolunteerStyle,String headUrl) {
        List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
        List<ScVolunteerStyle> scVolunteerStyles = this.selectScVolunteerStyleList(scVolunteerStyle);
        for (ScVolunteerStyle scVolunteerStyle2 : scVolunteerStyles) {
            Map<String,Object> map = new LinkedHashMap<String,Object>();
            List<ScFileInfo> fileInfos = scFileInfoMapper.selectScFileInfoList(new ScFileInfo(scVolunteerStyle2.getStyleId(), "style"));
            String [] fileStrings = new String[fileInfos.size()];
            for (int i = 0; i < fileInfos.size(); i++) {
                fileStrings[i] = headUrl + fileInfos.get(i).getFilePath();
            }
            map.put("style_id", scVolunteerStyle2.getStyleId());
            map.put("style_title", scVolunteerStyle2.getStyleTitle());
            map.put("style_content", scVolunteerStyle2.getStyleContent());
            map.put("style_files", fileStrings);
            map.put("create_time", scVolunteerStyle2.getCreateTime());
            map.put("volunteer_id", scVolunteerStyle2.getVolunteerId());
            map.put("volunteer_avatar", scVolunteerStyle2.getVolunteer().getVolunteerAvatar()==null||"".equals(scVolunteerStyle2.getVolunteer().getVolunteerAvatar())?"":headUrl+scVolunteerStyle2.getVolunteer().getVolunteerAvatar());
            map.put("volunteer_score", scVolunteerStyle2.getVolunteer().getVolunteerScore());
            mList.add(map);
        }
        return mList;
    }
}