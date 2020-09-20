package com.feather.lpscommunity.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.constant.Constants;
import com.feather.common.core.domain.UrlSeries;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.DateUtils;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.common.utils.file.ThumbnailFactory;
import com.feather.lpscommunity.domain.ScFileInfo;
import com.feather.lpscommunity.domain.ScPartyBuild;
import com.feather.lpscommunity.mapper.ScFileInfoMapper;
import com.feather.lpscommunity.mapper.ScPartyBuildMapper;
import com.feather.lpscommunity.service.IScPartyBuildService;

@Service
public class ScPartyBuildServiceImpl implements IScPartyBuildService {

    @Autowired
    private ScPartyBuildMapper scPartyBuildMapper;

    @Autowired
    private ScFileInfoMapper scFileInfoMapper;

    private String templateContent;

    @Override
    public ScPartyBuild selectScPartyBuildById(Long partyBuildId) {
        return scPartyBuildMapper.selectScPartyBuildById(partyBuildId);
    }

    @Override
    public List<ScPartyBuild> selectScPartyBuildByList(ScPartyBuild scPartyBuild) {
        return scPartyBuildMapper.selectScPartyBuildByList(scPartyBuild);
    }

    @Override
    public int insertScPartyBuild(ScPartyBuild scPartyBuild) {
        if (!scPartyBuild.getPartyBuildType().equals("1")) {
            saveFile(scPartyBuild);
            scPartyBuild.setVideoUpdateType("no_video");
        }
        scPartyBuild.setPartyBuildTime(setPartyBuileTime(scPartyBuild));
        return scPartyBuildMapper.insertScPartyBuild(scPartyBuild);
    }

    @Override
    public int updateScPartyBuild(ScPartyBuild scPartyBuild) {
        if (!scPartyBuild.getPartyBuildType().equals("1")) {
            saveFile(scPartyBuild);
            scPartyBuild.setVideoUpdateType("no_video");
        }
        scPartyBuild.setPartyBuildTime(setPartyBuileTime(scPartyBuild));
        return scPartyBuildMapper.updateScPartyBuild(scPartyBuild);
    }

    @Override
    public int deleteScPartyBuildById(Long partyBuildId) {
        return scPartyBuildMapper.deleteScPartyBuildById(partyBuildId);
    }

    @Override
    public int deleteScPartyBuildByIds(String partyBuildIds) {
        return scPartyBuildMapper.deleteScPartyBuildByIds(Convert.toStrArray(partyBuildIds));
    }

