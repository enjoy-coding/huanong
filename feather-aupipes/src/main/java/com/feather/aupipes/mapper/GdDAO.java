package com.feather.aupipes.mapper;


import com.feather.aupipes.domain.query.Fw;
import com.feather.aupipes.domain.query.Hlgx;
import com.feather.aupipes.domain.query.Pdf;

import java.util.List;
import java.util.Map;

public interface GdDAO {

    List<Fw> getFwinfo(Map<String, Object> map);

    List<Fw> getPdfinfo(Map<String, Object> map);

    /**
    *功能描述 查询配电房信息
    * @author tql
    * @date 2020/2/17
    * @param
    * @return java.util.List<com.kanq.pipe.domain.query.Pdf>
    */
    List<Pdf> getPdfs(Map<String, Object> map);

    List<Fw> getFws(Map<String, Object> map);

    /**
     *功能描述 按回路id查询回路关系信息
     * @author tql
     * @date 2020/3/23
     * @param
     * @return java.util.List<com.kanq.pipe.domain.query.Hlgx>
     */
    List<Hlgx> getHlgxs(Map<String, Object> map);

}
