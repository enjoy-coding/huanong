/**
* 运行监控水电用户树列表点击右侧详情
* typeId 树节点id
* queryType 查询的地区类型
*/
ALTER PROCEDURE [dbo].[yxjk_queryMeterInfo]
(
	@i_typeid varchar(64),
	@i_queryType VARCHAR(32)
)
AS
BEGIN
			DECLARE @v_nullMsg VARCHAR(64) ='未获取到数据';
            --如果是房屋类型
			IF @i_queryType = 'house'
			BEGIN
					SELECT HouseOwnerName,HouseOwnerName,houseAddress,houseUseArea,houseType,categoryName
								,ISNULL(meterType, @v_nullMsg) as meterType
								,ISNULL(meterName, @v_nullMsg) as meterName
								,ISNULL(meterNo, @v_nullMsg) as meterNo
								,m.phoneNumber
								,CASE WHEN meterStatus = 0 THEN '离线' WHEN meterStatus = 1 THEN '在线' ELSE '质疑' END as meterStatus
								,ISNULL(CONVERT(VARCHAR(32),endNumber), @v_nullMsg) as endNumber
								,ISNULL(CONVERT(VARCHAR(32),cast(round(m.thisUseNumber,2) as DECIMAL(18,2))), @v_nullMsg) as thisUseNumber
								,CASE WHEN CONVERT(VARCHAR(10),lastReadTime,20) = '1900-01-01' THEN @v_nullMsg WHEN m.lastReadTime  is NULL
						THEN @v_nullMsg ELSE CONVERT(VARCHAR(20),lastReadTime,20)  END lastReadTime
								,ISNULL(CONVERT(VARCHAR(32),cast(round(m.yesterdayNumber,2) as DECIMAL(18,2))), @v_nullMsg) as yesterDayUseNumber
					FROM aup_TjHouseMeterInfo m WHERE m.houseId = @i_typeid
					ORDER BY meterType
			END 
            --如果是楼栋类型
			ELSE IF @i_queryType = 'building'
			BEGIN
				SELECT b.name
						,m.meterCount,m.eleCount,m.eleOnlineCount,m.eleLineCount,m.eleZyCount,m.waterCount,m.waterOnlineCount
						,m.waterLineCount,m.waterZyCount
						,u.waterMonthUseNumber,u.waterDayUseNumber
						,u.eleMonthUseNumber,u.eleDayUseNumber
						,u.yesterdayEleNumber
						,u.yesterdayWaterNumber
						,CASE WHEN CONVERT(VARCHAR(10),lastReadTime,20) = '1900-01-01'
						THEN @v_nullMsg ELSE CONVERT(VARCHAR(20),lastReadTime,20)  END lastReadTime
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b
				LEFT JOIN aup_TjMeterBaseInfo m on m.id = b.no
				LEFT JOIN aup_TjMeterUseNumberInfo u on u.id = b.no
				WHERE b.id = @i_typeid
			END
			ELSE 
			BEGIN
				SELECT g.name
						,m.meterCount,m.eleCount,m.eleOnlineCount,m.eleLineCount,m.eleZyCount,m.waterCount,m.waterOnlineCount
						,m.waterLineCount,m.waterZyCount
						,u.waterMonthUseNumber,u.waterDayUseNumber
						,u.eleMonthUseNumber,u.eleDayUseNumber
						,CASE WHEN CONVERT(VARCHAR(10),lastReadTime,20) = '1900-01-01'
						THEN @v_nullMsg ELSE CONVERT(VARCHAR(20),lastReadTime,20)  END lastReadTime
						,u.yesterdayEleNumber
						,u.yesterdayWaterNumber
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g
				LEFT JOIN aup_TjMeterBaseInfo m on m.id = g.id
				LEFT JOIN aup_TjMeterUseNumberInfo u on u.id = g.id
				WHERE g.id = @i_typeid

			END
END