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

Date: 2020-06-09 09:53:23
*/


-- ----------------------------
-- Table structure for sc_goods
-- ----------------------------
DROP TABLE [dbo].[sc_goods]
GO
CREATE TABLE [dbo].[sc_goods] (
[goods_id] bigint NOT NULL ,
[shop_id] bigint NOT NULL ,
[goods_name] nvarchar(32) NULL ,
[goods_brand] nvarchar(32) NULL ,
[goods_content] nvarchar(255) NULL ,
[goods_price] decimal(18,2) NULL ,
[goods_quantity] int NULL ,
[goods_unit] nvarchar(10) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'shop_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'商家'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'shop_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'商家'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'shop_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'goods_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'商品名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'商品名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'goods_brand')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'商品品牌'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_brand'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'商品品牌'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_brand'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'goods_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'商品描述'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'商品描述'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'goods_price')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'商品价格'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_price'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'商品价格'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_price'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'goods_quantity')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'商品数量'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_quantity'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'商品数量'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_quantity'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'goods_unit')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'单位'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_unit'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'单位'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'goods_unit'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_goods', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_goods'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_goods
-- ----------------------------
INSERT INTO [dbo].[sc_goods] ([goods_id], [shop_id], [goods_name], [goods_brand], [goods_content], [goods_price], [goods_quantity], [goods_unit], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'119734008321019904', N'119733846257307648', N'西瓜', N'西瓜派', N'西瓜西瓜西瓜大西瓜', N'100.00', N'0', null, null, N'20191127094012', null, N'20191127145609', null)
GO
GO
INSERT INTO [dbo].[sc_goods] ([goods_id], [shop_id], [goods_name], [goods_brand], [goods_content], [goods_price], [goods_quantity], [goods_unit], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'119734328442884096', N'119733846257307648', N'香蕉', N'海南', N'海南产的香蕉', N'20.00', N'0', null, null, N'20191127094128', null, N'20191127094128', null)
GO
GO

-- ----------------------------
-- Table structure for sc_indexslide
-- ----------------------------
DROP TABLE [dbo].[sc_indexslide]
GO
CREATE TABLE [dbo].[sc_indexslide] (
[slide_id] bigint NOT NULL ,
[slide_path] nvarchar(255) NULL ,
[slide_sort] int NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_indexslide', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'首页轮播图'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'首页轮播图'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_indexslide', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_indexslide', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_indexslide', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_indexslide', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_indexslide', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_indexslide'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_indexslide
-- ----------------------------
INSERT INTO [dbo].[sc_indexslide] ([slide_id], [slide_path], [slide_sort], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'120093113942413312', N'/profile/upload/2019/11/28/2b383120b79edc1e821153562de653d3.jpg', N'1', N'', N'20191128092709', N'', N'20191128092709', null)
GO
GO
INSERT INTO [dbo].[sc_indexslide] ([slide_id], [slide_path], [slide_sort], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'120093145118674944', N'/profile/upload/2019/11/28/615a8490250a55a4be00295b55a4307d.jpg', N'2', N'', N'20191128092717', N'', N'20191128092717', null)
GO
GO
INSERT INTO [dbo].[sc_indexslide] ([slide_id], [slide_path], [slide_sort], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'120093172784304128', N'/profile/upload/2019/11/28/4276b8bb4b0a4cf7e3c1ffdcebadee3c.jpg', N'3', N'', N'20191128092723', N'', N'20191128092723', null)
GO
GO

-- ----------------------------
-- Table structure for sc_order
-- ----------------------------
DROP TABLE [dbo].[sc_order]
GO
CREATE TABLE [dbo].[sc_order] (
[order_id] bigint NOT NULL ,
[order_name] nvarchar(32) NULL ,
[goods_id] bigint NOT NULL ,
[equipmentId] nvarchar(64) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_order', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_order', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_order', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'编辑人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'编辑人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_order', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'编辑时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'编辑时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_order'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO

-- ----------------------------
-- Records of sc_order
-- ----------------------------
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119833348976807936', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127161456', null, N'20191127161456')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119833362063036416', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127161459', null, N'20191127161459')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119836845587697664', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127162850', null, N'20191127162850')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119836849236742144', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127162851', null, N'20191127162851')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119836850251763712', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127162851', null, N'20191127162851')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119836851623301120', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127162851', null, N'20191127162851')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839392696242176', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163857', null, N'20191127163857')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839398048174080', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163859', null, N'20191127163859')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839409603481600', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163901', null, N'20191127163901')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839413655179264', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163902', null, N'20191127163902')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839418256330752', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163903', null, N'20191127163903')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839421553053696', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163904', null, N'20191127163904')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839427060174848', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163905', null, N'20191127163905')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839434286960640', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163907', null, N'20191127163907')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839443665424384', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163909', null, N'20191127163909')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839450523111424', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163911', null, N'20191127163911')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839462275551232', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127163914', null, N'20191127163914')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839465027014656', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127163914', null, N'20191127163914')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839471020675072', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127163916', null, N'20191127163916')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839478180352000', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163918', null, N'20191127163918')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839483322568704', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163919', null, N'20191127163919')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839487059693568', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163920', null, N'20191127163920')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839500259168256', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163923', null, N'20191127163923')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839598422659072', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127163946', null, N'20191127163946')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839622657347584', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127163952', null, N'20191127163952')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839630806880256', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163954', null, N'20191127163954')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119839637396131840', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191127163956', null, N'20191127163956')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119846239679418368', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127170610', null, N'20191127170610')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'119846267282132992', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191127170616', null, N'20191127170616')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'120095940211249152', N'123加入西瓜', N'119734008321019904', N'123', null, N'20191128093823', null, N'20191128093823')
GO
GO
INSERT INTO [dbo].[sc_order] ([order_id], [order_name], [goods_id], [equipmentId], [create_by], [create_time], [update_by], [update_time]) VALUES (N'120096346505089024', N'123加入香蕉', N'119734328442884096', N'123', null, N'20191128094000', null, N'20191128094000')
GO
GO

-- ----------------------------
-- Table structure for sc_parkrent
-- ----------------------------
DROP TABLE [dbo].[sc_parkrent]
GO
CREATE TABLE [dbo].[sc_parkrent] (
[parkrent_id] bigint NOT NULL ,
[parkrent_name] nvarchar(12) NULL ,
[parkrent_title] nvarchar(32) NULL ,
[parkrent_type] nchar(2) NULL ,
[parkrent_tel] nvarchar(12) NULL ,
[parkrent_content] nvarchar(2000) NULL ,
[parkrent_file] nvarchar(12) NULL ,
[parkrent_status] nchar(2) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL ,
[parkent_audit_state] nchar(2) NULL ,
[equipment_id] nvarchar(128) NULL ,
[begin_Time] bigint NULL ,
[end_Time] bigint NULL ,
[audit_state] nvarchar(10) NULL ,
[audit_pass_state] nvarchar(10) NULL ,
[audit_content] nvarchar(255) NULL ,
[audit_time] bigint NULL ,
[audit_by] nvarchar(32) NULL ,
[parkrent_score] int NULL ,
[del_flag] nchar(2) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'parkrent_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'parkrent_title')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_title'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_title'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'parkrent_type')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_type'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_type'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'parkrent_tel')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'联系电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_tel'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'联系电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_tel'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'parkrent_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'parkrent_file')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'文件路径'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_file'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'文件路径'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_file'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'parkrent_status')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_status'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'parkrent_status'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'remark'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'begin_Time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'任务开始时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'begin_Time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'任务开始时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'begin_Time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'end_Time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'任务结束时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'end_Time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'任务结束时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'end_Time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'audit_state')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_state'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_state'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'audit_pass_state')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核通过状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_pass_state'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核通过状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_pass_state'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'audit_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'audit_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_parkrent', 
'COLUMN', N'audit_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_parkrent'
, @level2type = 'COLUMN', @level2name = N'audit_by'
GO

