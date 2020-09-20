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

Date: 2020-07-29 15:33:25
*/


-- ----------------------------
-- Table structure for np_mobile_device
-- ----------------------------
DROP TABLE [dbo].[np_mobile_device]
GO
CREATE TABLE [dbo].[np_mobile_device] (
[device_id] nvarchar(200) NOT NULL ,
[send_flag] int NULL DEFAULT ((0)) ,
[create_time] bigint NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_mobile_device', 
'COLUMN', N'send_flag')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'发送未读消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_mobile_device'
, @level2type = 'COLUMN', @level2name = N'send_flag'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'发送未读消息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_mobile_device'
, @level2type = 'COLUMN', @level2name = N'send_flag'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'np_mobile_device', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_mobile_device'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'np_mobile_device'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO

-- ----------------------------
-- Indexes structure for table np_mobile_device
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table np_mobile_device
-- ----------------------------
ALTER TABLE [dbo].[np_mobile_device] ADD PRIMARY KEY ([device_id])
GO
