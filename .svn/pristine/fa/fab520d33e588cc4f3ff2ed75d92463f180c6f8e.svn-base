package com.feather.aupipes.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feather.aupipes.service.IAupTimingTaskService;

/**
 * @author 15807
 */
@Component("timingTask")
public class AupTimingTaskController {
    @Autowired
    private IAupTimingTaskService aupTimingTaskService;

    /**
     * 向数据库插入能耗监管_总数
     * 
     * @param table
     */
    public void insertEnergyAll() {
        String table = "NT_DataCleaning_UseNumMonth";

        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;

        year = 2019;
        month = 12;

        double yearWaTer = 0;
        double monthWater = 0;

        for (int i = month; i > 0; i--) {
            String tableName = table + year;
            if (i < 10) {
                tableName += "0" + i;
            } else {
                tableName += i;
            }
            yearWaTer += aupTimingTaskService.getMonthWaterData(tableName);
            if (i == month) {
                monthWater = aupTimingTaskService.getMonthWaterData(tableName);
            }
        }

        // double
        // monthElectric=aupTimingTaskService.getMonthElectricityData(table);
        // System.out.println("当月用水量"+monthWater);
        // System.out.println("当年用水量"+yearWaTer);
        // System.out.println(monthElectric);
    }
}
