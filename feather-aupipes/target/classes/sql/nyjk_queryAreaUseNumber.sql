/**
* 能耗监管-面积水耗，电耗
*/
    ALTER PROCEDURE [dbo].[nyjk_queryAreaUseNumber]
    (
        @i_cacheId varchar(64),
        @i_year varchar(10),
        @i_important varchar(2)
        )
    AS
    BEGIN
        IF CHARINDEX('roote', @i_cacheId) > 0
            BEGIN
                SELECT d.dict_label as name
                     ,CAST(ROUND(ISNULL(t.mj, 0), 2) as DECIMAL(16,2) ) as [value]
                FROM sys_dict_data d
                         OUTER apply(
                    SELECT CASE WHEN MAX(k.buildArea) = 0 OR MAX(k.buildArea) is null
                                    THEN 0 ELSE  (MAX(sumUseNumber)/10000) /MAX(k.buildArea)  END AS mj
                    FROM aup_TjElectricityByYearMonth b1
                             OUTER apply(SELECT TOP 1 buildArea FROM aup_electricity_side s WHERE s.cacheId = b1.cacheId) k
                    WHERE b1.cacheId =@i_cacheId AND b1.year = @i_year  AND b1.month =d.dict_value
                ) t
                WHERE d.dict_type = 'sys_month'
                ORDER BY dict_sort
            END
        ELSE
            BEGIN
                SELECT dict_label as name
                     ,CAST (round(ISNULL(t1.mj, 0),2) as DECIMAL(18,2)) as [value]
                FROM sys_dict_data d
                         OUTER apply(
                    SELECT CASE WHEN MAX(k.buildArea) = NULL OR MAX(k.buildArea) = 0 THEN 0
                                ELSE SUM(j.sumUseNumber)/MAX(k.buildArea) END AS mj
                    FROM aup_TjWaterSideByYearMonth j
                             OUTER apply( SELECT TOP 1 s.buildArea FROM aup_water_side s WHERE s.cacheId = j.cacheId) k
                    WHERE j.[YEAR] = @i_year AND j.month = d.dict_value AND j.cacheId = @i_cacheId
                ) t1
                WHERE d.dict_type = 'sys_month'
                ORDER BY dict_sort

            END

    END