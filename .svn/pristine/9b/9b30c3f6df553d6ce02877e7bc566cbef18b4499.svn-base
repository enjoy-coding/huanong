package com.feather.aupipes.utils;

import org.springframework.stereotype.Component;

/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2020/2/15 12:22
 */
@Component
public class PineLineStandardDistance {
    /**
     *  埋设方式 编号从 1 开始
     */
    public String[][] layerOutCode = {
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"},
            {"直埋", "管埋", "管块", "方沟", "架空", "拱沟", "排管", "人防", "隧道", "其他", "组合"}
    };
        /*
            管线类型	是否直埋	人行道最下深度	车行道最下深度
            DL	1	0.5	0.7
            DL	0	0.4	0.5
            DX	1	0.7	0.8
            DX	0	0.4	0.7
            JS	1	0.6	0.7
            JS	0	0.6	0.7
            PS	1	0.6	0.7
            PS	0	0.6	0.7
            RQ	1	0.6	0.8
            RQ	0	0.6	0.8
            RL	1	0.5	0.7
            RL	0	0.2	0.2
            GY	1
            GY	0
            ZH	1
            ZH	0
        */
    public double[][] standardTableCov =
            {
                    {0.5, 0.4, 0.7, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6,0.6,0.5,0.2,0.5,0.5,0.5,0.5},   // 人行道
                    {0.7, 0.5, 0.8, 0.7, 0.7, 0.7, 0.7, 0.7, 0.8,0.8,0.7,0.2,0.5,0.5,0.5,0.5}    // 车行道
            };

    public double GetCoverageStandard(String lineType, String directly, String roadway)
    {

        int row = 0, col = 0;
        if("车行道".equals(roadway)) {
            row = 1;
        }
        switch (lineType.toUpperCase()) {
            // 电力
            case "DL":
                col = 0;
                break;
            // 电信
            case "DX":
                col = 1;
                break;
            // 给水
            case "JS":
                col = 2;
                break;
            // 给水
            case "PS":
                col = 3;
                break;
            // 燃气
            case "RQ":
                col = 4;
                break;
            //蒸汽
            case "RL":
                col = 5;
                break;
            // 燃油
            case "GY":
                col = 6;
                break;
            // 综合
            case "ZH":
                col = 7;
                break;
            default:
                return 0.0;
        }
        if("1".equals(directly)){
            col=col*2;
        }else{
            col=col*2+1;
        }
        return standardTableCov[row][col];
    }

