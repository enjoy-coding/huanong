/**
* 能耗统计 当前年度12个月 与上一年度12个月份用量的柱状图
*
*/
ALTER PROCEDURE [dbo].[nyjk_queryMonthUseNumber]
(
	@i_cacheId varchar(255),
	@i_year varchar(4),
	@i_important varchar(1)
)
AS
BEGIN
		DECLARE @i_preYear VARCHAR(4)
	--如果时间为null默认为当前年份
		IF ISNULL(@i_year, '') = ''
		BEGIN
				SELECT @i_year = DATENAME(YYYY,GETDATE())
		END

		SET @i_preYear = @i_year - 1
		if CHARINDEX('rootw', @i_cacheId)>0
		BEGIN
			SELECT dict_label as name
						,CAST(round(ISNULL(t1.tableUseNumber, 0)/10000,5) as DECIMAL(16,5) ) as useNumber1
						,CAST(round(ISNULL(t2.tableUseNumber,0)/10000,5) as DECIMAL(16,5) ) as  useNumber2
			FROM sys_dict_data d
			OUTER apply(
					SELECT SUM(sumUseNumber) as tableUseNumber
					FROM aup_TjWaterSideByYearMonth j
					WHERE j.[YEAR] = 2020  AND j.month = d.dict_value AND cacheId = @i_cacheId
			) t1
			OUTER apply(
					SELECT SUM(sumUseNumber) as tableUseNumber
					FROM aup_TjWaterSideByYearMonth j
					WHERE j.[YEAR] = 2019 AND j.month = d.dict_value AND cacheId = @i_cacheId
			) t2
			WHERE d.dict_type = 'sys_month'
			ORDER BY dict_sort
		END
		ELSE
		BEGIN
			SELECT dict_label as name --月份
						,CAST(round(ISNULL(t1.tableUseNumber, 0)/10000,5) as DECIMAL(16,5) ) as useNumber1
						,CAST(round(ISNULL(t2.tableUseNumber,0)/10000,5) as DECIMAL(16,5) ) as  useNumber2
			FROM sys_dict_data d
			OUTER apply(
				SELECT SUM(e.sumUseNumber) as  tableUseNumber
				FROM aup_TjElectricityByYearMonth e
				WHERE [year] = @i_year AND [month] = d.dict_value
				AND e.cacheId = @i_cacheId
				)t1
			OUTER apply(
				SELECT SUM(e.sumUseNumber) as tableUseNumber
				FROM aup_TjElectricityByYearMonth e
				WHERE e.cacheId = @i_cacheId AND [year] =@i_preYear AND [month] = d.dict_value
			)t2
			WHERE d.dict_type = 'sys_month'
			ORDER BY dict_sort			
		END
		
END