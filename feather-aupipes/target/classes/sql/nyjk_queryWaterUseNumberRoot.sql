/**
* 能耗监管-从跟节点-分区节点-片区节点-楼栋节点 所显示的弹框详情信息
*/
ALTER PROCEDURE [dbo].[nyjk_queryWaterUseNumberRoot]
(
	@i_cacheId VARCHAR(255),
	@i_year varchar(4),
	@i_important varchar(2)
)
AS
BEGIN
			
			DECLARE @v_month VARCHAR(2),@v_preMonth VARCHAR(2)
		
			--如果时间为null默认为当前年份
			IF ISNULL(@i_year, '') = ''
			BEGIN 
					SELECT @i_year = DATENAME(YYYY,GETDATE())
			END 
			--默认月份为本月
					SELECT @v_month = DATEPART(MM,GETDATE()),@v_preMonth = DATENAME(MM, dateAdd(MM,-1,GETDATE()))
					
				SELECT t4.name
							,t1.yearUseNumber
							,ISNULL(t2.monthTableUseNumber, 0) as monthTableUseNumber
							,ISNULL(t3.perMonthTableUseNumber,0) as perMonthTableUseNumber
							,t4.waterCount
				FROM
				(	SELECT SUM(sumUseNumber) as yearUseNumber 
					FROM aup_TjWaterSideByYearMonth 
					WHERE [Year] = @i_year
					AND cacheId = @i_cacheId
				) t1 
				OUTER apply(
					SELECT ISNULL(sumUseNumber,0) as monthTableUseNumber
					FROM aup_TjWaterSideByYearMonth 
					WHERE [Year] = @i_year
					AND [Month] = @v_month
					AND cacheId = @i_cacheId
				) t2 
				OUTER apply(
					SELECT ISNULL(sumUseNumber,0) as perMonthTableUseNumber
					FROM aup_TjWaterSideByYearMonth 
					WHERE [Year] = @i_year
					AND [Month] = @v_preMonth
					AND cacheId = @i_cacheId
				) t3
				outer apply(
					SELECT s.name,waterCount from aup_water_side s
					WHERE s.cacheId = @i_cacheId
					
				) t4
			
END