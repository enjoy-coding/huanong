/**
* 能耗监管-计算年度的总用量,当前年度下的当前月用量
* 以及和当前年度的上月份比较 计算环比（本月-上月）/上月 *100
*/
ALTER PROCEDURE [dbo].[nyjk_queryTotalUseNumber]
(
	@i_cacheId VARCHAR(255),
	@i_year varchar(4),
	@i_important VARCHAR(1)
)
AS
BEGIN
		DECLARE @v_month INT,@v_preMonth INT
		IF ISNULL(@i_year, '') = ''
		BEGIN
				SELECT @i_year = DATENAME(YYYY,GETDATE())
		END

		SELECT @v_month = DATEPART(MM,GETDATE())
					,@v_preMonth = DATENAME(MM, dateAdd(MM,-1,GETDATE()))


		IF CHARINDEX('rootw', @i_cacheId) >0 --用水
		BEGIN
			SELECT @i_year as yearName
						,ISNULL(CAST(round(t.yearUseNumber,2) as DECIMAL(16,2) ), 0) as yearUseNumber
						,ISNULL(CAST(round(currMonthUseNumber,2) as DECIMAL(16,2) ), 0) as currMonthUseNumber
						,CAST(round(CASE WHEN pre.preMonthUseNumber = 0 OR pre.preMonthUseNumber is null
									THEN 0
									ELSE ((curr.currMonthUseNumber - pre.preMonthUseNumber)/pre.preMonthUseNumber)*100 END,2) as DECIMAL(16,2) ) as  gl
						,CASE WHEN pre.preMonthUseNumber = 0 OR pre.preMonthUseNumber is null
									THEN 1
									WHEN  ((curr.currMonthUseNumber - pre.preMonthUseNumber)/pre.preMonthUseNumber)*100 >0
									THEN 1
									ELSE 0 END  isUpDown
			FROM
			(	SELECT SUM(sumUseNumber) as yearUseNumber
				FROM aup_TjWaterSideByYearMonth
				WHERE [Year] = @i_year
				AND cacheId = @i_cacheId
			) t
			OUTER apply(
				SELECT SUM(ISNULL(sumUseNumber,0)) as currMonthUseNumber
				FROM aup_TjWaterSideByYearMonth
				WHERE [Year] = @i_year
				AND [Month] = @v_month AND cacheId = @i_cacheId
			) curr
				OUTER apply(
				SELECT SUM(ISNULL(sumUseNumber,0)) as preMonthUseNumber
				FROM aup_TjWaterSideByYearMonth
				WHERE [Year] = @i_year
				AND [Month] = @v_preMonth AND cacheId = @i_cacheId
			) pre
		END
		ELSE --用电
		BEGIN
			SELECT @i_year as yearName
						,ISNULL(CAST(round(t.yearUseNumber,2) as DECIMAL(16,2) ), 0) as yearUseNumber --当前年用量
						,ISNULL(CAST(round(currMonthUseNumber,2) as DECIMAL(16,2) ), 0) as currMonthUseNumber --当前月用量
						,CAST(round(CASE WHEN preMonthUseNumber = 0 OR preMonthUseNumber is null
								THEN 0 ELSE ((currMonthUseNumber - preMonthUseNumber)/preMonthUseNumber)*100 END,2) as DECIMAL(16,2) ) gl --环比
					,CASE WHEN preMonthUseNumber = 0 OR preMonthUseNumber is null THEN 1
								WHEN  ((currMonthUseNumber - preMonthUseNumber)/preMonthUseNumber)*100 >0 THEN 1
								ELSE 0 END isUpDown --上升或下降图标
		FROM
		--全年
		(
			SELECT SUM(a.sumUseNumber)/10000  as yearUseNumber
			FROM aup_TjElectricityByYearMonth a
			WHERE cacheId = @i_cacheId AND [year] = @i_year
		) t
		--当前月
		outer apply(
			SELECT b.sumUseNumber/10000 as currMonthUseNumber FROM aup_TjElectricityByYearMonth b
			WHERE b.[month] = @v_month AND [year] = @i_year
			AND cacheId = @i_cacheId
		) curr
		--上个月
		outer apply(
			SELECT b.sumUseNumber/10000 as preMonthUseNumber  FROM aup_TjElectricityByYearMonth b
			WHERE b.[month] = @v_preMonth AND [year] = @i_year
			AND b.cacheId = @i_cacheId
		) pre
		END
END