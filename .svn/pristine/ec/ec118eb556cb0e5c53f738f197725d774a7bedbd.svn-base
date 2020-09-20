/**
* 定时修改房屋表信息（表止码，表用量,昨日用量,表状态，读表时间）
*/
ALTER PROCEDURE [dbo].[updateHouseMeterInfo]
AS
BEGIN
        UPDATE hm SET hm.thisUseNumber = mm.thisUseNumber
					    ,hm.lastReadTime = ISNULL(mm.lastReadTime, '')
						,hm.meterStatus = mm.meterStatus
						,hm.endNumber = mm.endNumber
						,hm.valveControlStatus = mm.valveControlStatus
						,hm.yesterdayNumber = mm.yesterDayUseNumber
		FROM aup_TjHouseMeterInfo hm(NOLOCK)
        JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] h on h.id = hm.houseId
		OUTER apply(
				SELECT r.thisUseNumber as thisUseNumber
						, CONVERT(VARCHAR(32),m.lastReadTime,21) as lastReadTime
						, m.endNumber as endNumber
						, m.valveControlStatus
						, m.meterStatus 
						, y.yesterDayUseNumber
                FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
                JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
				OUTER apply(
						SELECT rr.thisUseNumber as yesterDayUseNumber
                        FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] rr
                        LEFT JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m1 ON rr.meter = m1.id
                        WHERE DateDiff(dd,rr.readTime,getdate())= 1 AND m1.sn = m.sn
					    ) y
                WHERE m.lastReadTime = r.readTime
                AND m.house = h.id AND m.sn = hm.meterNo
                ) mm

END