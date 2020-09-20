package com.feather.aupipes.utils;


import com.feather.aupipes.domain.enums.PipelineLayoutType;
import com.feather.aupipes.domain.enums.PipelineTypes;
import org.springframework.stereotype.Component;


/**
 * 功能描述：
 *
 * @Author: fang xinliang
 * @Date: 2020/2/6 18:22
 */
@Component
public class PipelineStandard {

   public PipelineTypes GetPipelineType(String pipelineName)
    {
        PipelineTypes pipelineType = PipelineTypes.Unknown;
        switch (pipelineName)
        {
            //给水
            case "js":
            case "JS":
                pipelineType = PipelineTypes.FeedWater;
                break;

            //排水
            case "ps":
            case "PS":
                pipelineType = PipelineTypes.DeWater;
                break;

            //电力
            case "dl":
            case "DL":
                pipelineType = PipelineTypes.Electricity;
                break;

            //燃气
            case "rq":
            case "RQ":
                pipelineType = PipelineTypes.Gas;
                break;

            //电信
            case "dx":
            case "DX":
                pipelineType = PipelineTypes.Telegraphy;
                break;

            //工业
            case "gy":
            case "GY":
                pipelineType = PipelineTypes.Industry;
                break;

            //热力
            case "rl":
            case "RL":
                pipelineType = PipelineTypes.Energetics;
                break;
        }

        return pipelineType;
    }

    /**
     *  编号从 1 开始
     */
    public String[] layerOutCode={"直埋","管埋","管块","方沟","架空","拱沟","排管","人防","隧道","其他","组合" };
    /// <summary>
    /// 获取埋设方式枚举
    /// </summary>
    /// <param name="layoutName">埋设方式字符串</param>
    /// <returns>埋设方式枚举</returns>
    public PipelineLayoutType GetPipelineLayoutType(String layoutName)
    {
        PipelineLayoutType layoutType = PipelineLayoutType.Layout_UNKNOWN;

        switch (layoutName)
        {
            //直埋
            case "直埋":
                layoutType = PipelineLayoutType.Layout_ZHIMAI ;
                break;

            //管埋
            case "管埋":
                layoutType = PipelineLayoutType.Layout_GUANMAI;
                break;

            //管块
            case "管块":
                layoutType = PipelineLayoutType.Layout_GUANKUAI;
                break;

            //沟埋
            case "沟埋":
                layoutType = PipelineLayoutType.Layout_GOUMAI;
                break;

            //架空
            case "架空":
                layoutType = PipelineLayoutType.Layout_JIAKONG;
                break;

            //综合管沟
            case "综合管沟":
                layoutType = PipelineLayoutType.Layout_ZONGHEGUANGOU;
                break;
        }

        return layoutType;
    }

    /// <summary>
    /// 获取埋深标准
    /// </summary>
    /// <param name="lineType">管线类型</param>
    /// <param name="layoutType">埋设类型</param>
    /// <param name="beRoadway">是否是车行道，是为true，否则为false</param>
    /// <returns>埋深标准</returns>
    public double GetCoverageStandard(PipelineTypes lineType, PipelineLayoutType layoutType, boolean beRoadway)
    {
        //int pipelineTypesCode=lineType.getCode();

        int row = 0, col = 0;
        if (beRoadway)
        {
            row = 1;
        }
        switch (lineType)
        {
            // 电力
            case Electricity:
                if (layoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    col = 0;
                }
                else
                {
                    col = 1;
                }
                break;

            // 电信
            case Telegraphy:
                if (layoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    col = 2;
                }
                else
                {
                    col = 3;
                }
                break;

            // 热力
            case Energetics:
                if (layoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    col = 4;
                }
                else
                {
                    col = 5;
                }
                break;

            // 给水
            case FeedWater:
                col = 6;
                break;

            // 排水
            case DeWater:
                col = 7;
                break;

            // 燃气
            case Gas:
                col = 8;
                break;

            // 路灯
            case Streetlamp:
                col = 0;
                break;
            default:
                return 0.0;
        }
        return standardTableCov[row][col];
    }

