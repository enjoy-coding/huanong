package com.feather.lpscommunity.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feather.common.constant.Constants;
import com.feather.common.core.text.Convert;
import com.feather.common.utils.file.FileUploadUtils;
import com.feather.lpscommunity.domain.ScService;
import com.feather.lpscommunity.mapper.ScServiceMapper;
import com.feather.lpscommunity.service.IScServiceService;
import com.feather.system.domain.SysDictData;
import com.feather.system.mapper.SysDictDataMapper;

/**
 * 社区服务Service业务层处理
 *
 * @author dufan
 * @date 2019-10-18
 */
@Service
public class ScServiceServiceImpl implements IScServiceService {
    @Autowired
    private ScServiceMapper scServiceMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    private String templateContent;

    /**
     * 查询社区服务
     *
     * @param serviceDept
     *            社区服务ID
     * @return 社区服务
     */
    @Override
    public ScService selectScServiceById(Long serviceDept) {
        return scServiceMapper.selectScServiceById(serviceDept);
    }

    /**
     * 查询社区服务列表
     *
     * @param scService
     *            社区服务
     * @return 社区服务
     */
    @Override
    public List<ScService> selectScServiceList(ScService scService) {
        return scServiceMapper.selectScServiceList(scService);
    }

    /**
     * 查询社区服务列表
     *
     * @param scService
     *            社区服务
     * @return 社区服务
     */
    @Override
    public List<ScService> selectScServiceListByDicData(ScService scService) {
        List<ScService> list = this.selectScServiceList(scService);
        for (ScService scService2 : list) {
            List<SysDictData> dictLabel = sysDictDataMapper.selectDictDataByType(scService2.getServiceType());
            for (int i = 0; i < dictLabel.size(); i++) {
                if (scService2.getServiceCType().equals(dictLabel.get(i).getDictValue())) {
                    scService2.setServiceCType(dictLabel.get(i).getDictLabel());
                }
            }

        }
        return list;
    }

    /**
     * 筛选指定列返回列表
     *
     * @param scService
     *            社区服务
     * @return 社区服务
     */
    @Override
    public List<Map<String, Object>> screenScServiceList(ScService scService) {
        List<ScService> sList = this.selectScServiceList(scService);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < sList.size(); i++) {
            Map<String, Object> data = new HashMap<String, Object>();
            list.add(data);
        }
        return list;

    }

    /**
     * 新增社区服务
     *
     * @param scService
     *            社区服务
     * @return 结果
     */
    @Override
    public int insertScService(ScService scService) {
        saveFile(scService);
        return scServiceMapper.insertScService(scService);
    }

    /**
     * 修改社区服务
     *
     * @param scService
     *            社区服务
     * @return 结果
     */
    @Override
    public int updateScService(ScService scService) {
        saveFile(scService);
        return scServiceMapper.updateScService(scService);
    }

    /**
     * 删除社区服务对象
     *
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteScServiceByIds(String ids) {
        return scServiceMapper.deleteScServiceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除社区服务信息
     *
     * @param serviceDept
     *            社区服务ID
     * @return 结果
     */
    public int deleteScServiceById(Long serviceDept) {
        return scServiceMapper.deleteScServiceById(serviceDept);
    }

    /**
     * 检查社区是否唯一
     * 
     * @param scService
     *            社区服务
     * @return
     */
    public boolean checkServiceDeptUnique(ScService scService) {
        Long serviceId = scService.getServiceId() == null ? -1L : scService.getServiceId();
        ScService scService1 = new ScService(scService.getServiceType(), scService.getServiceCType());
        ScService info = scServiceMapper.checkServiceDeptUnique(scService1);
        if (info != null && info.getServiceId().longValue() != serviceId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 生成html文件
     * 
     * @param scService
     */
    public void saveFile(ScService scService) {
        String content = scService.getServiceContent();
        String url = null;
        try {
            // 根据正则将电话号码用标签套入
            // <a href="tel:13366668888">打电话</a>
            String html = StringUtils.replace(getTemplateContent(), "###content###", content);
            url = FileUploadUtils.upload(html, "html", scService.getServiceFile());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        // 将路径存字段
        scService.setServiceFile(url);
    }

    /**
     * 读取模板
     */
    public String getTemplateContent() throws IOException {
        if (StringUtils.isEmpty(templateContent)) {
            try (InputStream is = getClass().getClassLoader()
                    .getResourceAsStream("templates/lpscommunity/service/template.html")) {
                int lenght = is.available();
                byte bytes[] = new byte[lenght];
                is.read(bytes);
                templateContent = new String(bytes, Constants.UTF8);
            }
        }
        return templateContent;
    }

    /**
     * 服务之窗栏目
     * 
     * @return
     */
    public List<Map<String, Object>> getServiceValue() {
        String colunm_type = "sc_fwzc_type";
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType(colunm_type);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < sysDictDataList.size(); i++) {
            Map<String, Object> data = new LinkedHashMap<String, Object>();
            data.put("service_label", sysDictDataList.get(i).getDictLabel());
            data.put("service_type", sysDictDataList.get(i).getDictValue());
            mapList.add(data);
        }
        return mapList;
    }

    /**
     * 服务子服务以及地址
     * 
     * @param headUrl
     *            请求头
     * @param serviceType
     *            类型
     * @return
     */
    public List<Map<String, Object>> getServiceChildrenValue(String headUrl, String serviceType) {
        List<ScService> scServiceList = this.selectScServiceList(new ScService(serviceType));
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType(serviceType);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < scServiceList.size(); i++) {
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("serviceFile", "");
            map.put("serviceType", sysDictDataList.get(i).getDictLabel());
            for (int j = 0; j < scServiceList.size(); j++) {
                if (scServiceList.get(j).getServiceCType().equals(sysDictDataList.get(i).getDictValue())) {
                    map.put("serviceFile",
                            com.feather.common.utils.StringUtils.isNotEmpty(scServiceList.get(j).getServiceFile())
                                    ? headUrl + scServiceList.get(j).getServiceFile() : "");
                }
            }
            mapList.add(map);
        }
        return mapList;
    }

}
