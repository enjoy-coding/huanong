/**
* 插入用电平衡
* 先统计楼栋的用量，将楼栋的汇总量和变压器，配电房，根节点设置为0 插入用电平衡表
* 再根据楼栋汇总叠加修改变压器用量
* 根据变压器汇总叠加修改配电房用量
* 根据配电房汇总叠加修改根节点用量
*/
ALTER PROCEDURE [dbo].[insetElectricityUseNumber]
(
@i_year varchar(4),
@i_month varchar(2)
)
AS
BEGIN
		DECLARE @v_month int
	if ISNULL(@i_year, '') = ''
		BEGIN
			SET @i_year = DATENAME(YYYY,GETDATE())
		END
		IF ISNULL(@i_month, '') = ''
		BEGIN
			SET @i_month = DATEPART(MM,GETDATE())
		END
		SET @v_month = @i_month
		IF @v_month <10
		BEGIN
				SET @i_month = '0'+CONVERT(VARCHAR(2),@v_month)
		END

		DECLARE @v_time VARCHAR(32) =@i_year +'-'+@i_month

		DECLARE @o_code int
		--SELECT @v_time,@i_year,@v_month
		EXEC updateElectricityUseNumber @i_year,@v_month,@v_time, @o_code OUTPUT
		--SELECT @o_code
		SET @o_code = 1
		IF  LEN(@o_code)>0
		BEGIN
						--修改变压器的楼栋汇总值
					  UPDATE y SET y.sumUseNumber = x.sumUseNumber
									,y.scaleSumUseNumber = x.scaleSumUseNumber
									,y.tableCount = x.tableCount
					 FROM aup_TjElectricityByYearMonth y
					 JOIN(
						 SELECT  e.id,e.cacheId,e.cacheName
											,sum(t.sumUseNumber)+SUM(t.tableUseNumber)  as sumUseNumber
											,SUM(t.scaleSumUseNumber) + sum(t.scaleTableUseNumber) as scaleSumUseNumber
											,MAX(o.tableCount) as tableCount
						 FROM aup_TjElectricityByYearMonth e
						 OUTER apply(
								SELECT o.cacheName,o.sumUseNumber,o.scaleSumUseNumber
												,o.tableUseNumber,o.scaleTableUseNumber
								FROM aup_TjElectricityByYearMonth o
								WHERE o.cacheId LIKE e.cacheId+'%'
								AND o.CacheLevel =4
								AND o.[year] = e.[year]
								AND o.[month] = e.[month]
						 ) t
						OUTER apply(
								SELECT SUM(tableCount) as tableCount
								FROM
								(
									SELECT COUNT(1) as tableCount,s.cacheId
									FROM aup_electricity_side s
									WHERE s.cacheId LIKE e.cacheId+'%' AND s.CacheLevel =4
									GROUP BY s.cacheId
								) y
						 ) o
						 WHERE e.cacheLevel  =3 AND e.[year] =@i_year AND e.[month] = @v_month --AND e.cacheId = 'roote-0001-29'
						 GROUP BY e.id,e.cacheId,e.cacheName
					 ) x on x.id = y.id
					 --修改配电房的汇总值
					 UPDATE y SET y.tableCount = x.tableCount
										,y.tableUseNumber = x.tableUseNumber
										,y.scaleTableUseNumber = x.scaleTableUseNumber
										,y.sumUseNumber = x.sumUseNumber
										,y.scaleSumUseNumber = x.scaleSumUseNumber
					  FROM aup_TjElectricityByYearMonth y
					 JOIN (
					 SELECT e.id,e.cacheId,e.cacheName
									,SUM(ISNULL(t.tableUseNumber, 0)) as tableUseNumber
									,SUM(ISNULL(t.sumUseNumber, 0)) as sumUseNumber
									,SUM(ISNULL(t.scaleSumUseNumber, 0)) as scaleSumUseNumber
									,SUM(ISNULL(t.scaleTableUseNumber, 0)) as scaleTableUseNumber
									,SUM(t.tableCount) as tableCount
					 FROM aup_TjElectricityByYearMonth e
					 OUTER apply(
							SELECT o.cacheName,o.sumUseNumber,o.scaleSumUseNumber
											,o.tableUseNumber,o.scaleTableUseNumber
											,o.tableCount
							FROM aup_TjElectricityByYearMonth o
							WHERE o.cacheId LIKE e.cacheId+'%'
							AND o.CacheLevel =3
							AND o.[year] = e.[year]
							AND o.[month] = e.[month]
					 ) t
					 WHERE e.cacheLevel  =2 AND e.[year] =@i_year AND e.[month] = @v_month --AND e.cacheId = 'roote-0001'
					 GROUP BY e.id,e.cacheId,e.cacheName
					 ) x on x.id = y.id

					 --修改根节点的总值
					 UPDATE y SET y.tableCount = x.tableCount
										,y.tableUseNumber = x.tableUseNumber
										,y.scaleTableUseNumber = x.scaleTableUseNumber
										,y.sumUseNumber = x.sumUseNumber
										,y.scaleSumUseNumber = x.scaleSumUseNumber

					 FROM aup_TjElectricityByYearMonth y
					 JOIN(
						 SELECT e.id,e.cacheId,e.cacheName
									,SUM(ISNULL(t.tableUseNumber, 0)) as tableUseNumber
									,SUM(ISNULL(t.sumUseNumber, 0)) as sumUseNumber
									,SUM(ISNULL(t.scaleSumUseNumber, 0)) as scaleSumUseNumber
									,SUM(ISNULL(t.scaleTableUseNumber, 0)) as scaleTableUseNumber
									,SUM(t.tableCount) as tableCount
						 FROM aup_TjElectricityByYearMonth e
						 OUTER apply(
								SELECT o.cacheName,o.sumUseNumber,o.scaleSumUseNumber
												,o.tableUseNumber,o.scaleTableUseNumber
												,o.tableCount
								FROM aup_TjElectricityByYearMonth o
								WHERE o.cacheId LIKE e.cacheId+'%'
								AND o.CacheLevel =2
								AND o.[year] = e.[year]
								AND o.[month] = e.[month]
						 ) t
						 WHERE e.cacheLevel  =1 AND e.[year] =@i_year AND e.[month] = @v_month --AND e.cacheId = 'roote-0001-29'
						 GROUP BY e.id,e.cacheId,e.cacheName
					 ) x on x.id = y.id

                        --修改损耗率
                        UPDATE j SET shl = y.shl
                                     --SELECT j.cacheName,y.shl
                        FROM aup_TjElectricityByYearMonth j
                                 JOIN (
                            SELECT e.id,CAST(round(CASE WHEN sumUseNumber = 0 THEN 0 ELSE ((tableUseNumber-sumUseNumber)/sumUseNumber)*100 END,2) as DECIMAL(16,2)) shl
                            FROM aup_TjElectricityByYearMonth e
                            WHERE  e.[year] =@i_year AND e.[month] = @v_month
                        ) y on y.id = j .id

        END



END