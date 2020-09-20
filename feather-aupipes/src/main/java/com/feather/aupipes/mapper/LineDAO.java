package com.feather.aupipes.mapper;


import com.feather.aupipes.domain.query.Line;

import java.util.List;
import java.util.Map;

/**
 * @author tql
 * @description 管线段信息查询接口
 * @date 2019年12月18日15:03:18
 */
public interface LineDAO {

    /**
    *功能描述 查询管线段信息
    * @author tql
    * @date 2019/12/18
    * @param
    * @return java.util.List<domain.query.Line>
    */
    List<Line> getLineByCondition(Map<String, Object> map);

}
