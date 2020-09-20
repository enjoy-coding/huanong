package com.feather.aupipes.mapper;

import com.feather.aupipes.domain.AupHouse;
import java.util.List;

/**
 * 房屋信息Mapper接口
 *
 * @author fancy
 * @date 2019-12-12
 */
public interface AupHouseMapper
{
    /**
     * 查询房屋信息
     *
     * @param rid 房屋信息ID
     * @return 房屋信息
     */
    public AupHouse selectAupHouseById(Long rid);

    /**
     * 根据编号查询房屋
     * @param no
     * @return
     */
    public AupHouse selectAupHouseByNO(String no);


    /**
     * 查询房屋信息列表
     *
     * @param aupHouse 房屋信息
     * @return 房屋信息集合
     */
    public List<AupHouse> selectAupHouseList(AupHouse aupHouse);

    /**
     * 新增房屋信息
     *
     * @param aupHouse 房屋信息
     * @return 结果
     */
    public int insertAupHouse(AupHouse aupHouse);

    /**
     * 修改房屋信息
     *
     * @param aupHouse 房屋信息
     * @return 结果
     */
    public int updateAupHouse(AupHouse aupHouse);

    /**
     * 删除房屋信息
     *
     * @param rid 房屋信息ID
     * @return 结果
     */
    public int deleteAupHouseById(Long rid);

    /**
     * 批量删除房屋信息
     *
     * @param rids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAupHouseByIds(String[] rids);


}