package com.feather.aupipes.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.feather.aupipes.domain.AupWarring;
import com.feather.aupipes.domain.AupWarring1;

/**
 * 预警记录信息Mapper接口
 * 
 * @author fancy
 * @date 2020-01-15
 */
public interface AupWarringMapper {
    /**
     * 查询预警记录信息
     * 
     * @param id
     *            预警记录信息ID
     * @return 预警记录信息
     */
    public AupWarring selectAupWarringById(String id);

    /**
     * 查询预警记录信息列表
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 预警记录信息集合
     */
    public List<AupWarring> selectAupWarringList(AupWarring aupWarring);

    /**
     * 查询所有带外部图片的
     * 
     * @return 预警记录信息集合
     */
    public List<AupWarring> selectAllWithOuterPic();

    /**
     * 新增预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    public int insertAupWarring(AupWarring aupWarring);

    /**
     * 修改预警记录信息
     * 
     * @param aupWarring
     *            预警记录信息
     * @return 结果
     */
    public int updateAupWarring(AupWarring aupWarring);

    /**
     * 删除预警记录信息
     * 
     * @param id
     *            预警记录信息ID
     * @return 结果
     */
    public int deleteAupWarringById(String id);

    /**
     * 批量删除预警记录信息
     * 
     * @param ids
     *            需要删除的数据ID
     * @return 结果
     */
    public int deleteAupWarringByIds(String[] ids);

    public List<AupWarring1> downWarringList(@Param("content") String content, @Param("level") String level,
            @Param("author") String author, @Param("dateTime1") String dateTime1, @Param("dateTime2") String dateTime2,
            @Param("state") String state, @Param("name") String name, @Param("formSysName") String formSysName);

    public List<Map> localWarring();

    public List<Map> getlocalWarring(@Param("formSysName") String formSysName, @Param("id") String id,
            @Param("state") int state);

    public List<Map> getPosition(@Param("table") String table, @Param("col") String col, @Param("name") String name);

    public List<Map> getAreaNo(@Param("lat") String lat, @Param("lont") String lont);

    public Map getInfoBySn(@Param("sn") String sn);
}
