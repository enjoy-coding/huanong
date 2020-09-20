/**
* 定时更新aup_area 行政地区
*/
ALTER PROCEDURE [dbo].[insertAupArea]
AS
BEGIN
		
		SET    NOCOUNT    ON;
		BEGIN TRAN
		--更新前查看最新的数据是否在aup_area中,新增的数据
		INSERT INTO aup_area_updateHistory(id,oldId,oldName,oldType,oldAreaNo,updateType,updateTime)
		SELECT CONVERT(BIGINT,DATEDIFF(s,'1970-01-01 00:00:00.000', GETDATE())) * 10000 + DATEPART(MS, GETDATE()) as id
					,h.id,h.name,h.type,h.areaNo,'INSERT' as updateType,CONVERT(VARCHAR(32),GETDATE(),20) as updateTime
		FROM (
			SELECT id,parent as pid,name,type,no as areaNo,0 as floors,1 as [level],0 as useArea,'' as categortId,'' as categoryName,no,CONVERT(varchar(32),GETDATE(),20) as updateTime
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] 
				WHERE type ='AreaLine' AND NO = 'roota'
				UNION ALL
				SELECT g.id,g.parent as pid,g.name,g.type,g.no as areaNo,0 as floors,2 as [level],0 as useArea,'' as categortId,'' as categoryName,g.no,CONVERT(varchar(32),GETDATE(),20) as updateTime
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g
				WHERE type ='AreaLine' AND parent = 'AreaLine0000' 
				UNION ALL
				SELECT b.id,b.area as pid,b.name,'building' as type,b.no as areaNo,b.floors,3 as [level],b.buildArea as useArea,'' as categortId,'' as categoryName,b.no,CONVERT(varchar(32),GETDATE(),20) as updateTime
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b
				JOIN  [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g on b.area = g.id
				WHERE g.parent = 'AreaLine0000' 
				UNION ALL
				SELECT h.id,h.buildingno as pid,h.name,'house' as type,h.no as areaNo,1 as floor,4 as [level],h.useArea,MAX(c.id) as id,MAX(c.name) as name,b.no,CONVERT(varchar(32),GETDATE(),20) as updateTime 
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] h 
				left JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b on h.buildingno = b.no
				left JOIN  [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g on b.area = g.id
				left JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Account] a on h.id = a.house
				LEFT JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_AccountCategory] c 
												on c.id = a.accountCategory
				GROUP BY h.id,h.buildingno,h.no,h.name,h.useArea,b.no 			
		) h
		LEFT JOIN aup_area a on a.id = h.id
		WHERE (a.id is NULL OR a.areaNo is NULL)

		--更新前查看最新的数据是否在aup_area中，名字相同，修改了areaNo和id的
		INSERT INTO aup_area_updateHistory(id,aid,aname,atype,aareaNo,oldId,oldName,oldType,oldAreaNo,updateType,updateTime)
		SELECT CONVERT(BIGINT,DATEDIFF(s,'1970-01-01 00:00:00.000', GETDATE())) * 10000 + DATEPART(MS, GETDATE()) as id
							,a.id,a.name,a.type,a.areaNo
							,h.id as oldId,h.name as oldName,h.type as oldType,h.areaNo as oldAreaNo
							,'UPDATE' as updateType,CONVERT(VARCHAR(32),GETDATE(),20) as updateTime FROM(
				SELECT id,parent as pid,name,type,no as areaNo,0 as floors,1 as [level],0 as useArea,'' as categortId,'' as categoryName,no,CONVERT(varchar(32),GETDATE(),20) as updateTime
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] 
				WHERE type ='AreaLine' AND NO = 'roota'
				UNION ALL
				SELECT g.id,g.parent as pid,g.name,g.type,g.no as areaNo,0 as floors,2 as [level],0 as useArea,'' as categortId,'' as categoryName,g.no,CONVERT(varchar(32),GETDATE(),20) as updateTime
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g
				WHERE type ='AreaLine' AND parent = 'AreaLine0000' 
				UNION ALL
				SELECT b.id,b.area as pid,b.name,'building' as type,b.no as areaNo,b.floors,3 as [level],b.buildArea as useArea,'' as categortId,'' as categoryName,b.no,CONVERT(varchar(32),GETDATE(),20) as updateTime
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b
				JOIN  [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g on b.area = g.id
				WHERE g.parent = 'AreaLine0000' 
				UNION ALL
				SELECT h.id,h.buildingno as pid,h.name,'house' as type,h.no as areaNo,1 as floor,4 as [level],h.useArea,MAX(c.id) as id,MAX(c.name) as name,b.no,CONVERT(varchar(32),GETDATE(),20) as updateTime 
				FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] h 
				left JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Building] b on h.buildingno = b.no
				left JOIN  [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_BuildingGroup] g on b.area = g.id
				left JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Account] a on h.id = a.house
				LEFT JOIN [LINK_NT]. [NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_AccountCategory] c 
												on c.id = a.accountCategory
				GROUP BY h.id,h.buildingno,h.no,h.name,h.useArea,b.no 	
		) h 
		JOIN aup_area a on a.id = h.id 
		WHERE a.areaNo <> h.areaNo
		IF @@error <> 0
				 ROLLBACK TRAN
		else
				COMMIT TRAN
		
		BEGIN TRAN
		--执行更新行政地区操作
		EXEC updateAupArea	
		
		IF @@error <> 0
				 ROLLBACK TRAN
		else
				COMMIT TRAN
		
		
		
END