-- ----------------------------
-- Records of sc_parkrent
-- ----------------------------
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'119467402437201920', N'文化建设', N'2019年中国文联志愿服务项目贵州省基层文艺人才书法培训班', N'2 ', null, N'围绕学习贯彻习近平新时代中国特色社会主义思想，习近平总书记关于宣传思想工作重要论述作专题辅导。围绕书法艺术专业知识和前沿理论作专题辅导和现场教学指导。', null, N'0 ', null, N'20191126160048', null, N'20191126160850', null, N'1 ', null, N'20191115000000', N'20191130000000', N'1', N'0', null, null, null, null, N'0 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'119860337439805440', N'社区保健院', N'万医下基层', N'2 ', N'02379273843', N' 万医下基层', null, N'0 ', null, N'20191127180211', null, N'20191127180226', null, N'1 ', null, null, null, N'1', N'0', null, null, null, null, N'0 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120124389755850752', N'文件建设志愿者', N'首届青少年机器人大赛志愿服务活动', N'2 ', N'13765731720', N'岗位描述：
（一）2019年11月23日必须全天参与，2019年11月22号（星期五）中午十二点大礼堂集中前往铜仁一中体育馆培训。
（二）请慎重报名此次志愿服务活动，被录用后必须参加活动，不得请假，不得早退。

岗位条件：
1、我校学生志愿者；
2、需为男生；
3、慎重选择，一旦被录用，不得请假', null, N'0 ', null, N'20191128113126', null, N'20191128135430', null, N'1 ', N'12345789', null, null, N'1', N'0', null, null, null, null, N'0 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120127209313800192', N'测试', N'发布心愿树', N'1 ', N'87914590', N'测试', null, N'0 ', null, N'20191128114238', null, N'20191128114238', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120156531554652160', N'1', N'1', N'1 ', N'1', N' 1', null, N'0 ', null, N'20191128133909', null, N'20191128133954', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120158156444798976', N'高以翔', N'高以翔《追我吧》节目酬劳曝光 一集仅15万', N'2 ', N'13765731720', N'台湾艺人高以翔因录制综艺节目《追我吧》意外去世。据台媒报道，高以翔以往在大陆录制综艺节目一集酬劳约在100至150万元台币之间（约23万至34万人民币），这次在《追我吧》固定班底黄景瑜邀请下，才以66万元台币（15万人民币）友情价录该节目，没想到却发生憾事。当天他忍着9度低温，进行极限挑战，目的就是让观众看见他努力和敬业的表现，却让正值壮年的他因此失去一条宝贵生命。', null, N'0 ', null, N'20191128134537', null, N'20191128135427', null, N'1 ', N'12345789', null, null, N'1', N'0', null, null, null, null, N'0 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120158882797588480', N'测试', N'测试', N'1 ', N'87914590', N'测试', null, N'0 ', null, N'20191128134830', null, N'20191128134830', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120160988237533184', N'测的', N'技术控', N'1 ', N'测试', N'测试呢', null, N'0 ', null, N'20191128135652', null, N'20191128135652', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120164819004428288', N'测试', N'测试', N'1 ', N'15871718535', N'测试一下', null, N'0 ', null, N'20191128141205', null, N'20191128141205', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120167012042412032', N'测', N'测试', N'1 ', N'135544890839', N'测', null, N'0 ', null, N'20191128142048', null, N'20191128142048', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120168475573489664', N'测试', N'测试', N'1 ', N'13547896796', N'测试测试一下那是啥', null, N'0 ', null, N'20191128142637', null, N'20191128142637', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120169080828334080', N'测试', N'测试', N'1 ', N'87914590', N'可是', null, N'0 ', null, N'20191128142901', null, N'20191128142901', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120169615790837760', N'测试', N'测试', N'1 ', N'122', N'测试', null, N'0 ', null, N'20191128143109', null, N'20191128143109', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120173343134584832', N'看的', N'清空', N'1 ', N'1234566', N'通道', null, N'0 ', null, N'20191128144557', null, N'20191128144557', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120173976352854016', N'订单的', N'测', N'1 ', N'111111', N'呜呜呜呜', null, N'0 ', null, N'20191128144828', null, N'20191128144828', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120174406562615296', N'测试', N'啊对孩子呀', N'1 ', N'134866', N'个性的', null, N'0 ', null, N'20191128145011', null, N'20191128145011', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120175630615711744', N'测试', N'啊对话', N'1 ', N'测试', N'听到', null, N'0 ', null, N'20191128145536', null, N'20191128145536', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120176340048678912', N'测试', N'测试心愿树', N'1 ', N'1355478844', N'测试心愿树', null, N'0 ', null, N'20191128145752', null, N'20191128145752', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120177436779483136', N'app', N'测试图片', N'1 ', N'12222', N'测试app图片', null, N'0 ', null, N'20191128150213', null, N'20191128150213', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120177970085236736', N'测试', N'测试', N'1 ', N'测试', N'时候', null, N'0 ', null, N'20191128150421', null, N'20191128150421', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120179764060360704', N'测试', N'可乐', N'1 ', N'134444444444', N'需要可乐', null, N'0 ', null, N'20191128151128', null, N'20191128151128', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120180323882504192', N'hhh', N'测试', N'1 ', N'134444', N'燃气热水器', null, N'0 ', null, N'20191128151342', null, N'20191128151342', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120187661473419264', N'1', N'1', N'1 ', N'11', N'1', null, N'0 ', null, N'20191128154251', null, N'20191128154251', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120188051803738112', N'头模', N'的的', N'1 ', N'111', N'同弄', null, N'0 ', null, N'20191128154424', null, N'20191128154424', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120189094050533376', N'文', N'有问题', N'1 ', N'87914590', N'有个问题测试一下', null, N'0 ', null, N'20191128154833', null, N'20191128154833', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120189505163628544', N'85666', N'擦擦擦擦擦', N'1 ', N'111111', N' 我默默', null, N'0 ', null, N'20191128155011', null, N'20191128155011', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120189809837871104', N'天空弄', N'图我', N'1 ', N'2778', N' yoooooo', null, N'0 ', null, N'20191128155123', null, N'20191128155123', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120190643556454400', N'1', N'1', N'1 ', N'1', N'1', null, N'0 ', null, N'20191128155442', null, N'20191128155442', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120191432114966528', N'33', N'33', N'1 ', N'333', N'33', null, N'0 ', null, N'20191128155750', null, N'20191128155750', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120192704687771648', N'1', N'1', N'1 ', N'1', N'1', null, N'0 ', null, N'20191128160253', null, N'20191128160253', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120197265691381760', N'1', N'1', N'1 ', N'1', N'1', null, N'0 ', null, N'20191128162101', null, N'20191128162101', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120198001007398912', N'cvh', N'默默', N'1 ', N'4677', N'天空弄', null, N'0 ', null, N'20191128162356', null, N'20191128162356', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120198481146155008', N'1', N'1', N'1 ', N'1', N'1', null, N'0 ', null, N'20191128162551', null, N'20191128162551', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120203921812754432', N'1', N'1', N'1 ', N'1', N'1', null, N'0 ', null, N'20191128164728', null, N'20191128164728', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120206076594491392', N'可', N'测试', N'1 ', N'15', N'可乐', null, N'0 ', null, N'20191128165601', null, N'20191128165601', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO
INSERT INTO [dbo].[sc_parkrent] ([parkrent_id], [parkrent_name], [parkrent_title], [parkrent_type], [parkrent_tel], [parkrent_content], [parkrent_file], [parkrent_status], [create_by], [create_time], [update_by], [update_time], [remark], [parkent_audit_state], [equipment_id], [begin_Time], [end_Time], [audit_state], [audit_pass_state], [audit_content], [audit_time], [audit_by], [parkrent_score], [del_flag]) VALUES (N'120225576261914624', N'啦啦啦', N'测试', N'1 ', N'6777', N'他在客厅呢', null, N'0 ', null, N'20191128181331', null, N'20191128181331', null, N'0 ', N'111', null, null, N'0', null, null, null, null, null, N'1 ')
GO
GO

