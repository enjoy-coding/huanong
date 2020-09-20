/*
Navicat SQL Server Data Transfer

Source Server         : 172.16.0.204,1433
Source Server Version : 105000
Source Host           : 172.16.0.204,1433:1433
Source Database       : napo
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2020-07-29 15:14:44
*/


-- ----------------------------
-- Table structure for np_info_read_log
-- ----------------------------
DROP TABLE [dbo].[np_info_read_log]
GO
CREATE TABLE [dbo].[np_info_read_log] (
[read_id] bigint NOT NULL ,
[info_id] bigint NULL ,
[device_id] nvarchar(200) NULL ,
[read_flag] int NULL DEFAULT ((0)) ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'菜单权限表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'菜单权限表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'info_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'信息id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'info_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'信息id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'info_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'device_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'设备id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'device_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'设备id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'device_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'read_flag')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'是否已读，0代表未读、1代表已读'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'read_flag'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'是否已读，0代表未读、1代表已读'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'read_flag'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_info_read_log', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_info_read_log'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Indexes structure for table np_info_read_log
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table np_info_read_log
-- ----------------------------
ALTER TABLE [dbo].[np_info_read_log] ADD PRIMARY KEY ([read_id])
GO
