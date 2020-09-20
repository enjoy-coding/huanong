/**
* 定时清理路灯监控数据
*/
ALTER PROCEDURE [dbo].[deleteStreetlightDetailTime]
AS
BEGIN
			DELETE c FROM aup_streetlight_detail c
			JOIN (
				 SELECT MIN(Id) Id,luid,ltime,u FROM aup_streetlight_detail d 
					GROUP BY d.luid,ltime,u
					HAVING COUNT(1) >  1
			) TMP ON c.luid = TMP.luid AND c.ltime = TMP.ltime AND C.Id <> TMP.Id

END