-- ----------------------------
-- Table structure for sc_partybuild
-- ----------------------------
DROP TABLE [dbo].[sc_partybuild]
GO
CREATE TABLE [dbo].[sc_partybuild] (
[partybuild_id] bigint NOT NULL ,
[partybuild_title] nvarchar(128) NULL ,
[partybuild_content] nvarchar(MAX) NULL ,
[partybuild_file] nvarchar(128) NULL ,
[partybuild_type] nvarchar(255) NULL ,
[partybuild_status] nchar(2) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL ,
[partybuild_source] nvarchar(128) NULL ,
[partybuild_time] nvarchar(32) NULL ,
[partybuild_cover] nvarchar(128) NULL ,
[video_update_type] nvarchar(20) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'党建信息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'党建信息'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'partybuild_title')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_title'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_title'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'partybuild_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'partybuild_file')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'文件路径'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_file'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'文件路径'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_file'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'partybuild_type')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_type'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_type'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'partybuild_status')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_status'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'partybuild_status'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_partybuild', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_partybuild'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_partybuild
-- ----------------------------
INSERT INTO [dbo].[sc_partybuild] ([partybuild_id], [partybuild_title], [partybuild_content], [partybuild_file], [partybuild_type], [partybuild_status], [create_by], [create_time], [update_by], [update_time], [remark], [partybuild_source], [partybuild_time], [partybuild_cover], [video_update_type]) VALUES (N'119496166340694016', N'马头琴的来历', N'<span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">很久很久以前，草原上有个放牛娃，名字叫苏和。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　一天，太阳下山了，天慢慢地黑下来了，苏和才赶着羊回家。走着，走着，忽然看见前面路边有个毛茸茸的东西，走近一看，啊，原来是一匹刚生下来的小白马，多可怜啊，苏和就把它抱回家去养着。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　日子一天天过去了，小白马一天天长大了，浑身雪白，又美丽又健壮，人人见了人人爱。苏和更是爱得不得了，每天骑着小白马去放羊，他们真象一对好朋友，一时一刻也分不开。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　一年，草原上的王爷举行赛马会，四面八方的人都去参加。苏和对他心爱得小白马说：“小白马，小白马，人家都去参加赛马会，咱们也去，好吗？”小白马不会说话，一边点着头，一边咴咴地叫，好象在说：“咱们也去，咱们也去！”苏和别说有多高兴了，他带上干粮，骑着小白马也去参加了。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　赛马会开始了，好多身强力壮的小伙子，骑着棕色的、黑色的、黄色的马在草原上奔跑，可谁也没有苏和的小白马跑得快。小白马象一道闪电，一会儿就到了目的地。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　王爷一看，得第一名的是个穷小子，心里很不高兴，他让人把苏和叫来，对他说：“你是个穷小子，不配骑这样好的马。喏，我给你三个金元宝，把这匹小白马卖给我。你回去吧！”苏和怎么舍得他心爱的小白马啊，他对王爷说：“我是来赛马的，不是来卖马的！”说着牵了小白马就走。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　王爷一听发了火：“你这个放羊的穷小子，敢顶撞我王爷！来人啊！拉下去狠狠地打！”苏和挨了一顿打，被王爷赶了回去。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　王爷抢了苏和的小白马，就想在别人面前显一显。第二天，王爷摆了酒席，请了许多许多客人，王爷对大家说：“我刚得了匹小白马，奔跑起来，就象一道闪电，谁也比不过它。你们好好瞧着。”</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　他说完话，就骑上了小白马，可是小白马一动也不动，王爷生气了，就拿鞭子打它，这一打可不得了，小白马猛得一跳，把王爷摔了个四脚朝天，小白马撒开腿就跑，去找它的小主人苏和了。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　“捉住它，捉住它！”王爷从地上爬起来，没命地喊着。可是谁也追不上它，王爷接着喊：“别让它跑了。用箭射死它！”</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　几十支箭，嗖嗖嗖嗖，向小白马射去。小白马让箭射中了，血不断地流出来。可是小白马很勇敢，它忍着痛，一个劲地向前跑，一直跑到小主人苏和家。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　苏和给打得浑身上下都是伤，躺着一动也不动，心里正想着他的小白马，忽然听见一阵咴咴咴的叫声，啊，是小白马，是小白马，是小白马回来了。他忍住痛，一个翻身爬起来，打开门一看，真的是小白马回来了，可是小白马呀，雪白的毛都让血染红了，它亲了亲小主人苏和的脸，倒在地上就死了。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　小白马死了，苏和几夜都睡不着觉，心里不停地说着：“小白马回来吧！小白马回来吧！”一天晚上，苏和刚一睡着，看见小白马回来了。苏和搂着小白马的脖子，亲了又亲，说：“小白马，我真想你啊！”小白马轻轻地说：“我的小主人，我也真想你啊！你拿我身上的东西做一把琴吧！这样，我们就永远在一起了。”</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　苏和睁开眼睛一看，小白马不见了，原来刚才是在做梦呢。他含着眼泪拿小白马的骨头做了一把琴，拿它的筋做弦，拿它的尾巴骨做弓，琴杆顶上雕刻了个马头。这就是马头琴的来历。从此，苏和天天拉琴，拉了许多好听的曲子，远远听起来，就象小白马在唱歌。</span><br style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft Yahei&quot;, 宋体, Helvetica, Arial, sans-serif; font-size: 16px; text-size-adjust: auto; background-color: rgb(249, 247, 243);">　　其它的牧民听到这优美的曲子，都学着苏和的琴的样子，用木头做了许多马头琴，他们一边放牧一边弹着马头琴。就这样马头琴传遍了整个草原。</span>', N'/profile/upload/2019/11/26/2468709c268826c8f2267a442376cec1.html', N'2', N'0 ', null, N'20191126175506', null, N'20191126175506', null, N'中国红故事', N'2019-55-26', null, N'no_video')
GO
GO
INSERT INTO [dbo].[sc_partybuild] ([partybuild_id], [partybuild_title], [partybuild_content], [partybuild_file], [partybuild_type], [partybuild_status], [create_by], [create_time], [update_by], [update_time], [remark], [partybuild_source], [partybuild_time], [partybuild_cover], [video_update_type]) VALUES (N'119496819981029376', N'人有德，必有福（精辟）', N'<span style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;">很多人终其一生，都在寻找幸福，那么，幸福到底是什么？其实幸福往往无关金钱名利，无关权势地位，而在于做人的德行。</span><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;">有个词说得好，“厚德载物”，这是多少年来古今圣贤总结出的一个亘古不变的真理，德，往往是一个人做人的根本。</span><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;">人生，是一场修行；人心，是一块良田，种什么就会得什么。</span><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;">一个人一辈子，不仅会有顺境，很多时候，还会遭遇逆境，但不管处在哪种境地，首先都要做到自信豁达，培养自己良好的修养和道德。</span><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;">己所不欲，勿施于人。一个人若想得到别人的尊敬，首先就要与人为善，做什么事情，首先都要考虑别人的感受，不能只想着自己。</span><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;">做人，缺什么都不能缺德，不能背信弃义见利忘义，不能朝三暮四喜新厌旧，一定要有一颗善良的心和良好的德行。</span><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><br style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;"><span style="caret-color: rgb(68, 68, 68); color: rgb(68, 68, 68); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑; font-size: 16px; text-size-adjust: auto;">世间万物，皆有因果。人在做，天在看，积善之家，必有余庆，积恶之家，必有余殃。你有多少德，就会有多少福。</span>', N'/profile/upload/2019/11/26/9164e55a87db084cd0f32045e27be9ac.html', N'3', null, null, N'20191126175742', null, N'20191126175742', null, N'人生感悟', N'2019-57-26', null, N'no_video')
GO
GO

