package com.feather.aupipes.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feather.aupipes.produce.service.AupCountService;

/**
 * 统计更新
 */
@Component("count")
public class UpdateTempCountData {
    @Autowired
    private AupCountService aupCountService;

    /**
     * 每天早上6点更新設備統計
     */
    public void updateEquipmentCount() {
        aupCountService.updateEquipmentCount();
    }

    /**
     * 半个月更新一次华农水电行政地区
     */
    public void insertAupArea(){
        aupCountService.insertAupArea();
    }


    /**
     * 每周日早上8点更新需要一次房屋信息
     */
    public void insertHouseMeterInfo() {
        aupCountService.insertHouseMeterInfo();
    }

    /**
     * 每天早上5,10,15,20更新用水分区的统计
     */
    public void insertWaterUseNumber() {
        aupCountService.insertWaterUseNumber();
    }

    /**
     * 每天早上6点50更新用电分区
     */
    public void  insetElectricityUseNumber(){ aupCountService.insetElectricityUseNumber(); }
    /**
     * 每天凌晨2点更新房屋的水表值
     */
    public void updateHouseMeterInfo() {
        aupCountService.updateHouseMeterInfo();
    }


    /**
     * 每天凌晨3点更新行政地区到楼栋的在线离线表个数
     */
    public void updateMeterInfo() {
        aupCountService.updateMeterInfo();
    }

    /**
     * 每天凌晨1点更新行政地区到楼栋的水电表值
     */
    public void updateMeterUseNumber() {
        aupCountService.updateMeterUseNumber();
    }

    /**
     * 每天凌晨5点更新行政地区到楼栋的水电表值
     */
    public void updateTotalUseNumber() {
        aupCountService.updateTotalUseNumber();
    }

}
