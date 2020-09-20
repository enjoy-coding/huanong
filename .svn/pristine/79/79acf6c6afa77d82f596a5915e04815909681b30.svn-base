/**
* 定时修改总地区，区域，楼栋的表信息
*  水电表个数，在线数，离线数
*/
ALTER PROCEDURE [dbo].[updateMeterInfo]
AS
BEGIN
			DELETE  aup_TjMeterBaseInfo WHERE 1=1
			INSERT INTO aup_TjMeterBaseInfo(id,meterCount,eleCount,eleLineCount,eleOnlineCount,eleZyCount,waterCount,waterLineCount,waterOnlineCount,waterZyCount)
			SELECT  g.id
							,t.meterCount
							,ISNULL(e.eleLineCount+e.eleOnlineCount+e.eleZyCount,0) as eleCount
							,ISNULL(e.eleLineCount, 0) as eleLineCount,ISNULL(e.eleOnlineCount, 0) as eleOnlineCount, ISNULL(E.eleZyCount, 0) AS eleZyCount
							,ISNULL(w.waterLineCount+w.waterOnlineCount+w.waterZyCount, 0) as waterCount
							,ISNULL(w.waterLineCount, 0),ISNULL(w.waterOnlineCount, 0),ISNULL(w.waterZyCount, 0) as waterZyCount
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g
			OUTER apply(
					SELECT COUNT(1) as meterCount 
					FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
			)t
			OUTER apply(
					SELECT [0] as waterLineCount,[1] as waterOnlineCount,[2] as waterZyCount 
                    FROM (
					    select m.id,m.meterStatus
						FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
						WHERE m.meterType = 'WaterTable' 
					    ) as c PIVOT(count(c.id) FOR [meterStatus] IN([0],[1],[2])) AS T
					
				    )w
			OUTER apply(
					SELECT [0] as eleLineCount,[1] as eleOnlineCount,[2] as eleZyCount 
					FROM (
						select m.id,m.meterStatus
						FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
						WHERE m.meterType = 'Ammeter' 
						) as c PIVOT(count(c.id) FOR [meterStatus] IN([0],[1],[2])) AS T		
					)e
			WHERE g.id ='AreaLine0000'
			UNION ALL
			SELECT g.id,t.meterCount
					,ISNULL(e.eleLineCount+e.eleOnlineCount+e.eleZyCount,0) as eleCount
					,ISNULL(e.eleLineCount, 0) as eleLineCount,ISNULL(e.eleOnlineCount, 0) as eleOnlineCount, ISNULL(E.eleZyCount, 0) AS eleZyCount
					,ISNULL(w.waterLineCount+w.waterOnlineCount+w.waterZyCount, 0) as waterCount
					,ISNULL(w.waterLineCount, 0),ISNULL(w.waterOnlineCount, 0),ISNULL(w.waterZyCount, 0) as waterZyCount
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g
			OUTER apply(
                    SELECT COUNT(1) as meterCount 
                    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
                    JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b on b.NO = m.building
                    WHERE  b.area = g.id
				    )t
			OUTER apply(
					SELECT [0] as waterLineCount,[1] as waterOnlineCount,[2] as waterZyCount 
					FROM ( 
						SELECT m.id,m.meterStatus
						FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
						JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b on b.NO = m.building
						WHERE  b.area = g.id
					    AND m.meterType = 'WaterTable' 
						) as c PIVOT(count(c.id) FOR [meterStatus] IN([0],[1],[2])) AS T
				    )w
			OUTER apply(
					        SELECT [0] as eleLineCount,[1] as eleOnlineCount,[2] as eleZyCount 
							FROM ( 
									SELECT m.id,m.meterStatus
									FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
									JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b on b.NO = m.building
									WHERE  b.area = g.id
									AND m.meterType = 'Ammeter' 
							) as c PIVOT(count(c.id) FOR [meterStatus] IN([0],[1],[2])) AS T
				)e
			WHERE g.parent = 'AreaLine0000'
			UNION ALL
			SELECT b.id
					,t.meterCount
					,ISNULL(e.eleLineCount+e.eleOnlineCount+e.eleZyCount,0) as eleCount
					,ISNULL(e.eleLineCount, 0) as eleLineCount,ISNULL(e.eleOnlineCount, 0) as eleOnlineCount, ISNULL(E.eleZyCount, 0) AS eleZyCount
					,ISNULL(w.waterLineCount+w.waterOnlineCount+w.waterZyCount, 0) as waterCount
					,ISNULL(w.waterLineCount, 0),ISNULL(w.waterOnlineCount, 0),ISNULL(w.waterZyCount, 0) as waterZyCount
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b
			JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g on b.area = g.id
			AND g.type ='AreaLine'
			OUTER apply(
					SELECT COUNT(1) as meterCount 
					FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
					WHERE  m.building = b.no
					)t 
			OUTER apply(
					SELECT [0] as waterLineCount,[1] as waterOnlineCount,[2] as waterZyCount 
					FROM ( 
                        select m.id,m.meterStatus
						FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
						WHERE  m.building = b.no
						AND m.meterType = 'WaterTable' 
						) as c PIVOT(count(c.id) FOR [meterStatus] IN([0],[1],[2])) AS T
					) w
			OUTER apply(
					SELECT [0] as eleLineCount,[1] as eleOnlineCount,[2] as eleZyCount 
					FROM (
						select m.id,m.meterStatus
						FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
						WHERE  m.building = b.no
						AND m.meterType = 'Ammeter' 
						) as c PIVOT(count(c.id) FOR [meterStatus] IN([0],[1],[2])) AS T
					) e
			WHERE g.parent ='AreaLine0000'
END