-- ----------------------------
-- Table structure for sc_point
-- ----------------------------
DROP TABLE [dbo].[sc_point]
GO
CREATE TABLE [dbo].[sc_point] (
[id] bigint NOT NULL ,
[sum_point] bigint NULL ,
[user_id] bigint NULL ,
[user_name] nvarchar(250) NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'sum_point')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'总积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'sum_point'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'总积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'sum_point'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'user_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'用户id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'user_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'用户id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'user_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'user_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'所属用户'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'user_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'所属用户'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'user_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'del_flag')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'del_flag'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'del_flag'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_point
-- ----------------------------
INSERT INTO [dbo].[sc_point] ([id], [sum_point], [user_id], [user_name], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'2', N'14', N'1', N'管理员', N'0', N'admin', N'2019-10-18 17:19:16.0000000', N'admin', N'2019-10-18 17:19:16.0000000', null)
GO
GO
INSERT INTO [dbo].[sc_point] ([id], [sum_point], [user_id], [user_name], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'3', N'3', N'2', N'若依', N'0', N'admin', N'2019-10-22 14:36:47.0000000', N'admin', N'2019-10-22 14:36:47.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for sc_point_copy1
-- ----------------------------
DROP TABLE [dbo].[sc_point_copy1]
GO
CREATE TABLE [dbo].[sc_point_copy1] (
[id] bigint NOT NULL ,
[sum_point] bigint NULL ,
[user_id] bigint NULL ,
[user_name] nvarchar(250) NULL ,
[del_flag] nchar(1) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'sum_point')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'总积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'sum_point'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'总积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'sum_point'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'user_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'用户id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'user_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'用户id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'user_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'user_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'所属用户'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'user_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'所属用户'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'user_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'del_flag')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'del_flag'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'del_flag'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_copy1', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_copy1'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_point_copy1
-- ----------------------------
INSERT INTO [dbo].[sc_point_copy1] ([id], [sum_point], [user_id], [user_name], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'2', N'14', N'1', N'管理员', N'0', N'admin', N'2019-10-18 17:19:16.0000000', N'admin', N'2019-10-18 17:19:16.0000000', null)
GO
GO
INSERT INTO [dbo].[sc_point_copy1] ([id], [sum_point], [user_id], [user_name], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'3', N'3', N'2', N'若依', N'0', N'admin', N'2019-10-22 14:36:47.0000000', N'admin', N'2019-10-22 14:36:47.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for sc_point_detail
-- ----------------------------
DROP TABLE [dbo].[sc_point_detail]
GO
CREATE TABLE [dbo].[sc_point_detail] (
[id] bigint NOT NULL ,
[point_id] bigint NULL ,
[task_id] bigint NULL ,
[score] int NULL ,
[chinese_score] nvarchar(250) NULL ,
[del_flag] nvarchar(1) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'point_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'分积id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'point_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'分积id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'point_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'task_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'任务id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'task_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'任务id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'task_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'score')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'分数'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'score'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'分数'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'score'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'chinese_score')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'文中分数，用于描述是增加还是减少积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'chinese_score'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'文中分数，用于描述是增加还是减少积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'chinese_score'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'del_flag')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'del_flag'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'del_flag'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_point_detail', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_point_detail'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_point_detail
-- ----------------------------
INSERT INTO [dbo].[sc_point_detail] ([id], [point_id], [task_id], [score], [chinese_score], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'34', N'2', N'39', N'10', N'增加10积分', N'0', N'admin', N'2019-10-18 17:19:16.0000000', N'admin', N'2019-10-18 17:19:16.0000000', null)
GO
GO
INSERT INTO [dbo].[sc_point_detail] ([id], [point_id], [task_id], [score], [chinese_score], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'35', N'3', N'40', N'3', N'增加3积分', N'0', N'admin', N'2019-10-22 14:36:48.0000000', N'admin', N'2019-10-22 14:36:48.0000000', null)
GO
GO
INSERT INTO [dbo].[sc_point_detail] ([id], [point_id], [task_id], [score], [chinese_score], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'36', N'2', N'41', N'4', N'增加4积分', N'0', N'admin', N'2019-10-22 14:37:21.0000000', N'admin', N'2019-10-22 14:37:21.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for sc_prizes
-- ----------------------------
DROP TABLE [dbo].[sc_prizes]
GO
CREATE TABLE [dbo].[sc_prizes] (
[prizes_id] bigint NOT NULL ,
[prizes_name] nvarchar(32) NULL ,
[prizes_num] int NULL ,
[prizes_score] decimal(18,2) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'prizes_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'奖品id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'奖品id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'prizes_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'奖品名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'奖品名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'prizes_num')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'奖品数量'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_num'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'奖品数量'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_num'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'prizes_score')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'奖品兑换分数'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_score'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'奖品兑换分数'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'prizes_score'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_prizes
-- ----------------------------
INSERT INTO [dbo].[sc_prizes] ([prizes_id], [prizes_name], [prizes_num], [prizes_score], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'119475256690872320', N'保温杯', N'50', N'15.00', null, N'20191126163200', null, N'20191126163233', null)
GO
GO
INSERT INTO [dbo].[sc_prizes] ([prizes_id], [prizes_name], [prizes_num], [prizes_score], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'119475353226973184', N'洗衣液', N'100', N'20.00', null, N'20191126163223', null, N'20191126163223', null)
GO
GO

-- ----------------------------
-- Table structure for sc_prizes_volunteer
-- ----------------------------
DROP TABLE [dbo].[sc_prizes_volunteer]
GO
CREATE TABLE [dbo].[sc_prizes_volunteer] (
[pv_id] bigint NOT NULL ,
[volunteer_id] bigint NOT NULL ,
[prizes_id] bigint NOT NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes_volunteer', 
'COLUMN', N'pv_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'志愿者领奖'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'pv_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'志愿者领奖'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'pv_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes_volunteer', 
'COLUMN', N'volunteer_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'志愿者id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'志愿者id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes_volunteer', 
'COLUMN', N'prizes_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'奖品id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'prizes_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'奖品id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'prizes_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes_volunteer', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes_volunteer', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes_volunteer', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'编辑人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'编辑人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_prizes_volunteer', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'编辑时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'编辑时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_prizes_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO

-- ----------------------------
-- Records of sc_prizes_volunteer
-- ----------------------------

-- ----------------------------
-- Table structure for sc_register
-- ----------------------------
DROP TABLE [dbo].[sc_register]
GO
CREATE TABLE [dbo].[sc_register] (
[register_id] bigint NOT NULL ,
[register_name] nvarchar(64) NULL ,
[register_tel] nvarchar(32) NULL ,
[register_sex] nvarchar(10) NULL ,
[register_bir] nvarchar(32) NULL ,
[register_idcard] nvarchar(255) NULL ,
[register_address] nvarchar(256) NULL ,
[register_profession] nvarchar(128) NULL ,
[register_synopsis] nvarchar(512) NULL ,
[audit_state] nchar(2) NULL ,
[audit_pass_state] nchar(2) NULL ,
[audit_by] nvarchar(64) NULL ,
[audit_time] bigint NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[equipmentId] nvarchar(128) NULL ,
[audit_content] nvarchar(255) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_tel')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_tel'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_tel'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_sex')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'性别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_sex'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'性别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_sex'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_bir')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'出生日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_bir'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'出生日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_bir'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_idcard')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'身份证号'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_idcard'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'身份证号'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_idcard'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_address')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'地址'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_address'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'地址'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_address'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_profession')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'职业'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_profession'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'职业'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_profession'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'register_synopsis')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'个人简介'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_synopsis'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'个人简介'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'register_synopsis'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'audit_state')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_state'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_state'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'audit_pass_state')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核通过状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_pass_state'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核通过状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_pass_state'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'audit_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'audit_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'audit_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'编辑人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'编辑人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_register', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'编辑时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'编辑时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_register'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO

