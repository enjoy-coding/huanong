/**
* 移动端查询运行监控
*
*/
ALTER PROCEDURE [dbo].[app_yxjk_queryInfoByType]
(
	@i_queryType VARCHAR(32)
)
AS
BEGIN
			IF @i_queryType = 'pump'
			BEGIN
					SELECT id,name,bfid as dingweiCode,'0.35Mpa' as szyl
					FROM aup_pump p 
					
			END
			ELSE IF @i_queryType = 'light'
			BEGIN
					SELECT c.sname as name, '在线' as status,c.poles as totalCount
							,t.lightcount as lightCount,t.dayEnergy,t.fullpower,t.readTime
							,c.sid,c.cuid
					FROM aup_streetlight_control c
					OUTER apply(
							SELECT e.userdenengy/1000 as dayEnergy,e.lightcount,e.fullpower
								,years+'-'+[month]+'-'+[day] as readTime
							FROM aup_control_energy e WHERE e.cuid = c.cuid
							AND years = (SELECT MAX(years) FROM aup_control_energy WHERE cuid=e.cuid)
							AND [month] = (SELECT MAX([month]) FROM aup_control_energy WHERE cuid=e.cuid)
							AND [day] = (SELECT MAX([day]) FROM aup_control_energy WHERE cuid=e.cuid)
						)t	
			END
			ELSE
			BEGIN
					SELECT '11'
			END
END