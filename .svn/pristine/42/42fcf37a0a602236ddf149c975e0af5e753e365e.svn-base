package com.feather.napo.mapper;

import com.feather.napo.domain.NpCar;

import java.util.List;

/**
 * @author nothing
 * @date 2020-06-29 9:35
 */
public interface NpCarMapper {
    /**
     * 主键查询
     * @param carId
     * @return
     */
    NpCar selectNpCarById(Long carId);

    /**
     * 列表查询
     * @param npCar
     * @return
     */
    List<NpCar> selectNpCarList(NpCar npCar);

    /**
     * 插入一条npCar
     *
     * @param npCar
     * @return
     */
    int insertNpCar(NpCar npCar);

    /**
     * 更新一条npCar
     * @param npCar
     * @return
     */
    int updateNpCar(NpCar npCar);

    /**
     * 删除一条npCar
     * @param deviceId
     * @return
     */
    int deleteNpCarById(Long deviceId);

    /**
     * 删除一组详情
     * @param ids
     * @return
     */
    int deleteNpCarByIds(Long[] ids);
}