-- ----------------------------
-- Records of sc_register
-- ----------------------------
INSERT INTO [dbo].[sc_register] ([register_id], [register_name], [register_tel], [register_sex], [register_bir], [register_idcard], [register_address], [register_profession], [register_synopsis], [audit_state], [audit_pass_state], [audit_by], [audit_time], [create_by], [create_time], [update_by], [update_time], [equipmentId], [audit_content]) VALUES (N'119473053737553920', N'刘亦菲', N'18877664431', N'1', N'1987-08-25', N'433778829293748327482', N'湖北省武汉市', N'北京电影学院2002级表演系本科班', N'刘亦菲，1987年8月25日出生于湖北省武汉市，华语影视女演员、歌手，毕业于北京电影学院2002级表演系本科班。演艺事业外，刘亦菲还热心慈善公益。2008年担任中国少年儿童基金会形象大使 [20]  ；2009年被授予“中国儿童慈善奖”', N'1 ', N'0 ', null, N'20191126162335', null, N'20191126162315', null, N'20191126162335', N'11111', null)
GO
GO
INSERT INTO [dbo].[sc_register] ([register_id], [register_name], [register_tel], [register_sex], [register_bir], [register_idcard], [register_address], [register_profession], [register_synopsis], [audit_state], [audit_pass_state], [audit_by], [audit_time], [create_by], [create_time], [update_by], [update_time], [equipmentId], [audit_content]) VALUES (N'119473084062371840', N'袁弘', N'18877664433', N'0', N'1982-08-23', N'433778829293748327482', N'湖北省武汉市', N'上海戏剧学院表演系2001级本科班', N'袁弘，1982年8月23日出生于湖北省武汉市，中国内地影视男演员，毕业于上海戏剧学院表演系2001级本科班', N'1 ', N'0 ', null, N'20191126162322', null, N'20191126162014', null, N'20191126162323', N'11111', null)
GO
GO
INSERT INTO [dbo].[sc_register] ([register_id], [register_name], [register_tel], [register_sex], [register_bir], [register_idcard], [register_address], [register_profession], [register_synopsis], [audit_state], [audit_pass_state], [audit_by], [audit_time], [create_by], [create_time], [update_by], [update_time], [equipmentId], [audit_content]) VALUES (N'119473137686548480', N'胡歌', N'18877664432', N'0', N'1982-09-20', N'433778829293748327482', N'上海市徐汇区', N'上海戏剧学院表演系2001级本科班', N'胡歌，1982年9月20日出生于上海市徐汇区，中国内地男演员、歌手。2019年，主演的刁亦男执导电影《南方车站的聚会》入围戛纳国际电影节主竞赛单元。', N'1 ', N'0 ', null, N'20191126162326', null, N'20191126162140', null, N'20191126162326', N'11111', null)
GO
GO
INSERT INTO [dbo].[sc_register] ([register_id], [register_name], [register_tel], [register_sex], [register_bir], [register_idcard], [register_address], [register_profession], [register_synopsis], [audit_state], [audit_pass_state], [audit_by], [audit_time], [create_by], [create_time], [update_by], [update_time], [equipmentId], [audit_content]) VALUES (N'119473683034148864', N'刘诗诗', N'18877664430', N'1', N'1987-03-10', N'433778829293748327482', N'北京市', N'北京电影学院2002级表演系本科班', N'刘诗诗，原名刘诗施，1987年3月10日出生于北京市，中国内地影视女演员、影视出品人', N'1 ', N'0 ', null, N'20191126162545', null, N'20191126162538', null, N'20191126162545', N'11111', null)
GO
GO

-- ----------------------------
-- Table structure for sc_service
-- ----------------------------
DROP TABLE [dbo].[sc_service]
GO
CREATE TABLE [dbo].[sc_service] (
[service_id] bigint NOT NULL ,
[service_dept] bigint NOT NULL ,
[service_name] nvarchar(32) NULL ,
[service_type] nvarchar(20) NULL ,
[service_content] nvarchar(MAX) NULL ,
[service_file] nvarchar(128) NULL ,
[create_by] nvarchar(32) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(32) NULL ,
[update_time] bigint NULL ,
[service_ctype] nvarchar(2) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_service', 
'COLUMN', N'service_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'部门名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'部门名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_service', 
'COLUMN', N'service_type')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'服务类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_type'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'服务类型'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_type'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_service', 
'COLUMN', N'service_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'服务内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'服务内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_service', 
'COLUMN', N'service_file')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'内容文件'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_file'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'内容文件'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_service'
, @level2type = 'COLUMN', @level2name = N'service_file'
GO

