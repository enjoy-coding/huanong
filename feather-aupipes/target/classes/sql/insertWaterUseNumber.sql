/**
* 按年份和时间插入用水分区的用量数据
* 用水分区表为aup_water_side
* 从房产信息:每一户的汇总量 = 每户水表的总量,每一户的总量=每户水表表号所对应的用量叠加
* 楼栋信息:每一个楼栋的汇总量 = 每栋楼下面户的汇总量，每一栋楼的总量 = 每栋楼表号对应的用量
* 片区信息:每一个片区的汇总量 = 每个片区下面楼栋的汇总量，每个片区的总量 = 每个片区下面有表号的楼栋对应用量的叠加量
* 区域信息:每个区域汇总量 = 每个区域下面的片区用量的汇总量,每个区域的总量 = 该区域下面片区的表对应读数求和
*/
ALTER PROCEDURE [dbo].[insertWaterUseNumber]
@i_year varchar(4),
@i_month varchar(2)
AS
BEGIN

		if ISNULL(@i_year, '') = ''
		BEGIN
			SET @i_year = DATENAME(YYYY,GETDATE())
		END
		IF ISNULL(@i_month, '') = ''
		BEGIN
			SET @i_month = DATEPART(MM,GETDATE())
		END
		
		IF @i_month <10 
		BEGIN 
				SET @i_month = '0'+@i_month
		END
		
		DECLARE @v_time VARCHAR(32) =@i_year +'-'+@i_month
		
		DELETE aup_TjWaterSideByYearMonth WHERE [year] = CONVERT(INT,@i_year) AND [month] = CONVERT(INT,@i_month)
		
		INSERT INTO aup_TjWaterSideByYearMonth(id,cacheId,cacheLevel,cacheName,year,month,tableUseNumber,sumUseNumber,sideId,createTime,shl)
		
		SELECT  CONVERT(bigint,CONVERT(VARCHAR(32),ss.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
					,ss.cacheId,ss.cacheLevel,ss.cacheName
					,@i_year,@i_month
					,ISNULL(h.tableUseNumber, 0) as tableUseNumber
					,ISNULL(k.sumUseNumber, 0) as sumUseNumber
					,ss.id
					,CONVERT(VARCHAR(32),GETDATE(),20)
					,CASE WHEN ISNULL(h.tableUseNumber, 0) = 0 THEN 0 ELSE ((ISNULL(h.tableUseNumber, 0)-ISNULL(k.sumUseNumber, 0))/ISNULL(h.tableUseNumber, 0)) *100 END as shl
		FROM aup_water_side ss 
		OUTER apply(
				SELECT p.id,p.name,SUM(ISNULL(r.thisUseNumber, 0)) as sumUseNumber
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
				JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
					LEFT JOIN aup_water_side b on b.tableId = m.sn --楼栋对应表号
					LEFT JOIN aup_water_side c on c.id = b.pid --片区
					LEFT JOIN  aup_water_side s  ON s.id = c.pid --区域
					LEFT JOIN aup_water_side p on p.id = s.pid
					WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
					AND p.[level] =1 AND p.id = ss.id
					GROUP BY p.id,p.name
			) k
			OUTER apply(
				SELECT f.id,f.name,SUM(ISNULL(r.thisUseNumber, 0)) as tableUseNumber
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
				JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
				LEFT JOIN aup_water_side c on c.tableId = m.sn
				LEFT JOIN aup_water_side s on s.id = c.pid
				LEFT JOIN aup_water_side f ON f.id = s.pid
				WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
				AND f.[level] =1 AND f.id = ss.id 
				GROUP BY f.id,f.name
		) h
		WHERE ss.[level] =1 
		UNION ALL 
		--区域叠加用水量
		--区域的叠加是把片区的叠加求和
		--区域的表对应值把片区的表对应读数求和
		SELECT CONVERT(bigint,CONVERT(VARCHAR(32),ss.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
					,ss.cacheId,ss.cacheLevel,ss.cacheName,@i_year,@i_month
					,ISNULL(h.tableUseNumber, 0) as tableUseNumber
					,ISNULL(k.sumUseNumber, 0) as sumUseNumber
					,ss.id,CONVERT(VARCHAR(32),GETDATE(),20)
					,CASE WHEN ISNULL(h.tableUseNumber, 0) = 0 THEN 0 ELSE ((ISNULL(h.tableUseNumber, 0)-ISNULL(k.sumUseNumber, 0))/ISNULL(h.tableUseNumber, 0)) *100 END as shl
		FROM aup_water_side ss 
		OUTER apply (
				SELECT s.id,s.name,SUM(ISNULL(r.thisUseNumber, 0)) as sumUseNumber
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
				JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
				LEFT JOIN aup_water_side b on b.tableId = m.sn --楼栋对应表号
				LEFT JOIN aup_water_side c on c.id = b.pid --片区
				LEFT JOIN  aup_water_side s  ON s.id = c.pid --区域
				WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
				AND s.[level] =2 AND s.id = ss.id
				GROUP BY s.id,s.name
		) k
		OUTER apply(
			SELECT s.id,s.name,SUM(ISNULL(r.thisUseNumber, 0)) as tableUseNumber
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
				JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
				LEFT JOIN aup_water_side c on c.tableId = m.sn --片区对应表号
				LEFT JOIN aup_water_side s on s.id = c.pid --区域
				WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
				AND s.[level] =2 AND s.id = ss.id
				GROUP BY s.id,s.name
		) h
		WHERE ss.level = 2
		--ORDER BY ss.id
		UNION ALL
		--片区
		--统计片区是楼栋有表号的叠加
		SELECT CONVERT(bigint,CONVERT(VARCHAR(32),ss.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
					,ss.cacheId,ss.cacheLevel,ss.cacheName,@i_year,@i_month
					,ISNULL(h.tableUseNumber, 0) as tableUseNumber
					,ISNULL(k.sumUseNumber, 0) as sumUseNumber
					,ss.id,CONVERT(VARCHAR(32),GETDATE(),20)
					,CASE WHEN ISNULL(h.tableUseNumber, 0) = 0 THEN 0 ELSE ((ISNULL(h.tableUseNumber, 0)-ISNULL(k.sumUseNumber, 0))/ISNULL(h.tableUseNumber, 0)) *100 END as shl
		FROM aup_water_side ss 
		OUTER apply (
				SELECT c.id,c.name,SUM(ISNULL(r.thisUseNumber, 0)) as sumUseNumber
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
				JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
				LEFT JOIN aup_water_side b on b.tableId = m.sn
				LEFT JOIN aup_water_side c on c.id = b.pid
				WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
				AND c.[level] =3 AND c.id = ss.id
				GROUP BY c.id,c.name
		) k
		OUTER apply(
			SELECT c.id,c.name,SUM(ISNULL(r.thisUseNumber, 0)) as tableUseNumber
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
				JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
				LEFT JOIN aup_water_side c on c.tableId = m.sn
				WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
				AND c.[level] =3 AND c.id = ss.id
				GROUP BY c.id,c.name
		) h
		WHERE ss.level = 3 
		--ORDER BY ss.id
		UNION ALL
		--楼栋
		--叠加统计楼栋下面的户用量叠加
		--表对应统计楼栋对应表号
		SELECT CONVERT(bigint,CONVERT(VARCHAR(32),ss.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
					,ss.cacheId,ss.cacheLevel,ss.cacheName,@i_year,@i_month
					,ISNULL(h.tableUseNumber, 0) as tableUseNumber
					,ISNULL(k.sumUseNumber, 0) as sumUseNumber
					,ss.id,CONVERT(VARCHAR(32),GETDATE(),20)
					,CASE WHEN ISNULL(h.tableUseNumber, 0) = 0 THEN 0 ELSE ((ISNULL(h.tableUseNumber, 0)-ISNULL(k.sumUseNumber, 0))/ISNULL(h.tableUseNumber, 0)) *100 END as shl
		FROM aup_water_side ss
		OUTER apply(
			SELECT c.id,c.name,SUM(ISNULL(r.thisUseNumber, 0)) as sumUseNumber
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
			JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
			LEFT JOIN aup_water_side h on h.tableId = m.sn
			LEFT JOIN aup_water_side c on c.id = h.pid
			WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
			AND c.[level] =4 AND c.id = ss.id 
			GROUP BY c.id,c.name
		) k
		OUTER apply(
			SELECT c.id,c.name,SUM(ISNULL(r.thisUseNumber, 0)) as tableUseNumber
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
			JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
			LEFT JOIN aup_water_side c on c.tableId = m.sn
			WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
			AND c.[level] =4 AND c.id = ss.id 
			GROUP BY c.id,c.name
		) h
		WHERE ss.level = 4
		UNION ALL		
		--房屋
		SELECT CONVERT(bigint,CONVERT(VARCHAR(32),MAX(ss.id))+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
					,ss.cacheId,ss.cacheLevel,ss.cacheName,@i_year,@i_month
					,SUM(ISNULL(h.tableUseNumber, 0)) as tableUseNumber
					,SUM(ISNULL(k.sumUseNumber, 0)) as sumUseNumber
					,ss.id,CONVERT(VARCHAR(32),GETDATE(),20)
					,CASE WHEN SUM(ISNULL(h.tableUseNumber, 0)) = 0 THEN 0 ELSE (SUM(ISNULL(h.tableUseNumber, 0))-SUM(ISNULL(k.sumUseNumber, 0))/SUM(ISNULL(h.tableUseNumber, 0))) *100 END as shl
		FROM aup_water_side ss
		OUTER apply(
			SELECT c.id,c.name,SUM(ISNULL(r.thisUseNumber, 0)) as sumUseNumber
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
			JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
			LEFT JOIN aup_water_side c on c.tableId = m.sn
			WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
			AND c.[level] =5 AND c.id = ss.id 
			GROUP BY c.id,c.name
		) k
		OUTER apply(
			SELECT c.id,c.name,SUM(ISNULL(r.thisUseNumber, 0)) as tableUseNumber
			FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] m
			JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_MeterRead] r on m.id = r.meter
			LEFT JOIN aup_water_side c on c.tableId = m.sn
			WHERE CONVERT(varchar(7),r.readTime,20) = @v_time AND m.meterType = 'WaterTable'
			AND c.[level] =5 AND c.id = ss.id 
			GROUP BY c.id,c.name
		) h
		WHERE ss.level = 5		
		GROUP BY ss.cacheId,ss.cacheLevel,ss.cacheName,ss.id
END