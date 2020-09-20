/**
* 搜索周边-搜索设备信息
*/
ALTER PROCEDURE [dbo].[search_queryEquipment]
(
	@i_keywords varchar(255),
	@i_type int
)
AS
BEGIN

		IF @i_type = 1 OR @i_type = 2
		BEGIN
			DECLARE @v_year VARCHAR(32),
							@v_month VARCHAR(32)
			SELECT @v_month = DATEPART(MM,GETDATE()), @v_year = DATENAME(YYYY,GETDATE())
		END
		
		IF @i_type = 1 --水表
		BEGIN
				SELECT  '水表' as type,'waterTable' as code,p.name+'-'+a.name as areaName,a.areaNo,i.waterMonthUseNumber as localMonthUseNumber 
				FROM aup_area a
				JOIN aup_area p on a.pid = p.id
				JOIN aup_TjMeterUseNumberInfo i on i.id = a.id
				WHERE a.type='building'
				AND (@i_keywords is NULL OR a.name LIKE '%'+@i_keywords+'%')
				ORDER BY p.id,a.name
		END
		ELSE IF @i_type = 2
		BEGIN
			    SELECT  '电表' as type,'waterTable' as code,p.name+'-'+a.name as areaName,a.areaNo,i.eleMonthUseNumber as localMonthUseNumber 
				FROM aup_area a
				JOIN aup_area p on a.pid = p.id
				JOIN aup_TjMeterUseNumberInfo i on i.id = a.id
				WHERE a.type='building'
				AND (@i_keywords is NULL OR a.name LIKE '%'+@i_keywords+'%')
				ORDER BY p.id,a.name	
		END
		ELSE if @i_type = 3
		BEGIN
		SELECT '水质' as type,'waterQuality' as code,l.sname
					,l.oid,l.sid,l.type,t1.ls,t1.sw,t1.jcsj as lsjcsj
					,t2.ygfrjy,t2.dcsddl,t2.zd,t2.ph,t2.orp,t2.wd,t2.jcsj as szjcsj
		FROM aup_waterQuality l(NOLOCK)
		OUTER apply(
				SELECT * FROM aup_waterQuality_lsjc sq 
				WHERE sq.oid = l.oid 
				AND sq.jcsj IN(
						SELECT MAX(jcsj) FROM aup_waterQuality_lsjc j
						WHERE j.oid = l.oid
				)
				AND l.type IN ('流速水位')
		)t1
		OUTER apply(
				SELECT * FROM aup_waterQuality_szjc jq WHERE jq.oid = l.oid
				AND jcsj IN (
				SELECT MAX(jcsj) as jcsj FROM aup_waterQuality_szjc j
					WHERE j.oid = l.oid
				)
				AND l.type IN ('水质')
		)t2
		WHERE (@i_keywords is  null OR l.sname LIKE '%'+@i_keywords+'%')
		END
	
		IF @i_type = 4
		BEGIN
            SELECT  '路灯' as type,'streetlight' as code,s.name as name,s.lid  
            FROM aup_streetlight s(NOLOCK) 
            WHERE (@i_keywords is  null OR s.name LIKE '%'+@i_keywords+'%')
		END
		ELSE if @i_type = 5
		BEGIN
            SELECT '探漏' as type,'leak' as code,l.placeAddress as name,l.placeId  
            FROM aup_leak l(NOLOCK)
            WHERE (@i_keywords is  null OR l.placeAddress LIKE '%'+@i_keywords+'%')
		END
		ELSE 
		BEGIN
            SELECT  '监控' as type,'camera' as code,c.cameraName as name,c.id,c.cameraIndexCode as sid
            FROM aup_region_camera c(NOLOCK)
            WHERE c.cameraName is not NULL AND (@i_keywords is  null OR c.cameraName LIKE '%'+@i_keywords+'%')
		END
END