-- ----------------------------
-- Records of sc_service
-- ----------------------------
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'114385104163442688', N'101', N'凤凰社区', N'sc_dq_type', N'电话：1232784723847', N'/profile/upload/2019/11/26/80ebbd3de274ca6d57fd22fa18ddcb92.html', null, N'20191112152534', null, N'20191126135706', N'2')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'114385928134463488', N'101', N'凤凰社区', N'sc_service_type', N'电话：社区医院', N'/profile/upload/2019/11/26/377b51faf2d6c2fac93bfdd4669afbf6.html', null, N'20191112152850', null, N'20191126135827', N'2')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119372810605957120', N'101', N'凤凰社区', N'sc_dq_type', N'电话：12322131232', N'/profile/upload/2019/11/26/8c3343d0ad7ce40ce172a479a5dc2c11.html', null, N'20191126094507', null, N'20191126134123', N'1')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119374165961740288', N'101', N'凤凰社区', N'sc_dq_type', N'电话：12372131', N'/profile/upload/2019/11/26/35dcfd577245dfeb73e1857593f82f64.html', null, N'20191126095019', null, N'20191126135741', N'4')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119375957030211584', N'101', N'凤凰社区', N'sc_dq_type', N'电话:123142342', N'/profile/upload/2019/11/26/abb969a70773806e11c49e50570a1baa.html', null, N'20191126095726', null, N'20191126135710', N'3')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119384577750994944', N'101', N'凤凰社区', N'sc_service_type', N'物业电话：2912342342', N'/profile/upload/2019/11/26/daa478f5f4708f49fecdd7f0e71f759c.html', null, N'20191126103141', null, N'20191126135845', N'3')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119445315710488576', N'101', N'凤凰社区', N'sc_service_type', N'<p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">新生孩子上<a href="https://www.66law.cn/special/hkszd/" title="户口" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">户口</a>要在小孩出生一个月内，到父母任何一方的当地派出所的户证部办理入户。那么新生孩子上户流程是什么？新生儿上户口需要什么证件？关于以上法律问题，<a href="https://www.66law.cn/" title="华律网" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">华律网</a>小编为您解答，希望对您有所帮助。</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;"><strong>一、新生儿上户口需要什么证件</strong></p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">依据新生儿落户随父随母自愿的原则，可以随父也可以随母落户。所需材料：<a href="https://www.66law.cn/special/jiehunzheng/" title="结婚证" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">结婚证</a>、新生儿父母的书面申请、新生婴儿的出生医学证明、其父或其母的户口簿，向户籍地公安派出所申报出生登记即可。</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;"><strong>二、新生孩子上户口流程</strong></p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">（一）办理地址：</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">新生孩子上户口要在小孩出生一个月内，到父母任何一方的当地派出所的户证部办理</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">（二）办理材料：<br></p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">1.婴儿《出生医学证明》(复印件1份，验原件);</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">2.父母<a href="https://www.66law.cn/laws/hunyinjiating/jiehun/" title="结婚" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">结婚</a>证、<a href="https://www.66law.cn/special/jmsfz/" title="身份证" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">身份证</a>、户口簿(复印件各1份，验原件);</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">3.拟入户地街道计划<a href="https://www.66law.cn/special/rkyjhsyf/" title="生育" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">生育</a>工作机构出具的<a href="https://www.66law.cn/special/rkyjhsyf/" title="计划生育" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">计划生育</a>证明(原件1份);</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">4.政策内生育一孩的，提供母亲的计划生育服务证;政策内生育二孩的，提供二孩生育审批表(复印件1份，验原件);</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">5.超过一个月申报，提交婴儿未随户口在外市县父或母一方入户的证明(原件1份)。</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">（三）办理方式：</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">1.首先要凭出生证到所在居委会开具“新生儿入户通知单”;</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">2.其次带着父母双方户口本、身份证、结婚证、孩子的<a href="https://www.66law.cn/special/chushengzheng/" title="出生证" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">出生证</a>明、生育服务证、入户通知单到所在街道办事处盖章;</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">3.最后去所在街道的派出所<a href="https://www.66law.cn/special/hjgg/" title="户籍" target="_blank" style="transition: none 0s ease 0s; color: rgb(41, 122, 204) !important;">户籍</a>科办理户口，带齐父母双方户口本、身份证、结婚证、孩子的出生证明、生育服务证。</p><p style="margin-bottom: 30px; padding: 0px; font-size: 16px; line-height: 30px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; text-size-adjust: auto; text-indent: 2em;">以上就是本次华律网小编为大家整理的关于2019新生儿上户口需要什么证件的相关知识，更多法律问题欢迎咨询华律网律师。</p><div><br></div>', N'/profile/upload/2019/11/26/8276d02108a5f7082448d76d6630a5e0.html', null, N'20191126143302', null, N'20191126143302', N'4')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119446683607240704', N'101', N'凤凰社区', N'sc_service_type', N'居委会电话:0022134324', N'/profile/upload/2019/11/26/18a143c2db26645fe750290cf909498c.html', null, N'20191126143828', null, N'20191126143828', N'1')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119447349717241856', N'101', N'凤凰社区', N'sc_sh_type', N'<span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; text-size-adjust: auto;">开办各种类型的老年大学，开展各种有利于老年健康的文体活动，兴办社会福利机构，如福利院、老年公寓、老年保健、老年法律、老年婚介、老年心理及健康服务，为方便老年人生活，提高老年人生存质量提供各种服务项目。</span>', N'/profile/upload/2019/11/26/163a127a1c408e89bda8ebfebbdecc27.html', null, N'20191126144107', null, N'20191126144107', N'1')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119447410375266304', N'101', N'凤凰社区', N'sc_sh_type', N'<span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; text-size-adjust: auto;">对社区低收人家庭，根据国家政策提供社会保障和救助，落实低保政策。建立再就业基地，加强职业中介等千方百计安排下岗职工再就业</span><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; text-size-adjust: auto;">。</span>', N'/profile/upload/2019/11/26/b85ad25fd317e99f267dc043a39e8ae0.html', null, N'20191126144121', null, N'20191126144121', N'2')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119447466071429120', N'101', N'凤凰社区', N'sc_sh_type', N'<p style="margin-top: 17px; margin-bottom: 17px; padding: 0px; line-height: 28px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; text-size-adjust: auto;">主要协助政府落实优抚政策，做好退伍安置工作，开展拥军优属服务，为军人家属和伤残人员的生活提供方便。</p><div><br></div>', N'/profile/upload/2019/11/26/77714053b51ae41fd5315e3ea5d73330.html', null, N'20191126144135', null, N'20191126144135', N'3')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119447542818803712', N'101', N'凤凰社区', N'sc_sh_type', N'<span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; text-size-adjust: auto;">为残疾人提供就业安置服务，医疗康复服务，基本生活服务及婚介服务</span><span style="caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; text-size-adjust: auto;">。</span>', N'/profile/upload/2019/11/26/7518eca895b3b4b14fc523a9ba9257f5.html', null, N'20191126144153', null, N'20191126144153', N'4')
GO
GO
INSERT INTO [dbo].[sc_service] ([service_id], [service_dept], [service_name], [service_type], [service_content], [service_file], [create_by], [create_time], [update_by], [update_time], [service_ctype]) VALUES (N'119447655893045248', N'101', N'凤凰社区', N'sc_sh_type', N'<p style="margin-top: 17px; margin-bottom: 17px; padding: 0px; line-height: 28px; caret-color: rgb(51, 51, 51); color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 16px; text-size-adjust: auto;">社区居委会可以根据需要建造社区安全防范设施，如电子防盗监控系统、楼宇对讲系统等。在社区可以 成立由社区志愿者组成的治安巡逻队。</p>', N'/profile/upload/2019/11/26/e06716c80d39b23562b806c4d95cba6d.html', null, N'20191126144220', null, N'20191126144220', N'5')
GO
GO

-- ----------------------------
-- Table structure for sc_shop
-- ----------------------------
DROP TABLE [dbo].[sc_shop]
GO
CREATE TABLE [dbo].[sc_shop] (
[shop_id] bigint NOT NULL ,
[shop_name] nvarchar(64) NULL ,
[shop_title] nvarchar(64) NULL ,
[shop_content] nvarchar(2000) NULL ,
[shop_file] nvarchar(64) NULL ,
[shop_type] nchar(2) NULL ,
[shop_status] nchar(2) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL ,
[shop_tel] nvarchar(32) NULL ,
[shop_address] nvarchar(258) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'shop_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'shop_title')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_title'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_title'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'shop_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'shop_file')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'图片'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_file'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'图片'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_file'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'shop_type')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'分类'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_type'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'分类'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_type'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'shop_status')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_status'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'shop_status'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_shop', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_shop'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_shop
-- ----------------------------
INSERT INTO [dbo].[sc_shop] ([shop_id], [shop_name], [shop_title], [shop_content], [shop_file], [shop_type], [shop_status], [create_by], [create_time], [update_by], [update_time], [remark], [shop_tel], [shop_address]) VALUES (N'119733846257307648', N'水果店', N'测试商家', N'发布水果店', null, N'1 ', N'0 ', null, N'20191127093933', null, N'20191127145523', null, null, N'1栋一单元门面')
GO
GO
INSERT INTO [dbo].[sc_shop] ([shop_id], [shop_name], [shop_title], [shop_content], [shop_file], [shop_type], [shop_status], [create_by], [create_time], [update_by], [update_time], [remark], [shop_tel], [shop_address]) VALUES (N'119737550729515008', N'苹果苹果', N'苹果', N'拼多多买苹果', null, N'1 ', N'0 ', null, N'20191127095416', null, N'20191127095416', null, N'134534534', N'拼多多')
GO
GO

