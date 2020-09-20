package com.feather.common4nutz.controller;

import org.nutz.plugin.spring.boot.service.entity.PageredData;

import com.feather.common.core.controller.BaseController;
import com.feather.common.core.page.TableDataInfo;

/**
 * web层通用数据处理
 *
 * @author feather
 */
public class NutzBaseController extends BaseController {
    protected TableDataInfo getDataTable(PageredData<?> pageredData) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(pageredData.getDataList());
        rspData.setTotal(pageredData.getPager().getRecordCount());
        return rspData;
    }
}
