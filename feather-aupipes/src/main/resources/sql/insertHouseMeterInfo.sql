/**
* 定时插入房屋单户的水电表数据，会存在一户多表数据信息
* 按照房屋表号插入
*/
ALTER PROCEDURE [dbo].[insertHouseMeterInfo]
AS
BEGIN
				DELETE aup_TjHouseMeterInfo where 1 = 1
				INSERT INTO [dbo].[aup_TjHouseMeterInfo](
							id,[houseId], [houseNo], [HouseOwnerName]
							, [houseAddress], [phoneNumber], [houseType]
							, [catergoryId], [categoryName], [buildNo]
							, [buildName], [areaNo], [areaName]
							, [lastReadTime], [endNumber]
							, [thisUseNumber], [valveControlStatus]
							, [meterStatus], [meterType]
							, [houseUseArea], [meterName],meterNo,yesterdayNumber) 
				SELECT  RIGHT(100000000 + CONVERT(bigint, ABS(CHECKSUM(NEWID()))), 100) as id 
						,h.id as houseId
						,h.no as houseNo
						,t.HouseOwnerName
						,h.name as houseAddress
						,t.phoneNumber
						,ISNULL(p.ParamValue, '无数据') as houseType
						,c.id,c.name
						,b.no
						,b.name
						,g.no
						,g.name
						,mm.lastReadTime
						,mm.endNumber
						,mm.thisUseNumber
						,mm.valveControlStatus
						,mm.meterStatus
						,mm.meterType
						,h.buildArea
						,mm.meterName
						,mm.sn
						,mm.yesterDayUseNumber
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] h
			LEFT JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b on h.building = b.no
			LEFT JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g on g.id = b.area
			left JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].NT_AgriculturalUniversity_Account AS a ON a.house = h.id
			OUTER apply(
					SELECT TOP 1 ISNULL(CASE WHEN u.name = u1.name THEN u.name ELSE u1.name + '(' + u.name + ')' END, '无数据') AS HouseOwnerName
								,u.mobileNumber as phoneNumber
					FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].NT_AgriculturalUniversity_AccountUser AS au  
					left JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].NT_Platform_SysUser AS u ON au.sysUser = u.id
					left JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].NT_Platform_SysUser AS u1 ON a.sysUser = u1.id
					WHERE au.account = a.id
				) t
			LEFT JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].NT_AgriculturalUniversity_AccountCategory c on a.accountCategory = c.id
			LEFT join [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].Pm_Parameter p on p.ParamId = REPLACE(h.houseType,'houseType','' )
			OUTER apply (
					SELECT r.thisUseNumber as thisUseNumber 
									,CONVERT(VARCHAR(32),m.lastReadTime,21) as lastReadTime
									,m.endNumber as endNumber
									,m.valveControlStatus
									,m.meterStatus 
									,dic.description as meterType
									,dic.value as meterName
									,m.sn
									,y.yesterDayUseNumber
					FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m 
					JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
					left join [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on m.type=dic.id
					OUTER apply(
							SELECT rr.thisUseNumber as yesterDayUseNumber
							FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] rr 
							LEFT JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m1 ON rr.meter = m1.id
							WHERE DateDiff(dd,rr.readTime,getdate())= 1 
							AND m1.sn = m.sn
					) y
					WHERE m.lastReadTime = r.readTime
					AND m.house = h.id
			)mm  
								
END