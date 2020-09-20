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

Date: 2020-07-27 10:22:48
*/


-- ----------------------------
-- Table structure for np_thumbsup_log
-- ----------------------------
DROP TABLE [dbo].[np_thumbsup_log]
GO
CREATE TABLE [dbo].[np_thumbsup_log] (
[thumbsUp_id] bigint NOT NULL ,
[info_id] bigint NULL ,
[ip] nvarchar(100) NULL ,
[log_time] bigint NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_thumbsup_log', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'菜单权限表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_thumbsup_log'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'菜单权限表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_thumbsup_log'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_thumbsup_log', 
'COLUMN', N'thumbsUp_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'设备ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_thumbsup_log'
, @level2type = 'COLUMN', @level2name = N'thumbsUp_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'设备ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_thumbsup_log'
, @level2type = 'COLUMN', @level2name = N'thumbsUp_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_thumbsup_log', 
'COLUMN', N'log_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_thumbsup_log'
, @level2type = 'COLUMN', @level2name = N'log_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_thumbsup_log'
, @level2type = 'COLUMN', @level2name = N'log_time'
GO

-- ----------------------------
-- Indexes structure for table np_thumbsup_log
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table np_thumbsup_log
-- ----------------------------
ALTER TABLE [dbo].[np_thumbsup_log] ADD PRIMARY KEY ([thumbsUp_id])
GO