    /// <summary>
    /// 获取垂直间距标准
    /// </summary>
    /// <param name="targetLineType">目标管线类型</param>
    /// <param name="targetLayoutType">目标埋设类型</param>
    /// <param name="referLineType">参照管线类型</param>
    /// <param name="referLayoutType">参照埋设类型</param>
    /// <returns>垂直间距标准</returns>
    public double GetVerticalSpacingStandard(PipelineTypes targetLineType, PipelineLayoutType targetLayoutType, PipelineTypes referLineType, PipelineLayoutType referLayoutType)
    {
        int row = 0, col = 0;

        switch (targetLineType)
        {
            // 给水
            case FeedWater:
                row = 0;
                break;

            // 排水
            case DeWater:
                row = 1;
                break;

            // 热力
            case Energetics:
                row = 2;
                break;

            // 燃气
            case Gas:
                row = 3;
                break;

            // 电信
            case Telegraphy:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    row = 4;
                }
                else
                {
                    row = 5;
                }
                break;

            // 电力
            case Electricity:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    row = 6;
                }
                else
                {
                    row = 7;
                }
                break;

            // 路灯
            case Streetlamp:
                row = 6;
                break;
            default:
                return 0.0;
        }
        switch (referLineType)
        {

            // 给水
            case FeedWater:
                col = 0;
                break;

            // 排水
            case DeWater:
                col = 1;
                break;

            // 热力
            case Energetics:
                col = 2;
                break;

            // 燃气
            case Gas:
                col = 3;
                break;

            // 电信
            case Telegraphy:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    col = 4;
                }
                else
                {
                    col = 5;
                }
                break;

            // 电力
            case Electricity:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    col = 6;
                }
                else
                {
                    col = 7;
                }
                break;

            // 路灯
            case Streetlamp:
                col = 6;
                break;
            default:
                return 0.0;
        }

        return standardTableVer[row][col];
    }

    /// <summary>
    /// 获取水平间距标准
    /// </summary>
    /// <param name="targetLineType">目标管线类型</param>
    /// <param name="targetLayoutType">目标埋设类型</param>
    /// <param name="referLineType">参照管线类型</param>
    /// <param name="referLayoutType">参照埋设类型</param>
    /// <returns>水平间距标准</returns>
    public double GetHorizontalSpacingStandard(PipelineTypes targetLineType, PipelineLayoutType targetLayoutType, PipelineTypes referLineType, PipelineLayoutType referLayoutType)
    {
        int row = 0, col = 0;

        switch (targetLineType)
        {
            // 给水, 0-1
            case FeedWater:
                row = 0;
                break;

            // 排水, 2
            case DeWater:
                row = 2;
                break;

            // 燃气, 3-7
            case Gas:
                row = 3;
                break;

            // 热力, 8-9
            case Energetics:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    row = 8;
                }
                else
                {
                    row = 9;
                }
                break;

            // 电力, 10
            case Electricity:
                row = 10;
                break;

            // 电信, 11-12
            case Telegraphy:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    row = 11;
                }
                else
                {
                    row = 12;
                }
                break;

            // 路灯
            case Streetlamp:
                row = 10;
                break;
            default:
                return 0.0;
        }
        switch (referLineType)
        {
            // 给水, 0-1
            case FeedWater:
                col = 0;
                break;

            // 排水, 2
            case DeWater:
                col = 2;
                break;

            // 燃气, 3-7
            case Gas:
                col = 3;
                break;

            // 热力, 8-9
            case Energetics:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    col = 8;
                }
                else
                {
                    col = 9;
                }
                break;

            // 电力, 10
            case Electricity:
                col = 10;
                break;

            // 电信, 11-12
            case Telegraphy:
                if (targetLayoutType == PipelineLayoutType.Layout_ZHIMAI)
                {
                    col = 11;
                }
                else
                {
                    col = 12;
                }
                break;

            // 路灯
            case Streetlamp:
                col = 10;
                break;
            default:
                return 0.0;
        }
        return standardTableHor[row][col];
    }


   // {
        // |-----------------------------------------------------------------------------------------|
        // |  最小  |     电    力    |    电    信     |    热    力     |        |        |        |
        // |  覆土  |-----------------|-----------------|-----------------|  燃气  |  给水  |  排水  |
        // |  深度  |  直埋  |  管沟  |  直埋  |  管沟  |  直埋  |  管沟  |        |        |        |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 人行道 |  0.5   |  0.4   |  0.7   |  0.4   |  0.5   |  0.2   |  0.6   |  0.6   |  0.6   |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 车行道 |  0.7   |  0.5   |  0.8   |  0.7   |  0.7   |  0.2   |  0.7   |  0.7   |  0.7   |
        // |-----------------------------------------------------------------------------------------|
        double[][] standardTableCov =
        {
            {0.5, 0.4, 0.7, 0.4, 0.5, 0.2, 0.6, 0.6, 0.6}, // 人行道
            {0.7, 0.5, 0.8, 0.7, 0.7, 0.2, 0.7, 0.7, 0.7}  // 车行道
        };

        // |--------------------------------------------------------------------------------|
        // |  最小  |        |        |        |        |    电    信     |     电    力    |
        // |  垂直  |  给水  |  排水  |  热力  |  燃气  |-----------------|-----------------|
        // |  净距  |        |        |        |        |  直埋  |  管块  |  直埋  |  管沟  |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 给水   |  0.15  |        |        |        |        |        |        |        |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 排水   |  0.4   |  0.15  |        |        |        |        |        |        |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 热力   |  0.15  |  0.15  |  0.15  |        |        |        |        |        |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 燃气   |  0.15  |  0.15  |  0.15  |  0.15  |        |        |        |        |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 电|直埋|  0.5   |  0.5   |  0.15  |  0.5   |  0.25  |  0.25  |        |        |
        // |   |----|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 信|管块|  0.15  |  0.15  |  0.15  |  0.15  |  0.25  |  0.25  |        |        |
        // |--------|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 电|直埋|  0.15  |  0.5   |  0.5   |  0.5   |  0.5   |  0.5   |  0.5   |  0.5   |
        // |   |----|--------|--------|--------|--------|--------|--------|--------|--------|
        // | 力|管沟|  0.15  |  0.5   |  0.5   |  0.15  |  0.5   |  0.5   |  0.5   |  0.5   |
        // |--------------------------------------------------------------------------------|
        double[][]  standardTableVer =
        {
            {0.15, 0.4,  0.15, 0.15, 0.5,  0.15, 0.15, 0.15}, // 给水
            {0.4,  0.15, 0.15, 0.15, 0.5,  0.15, 0.5,  0.5 }, // 排水
            {0.15, 0.15, 0.15, 0.15, 0.15, 0.15, 0.5,  0.5 }, // 热力
            {0.15, 0.15, 0.15, 0.15, 0.5,  0.15, 0.5,  0.15}, // 燃气
            {0.5,  0.5,  0.15, 0.5,  0.25, 0.25, 0.5,  0.5 }, // 电信直埋
            {0.15, 0.15, 0.15, 0.15, 0.25, 0.25, 0.5,  0.5 }, // 电信管块
            {0.15, 0.5,  0.5,  0.5,  0.5,  0.5,  0.5,  0.5 }, // 电力直埋
            {0.15, 0.5,  0.5,  0.15, 0.5,  0.5,  0.5,  0.5 }, // 电力管沟
        };

        // |--------------------------------------------------------------------------------------------------------------------------------|
        // |    最小   |    给    水     |        |             燃          气                 |    热    力     |        |     电    信    |
        // |    水平   |-----------------|  排水  |--------------------------------------------|-----------------|  电力  |-----------------|
        // |    净距   |d<=200mm|d>200mm |        |  低压  | 中压小 | 中压大 | 高压小 | 高压大 |  直埋  |  沟埋  |        |  直埋  |  管道  |
        // |-----------|-----------------|--------|--------------------------|--------|--------|-----------------|--------|-----------------|
        // | 给|d<=200 |                 |  1.0   |                          |        |        |                 |        |                 |
        // |   |-------|                 |--------|           0.5            |  1.0   |  1.5   |       1.5       |  0.5   |       1.0       |
        // | 水|d>200  |                 |  1.5   |                          |        |        |                 |        |                 |
        // |-----------|-----------------|--------|--------------------------|--------|--------|-----------------|--------|-----------------|
        // | 排水      |  1.0   |  1.5   |        |   1.0  |       1.2       |  1.5   |  2.0   |       1.5       |  0.5   |       1.0       |
        // |-----------|-----------------|--------|--------------------------------------------|-----------------|--------|-----------------|
        // |   |低压   |                 |  1.0   |                                            |       1.0       |        |        |        |
        // |   |-------|                 |--------|                                            |-----------------|        |        |        |
        // | 燃|中压小 |       0.5       |        |             DN <= 300mm 0.4                |        |        |  0.5   |  0.5   |  1.0   |
        // |   |-------|                 |  1.2   |                                            |  1.0   |  1.5   |        |        |        |
        // |   |中压大 |                 |        |                                            |        |        |        |        |        |
        // |   |-------|-----------------|--------|                                            |-----------------|--------|-----------------|
        // | 气|高压小 |       1.0       |  1.5   |             DN >  300mm 0.5                |  1.5   |  2.0   |  1.0   |       1.0       |
        // |   |-------|-----------------|--------|                                            |-----------------|--------|-----------------|
        // |   |高压大 |       1.5       |  2.0   |                                            |  2.0   |  4.0   |  1.5   |       1.5       |
        // |-----------|-----------------|--------|--------------------------------------------|-----------------|--------|-----------------|
        // | 热|直埋   |                 |        |        |       1.0       |  1.5   |  2.0   |                 |        |                 |
        // |   |-------|       1.5       |  1.5   |  1.0   |-----------------|--------|--------|                 |  2.0   |       1.0       |
        // | 力|沟埋   |                 |        |        |       1.5       |  2.0   |  4.0   |                 |        |                 |
        // |-----------|-----------------|--------|--------------------------|--------|--------|-----------------|--------|-----------------|
        // | 电力      |        0.5      |  0.5   |           0.5            |  1.0   |  1.5   |       2.0       |        |       0.5       |
        // |-----------|-----------------|--------|--------------------------|--------|--------|-----------------|--------|-----------------|
        // | 电|直埋   |                 |        |           0.5            |        |        |                 |        |                 |
        // |   |-------|       1.0       |  1.0   |--------------------------|  1.0   |  1.5   |       1.0       |  0.5   |       1.0       |
        // | 信|管道   |                 |        |           1.0            |        |        |                 |        |                 |
        // |--------------------------------------------------------------------------------------------------------------------------------|
        // 其中:
        // 燃气低压  ： p <= 0.05Mpa
        // 燃气中压小： 0.05Mpa < p <= 0.2 Mpa
        // 燃气中压大： 0.2 Mpa < p <= 0.4 Mpa
        // 燃气高压小： 0.4 Mpa < p <= 0.8 Mpa
        // 燃气高压大： 0.8 Mpa < p <= 1.6 Mpa
        double[][] standardTableHor =
        {
            {0.0, 0.0, 1.0, 0.5, 0.5, 0.5, 1.0, 1.5, 1.5, 1.5, 0.5, 1.0, 1.0}, // 给水管径小
            {0.0, 0.0, 1.5, 0.5, 0.5, 0.5, 1.0, 1.5, 1.5, 1.5, 0.5, 1.0, 1.0}, // 给水管径大
            {1.0, 1.5, 0.0, 1.0, 1.2, 1.2, 1.5, 2.0, 1.5, 1.5, 0.5, 1.0, 1.0}, // 排水
            {0.5, 0.5, 1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 1.0, 1.0, 0.5, 0.5, 1.0}, // 燃气低压
            {0.5, 0.5, 1.2, 0.5, 0.5, 0.5, 0.5, 0.5, 1.0, 1.5, 0.5, 0.5, 1.0}, // 燃气中压小
            {0.5, 0.5, 1.2, 0.5, 0.5, 0.5, 0.5, 0.5, 1.0, 1.5, 0.5, 0.5, 1.0}, // 燃气中压大
            {1.0, 1.0, 1.5, 0.5, 0.5, 0.5, 0.5, 0.5, 1.5, 2.0, 1.0, 1.0, 1.0}, // 燃气高压小
            {1.5, 1.5, 2.0, 0.5, 0.5, 0.5, 0.5, 0.5, 2.0, 4.0, 1.5, 1.5, 1.5}, // 燃气高压大
            {1.5, 1.5, 1.5, 1.0, 1.0, 1.0, 1.5, 2.0, 0.0, 0.0, 2.0, 1.0, 1.0}, // 热力直埋
            {1.5, 1.5, 1.5, 1.0, 1.5, 1.5, 2.0, 4.0, 0.0, 0.0, 2.0, 1.0, 1.0}, // 热力沟埋
            {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 1.0, 1.5, 2.0, 2.0, 0.0, 0.5, 0.5}, // 电力
            {1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 1.5, 1.0, 1.0, 0.5, 1.0, 1.0}, // 电信直埋
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.5, 1.0, 1.0, 0.5, 1.0, 1.0}, // 电信管道
        };

   // }

//}
}
