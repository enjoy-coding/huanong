/**
* 首页泵房的最近三日的供水量
*/
ALTER PROCEDURE [dbo].[index_queryPumpDayUseWaterNumber]
(
	@i_pumpId int 
)
AS
BEGIN
			if @i_pumpId = 2
			BEGIN 
				
						select TOP 1 ISNULL(cast(round(t.thisUseNumber,2) as DECIMAL(18,2)), 0) as thisUseNumber 
							,ISNULL(cast(round(t1.thisUseNumber1,2) as DECIMAL(18,2)), 0) as thisUseNumber1 
							,ISNULL(cast(round(t2.thisUseNumber2,2) as DECIMAL(18,2)), 0) as thisUseNumber2 
					FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
					OUTER apply(
									SELECT r.readTime,r.thisUseNumber
									FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r 
									where r.meter = m.id
									--统计最近三天
									AND datediff(day,r.readTime,getdate())= 0
									--用水量
							) t
OUTER apply(
									SELECT r.readTime,r.thisUseNumber as thisUseNumber1
									FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r 
									where r.meter = m.id
									--统计最近三天
									AND datediff(day,r.readTime,getdate())= 1
									--用水量
							) t1
OUTER apply(
									SELECT r.readTime,r.thisUseNumber as thisUseNumber2
									FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r 
									where r.meter = m.id
									--统计最近三天
									AND datediff(day,r.readTime,getdate())= 2
									--用水量
							) t2
							WHERE m.meterType = 'WaterTable'
							AND m.sn IN ('190850013497')
							
				END
				ELSE
				BEGIN
						select cast(ROUND(SUM(ISNULL(t.thisUseNumber, 0)),2) as DECIMAL(16,2)) as thisUseNumber 
									,cast(ROUND(SUM(ISNULL(t1.thisUseNumber1, 0)),2) as DECIMAL(16,2)) as thisUseNumber1
									,cast(ROUND(SUM(ISNULL(t2.thisUseNumber2, 0)),2) as DECIMAL(16,2)) as thisUseNumber2 
							FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
							OUTER apply(
									SELECT r.readTime,r.thisUseNumber
									FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r 
									where r.meter = m.id
									--统计最近三天
									AND datediff(day,r.readTime,getdate())= 0
									--用水量
							) t
OUTER apply(
									SELECT r.readTime,r.thisUseNumber as thisUseNumber1
									FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r 
									where r.meter = m.id
									--统计最近三天
									AND datediff(day,r.readTime,getdate())= 1
									--用水量
							) t1
OUTER apply(
									SELECT r.readTime,r.thisUseNumber as thisUseNumber2
									FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r 
									where r.meter = m.id
									--统计最近三天
									AND datediff(day,r.readTime,getdate())= 2
									--用水量
							) t2
							WHERE m.meterType = 'WaterTable'
							AND m.sn IN ('5','6')
							
				END
END