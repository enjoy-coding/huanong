/**
* 能耗监管-用水分区到房屋层级显示的详情信息
*/
ALTER PROCEDURE [dbo].[nyjk_queryWaterUseMonthRootHouse]
(
	@i_cacheId NVARCHAR(255),
	@i_year varchar(4)
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
							,t4.accountName
							,t4.openDate
							,t4.categoryName
							,t4.phoneNumber
							,ISNULL(t4.houseTypeName, '未知') as houseTypeName
							,t4.Usearea
							,t1.yearUseNumber
							,ISNULL(t2.monthTableUseNumber, 0) as monthTableUseNumber
							,ISNULL(t3.perMonthTableUseNumber,0) as perMonthTableUseNumber
							,t4.waterCount
							,ISNULL(CONVERT(VARCHAR(32),t5.tableUseNumber), '当前时间未获取到数据') as tableUseNumber
							,t5.tableId
				FROM
				(	SELECT SUM(tableUseNumber) as yearUseNumber 
					FROM aup_TjWaterSideByYearMonth 
					WHERE [Year] = @i_year
					AND cacheId = @i_cacheId
				) t1 
				OUTER apply(
					SELECT SUM(ISNULL(tableUseNumber,0)) as monthTableUseNumber
					FROM aup_TjWaterSideByYearMonth 
					WHERE [Year] = @i_year
					AND [Month] = @v_month
					AND cacheId = @i_cacheId
				) t2 
				OUTER apply(
					SELECT SUM(ISNULL(tableUseNumber,0)) as perMonthTableUseNumber
					FROM aup_TjWaterSideByYearMonth 
					WHERE [Year] = @i_year
					AND [Month] = @v_preMonth
					AND cacheId = @i_cacheId
				) t3
				outer apply(
					SELECT s.name,s.typeNo
								,SUM(s.waterCount) as waterCount
								,a.openDate
								,c.name AS categoryName
								,MAX(y.accountName) AS accountName
								,MAX(y.phoneNumber) AS phoneNumber
								,h1.houseTypeName,hh.buildArea as Usearea
					FROM aup_water_side s 
					JOIN aup_house h1 on h1.NO = s.typeNo
					JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] hh on s.typeId = hh.id
					JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Account] a on s.typeId = a.house
					JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_AccountCategory] c on c.id = a.accountCategory
					OUTER apply(
						SELECT TOP 1 CASE WHEN u.name =  u1.name THEN u.name ELSE  u1.name + '(' +  u.name + ')' END AS accountName
									,u.mobileNumber AS phoneNumber
						FROM [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].NT_AgriculturalUniversity_AccountUser AS au 
						JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].NT_Platform_SysUser AS u ON au.sysUser = u.id 
						JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].NT_Platform_SysUser AS u1 ON a.sysUser = u1.id 
						WHERE au.account = a.id 
					) y
					WHERE s.cacheId =@i_cacheId
					GROUP BY  s.name,s.typeNo,a.openDate,c.name,h1.houseTypeName,hh.buildArea
				) t4
				OUTER apply(
						SELECT  tableUseNumber = (STUFF((SELECT ',' +  ISNULL(CONVERT(VARCHAR(32),cast(round(s1.sumUseNumber,2) as DECIMAL(18,2))), '无数据统计') 
												FROM    aup_TjWaterSideByYearMonth s1 
												WHERE s1.[year] = @i_year AND s1.[month] = @v_month
												AND s1.cacheId = s.cacheId
                        FOR
                          XML PATH('')
                        ) , 1, 1, '') )
										,tableId = ( STUFF(( SELECT    ',' + t.tableId
                          FROM     aup_water_side t
                          WHERE     t.cacheId = s.cacheId
                        FOR
                          XML PATH('')
                        ), 1, 1, '') )
					FROM  aup_water_side s
					WHERE s.cacheId = @i_cacheId
					GROUP BY s.cacheId
				) t5
				
END