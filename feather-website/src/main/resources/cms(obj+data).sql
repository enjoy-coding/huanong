/*
Navicat SQL Server Data Transfer

Source Server         : 27.17.43.14(172.16.0.204)
Source Server Version : 105000
Source Host           : d80255c518e7d610.natapp.cc,43490:1433
Source Database       : zhapp
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 105000
File Encoding         : 65001

Date: 2020-06-09 09:51:50
*/


-- ----------------------------
-- Table structure for cms_article
-- ----------------------------
DROP TABLE [dbo].[cms_article]
GO
CREATE TABLE [dbo].[cms_article] (
[id] bigint NOT NULL ,
[type] tinyint NULL ,
[title] nvarchar(250) NULL ,
[subTitle] nvarchar(250) NULL ,
[articleColumnId] bigint NULL ,
[articleColumnName] nvarchar(250) NULL ,
[summary] nvarchar(250) NULL ,
[keywords] nvarchar(250) NULL ,
[sourceFrom] nvarchar(250) NULL ,
[author] nvarchar(100) NULL ,
[publisher] nvarchar(50) NULL ,
[publishAt] datetime2(7) NULL ,
[publisherId] tinyint NULL ,
[isTop] tinyint NULL ,
[url] nvarchar(250) NULL ,
[coverImage] nvarchar(250) NULL ,
[canDownload] tinyint NULL ,
[content] nvarchar(MAX) NULL ,
[auditStatus] tinyint NULL ,
[auditor] nvarchar(50) NULL ,
[auditorId] bigint NULL ,
[auditRemark] nvarchar(250) NULL ,
[viewCount] int NULL ,
[orderNum] int NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(250) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(250) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO

-- ----------------------------
-- Records of cms_article
-- ----------------------------
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'44', N'0', N'www', N'', N'29', N'左侧', null, null, null, null, N'管理员', N'2019-11-06 15:35:15.0000000', N'1', null, null, N'', N'1', N'我这次跟踪一户大面积的，可还5号楼全是170平的，看来这还建房还是量身定做的，这栋层楼高度只有那些小面积楼层一半，证明该片都是小面积住户，该片以企业自管房为主，那几栋楼至少40层以上，南北朝向，基本上属原址还建</span>', null, null, null, null, null, null, N'0', N'admin', N'2019-11-06 15:35:19.0000000', N'admin', N'2019-11-06 15:51:16.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'45', N'0', N'2222', N'', N'29', N'左侧', null, null, null, null, N'管理员', N'2019-11-06 15:46:05.0000000', N'1', null, null, N'', N'1', N'<span style="color: rgb(68, 68, 68); font-family: &quot;Microsoft Yahei&quot;; font-size: 18px;">办证手套只要1.5个点，两年资金成本就算12个点吧，这算高的了，无风险理财现在也就4.5左右，6个点的我是没看到没风险的。就按两年12个点这样算资金保利成本23500，现在卖27000纯利3500一平，100平纯利35万</span>', null, null, null, null, null, null, N'0', N'admin', N'2019-11-06 15:46:10.0000000', N'admin', N'2019-11-06 15:57:04.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'46', N'0', N'这公告上说的还建房名称:光谷世家，慢滴又说我广告，得意罚我图片歪着', N'', N'66', N'分局动态', null, null, null, null, N'管理员', N'2019-11-06 17:15:07.0000000', N'1', null, null, N'', N'1', N'<span style="color: rgb(68, 68, 68); font-family: &quot;Microsoft Yahei&quot;; font-size: 18px;">这公告上说的还建房名称:光谷世家，慢滴又说我广告，得意罚我图片歪着</span>', null, null, null, null, null, null, N'0', N'admin', N'2019-11-06 17:15:25.0000000', N'admin', N'2019-11-06 17:15:25.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'47', N'0', N'  良心啊', N'', N'66', N'分局动态', null, null, null, null, N'管理员', N'2019-11-06 17:15:36.0000000', N'1', null, null, N'', N'1', N'<table cellspacing="0" cellpadding="0" style="margin: 0px; padding: 0px; overflow-wrap: break-word; empty-cells: show; table-layout: fixed; width: 757px; color: rgb(68, 68, 68); font-family: Tahoma, Helvetica, SimSun, sans-serif, Hei; font-size: 12px; background-color: rgb(255, 255, 255);"><tbody style="margin: 0px; padding: 0px; overflow-wrap: break-word;"><tr style="margin: 0px; padding: 0px; overflow-wrap: break-word;"><td class="t_f" id="postmessage_139435694" style="margin: 0px; overflow-wrap: break-word; font-size: 18px; font-family: &quot;Microsoft Yahei&quot;;"><br style="margin: 0px; padding: 0px; overflow-wrap: break-word;">良心啊</td></tr></tbody></table>', null, null, null, null, null, null, N'0', N'admin', N'2019-11-06 17:15:56.0000000', N'admin', N'2019-11-07 14:12:23.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'48', N'0', N'123', N'123', N'65', N'2', null, null, null, null, N'管理员', N'2019-11-07 14:16:18.0000000', N'1', null, null, N'', N'1', null, null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 14:16:30.0000000', N'admin', N'2019-11-07 14:16:30.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'49', N'0', N'1', N'', N'65', N'2', null, null, null, null, N'管理员', N'2019-11-07 14:20:46.0000000', N'1', null, null, N'', N'1', null, null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 14:20:50.0000000', N'admin', N'2019-11-07 14:20:50.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'50', N'0', N'1', N'', N'29', N'左侧', null, null, null, null, N'管理员', N'2019-11-07 14:24:00.0000000', N'1', null, null, N'', N'1', null, null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 14:24:05.0000000', N'admin', N'2019-11-07 14:24:05.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'51', N'0', N'111', N'', N'29', N'左侧', null, null, null, null, N'管理员', N'2019-11-07 14:24:10.0000000', N'1', null, null, N'', N'1', null, null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 14:24:14.0000000', N'admin', N'2019-11-07 14:24:14.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'52', N'0', N'123', N'', N'29', N'左侧', null, null, null, null, N'管理员', N'2019-11-07 14:25:54.0000000', N'1', null, null, N'', N'1', null, null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 14:26:58.0000000', N'admin', N'2019-11-07 14:26:58.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'53', N'0', N'123', N'', N'65', N'2', null, null, null, null, N'管理员', N'2019-11-07 14:27:59.0000000', N'1', null, null, N'', N'1', null, null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 14:28:07.0000000', N'admin', N'2019-11-07 14:28:07.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'54', N'0', N'12', N'', N'29', N'左侧', null, null, null, null, N'管理员', N'2019-11-07 14:34:07.0000000', N'1', null, null, N'', N'1', N'123', null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 14:34:58.0000000', N'admin', N'2019-11-07 14:34:58.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'55', N'0', N'1111', N'', N'29', N'左侧', null, null, null, null, N'管理员', N'2019-11-07 15:15:28.0000000', N'1', null, null, N'/profile/upload/2019/11/07/1f2b0814138e845031752a15e8cffb18.gif', N'1', N'1111', null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 15:15:53.0000000', N'admin', N'2019-11-07 15:15:53.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'56', N'0', N'轮播图1', N'111', N'30', N'中间轮播图', null, null, null, null, N'管理员', N'2019-11-07 15:19:15.0000000', N'1', null, null, N'/profile/upload/2019/11/07/061f0919492835cf56b0efb05d5856c3.png', N'1', N'1111', null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 15:19:30.0000000', N'admin', N'2019-11-07 15:19:30.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article] ([id], [type], [title], [subTitle], [articleColumnId], [articleColumnName], [summary], [keywords], [sourceFrom], [author], [publisher], [publishAt], [publisherId], [isTop], [url], [coverImage], [canDownload], [content], [auditStatus], [auditor], [auditorId], [auditRemark], [viewCount], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'57', N'0', N'轮播图2', N'', N'30', N'中间轮播图', null, null, null, null, N'管理员', N'2019-11-07 15:19:40.0000000', N'1', null, null, N'/profile/upload/2019/11/07/8ec43532e084c923c539efb587a9d9ac.png', N'1', N'轮播图2', null, null, null, null, null, null, N'0', N'admin', N'2019-11-07 15:19:56.0000000', N'admin', N'2019-11-07 15:19:56.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for cms_article_column
-- ----------------------------
DROP TABLE [dbo].[cms_article_column]
GO
CREATE TABLE [dbo].[cms_article_column] (
[id] bigint NOT NULL ,
[name] nvarchar(250) NULL ,
[alias] nvarchar(250) NULL ,
[type] tinyint NULL ,
[parentId] bigint NULL ,
[parentName] nvarchar(250) NULL ,
[level] tinyint NULL ,
[siteId] bigint NULL ,
[summary] nvarchar(250) NULL ,
[otherSummary] nvarchar(250) NULL ,
[publishAudit] tinyint NULL ,
[shareAudit] tinyint NULL ,
[url] nvarchar(250) NULL ,
[isNav] tinyint NULL ,
[frontMaxSize] tinyint NULL ,
[frontVisible] tinyint NULL ,
[frontListMethod] tinyint NULL ,
[listTpl] nvarchar(250) NULL ,
[contentTpl] nvarchar(250) NULL ,
[orderNum] int NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(250) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(250) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO

-- ----------------------------
-- Records of cms_article_column
-- ----------------------------
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'18', N'攀枝花市公安局东城分局', N'pzh', N'0', N'0', null, N'1', N'18', null, null, null, null, null, null, N'0', null, null, N'/list2', N'/content', N'1', N'0', null, N'2019-11-05 10:06:07.0000000', null, N'2019-11-05 10:06:07.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'19', N'导航栏', N'dhl', N'0', N'18', N'攀枝花市公安局东城分局', N'2', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 10:06:33.0000000', null, N'2019-11-05 10:06:33.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'20', N'网站首页13', N'wzsy', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 10:06:57.0000000', null, N'2019-11-05 10:06:57.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'21', N'法律法规', N'flfg', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 10:07:16.0000000', null, N'2019-11-05 10:07:16.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'22', N'规章制度', N'gzzd', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'3', N'0', null, N'2019-11-05 10:07:39.0000000', null, N'2019-11-05 10:07:39.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'23', N'电话簿', N'dhb', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'4', N'0', null, N'2019-11-05 10:08:11.0000000', null, N'2019-11-05 10:08:11.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'24', N'专题专栏', N'ztzl', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'5', N'0', null, N'2019-11-05 10:08:26.0000000', null, N'2019-11-05 10:08:26.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'25', N'信息判研', N'xxpy', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'6', N'0', null, N'2019-11-05 10:08:52.0000000', null, N'2019-11-05 10:08:52.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'26', N'建言献策', N'jyxc', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'7', N'0', null, N'2019-11-05 10:09:21.0000000', null, N'2019-11-05 10:09:21.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'27', N'网络警校', N'wljx', N'0', N'19', N'导航栏', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'8', N'0', null, N'2019-11-05 10:09:38.0000000', null, N'2019-11-05 10:09:38.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'29', N'左侧', N'zc', N'0', N'18', N'攀枝花市公安局东城分局', N'2', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 11:16:07.0000000', null, N'2019-11-05 11:16:07.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'30', N'中间轮播图', N'lbt', N'0', N'18', N'攀枝花市公安局东城分局', N'2', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'3', N'0', null, N'2019-11-05 11:16:33.0000000', null, N'2019-11-05 11:16:33.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'31', N'右侧头条', N'ttxw', N'0', N'18', N'攀枝花市公安局东城分局', N'2', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'4', N'0', null, N'2019-11-05 11:17:24.0000000', null, N'2019-11-05 11:17:24.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'32', N'中间内容', N'zjnr', N'0', N'18', N'攀枝花市公安局东城分局', N'2', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'5', N'0', null, N'2019-11-05 12:38:00.0000000', null, N'2019-11-05 12:38:00.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'33', N'通知公告', N'tzgg', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 12:38:47.0000000', null, N'2019-11-05 12:38:47.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'34', N'领导动态', N'lddt', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 13:50:25.0000000', null, N'2019-11-05 13:50:25.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'35', N'党建工作', N'djgz', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'3', N'0', null, N'2019-11-05 13:55:13.0000000', null, N'2019-11-05 13:55:13.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'36', N'警情分析', N'jqfx', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'4', N'0', null, N'2019-11-05 17:28:16.0000000', null, N'2019-11-05 17:28:16.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'37', N'每日', N'mr', N'0', N'36', N'警情分析', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 17:28:47.0000000', null, N'2019-11-05 17:28:47.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'38', N'每周', N'mz', N'0', N'36', N'警情分析', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 17:29:05.0000000', null, N'2019-11-05 17:29:05.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'39', N'重点分析', N'zdfx', N'0', N'36', N'警情分析', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'3', N'0', null, N'2019-11-05 17:29:19.0000000', null, N'2019-11-05 17:29:19.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'40', N'工作文件', N'gzwj', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'5', N'0', null, N'2019-11-05 17:40:22.0000000', null, N'2019-11-05 17:40:22.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'41', N'党委文件', N'dwwj', N'0', N'40', N'工作文件', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 17:40:46.0000000', null, N'2019-11-05 17:40:46.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'42', N'会议纪要', N'hyjy', N'0', N'40', N'工作文件', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 17:40:57.0000000', null, N'2019-11-05 17:40:57.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'43', N'分局文件', N'fjwj', N'0', N'40', N'工作文件', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'3', N'0', null, N'2019-11-05 17:41:09.0000000', null, N'2019-11-05 17:41:09.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'44', N'工作快讯', N'gzkx', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'6', N'0', null, N'2019-11-05 17:55:31.0000000', null, N'2019-11-05 17:55:31.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'45', N'公安简报', N'gajb', N'0', N'44', N'工作快讯', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 17:55:52.0000000', null, N'2019-11-05 17:55:52.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'46', N'信息调研', N'xxdy', N'0', N'44', N'工作快讯', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 17:56:10.0000000', null, N'2019-11-05 17:56:10.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'47', N'法制工作', N'fzgz', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'7', N'0', null, N'2019-11-05 17:58:19.0000000', null, N'2019-11-05 17:58:19.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'48', N'值班表', N'zbb', N'0', N'47', N'法制工作', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 17:58:38.0000000', null, N'2019-11-05 17:58:38.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'49', N'执法监督', N'zfjd', N'0', N'47', N'法制工作', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 17:58:50.0000000', null, N'2019-11-05 17:58:50.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'50', N'刑事侦查', N'xszc', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'8', N'0', null, N'2019-11-05 17:59:13.0000000', null, N'2019-11-05 17:59:13.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'51', N'现场勘查', N'xckc', N'0', N'50', N'刑事侦查', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 17:59:29.0000000', null, N'2019-11-05 17:59:29.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'52', N'协查通报', N'xctb', N'0', N'50', N'刑事侦查', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 17:59:52.0000000', null, N'2019-11-05 17:59:52.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'53', N'典型案例', N'dxal', N'0', N'50', N'刑事侦查', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'3', N'0', null, N'2019-11-05 18:00:02.0000000', null, N'2019-11-05 18:00:02.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'54', N'治安管理', N'zagl', N'0', N'32', N'中间内容', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'9', N'0', null, N'2019-11-05 18:00:25.0000000', null, N'2019-11-05 18:00:25.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'55', N'社区警务', N'sqjw', N'0', N'54', N'治安管理', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-05 18:00:38.0000000', null, N'2019-11-05 18:00:38.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'56', N'治安要情', N'zayq', N'0', N'54', N'治安管理', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-05 18:00:54.0000000', null, N'2019-11-05 18:00:54.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'57', N'防空预警', N'fkyj', N'0', N'54', N'治安管理', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'3', N'0', null, N'2019-11-05 18:01:11.0000000', null, N'2019-11-05 18:01:11.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'62', N'测试11', N'测试11', N'0', N'61', N'测试11', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-06 11:18:14.0000000', null, N'2019-11-06 11:18:14.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'63', N'测试', N'13', N'0', N'61', N'测试', N'4', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'2', N'0', null, N'2019-11-06 11:54:49.0000000', null, N'2019-11-06 11:54:49.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'64', N'1', N'1', N'0', N'62', N'测试11', N'5', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-06 14:39:44.0000000', null, N'2019-11-06 14:39:44.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'65', N'2', N'2', N'0', N'64', N'1', N'6', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', null, N'2019-11-06 14:45:14.0000000', null, N'2019-11-06 14:45:14.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_article_column] ([id], [name], [alias], [type], [parentId], [parentName], [level], [siteId], [summary], [otherSummary], [publishAudit], [shareAudit], [url], [isNav], [frontMaxSize], [frontVisible], [frontListMethod], [listTpl], [contentTpl], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'66', N'分局动态', N'fjdt', N'0', N'31', N'右侧头条', N'3', N'18', null, null, N'0', N'0', null, null, N'10', null, null, N'/list', N'/content', N'1', N'0', N'admin', N'2019-11-06 16:55:08.0000000', N'admin', N'2019-11-06 16:55:08.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for cms_site
-- ----------------------------
DROP TABLE [dbo].[cms_site]
GO
CREATE TABLE [dbo].[cms_site] (
[id] bigint NOT NULL ,
[name] nvarchar(250) NULL ,
[alias] nvarchar(250) NULL ,
[deptId] bigint NULL ,
[realmName] nvarchar(250) NULL ,
[tplInstId] bigint NULL ,
[folder] nvarchar(250) NULL ,
[indexTpl] nvarchar(250) NULL ,
[listTpl] nvarchar(250) NULL ,
[contentTpl] nvarchar(250) NULL ,
[keyword] nvarchar(250) NULL ,
[description] nvarchar(500) NULL ,
[logo] nvarchar(500) NULL ,
[orderNum] int NULL ,
[state] tinyint NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(250) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(250) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO

-- ----------------------------
-- Records of cms_site
-- ----------------------------
INSERT INTO [dbo].[cms_site] ([id], [name], [alias], [deptId], [realmName], [tplInstId], [folder], [indexTpl], [listTpl], [contentTpl], [keyword], [description], [logo], [orderNum], [state], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'18', N'攀枝花市公安局东城分局', N'pzh', N'101', N'', N'30', N'sjw/hb', N'', N'', N'', null, null, null, N'1', N'0', N'0', null, N'2019-11-05 10:06:07.0000000', null, N'2019-11-14 17:59:56.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for cms_template
-- ----------------------------
DROP TABLE [dbo].[cms_template]
GO
CREATE TABLE [dbo].[cms_template] (
[id] bigint NOT NULL ,
[name] nvarchar(250) NULL ,
[indexTpl] nvarchar(250) NULL ,
[listTpl] nvarchar(250) NULL ,
[contentTpl] nvarchar(250) NULL ,
[className] nvarchar(250) NULL ,
[classInfo] nvarchar(MAX) NULL ,
[coverImage] nvarchar(250) NULL ,
[orderNum] int NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(250) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(250) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO

-- ----------------------------
-- Records of cms_template
-- ----------------------------
INSERT INTO [dbo].[cms_template] ([id], [name], [indexTpl], [listTpl], [contentTpl], [className], [classInfo], [coverImage], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'28', N'model2', N'index.html', N'list.html', N'content.html', N'com.feather.cms.analysis.Model2Analysis', null, N'', N'0', N'0', null, N'2019-11-14 15:19:23.0000000', null, N'2019-11-15 15:41:55.0000000', N'蓝版')
GO
GO

-- ----------------------------
-- Table structure for cms_template_attr
-- ----------------------------
DROP TABLE [dbo].[cms_template_attr]
GO
CREATE TABLE [dbo].[cms_template_attr] (
[id] bigint NOT NULL ,
[tplId] bigint NULL ,
[blockName] nvarchar(250) NULL ,
[blockType] nvarchar(250) NULL ,
[method] nvarchar(250) NULL ,
[frontSize] int NULL ,
[textLength] int NULL ,
[orderNum] int NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(250) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(250) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO

-- ----------------------------
-- Records of cms_template_attr
-- ----------------------------
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'30', N'27', N'11', null, N'111', N'15', N'20', N'1', N'0', null, N'2019-11-14 15:10:11.0000000', null, N'2019-11-14 15:11:09.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'31', N'28', N'block1', null, N'logo', N'15', N'18', N'1', N'0', null, N'2019-11-14 15:20:11.0000000', null, N'2019-11-14 15:20:11.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'32', N'28', N'block2', null, N'nav', N'15', N'18', N'2', N'0', null, N'2019-11-14 15:20:29.0000000', null, N'2019-11-14 15:20:29.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'33', N'28', N'block3', null, N'carousel1', N'15', N'18', N'3', N'0', null, N'2019-11-14 15:20:49.0000000', null, N'2019-11-14 15:20:49.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'34', N'28', N'block4', null, N'carousel2', N'15', N'18', N'4', N'0', null, N'2019-11-14 15:20:59.0000000', null, N'2019-11-14 15:20:59.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'35', N'28', N'block5', null, N'list', N'15', N'18', N'5', N'0', null, N'2019-11-14 15:21:12.0000000', null, N'2019-11-14 15:21:12.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'36', N'28', N'block6', null, N'list', N'15', N'18', N'6', N'0', null, N'2019-11-14 15:21:25.0000000', null, N'2019-11-14 15:21:25.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'37', N'28', N'block7', null, N'oneCategoryLink', N'15', N'18', N'7', N'0', null, N'2019-11-14 15:21:41.0000000', null, N'2019-11-14 15:21:41.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'38', N'28', N'block8', null, N'list', N'15', N'18', N'8', N'0', null, N'2019-11-14 15:21:52.0000000', null, N'2019-11-14 15:21:52.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'39', N'28', N'block9', null, N'list', N'15', N'18', N'9', N'0', null, N'2019-11-14 15:22:10.0000000', null, N'2019-11-14 15:22:10.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'40', N'28', N'block10', null, N'list', N'15', N'18', N'10', N'0', null, N'2019-11-14 15:22:20.0000000', null, N'2019-11-14 15:22:20.0000000', null)
GO
GO
INSERT INTO [dbo].[cms_template_attr] ([id], [tplId], [blockName], [blockType], [method], [frontSize], [textLength], [orderNum], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'41', N'28', N'block11', null, N'footerInfo', N'15', N'18', N'11', N'0', null, N'2019-11-14 15:22:33.0000000', null, N'2019-11-14 15:22:33.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for cms_template_inst
-- ----------------------------
DROP TABLE [dbo].[cms_template_inst]
GO
CREATE TABLE [dbo].[cms_template_inst] (
[id] bigint NOT NULL ,
[name] nvarchar(250) NULL ,
[tplId] bigint NULL ,
[tplName] nvarchar(250) NULL ,
[siteId] bigint NULL ,
[siteName] nvarchar(250) NULL ,
[value] nvarchar(MAX) NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(250) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(250) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO

-- ----------------------------
-- Records of cms_template_inst
-- ----------------------------
INSERT INTO [dbo].[cms_template_inst] ([id], [name], [tplId], [tplName], [siteId], [siteName], [value], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'30', N'攀枝花公安局东城分局蓝色模板', N'28', N'model2', N'18', N'攀枝花市公安局东城分局', null, N'0', null, N'2019-11-14 15:25:06.0000000', null, N'2019-11-14 15:25:06.0000000', N'攀枝花公安局东城分局蓝色模板')
GO
GO

-- ----------------------------
-- Indexes structure for table cms_article
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table cms_article
-- ----------------------------
ALTER TABLE [dbo].[cms_article] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table cms_article_column
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table cms_article_column
-- ----------------------------
ALTER TABLE [dbo].[cms_article_column] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table cms_site
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table cms_site
-- ----------------------------
ALTER TABLE [dbo].[cms_site] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table cms_template
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table cms_template
-- ----------------------------
ALTER TABLE [dbo].[cms_template] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table cms_template_attr
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table cms_template_attr
-- ----------------------------
ALTER TABLE [dbo].[cms_template_attr] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table cms_template_inst
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table cms_template_inst
-- ----------------------------
ALTER TABLE [dbo].[cms_template_inst] ADD PRIMARY KEY ([id])
GO
