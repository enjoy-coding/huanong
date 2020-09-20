package com.feather.napo.service.impl;

import com.feather.common.config.UidWorker;
import com.feather.common.core.text.Convert;
import com.feather.napo.domain.NpCar;
import com.feather.napo.mapper.NpCarMapper;
import com.feather.napo.service.INpCarService;
import com.feather.system.domain.SysDictData;
import com.feather.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author nothing
 * @date 2020-06-17 8:30
 */
@Service
public class NpCarServiceImpl implements INpCarService {

    private final String NP_CAR_STATE = "np_car_state";

    @Autowired
    NpCarMapper npCarMapper;

    @Autowired
    ISysDictDataService sysDictDataService;

    @Autowired
    private UidWorker uidWorker;

    @Override
    public NpCar selectNpCarById(Long deviceId) {
        return npCarMapper.selectNpCarById(deviceId);
    }

    @Override
    public List<NpCar> selectNpCarList(NpCar npCar) {
        List<NpCar> npCars = npCarMapper.selectNpCarList(npCar);
        List<SysDictData> stateDictDatas = sysDictDataService.selectDictDataByType(NP_CAR_STATE);
        if (!Objects.isNull(npCars) && npCars.size() > 0) {
            for (NpCar car : npCars) {
                int state = car.getState();
                for (SysDictData stateDictData : stateDictDatas) {
                    String dictLabel = stateDictData.getDictLabel();
                    String dictValue = stateDictData.getDictValue();
                    if (String.valueOf(state).equals(dictValue)) {
                        car.setStateVal(dictLabel);
                        break;
                    }
                }
            }
        }
        return npCars;
    }

    @Override
    public int insertNpCar(NpCar npCar) {
        if (npCar.getCarId() == null) {
            npCar.setCarId(uidWorker.getNextId());
        }
        return npCarMapper.insertNpCar(npCar);
    }

    @Override
    public int updateNpCar(NpCar npCar) {
        return npCarMapper.updateNpCar(npCar);
    }

    @Override
    public int deleteNpCarById(Long deviceId) {
        return npCarMapper.deleteNpCarById(deviceId);
    }

    @Override
    public int deleteNpCarByIds(String ids) {
        Long[] npDeviceIds = Convert.toLongArray(ids);
        return npCarMapper.deleteNpCarByIds(npDeviceIds);
    }
}
