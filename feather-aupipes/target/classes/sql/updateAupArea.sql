/**
* 修改行政地区信息
*/
ALTER PROCEDURE [dbo].[updateAupArea]
AS
BEGIN
		DELETE aup_area WHERE 1=1
		INSERT INTO aup_area(id,pid,name,type,areaNo,floors,[level],useArea,categoryId,categoryName,dingweiCode,updateTime)
		SELECT id,parent as pid,name,'roota' as type,no as areaNo,0 as floors,1 as [level],0 as useArea,'' as categortId,'' as categoryName,no,CONVERT(varchar(32),GETDATE(),20) as updateTime
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
END