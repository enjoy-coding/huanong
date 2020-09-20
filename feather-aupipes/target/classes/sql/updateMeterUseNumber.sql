/**
* 定时修改总地区，区域，楼栋的表信息
*  水电表月用量，日用量和昨日用量
*/
ALTER PROCEDURE [dbo].[updateMeterUseNumber]
AS
BEGIN

    DELETE aup_TjMeterUseNumberInfo WHERE 1=1
    INSERT INTO  aup_TjMeterUseNumberInfo(id,waterMonthUseNumber,eleMonthUseNumber,waterDayUseNumber,eleDayUseNumber,lastReadTime,yesterdayWaterNumber,yesterdayEleNumber)
    SELECT gg.id
			, ISNULL(cast(round(m1.waterMonthUseNumber,2) as DECIMAL(16,2)), 0) as waterMonthUseNumber
			, ISNULL(cast(round(m2.eleMonthUseNumber,2) as DECIMAL(16,2)), 0) as eleMonthUseNumber
			, ISNULL(cast(round(m3.waterDayUseNumber,2) as DECIMAL(16,2)), 0) AS waterDayUseNumber
			, ISNULL(CAST(round(m4.eleDayUseNumber,2) as DECIMAL(16,2)),0) AS eleDayUseNumber
			, ISNULL(CONVERT(VARCHAR(32),m1.lastReadTime,20), '') as lastReadTime
			, ISNULL(CAST(round(m5.waterYesterDayUseNumber,2) as DECIMAL(16,2)), 0) as waterYesterDayUseNumber
			, ISNULL(CAST(round(m6.eleYesterDayUseNumber,2) as DECIMAL(16,2)),0) as eleYesterDayUseNumber
    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] gg
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterMonthUseNumber, MAX(m.lastReadTime) as lastReadTime
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE CONVERT(VARCHAR(7),r.readTime,20) =  CONVERT(VARCHAR(7),GETDATE(),20) AND m.meterType ='WaterTable' 
            ) m1
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleMonthUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE CONVERT(VARCHAR(7),r.readTime,20) =  CONVERT(VARCHAR(7),GETDATE(),20)
            AND m.meterType ='Ammeter'
		) m2
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE CONVERT(VARCHAR(10),r.readTime,20) =   CONVERT(VARCHAR(10),GETDATE(),20)
            AND m.meterType ='WaterTable'
		) m3
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE CONVERT(VARCHAR(10),r.readTime,20) =   CONVERT(VARCHAR(10),GETDATE(),20)
            AND m.meterType ='Ammeter'
		) m4
	OUTER apply(
		    SELECT SUM(r.thisUseNumber) as waterYesterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE DateDiff(dd,r.readTime,getdate())= 1
            AND m.meterType ='WaterTable'
		) m5
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleYesterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE DateDiff(dd,r.readTime,getdate())= 1
            AND m.meterType ='Ammeter'
		) m6
    WHERE gg.id ='AreaLine0000'
    UNION ALL
    SELECT gg.id
			, ISNULL(cast(round(m1.waterMonthUseNumber,2) as DECIMAL(16,2)), 0) as waterMonthUseNumber
			, ISNULL(cast(round(m2.eleMonthUseNumber,2) as DECIMAL(16,2)), 0) as eleMonthUseNumber
			, ISNULL(cast(round(m3.waterDayUseNumber,2) as DECIMAL(16,2)), 0) AS waterDayUseNumber
			, ISNULL(CAST(round(m4.eleDayUseNumber,2) as DECIMAL(16,2)),0) AS eleDayUseNumber
		    , ISNULL(CONVERT(VARCHAR(32),m1.lastReadTime,20), '') as lastReadTime
			, ISNULL(CAST(round(m5.waterYesterDayUseNumber,2) as DECIMAL(16,2)), 0) as waterYesterDayUseNumber
			, ISNULL(CAST(round(m6.eleYesterDayUseNumber,2) as DECIMAL(16,2)),0) as eleYesterDayUseNumber
    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] gg
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterMonthUseNumber, MAX(m.lastReadTime) as lastReadTime
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_building] b ON m.building = b.no
            WHERE  b.area = gg.id
            AND CONVERT(VARCHAR(7),r.readTime,20) =  CONVERT(VARCHAR(7),GETDATE(),20)
            AND m.meterType ='WaterTable'
			) m1
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleMonthUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_building] b ON m.building = b.no
            WHERE  b.area = gg.id
            AND CONVERT(VARCHAR(7),r.readTime,20) =  CONVERT(VARCHAR(7),GETDATE(),20)
            AND m.meterType ='Ammeter'
		    ) m2
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_building] b ON m.building = b.no
            WHERE  b.area = gg.id
            AND CONVERT(VARCHAR(10),r.readTime,20) =   CONVERT(VARCHAR(10),GETDATE(),20)
            AND m.meterType ='WaterTable'
			) m3
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_building] b ON m.building = b.no
            WHERE  b.area = gg.id
            AND CONVERT(VARCHAR(10),r.readTime,20) =   CONVERT(VARCHAR(10),GETDATE(),20)
            AND m.meterType ='WaterTable'
			) m4
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterYesterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_building] b ON m.building = b.no
            WHERE  b.area = gg.id
            AND DateDiff(dd,r.readTime,getdate())= 1
            AND m.meterType ='WaterTable'
			) m5
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleYesterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_building] b ON m.building = b.no
            WHERE  b.area = gg.id
            AND DateDiff(dd,r.readTime,getdate())= 1
            AND m.meterType ='Ammeter'
			) m6
    WHERE gg.parent ='AreaLine0000'
    UNION ALL
    SELECT b.id	
			, ISNULL(cast(round(m1.waterMonthUseNumber,2) as DECIMAL(16,2)), 0) as waterMonthUseNumber
			, ISNULL(cast(round(m2.eleMonthUseNumber,2) as DECIMAL(16,2)), 0) as eleMonthUseNumber
			, ISNULL(cast(round(m3.waterDayUseNumber,2) as DECIMAL(16,2)), 0) AS waterDayUseNumber
			, ISNULL(CAST(round(m4.eleDayUseNumber,2) as DECIMAL(16,2)),0) AS eleDayUseNumber
		    , ISNULL(CONVERT(VARCHAR(32),m1.lastReadTime,20), '') as lastReadTime
			, ISNULL(CAST(round(m5.waterYesterDayUseNumber,2) as DECIMAL(16,2)), 0) as waterYesterDayUseNumber
			, ISNULL(CAST(round(m6.eleYesterDayUseNumber,2) as DECIMAL(16,2)),0) as eleYesterDayUseNumber
    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_building] b
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterMonthUseNumber, MAX(m.lastReadTime) as lastReadTime
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE  m.building = b.no
            AND CONVERT(VARCHAR(7),r.readTime,20) =  CONVERT(VARCHAR(7),GETDATE(),20)
            AND m.meterType ='WaterTable'
			) m1
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleMonthUseNumber, MAX(m.lastReadTime) as lastReadTime
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE  m.building = b.no
            AND CONVERT(VARCHAR(7),r.readTime,20) =  CONVERT(VARCHAR(7),GETDATE(),20)
            AND m.meterType ='Ammeter'
			) m2
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE  m.building = b.no
            AND CONVERT(VARCHAR(10),r.readTime,20) =   CONVERT(VARCHAR(10),GETDATE(),20)
            AND m.meterType ='WaterTable'
			) m3
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE  m.building = b.no
            AND CONVERT(VARCHAR(10),r.readTime,20) =   CONVERT(VARCHAR(10),GETDATE(),20)
            AND m.meterType ='Ammeter'
			) m4
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as waterYesterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE  m.building = b.no
            AND DateDiff(dd,r.readTime,getdate())= 1
            AND m.meterType ='WaterTable'
			) m5
	OUTER apply(
			SELECT SUM(r.thisUseNumber) as eleYesterDayUseNumber
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
            WHERE  m.building = b.no
            AND DateDiff(dd,r.readTime,getdate())= 1
            AND m.meterType ='Ammeter'
		    ) m6


END