-- ----------------------------
-- Table structure for sc_task
-- ----------------------------
DROP TABLE [dbo].[sc_task]
GO
CREATE TABLE [dbo].[sc_task] (
[id] bigint NOT NULL ,
[title] nvarchar(250) NULL ,
[content] nvarchar(MAX) NULL ,
[score] int NULL ,
[state] tinyint NULL ,
[owner_id] bigint NULL ,
[notes] nvarchar(500) NULL ,
[del_flag] nvarchar(1) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] datetime2(7) NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] datetime2(7) NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
NULL, NULL)) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'积分表'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'title')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'title'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'title'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'score')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'分数'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'score'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'分数'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'score'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'state')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'state'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'state'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'owner_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'有者拥id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'owner_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'有者拥id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'owner_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'notes')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'任务完成以后的备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'notes'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'任务完成以后的备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'notes'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'del_flag')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'del_flag'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'删除标志（0代表存在 2代表删除）'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'del_flag'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新者'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'更新时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_task
-- ----------------------------
INSERT INTO [dbo].[sc_task] ([id], [title], [content], [score], [state], [owner_id], [notes], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'39', N'找狗狗', N'1111', N'10', N'2', N'1', N'统一完成', N'0', N'admin', N'2019-10-18 17:18:49.0000000', N'admin', N'2019-10-18 17:18:49.0000000', null)
GO
GO
INSERT INTO [dbo].[sc_task] ([id], [title], [content], [score], [state], [owner_id], [notes], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'40', N'找貓貓', N'', N'3', N'2', N'2', N'', N'0', N'admin', N'2019-10-22 14:36:25.0000000', N'admin', N'2019-10-22 14:36:47.0000000', null)
GO
GO
INSERT INTO [dbo].[sc_task] ([id], [title], [content], [score], [state], [owner_id], [notes], [del_flag], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'41', N'找鷄鷄', N'5', N'4', N'2', N'1', N'', N'0', N'admin', N'2019-10-22 14:37:09.0000000', N'admin', N'2019-10-22 14:37:21.0000000', null)
GO
GO

-- ----------------------------
-- Table structure for sc_task_volunteer
-- ----------------------------
DROP TABLE [dbo].[sc_task_volunteer]
GO
CREATE TABLE [dbo].[sc_task_volunteer] (
[tv_id] bigint NOT NULL ,
[task_id] bigint NOT NULL ,
[volunteer_id] bigint NOT NULL ,
[audit_state] nvarchar(10) NULL ,
[audit_pass_state] nvarchar(10) NULL ,
[audit_content] nvarchar(255) NULL ,
[audit_by] nvarchar(64) NULL ,
[audit_time] bigint NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'task_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'任务id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'task_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'任务id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'task_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'volunteer_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'志愿者id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'志愿者id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'audit_state')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_state'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核状态'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_state'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'audit_pass_state')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核是否通过'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_pass_state'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核是否通过'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_pass_state'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'audit_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'audit_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'audit_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'审核时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'审核时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'audit_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_task_volunteer', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_task_volunteer'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_task_volunteer
-- ----------------------------
INSERT INTO [dbo].[sc_task_volunteer] ([tv_id], [task_id], [volunteer_id], [audit_state], [audit_pass_state], [audit_content], [audit_by], [audit_time], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'120107283509284864', N'119860337439805440', N'119473683034148864', N'1', N'0', null, null, null, null, N'20191128102327', null, N'20191128102554', null)
GO
GO
INSERT INTO [dbo].[sc_task_volunteer] ([tv_id], [task_id], [volunteer_id], [audit_state], [audit_pass_state], [audit_content], [audit_by], [audit_time], [create_by], [create_time], [update_by], [update_time], [remark]) VALUES (N'120109220392734720', N'119467402437201920', N'119473683034148864', N'1', N'1', null, null, null, null, N'20191128103109', null, N'20191128103208', null)
GO
GO

-- ----------------------------
-- Table structure for sc_volunteer
-- ----------------------------
DROP TABLE [dbo].[sc_volunteer]
GO
CREATE TABLE [dbo].[sc_volunteer] (
[user_id] bigint NOT NULL ,
[volunteer_tel] nvarchar(255) NULL ,
[volunteer_account] nvarchar(32) NULL ,
[volunteer_score] int NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL ,
[volunteer_name] nvarchar(64) NULL ,
[volunteer_sex] nvarchar(10) NULL ,
[volunteer_bir] nvarchar(32) NULL ,
[volunteer_idcard] nvarchar(255) NULL ,
[volunteer_address] nvarchar(256) NULL ,
[volunteer_profession] nvarchar(128) NULL ,
[volunteer_synopsis] nvarchar(512) NULL ,
[volunteer_avatar] nvarchar(128) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'user_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'user_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'user_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_tel')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'志愿者电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_tel'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'志愿者电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_tel'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_account')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'志愿者账号'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_account'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'志愿者账号'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_account'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_score')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'志愿者积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_score'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'志愿者积分'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_score'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'remark'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'姓名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_sex')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'性别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_sex'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'性别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_sex'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_bir')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'出生日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_bir'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'出生日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_bir'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_idcard')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'身份证号'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_idcard'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'身份证号'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_idcard'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_address')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'地址'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_address'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'地址'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_address'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_profession')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'职业'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_profession'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'职业'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_profession'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer', 
'COLUMN', N'volunteer_synopsis')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'个人简介'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_synopsis'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'个人简介'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer'
, @level2type = 'COLUMN', @level2name = N'volunteer_synopsis'
GO

