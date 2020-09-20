/*
 Navicat Premium Data Transfer

 Source Server         : 129.28.190.79
 Source Server Type    : SQL Server
 Source Server Version : 10504000
 Source Host           : 129.28.190.79:1433
 Source Catalog        : zhapp
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 10504000
 File Encoding         : 65001

 Date: 13/02/2020 10:07:25
*/


-- ----------------------------
-- Table structure for aup_alarm_msg
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[aup_alarm_msg]') AND type IN ('U'))
	DROP TABLE [dbo].[aup_alarm_msg]
GO

CREATE TABLE [dbo].[aup_alarm_msg] (
  [id] bigint  IDENTITY(1,1) NOT NULL,
  [reason] varchar(512) COLLATE Chinese_PRC_CI_AS  NULL,
  [type] smallint DEFAULT ((0)) NULL,
  [toUserId] varchar(128) COLLATE Chinese_PRC_CI_AS  NULL,
  [title] varchar(512) COLLATE Chinese_PRC_CI_AS  NULL,
  [content] varchar(512) COLLATE Chinese_PRC_CI_AS  NULL,
  [sendTime] datetime  NULL
)
GO

ALTER TABLE [dbo].[aup_alarm_msg] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'发送原因',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg',
'COLUMN', N'reason'
GO

EXEC sp_addextendedproperty
'MS_Description', N'发送类型，0代表微信、1代表短信',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'发送给的用户ID',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg',
'COLUMN', N'toUserId'
GO

EXEC sp_addextendedproperty
'MS_Description', N'标题',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg',
'COLUMN', N'title'
GO

EXEC sp_addextendedproperty
'MS_Description', N'内容',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg',
'COLUMN', N'content'
GO

EXEC sp_addextendedproperty
'MS_Description', N'发送时间',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg',
'COLUMN', N'sendTime'
GO

EXEC sp_addextendedproperty
'MS_Description', N'区域树结构表',
'SCHEMA', N'dbo',
'TABLE', N'aup_alarm_msg'
GO


-- ----------------------------
-- Records of aup_alarm_msg
-- ----------------------------
SET IDENTITY_INSERT [dbo].[aup_alarm_msg] ON
GO

INSERT INTO [dbo].[aup_alarm_msg] ([id], [reason], [type], [toUserId], [title], [content], [sendTime]) VALUES (N'2', N'thing', N'0', N'dasfdasf', N'dsdsf', N'dasfdsafd', N'2020-02-11 11:30:02.000')
GO

INSERT INTO [dbo].[aup_alarm_msg] ([id], [reason], [type], [toUserId], [title], [content], [sendTime]) VALUES (N'3', N'dffads', N'1', N'123456678901', N'dfdas', N'dsaasdf', N'2020-02-11 11:30:38.000')
GO

SET IDENTITY_INSERT [dbo].[aup_alarm_msg] OFF
GO

