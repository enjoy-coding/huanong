/**
* 能耗监管-
* 用电统计配电房年度用能
* 用水统计分区年度用能
*/
ALTER PROCEDURE [dbo].[nyjk_querySideUseNumber]
(
	@i_cacheId varchar(255),
	@i_year varchar(4),
	@i_important varchar(1)
)
AS
BEGIN
		DECLARE @v_year int,@v_month int,@v_preMonth int

		IF ISNULL(@i_year, '') = ''
		BEGIN
				SET @i_year =  DATENAME(YYYY,GETDATE())
		END
			SELECT @v_month = DATEPART(MM,GETDATE())
						 ,@v_preMonth = DATENAME(MM, dateAdd(MM,-1,GETDATE()))
		IF CHARINDEX('roote', @i_cacheId) > 0
		BEGIN
			--用电分区年度汇总
			SELECT cacheName as name
						,CAST(round(SUM(e.sumUseNumber)/10000,2) as DECIMAL(16,2))  as [value]
			FROM aup_TjElectricityByYearMonth e
			WHERE e.year = @i_year AND cacheLevel =2
			GROUP BY e.cacheName
		END
		ELSE
		BEGIN
			--用水分区年度汇总
			SELECT k.name
						,CAST(ROUND(k.currYearCurrMonthNumber, 2) as DECIMAL(18,2)) as [value]
						,CAST(ROUND(CASE WHEN k.currYearPreMonthNumber = 0 THEN 0 ELSE (k.currYearCurrMonthNumber- k.currYearPreMonthNumber)/k.currYearPreMonthNumber * 100 END,2) as DECIMAL(18,1)) as hb
			FROM (
					SELECT s.name
								,ISNULL(t1.currYearCurrMonthNumber/10000,0) as currYearCurrMonthNumber
								,ISNULL(t2.currYearPreMonthNumber/10000,0) as currYearPreMonthNumber
					FROM aup_water_side  s
					LEFT JOIN (
							SELECT cacheId,ISNULL(sumUseNumber, 0) as currYearCurrMonthNumber
							FROM aup_TjWaterSideByYearMonth
							WHERE [year] = @i_year AND [month] =@v_month
					)	t1 on t1.cacheId = s.cacheId
					LEFT JOIN (
							SELECT cacheId,ISNULL(sumUseNumber, 0) as currYearPreMonthNumber
							FROM aup_TjWaterSideByYearMonth WHERE [year] = @i_year AND [month] =@v_preMonth
					)t2 on t2.cacheId = s.cacheId
					where s.cacheLevel = 2
			) k 
		END
END