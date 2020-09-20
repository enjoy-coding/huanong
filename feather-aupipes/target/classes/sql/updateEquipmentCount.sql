/**
*   定时修改设备的在线离线总数
*/
ALTER PROCEDURE [dbo].[updateEquipmentCount]
AS
BEGIN
    --配电房个数
    DECLARE @v_powerCount int 
			,@v_powerCount_unOnline int = 0 --配电房离线数
			,@v_waterCount_total int --水表总数
			,@v_waterCount_onine int --水表在线数
			,@v_waterCount_unOnine int --水表离线树
			,@v_electricityCount_total int  --电表总数
			,@v_electricityCount_online int  --电表在线数
			,@v_electricityCount_unOnline int  --电表离线数
			,@v_streetLightCount_total int --路灯总数
			,@v_streetLightCount_online int --路灯在线数
			,@v_streetLightCount_unOnline int --路灯离线数
			,@v_pumpCount_total int --泵房总数
			,@v_pumpCount_online int --泵房在线数
			,@v_pumpCount_unOnline int	= 0 --泵房离线数
			,@v_leakCount_online int  --探漏在线数
			,@v_leakCount_total int  --探漏总数
			,@v_leakCount_unOnline int --探漏离线数
			,@v_buildCount_online int --楼栋在线数(已废弃)
			,@v_buildCount_total int --楼栋总数(已废弃)
			,@v_buildCount_unOnline int  = 0 --楼栋离线数(已废弃)
			,@v_monitorCount_online int  --监控在线数 
			,@v_monitorCount_total int --监控总数
			,@v_monitorCount_unOnline int = 0 --监控离线数
			,@v_sfwaterCount_total int =0 --盛帆水表总数
			,@v_sfwaterCount_unOnline int = 0 --盛帆水表离线数
			,@v_sfeleCount_total int =0 --盛帆电表总数
			,@v_sfeleCount_unOnline int = 0 --盛帆电表离线数
			,@v_sz_online int --水质在线数
			,@v_sz_unOnline int = 0 --水质离线数
			,@v_sz_total int;
    --水质总数

    --水电用户
    DECLARE @v_meterErrorCount INT --水电离线用户数
            ,@v_meterCount INT
    --水电用户数
    SELECT @v_meterErrorCount = chCount+eleCount+waterCount, @v_meterCount =t3.meterCount
    FROM(
		SELECT COUNT(1) as chCount
        FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] nh
        WHERE 1=1
        AND EXISTS (
            SELECT 1
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
            WHERE mm.house = nh.id
            AND dic.description = '电表'
            AND mm.meterStatus !=1
			)
        AND EXISTS (
            SELECT 1
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
            JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
            WHERE mm.house = nh.id
            AND dic.description = '水表'
            AND mm.meterStatus !=1
			)
	) t 
	OUTER apply(
			SELECT COUNT(1) as eleCount
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] eh
            WHERE 1=1
            AND EXISTS (
                SELECT 1
                FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
                JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
                WHERE mm.house = eh.id
                AND dic.description = '电表'
                AND mm.meterStatus !=1
				)
                AND eh.NO NOT IN (
					SELECT nh.NO
                    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] nh
                    WHERE 1=1
                    AND EXISTS (
                        SELECT 1
                        FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
                        JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
                        WHERE mm.house = nh.id
                        AND dic.description = '电表'
                        AND mm.meterStatus !=1
					    )
                AND EXISTS (
                        SELECT 1
                        FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
                        JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
                        WHERE mm.house = nh.id
                        AND dic.description = '水表'
                        AND mm.meterStatus !=1
					)
				)
		) t1 
		OUTER apply(
				SELECT COUNT(1) as waterCount
                FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] eh
                WHERE 1=1
                AND EXISTS (
                    SELECT 1
                    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
                    JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
                    WHERE mm.house = eh.id
                    AND dic.description = '水表'
                    AND mm.meterStatus !=1
				)
                AND eh.NO NOT IN (
					SELECT nh.NO
                    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] nh
                    WHERE 1=1
                    AND EXISTS (
                        SELECT 1
                        FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
                        JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
                        WHERE mm.house = nh.id
                        AND dic.description = '电表'
                        AND mm.meterStatus !=1
					)
                    AND EXISTS (
                        SELECT 1
                        FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
                        JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
                        WHERE mm.house = nh.id
                        AND dic.description = '水表'
                        AND mm.meterStatus !=1
					)
				)
		) t2
	OUTER apply(
			SELECT COUNT(1) as meterCount
            FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_House] nh
            WHERE 1=1
            AND EXISTS (
                SELECT 1
                FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_Meter] mm
                JOIN [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_Platform_DictionaryItem] dic on mm.type=dic.id
                WHERE mm.house = nh.id
                AND dic.description = '电表' OR dic.description = '水表'					
		    )
	    ) t3

    --配电房
    SELECT @v_powerCount = count(1)
    from aup_power(NOLOCK)

    --路灯
    SELECT @v_streetLightCount_total = count(1)
    from aup_streetlight(NOLOCK)
    SELECT @v_streetLightCount_online = count(1)
    from aup_streetlight(NOLOCK)


    --水表
    select @v_waterCount_total = waterMeterCount
    from [LINK_NT].[NT.AgriculturalUniversity.Logistics].dbo.[动态数据_EnergyDeviceStatus]
    select @v_waterCount_onine = waterMeterCountOnline
    from [LINK_NT].[NT.AgriculturalUniversity.Logistics].dbo.[动态数据_EnergyDeviceStatus]
    SELECT @v_waterCount_unOnine = @v_waterCount_total - @v_waterCount_onine
    --电表
    select @v_electricityCount_total = electricityMeterCount
    from [LINK_NT].[NT.AgriculturalUniversity.Logistics].dbo.[动态数据_EnergyDeviceStatus]
    select @v_electricityCount_online = electricityCountOnline
    from [LINK_NT].[NT.AgriculturalUniversity.Logistics].dbo.[动态数据_EnergyDeviceStatus]
    SELECT @v_electricityCount_unOnline = @v_electricityCount_total - @v_electricityCount_online


    --水电表

    --盛帆水电表
    SELECT @v_sfwaterCount_total = COUNT(1), @v_sfwaterCount_unOnline = count(CASE when status is null THEN 1 END )
    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_SfMeter]
    WHERE meterType = '水表'

    SELECT @v_sfeleCount_total = count(1), @v_sfeleCount_unOnline = count(CASE when status is null THEN 1 END )
    FROM [LINK_NT].[NT.AgriculturalUniversity.Logistics].[dbo].[NT_AgriculturalUniversity_SfMeter]
    WHERE meterType = '电表'


    --泵房
    set @v_pumpCount_total = 5
    set @v_pumpCount_online = 5

    --探漏
    select @v_leakCount_total = count(1)
    from aup_leak(NOLOCK)
    select @v_leakCount_online = (
		select CONVERT(int,normal+Leakage+Lnterfere+Doubtleak) as online
        from aup_leak_count 
	)
    SELECT @v_leakCount_unOnline = @v_leakCount_total - @v_leakCount_online
    --楼栋
    SELECT @v_buildCount_online = count(1)
    from aup_building
    SELECT @v_buildCount_total = count(1)
    from aup_building


    --水质
    SELECT @v_sz_online = count(1)
    FROM aup_waterQuality
    SET @v_sz_total = @v_sz_online
    --监测
    set @v_monitorCount_online = 65
    set @v_monitorCount_total = 65

    UPDATE aup_tj_sbtj set pdf = @v_powerCount_unOnline
												,sb = @v_waterCount_unOnine
												,db = @v_electricityCount_unOnline
												,ld = @v_streetLightCount_unOnline
												,bf = @v_pumpCount_unOnline
												,tl = @v_leakCount_unOnline
												,jk = @v_monitorCount_unOnline
												,lf = @v_buildCount_unOnline
												,sz = @v_sz_unOnline
												,jc = 0
												,meterCount = @v_meterErrorCount
												,sfWaterMeterCount = @v_sfwaterCount_unOnline
												,sfEleMeterCount = @v_sfeleCount_unOnline
												
	where category = 0

    UPDATE aup_tj_sbtj set pdf = @v_powerCount
												,sb = @v_waterCount_total
												,db = @v_electricityCount_total
												,ld = @v_streetLightCount_total
												,bf = @v_pumpCount_total
												,tl = @v_leakCount_total
												,jk = @v_monitorCount_total
												,lf = @v_buildCount_total
												,sz = @v_sz_total
												,meterCount = @v_meterCount
												,sfWaterMeterCount = @v_sfwaterCount_total
												,sfEleMeterCount = @v_sfeleCount_total
											
	where category = 1


END