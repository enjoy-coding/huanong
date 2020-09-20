/**
*   插入用电平衡数据
*/
ALTER PROCEDURE [dbo].[updateElectricityUseNumber]
@i_year varchar(4),
@i_month varchar(2),
@i_time VARCHAR(7),
@o_code VARCHAR(500) OUTPUT
AS
BEGIN
		DECLARE @msg VARCHAR(500)
		DELETE aup_TjElectricityByYearMonth WHERE [year] = CONVERT(INT,@i_year) AND [month] = CONVERT(INT,@i_month)

		begin transaction
		 if @@error<>0
         begin
            rollback transaction
           return
       end
			--计算id为30的变压器下面的楼栋用量，和变压器用量
			INSERT INTO aup_TjElectricityByYearMonth(id,cacheId,cacheName,cacheLevel,[year],[month],tableUseNumber,scaleTableUseNumber,sumUseNumber,scaleSumUseNumber,tableCount,createTime,shl,scaleShl)
			--添加总地区
			SELECT CONVERT(bigint,CONVERT(VARCHAR(32),a.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
							,a.cacheId
							,a.cacheName
							,a.cacheLevel
							,@i_year
							,@i_month
							,0 as tableUseNumber
							,0 as scaleTableUseNumber
							,0 as sumTableUseNumber
							,0 as sacleSumUseNumber
							,0 as tableCount
							,CONVERT(VARCHAR(32),GETDATE(),20) as createTime
							,0 as shl
						,0 as scaleShl
			FROM aup_electricity_side a
			WHERE a.cacheLevel = 1
			UNION ALL
			SELECT CONVERT(bigint,CONVERT(VARCHAR(32),a.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
							,a.cacheId
							,a.cacheName
							,a.cacheLevel
							,@i_year
							,@i_month
							,0 as tableUseNumber
							,0 as scaleTableUseNumber
							,0 as sumTableUseNumber
							,0 as sacleSumUseNumber
							,0 as tableCount
							,CONVERT(VARCHAR(32),GETDATE(),20) as createTime
							,0 as shl
							,0 as scaleShl
			FROM aup_electricity_side a
			WHERE a.cacheLevel = 2
			UNION ALL
			SELECT CONVERT(bigint,CONVERT(VARCHAR(32),c.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
						,c.cacheId
						,c.cacheName
						,c.cacheLevel
						,@i_year
						,@i_month
						,ISNULL(ct.useNumber , 0)as tableUseNumber
						,ISNULL(CASE WHEN c.sfjsbb = 1 AND ct.useNumber is not null THEN ct.useNumber*c.bb ELSE 0 END, 0) as  scaleTableUseNumber
						,0 as sumUseNumber
						,0 as scaleSumUseNumber
						,0 as tableCount
						,CONVERT(VARCHAR(32),GETDATE(),20) as createTime
						,0 as shl
						,0 as scaleShl
			FROM aup_electricity_side c
			OUTER apply(
					SELECT SUM(r.thisUseNumber) as useNumber
					FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
					JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
					WHERE m.sn = c.tableId AND CONVERT(VARCHAR(7),r.readTime,20) = 	@i_time
			) ct
			WHERE c.cacheLevel =3
			UNION ALL
			SELECT CONVERT(bigint,CONVERT(VARCHAR(32),w.id)+convert(varchar(4),@i_year) + convert(varchar(2),@i_month)) as id
							,w.cacheId
							,w.cacheName
							,w.cacheLevel
							,@i_year
							,@i_month
							,w.tableUseNumber
							,w.scaleTableUseNumber
							,w.sumUseNumber
							,w.scacleSumUseNumber
							,w.tableCount
							,CONVERT(VARCHAR(32),GETDATE(),20) as createTime
							,CASE WHEN w.sumUseNumber is not null AND w.sumUseNumber <> 0 THEN ((w.tableUseNumber - w.sumUseNumber)/w.sumUseNumber)*100 ELSE 0 END shl
							,CASE WHEN w.scacleSumUseNumber is not null AND w.scacleSumUseNumber <> 0 THEN ((w.scaleTableUseNumber - w.scacleSumUseNumber)/w.scacleSumUseNumber)*100 ELSE 0 END scaleShl
			FROM (
				SELECT MIN(k.id) as id
								,k.cacheId
								,k.cacheName
								,k.cacheLevel
								,SUM(k.useNumber) as tableUseNumber
								,SUM(k.scaleUseNumber) as scaleTableUseNumber
								,SUM(k.houseUseNumber) as sumUseNumber
								,SUM(k.scacleHouserUseNumber) as scacleSumUseNumber
								,MAX(k.tableCount) as tableCount
					FROM (
					SELECT a.id as id
								,a.cacheId
								,a.cacheName
								,a.cacheLevel
								,ISNULL(t.useNumber, 0) as useNumber
								,ISNULL(CASE WHEN a.sfjsbb = 1 AND t.useNumber is not null THEN t.useNumber*a.bb ELSE t.useNumber END, 0) scaleUseNumber
								,ISNULL(t1.houseUseNumber, 0) as houseUseNumber
								,ISNULL(CASE WHEN a.sfjsbb = 1 AND t1.houseUseNumber is not null THEN t1.houseUseNumber*a.bb ELSE t1.houseUseNumber END,0) scacleHouserUseNumber
								,a.tableCount
					FROM aup_electricity_side a
					outer apply(
							SELECT SUM(r.thisUseNumber) as useNumber
							FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
							JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
							WHERE a.tableId = m.sn AND CONVERT(VARCHAR(7),r.readTime,20) = @i_time
						) t
						OUTER apply(
							SELECT SUM(thisUseNumber) houseUseNumber
							FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meter] m
							JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_meterRead] r ON r.meter = m.id
							WHERE a.sfjszh = 1 and m.building = a.typeNo AND CONVERT(VARCHAR(7),r.readTime,20) = @i_time
						)t1
					WHERE a.cacheLevel = 4
				) k
				GROUP BY k.cacheId,k.cacheName,k.cacheLevel
			)w

			if @@error<>0
      begin
            rollback transaction
           return
       end
			commit transaction
			SELECT @o_code = @@error
END
