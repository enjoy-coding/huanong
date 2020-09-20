/***
* 移动端查询泵房详情信息
*/
ALTER PROCEDURE [dbo].[app_yxjk_queryDetailInfoById]
(
	@i_queryType VARCHAR(32),
	@i_typeId VARCHAR(64)
)
AS
BEGIN
			IF @i_queryType = 'pump'
			BEGIN
					SELECT p.pump_id as pumpId
								,p.id  as pointId
								,p.point_name as pointName
								,p.ip,p.punpCount,s.statusName,s.status,s.readTime,ap.attrValue,ap.attrName
					FROM aup_pump_point p 
					JOIN aup_pump_point_status s on p.id = s.point_id
          join aup_pump_point_base ap on p.id=ap.point_id
					WHERE p.pump_id = @i_typeId
					--AND s.readTime = (SELECT MAX(readTime) FROM aup_pump_point_status WHERE pump_id = @i_typeId)
					ORDER BY p.id 
			END
END