    /**
     * 生成html文件
     * 
     * @param scPartyBuild
     */
    public void saveFile(ScPartyBuild scPartyBuild) {
        String title = scPartyBuild.getPartyBuildTitle();
        String author = scPartyBuild.getPartyBuildSource();
        String time = setPartyBuileTime(scPartyBuild);
        String content = scPartyBuild.getPartyBuildContent();
        String url = null;
        try {
            String html = getHtml(title, author, time, content);
            url = FileUploadUtils.upload(html, "html", scPartyBuild.getPartyBuildFile());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        // 将路径存字段
        scPartyBuild.setPartyBuildFile(url);
    }

    public String getHtml(String title, String author, String time, String content) throws IOException {
        String html = getTemplateContent();
        // System.out.print(content);
        // 把模板页面上的 ###content### 替换成 conetent 里的内容
        html = StringUtils.replace(html, "###title###", title);
        html = StringUtils.replace(html, "###author###", author);
        html = StringUtils.replace(html, "###time###", time);
        html = StringUtils.replace(html, "###content###", content);
        return html;
    }

    /**
     * 读取模板
     */
    public String getTemplateContent() throws IOException {
        if (StringUtils.isEmpty(templateContent)) {
            try (InputStream is = getClass().getClassLoader()
                    .getResourceAsStream("templates/lpscommunity/partybuild/template.html")) {
                int lenght = is.available();
                byte bytes[] = new byte[lenght];
                is.read(bytes);
                templateContent = new String(bytes, Constants.UTF8);
            }
        }
        return templateContent;
    }

    /**
     * 筛选查询社区商家信息列表
     *
     * @param scPartyBuild
     *            党建信息
     * @param domain
     *            请求路径
     * @return 社区商家信息集合
     */
    @Override
    public List<Map<String, Object>> screenScPartyBuildByList(ScPartyBuild scPartyBuild, String domain) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        List<ScPartyBuild> list = this.selectScPartyBuildByList(scPartyBuild);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<String, Object>();
                data.put("partyBuild_id", list.get(i).getPartyBuildId());
                data.put("partyBuild_title", list.get(i).getPartyBuildTitle());
                ScFileInfo scFileInfo = scFileInfoMapper.selectScFileInfoByTarget(list.get(i).getPartyBuildId());
                // 封面图
                if (scFileInfo != null) {
                    data.put("partyBuild_cover", domain + scFileInfo.getFilePath());
                }
                // 如果是视频,将视频地址换为内容地址
                if (scFileInfo != null && list.get(i).getPartyBuildType().equals("1")) {
                    if ("upload_local".equals(list.get(i).getVideoUpdateType())) {
                        // 根据缩略图获取视频
                        UrlSeries urlSeries = ThumbnailFactory.getUrlSeries(scFileInfo.getFilePath());
                        data.put("partyBuild_file",
                                urlSeries.getOriginal() != null ? domain + urlSeries.getOriginal() : "");
                    } else {
                        data.put("partyBuild_file", list.get(i).getPartyBuildFile());
                    }
                } else {
                    data.put("partyBuild_file", domain + list.get(i).getPartyBuildFile());
                }
                data.put("partyBuild_source", list.get(i).getPartyBuildSource());
                data.put("partyBuild_time", setPartyBuileTime(list.get(i)));
                data.put("videoUpload_Type", list.get(i).getVideoUpdateType());
                dataList.add(data);
            }
        }
        return dataList;
    }

    /**
     * 设置发布时间
     */
    public String setPartyBuileTime(ScPartyBuild scPartyBuild) {
        return scPartyBuild.getPartyBuildTime() == null ? scPartyBuild.getPartyBuildTime()
                : DateUtils.parseDateToStr("yyyy-mm-dd", new Date());
    }

    @Override
    public ScPartyBuild selectScPartyBuildFileById(Long partyBuildId, String headUrl) {
        ScPartyBuild scPartyBuild = scPartyBuildMapper.selectScPartyBuildById(partyBuildId);
        for (ScFileInfo f : scPartyBuild.getFiles()) {
            f.setFilePath(f.getFilePath().equals("") || f.getFilePath() == null ? "" : headUrl + f.getFilePath());
        }
        return scPartyBuild;
    }

    @Override
    public List<ScPartyBuild> selectScPartyBuildFileByList(ScPartyBuild scPartyBuild) {
        return scPartyBuildMapper.selectScPartyBuildFileByList(scPartyBuild);
    }

    @Override
    public List<ScPartyBuild> selectScPartyBuildFileByList(ScPartyBuild scPartyBuild, String headUrl) {
        List<ScPartyBuild> scPartyBuilds = this.selectScPartyBuildFileByList(scPartyBuild);
        for (ScPartyBuild scPartyBuild2 : scPartyBuilds) {
            if (scPartyBuild2.getFiles() != null && scPartyBuild2.getFiles().size() > 0) {
                scPartyBuild2.setPartyBuildCover(scPartyBuild2.getFiles() == null ? ""
                        : headUrl + scPartyBuild2.getFiles().get(0).getFilePath());
            }
        }
        return scPartyBuilds;
    }

    @Override
    public String getVideoUrl(ScPartyBuild scPartyBuild) {
        String videoUrl = "";
        if (scPartyBuild.getFiles().size() > 0) {
            String thumbFile = scPartyBuild.getFiles().get(0).getFilePath();
            UrlSeries data = ThumbnailFactory.getUrlSeries(thumbFile);
            videoUrl = data.getOriginal() != null ? data.getOriginal() : "";
        }
        return videoUrl;
    }
}