    /**
     * 7.3 垂直净距规范表
     * @param lineType
     * @param directly
     * @param lineType2
     * @param directly2
     * @return
     */
    public double GetVerticalSpacingStandard(String lineType, String directly, String lineType2, String directly2) {
        double v_distance = 0.0;
        switch (lineType.toUpperCase()) {
            // 电力
            case "DL":
                switch (lineType2.toUpperCase()) {
                    // 电力
                    case "DL":
                        v_distance = 0.5;
                        break;
                    // 电信
                    case "DX":
                        v_distance = 0.5;
                        break;
                    // 给水
                    case "JS":
                        v_distance = 0.15;
                        break;
                    // 给水
                    case "PS":
                        v_distance = 0.5;
                        break;
                    // 燃气
                    case "RQ":
                        if ("1".equals(directly)) {
                            v_distance = 0.5;
                        } else {
                            v_distance = 0.15;
                        }
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 0.5;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 电信
            case "DX":
                switch (lineType2.toUpperCase()) {
                    // 电信
                    case "DX":
                        v_distance = 0.25;
                        break;
                    // 给水
                    case "JS":
                        if ("1".equals(directly)) {
                            v_distance = 0.5;
                        } else {
                            v_distance = 0.15;
                        }
                        break;
                    // 给水
                    case "PS":
                        if ("1".equals(directly)) {
                            v_distance = 0.5;
                        } else {
                            v_distance = 0.15;
                        }
                        break;
                    // 燃气
                    case "RQ":
                        if ("1".equals(directly)) {
                            v_distance = 0.5;
                        } else {
                            v_distance = 0.15;
                        }
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 0.5;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 给水
            case "JS":
                switch (lineType2.toUpperCase()) {

                    // 给水
                    case "JS":
                        v_distance = 0.15;
                        break;
                    // 给水
                    case "PS":
                        v_distance = 0.4;
                        break;
                    // 燃气
                    case "RQ":
                        v_distance = 0.15;
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 0.5;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 给水
            case "PS":
                switch (lineType2.toUpperCase()) {
                    // 给水
                    case "PS":
                        v_distance = 0.15;
                        break;
                    // 燃气
                    case "RQ":
                        v_distance = 0.15;
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 0.5;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 燃气
            case "RQ":
                switch (lineType2.toUpperCase()) {
                    // 燃气
                    case "RQ":
                        v_distance = 0.15;
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 0.15;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            //蒸汽
            case "RL":
                switch (lineType2.toUpperCase()) {
                    //蒸汽
                    case "RL":
                        v_distance = 0.15;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 燃油
            case "GY":
                switch (lineType2.toUpperCase()) {

                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 综合
            case "ZH":
                switch (lineType2.toUpperCase()) {
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            default:
                return 0.0;
        }
        return v_distance;
    }

    /**
     *   水平距离计算
     *          // 燃气低压  ： p <= 0.05Mpa
     *         // 燃气中压小： 0.05Mpa < p <= 0.2 Mpa
     *         // 燃气中压大： 0.2 Mpa < p <= 0.4 Mpa
     *         // 燃气高压小： 0.4 Mpa < p <= 0.8 Mpa
     *         // 燃气高压大： 0.8 Mpa < p <= 1.6 Mpa
     * @param lineType
     * @param directly
     * @param lineType2
     * @param directly2
     * @return
     */
    public double GetHorizontalSpacingStandard(String lineType, String directly, double pressureStart,double pressureEnd,double radiusStart,double radiusEnd
                                       ,String lineType2, String directly2, double pressureStart2,double pressureEnd2,double radiusStart2,double radiusEnd2) {
        double v_distance = 0.0;
        switch (lineType.toUpperCase()) {
            // 电力
            case "DL":
                switch (lineType2.toUpperCase()) {
                    // 电力
                    case "DL":
                        v_distance = 0.5;
                        break;
                    // 电信
                    case "DX":
                        v_distance = 0.5;
                        break;
                    // 给水
                    case "JS":
                        v_distance = 0.15;
                        break;
                    // 给水
                    case "PS":
                        v_distance = 0.5;
                        break;
                    // 燃气
                    case "RQ":
                        if ("1".equals(directly)) {
                            if(pressureEnd2 <= 0.05){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.05&&pressureEnd2 <= 0.2){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.2&&pressureEnd2 <= 0.4){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.4&&pressureEnd2 <= 0.8){
                                v_distance = 1.0;
                            }else if(pressureStart2 > 0.8&&pressureEnd2 <= 1.6){
                                v_distance = 1.5;
                            }
                        } else {
                            if(pressureEnd2 <= 0.05){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.05&&pressureEnd2 <= 0.2){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.2&&pressureEnd2 <= 0.4){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.4&&pressureEnd2 <= 0.8){
                                v_distance = 1.0;
                            }else if(pressureStart2 > 0.8&&pressureEnd2 <= 1.6){
                                v_distance = 1.5;
                            }
                        }
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 2.0;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.0;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.0;
                        break;
                }
                break;
            // 电信
            case "DX":
                switch (lineType2.toUpperCase()) {

                    // 给水
                    case "JS":
                        v_distance = 1.0;
                        break;
                    // 给水
                    case "PS":
                        v_distance = 1.0;
                        break;
                    // 燃气
                    case "RQ":
                        if ("1".equals(directly)) {
                            if(pressureEnd2 <= 0.05){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.05&&pressureEnd2 <= 0.2){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.2&&pressureEnd2 <= 0.4){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.4&&pressureEnd2 <= 0.8){
                                v_distance = 1.0;
                            }else if(pressureStart2 > 0.8&&pressureEnd2 <= 1.6){
                                v_distance = 1.5;
                            }
                        } else {
                            if(pressureEnd2 <= 0.05){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.05&&pressureEnd2 <= 0.2){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.2&&pressureEnd2 <= 0.4){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.4&&pressureEnd2 <= 0.8){
                                v_distance = 1.0;
                            }else if(pressureStart2 > 0.8&&pressureEnd2 <= 1.6){
                                v_distance = 1.5;
                            }
                        }
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 1.0;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 给水
            case "JS":
                switch (lineType2.toUpperCase()) {
                    // 给水
                    case "PS":
                        if(radiusEnd<=200){
                            v_distance = 1.0;
                        }else if(radiusStart>=200){
                            v_distance = 1.5;
                        }
                        break;
                    // 燃气
                    case "RQ":
                        if(radiusEnd<=200){
                            if(pressureEnd2 <= 0.05){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.05&&pressureEnd2 <= 0.2){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.2&&pressureEnd2 <= 0.4){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.4&&pressureEnd2 <= 0.8){
                                v_distance = 1.0;
                            }else if(pressureStart2 > 0.8&&pressureEnd2 <= 1.6){
                                v_distance = 1.5;
                            }
                        }else if(radiusStart>=200){
                            if(pressureEnd2 <= 0.05){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.05&&pressureEnd2 <= 0.2){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.2&&pressureEnd2 <= 0.4){
                                v_distance = 0.5;
                            }else if(pressureStart2 > 0.4&&pressureEnd2 <= 0.8){
                                v_distance = 1.0;
                            }else if(pressureStart2 > 0.8&&pressureEnd2 <= 1.6){
                                v_distance = 1.5;
                            }
                        }
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 1.5;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.5;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.5;
                        break;
                }
                break;
            // 给水
            case "PS":
                switch (lineType2.toUpperCase()) {
                     // 燃气
                    case "RQ":
                        if(pressureEnd2 <= 0.05){
                            v_distance = 0.5;
                        }else if(pressureStart2 > 0.05&&pressureEnd2 <= 0.2){
                            v_distance = 0.5;
                        }else if(pressureStart2 > 0.2&&pressureEnd2 <= 0.4){
                            v_distance = 0.5;
                        }else if(pressureStart2 > 0.4&&pressureEnd2 <= 0.8){
                            v_distance = 1.0;
                        }else if(pressureStart2 > 0.8&&pressureEnd2 <= 1.6){
                            v_distance = 1.5;
                        }
                        break;
                    //蒸汽
                    case "RL":
                        v_distance = 0.5;
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.0;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.0;
                        break;
                }
                break;
            // 燃气
            case "RQ":
                switch (lineType2.toUpperCase()) {

                    //蒸汽
                    case "RL":
                        if ("1".equals(directly2)) {
                            if (pressureEnd <= 0.05) {
                                v_distance = 1.0;
                            } else if (pressureStart > 0.05 && pressureEnd <= 0.2) {
                                v_distance = 1.0;
                            } else if (pressureStart > 0.2 && pressureEnd <= 0.4) {
                                v_distance = 1.0;
                            } else if (pressureStart > 0.4 && pressureEnd <= 0.8) {
                                v_distance = 1.5;
                            } else if (pressureStart > 0.8 && pressureEnd <= 1.6) {
                                v_distance = 2.0;
                            }
                        }else{
                            if (pressureEnd <= 0.05) {
                                v_distance = 1.0;
                            } else if (pressureStart > 0.05 && pressureEnd <= 0.2) {
                                v_distance = 1.5;
                            } else if (pressureStart > 0.2 && pressureEnd <= 0.4) {
                                v_distance = 1.5;
                            } else if (pressureStart > 0.4 && pressureEnd <= 0.8) {
                                v_distance = 2.0;
                            } else if (pressureStart > 0.8 && pressureEnd <= 1.6) {
                                v_distance = 4.0;
                            }
                        }
                        break;
                    // 燃油,标准值有问题，还想确认
                    case "GY":
                        v_distance = 0.0;
                        break;
                    // 综合,标准值有问题，还想确认
                    case "ZH":
                        v_distance = 0.0;
                        break;
                }
                break;
            //蒸汽
            case "RL":
                break;
            // 燃油
            case "GY":
                break;
            // 综合
            case "ZH":
                break;
            default:
                return 0.0;
        }
        return v_distance;
    }
}