-- ----------------------------
-- Records of sc_volunteer
-- ----------------------------
INSERT INTO [dbo].[sc_volunteer] ([user_id], [volunteer_tel], [volunteer_account], [volunteer_score], [create_by], [create_time], [update_by], [update_time], [remark], [volunteer_name], [volunteer_sex], [volunteer_bir], [volunteer_idcard], [volunteer_address], [volunteer_profession], [volunteer_synopsis], [volunteer_avatar]) VALUES (N'119473084062371840', N'18877664433', N'18877664433', N'0', null, N'20191126162323', null, N'20191126162323', null, N'袁弘', N'0', N'1982-08-23', N'433778829293748327482', N'湖北省武汉市', N'上海戏剧学院表演系2001级本科班', N'袁弘，1982年8月23日出生于湖北省武汉市，中国内地影视男演员，毕业于上海戏剧学院表演系2001级本科班', N'/profile/upload/2019/11/26/e2f62e7cc907557e49271f24f15abddb-jpg/1.jpg')
GO
GO
INSERT INTO [dbo].[sc_volunteer] ([user_id], [volunteer_tel], [volunteer_account], [volunteer_score], [create_by], [create_time], [update_by], [update_time], [remark], [volunteer_name], [volunteer_sex], [volunteer_bir], [volunteer_idcard], [volunteer_address], [volunteer_profession], [volunteer_synopsis], [volunteer_avatar]) VALUES (N'119473099916840960', N'18877664432', N'18877664432', N'0', null, N'20191126162326', null, N'20191126162326', null, N'胡歌', N'0', N'1982-09-20', N'433778829293748327482', N'上海市徐汇区', N'上海戏剧学院表演系2001级本科班', N'胡歌，1982年9月20日出生于上海市徐汇区，中国内地男演员、歌手。2019年，主演的刁亦男执导电影《南方车站的聚会》入围戛纳国际电影节主竞赛单元。', N'/profile/upload/2019/11/26/ad988354b96e66c374ed262c1440b2a4-jpg/1.jpg')
GO
GO
INSERT INTO [dbo].[sc_volunteer] ([user_id], [volunteer_tel], [volunteer_account], [volunteer_score], [create_by], [create_time], [update_by], [update_time], [remark], [volunteer_name], [volunteer_sex], [volunteer_bir], [volunteer_idcard], [volunteer_address], [volunteer_profession], [volunteer_synopsis], [volunteer_avatar]) VALUES (N'119473137686548480', N'18877664431', N'18877664431', N'0', null, N'20191126162335', null, N'20191126162335', null, N'刘亦菲', N'1', N'1987-08-25', N'433778829293748327482', N'湖北省武汉市', N'北京电影学院2002级表演系本科班', N'刘亦菲，1987年8月25日出生于湖北省武汉市，华语影视女演员、歌手，毕业于北京电影学院2002级表演系本科班。演艺事业外，刘亦菲还热心慈善公益。2008年担任中国少年儿童基金会形象大使 [20]  ；2009年被授予“中国儿童慈善奖”', N'/profile/upload/2019/11/26/0516f5cafcd42ffdf480a75dcb74dc66-jpg/1.jpg')
GO
GO
INSERT INTO [dbo].[sc_volunteer] ([user_id], [volunteer_tel], [volunteer_account], [volunteer_score], [create_by], [create_time], [update_by], [update_time], [remark], [volunteer_name], [volunteer_sex], [volunteer_bir], [volunteer_idcard], [volunteer_address], [volunteer_profession], [volunteer_synopsis], [volunteer_avatar]) VALUES (N'119473683034148864', N'18877664430', N'18877664430', N'0', null, N'20191126162545', null, N'20191126162545', null, N'刘诗诗', N'1', N'1987-03-10', N'433778829293748327482', N'北京市', N'北京电影学院2002级表演系本科班', N'刘诗诗，原名刘诗施，1987年3月10日出生于北京市，中国内地影视女演员、影视出品人', N'/profile/upload/2019/11/26/c74c0f6f5392ce0bd365417d568400c4-jpg/1.jpg')
GO
GO
INSERT INTO [dbo].[sc_volunteer] ([user_id], [volunteer_tel], [volunteer_account], [volunteer_score], [create_by], [create_time], [update_by], [update_time], [remark], [volunteer_name], [volunteer_sex], [volunteer_bir], [volunteer_idcard], [volunteer_address], [volunteer_profession], [volunteer_synopsis], [volunteer_avatar]) VALUES (N'120224014898696192', N'15107128193', N'15107128193', N'0', null, N'20191128180718', null, N'20191128180718', null, N'测试', N'男', null, N',420115199310072314', N'湖北省武汉市', N'测试', N'测试测试', N'/profile/upload/2019/11/28/d99decbf323b50cfc0db642774382ba8-jpeg/1.jpg')
GO
GO
INSERT INTO [dbo].[sc_volunteer] ([user_id], [volunteer_tel], [volunteer_account], [volunteer_score], [create_by], [create_time], [update_by], [update_time], [remark], [volunteer_name], [volunteer_sex], [volunteer_bir], [volunteer_idcard], [volunteer_address], [volunteer_profession], [volunteer_synopsis], [volunteer_avatar]) VALUES (N'1186900216276717568', N'app', N'app', N'0', null, N'20191126162545', null, N'20191126162545', null, N'app测试', N'1', N'1987-03-10', N'433778829293748327482', N'湖北省武汉市', N'武汉技术中心', N'技术中心研发人员', N'')
GO
GO

-- ----------------------------
-- Table structure for sc_volunteer_style
-- ----------------------------
DROP TABLE [dbo].[sc_volunteer_style]
GO
CREATE TABLE [dbo].[sc_volunteer_style] (
[style_id] bigint NOT NULL ,
[volunteer_id] bigint NOT NULL ,
[style_title] nvarchar(125) NULL ,
[style_name] nvarchar(125) NULL ,
[style_content] nvarchar(3000) NULL ,
[create_by] nvarchar(64) NULL ,
[create_time] bigint NULL ,
[update_by] nvarchar(64) NULL ,
[update_time] bigint NULL ,
[remark] nvarchar(500) NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'style_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'风采id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'风采id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'volunteer_id')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'志愿者id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'volunteer_id'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'志愿者id'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'volunteer_id'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'style_title')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_title'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'标题'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_title'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'style_name')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_name'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_name'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'style_content')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_content'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'内容'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'style_content'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'create_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'create_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'create_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'create_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'create_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'create_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'update_by')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'update_by'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改人'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'update_by'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'update_time')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'update_time'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'修改时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'update_time'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sc_volunteer_style', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sc_volunteer_style'
, @level2type = 'COLUMN', @level2name = N'remark'
GO

-- ----------------------------
-- Records of sc_volunteer_style
-- ----------------------------

-- ----------------------------
-- Indexes structure for table sc_goods
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_goods
-- ----------------------------
ALTER TABLE [dbo].[sc_goods] ADD PRIMARY KEY ([goods_id])
GO

-- ----------------------------
-- Indexes structure for table sc_indexslide
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_indexslide
-- ----------------------------
ALTER TABLE [dbo].[sc_indexslide] ADD PRIMARY KEY ([slide_id])
GO

-- ----------------------------
-- Indexes structure for table sc_order
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_order
-- ----------------------------
ALTER TABLE [dbo].[sc_order] ADD PRIMARY KEY ([order_id])
GO

-- ----------------------------
-- Indexes structure for table sc_parkrent
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_parkrent
-- ----------------------------
ALTER TABLE [dbo].[sc_parkrent] ADD PRIMARY KEY ([parkrent_id])
GO

-- ----------------------------
-- Indexes structure for table sc_partybuild
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_partybuild
-- ----------------------------
ALTER TABLE [dbo].[sc_partybuild] ADD PRIMARY KEY ([partybuild_id])
GO

-- ----------------------------
-- Indexes structure for table sc_point
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_point
-- ----------------------------
ALTER TABLE [dbo].[sc_point] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table sc_point_copy1
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_point_copy1
-- ----------------------------
ALTER TABLE [dbo].[sc_point_copy1] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table sc_point_detail
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_point_detail
-- ----------------------------
ALTER TABLE [dbo].[sc_point_detail] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table sc_prizes
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_prizes
-- ----------------------------
ALTER TABLE [dbo].[sc_prizes] ADD PRIMARY KEY ([prizes_id])
GO

-- ----------------------------
-- Indexes structure for table sc_prizes_volunteer
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_prizes_volunteer
-- ----------------------------
ALTER TABLE [dbo].[sc_prizes_volunteer] ADD PRIMARY KEY ([pv_id])
GO

-- ----------------------------
-- Indexes structure for table sc_register
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_register
-- ----------------------------
ALTER TABLE [dbo].[sc_register] ADD PRIMARY KEY ([register_id])
GO

-- ----------------------------
-- Indexes structure for table sc_service
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_service
-- ----------------------------
ALTER TABLE [dbo].[sc_service] ADD PRIMARY KEY ([service_id])
GO

-- ----------------------------
-- Indexes structure for table sc_shop
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_shop
-- ----------------------------
ALTER TABLE [dbo].[sc_shop] ADD PRIMARY KEY ([shop_id])
GO

-- ----------------------------
-- Indexes structure for table sc_task
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_task
-- ----------------------------
ALTER TABLE [dbo].[sc_task] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table sc_task_volunteer
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_task_volunteer
-- ----------------------------
ALTER TABLE [dbo].[sc_task_volunteer] ADD PRIMARY KEY ([tv_id])
GO

-- ----------------------------
-- Indexes structure for table sc_volunteer
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_volunteer
-- ----------------------------
ALTER TABLE [dbo].[sc_volunteer] ADD PRIMARY KEY ([user_id])
GO

-- ----------------------------
-- Indexes structure for table sc_volunteer_style
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sc_volunteer_style
-- ----------------------------
ALTER TABLE [dbo].[sc_volunteer_style] ADD PRIMARY KEY ([style_id])
GO
