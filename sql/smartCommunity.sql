/*
 Navicat Premium Data Transfer

 Source Server         : 六盘水智慧社区APP
 Source Server Type    : MySQL
 Source Server Version : 50645
 Source Host           : 172.16.0.192:3306
 Source Schema         : zhapp

 Target Server Type    : MySQL
 Target Server Version : 50645
 File Encoding         : 65001

 Date: 21/11/2019 18:25:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 多文件存储
-- ----------------------------
DROP TABLE IF EXISTS sc_file_info;
CREATE TABLE sc_file_info (
  file_id bigint(20) NOT NULL,
  file_target bigint(20) NOT NULL COMMENT '来源实体',
  file_code varchar(64) DEFAULT NULL COMMENT '对应字符',
  file_type varchar(255) DEFAULT NULL COMMENT '类型',
  file_name varchar(128) DEFAULT NULL COMMENT '文件名称',
  file_path varchar(255) DEFAULT NULL COMMENT '文件路径',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(20) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '编辑人',
  update_time bigint(20) DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (file_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 商品表
-- ----------------------------
DROP TABLE IF EXISTS sc_goods;
CREATE TABLE sc_goods (
  goods_id bigint(20) NOT NULL,
  shop_id bigint(20) NOT NULL COMMENT '商家',
  goods_name varchar(32) DEFAULT NULL COMMENT '商品名称',
  goods_brand varchar(32) DEFAULT NULL COMMENT '商品品牌',
  goods_content varchar(255) DEFAULT NULL COMMENT '商品描述',
  goods_price decimal(18,2) DEFAULT NULL COMMENT '商品价格',
  goods_quantity int(11) DEFAULT NULL COMMENT '商品数量',
  goods_unit varchar(10) DEFAULT NULL COMMENT '单位',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (goods_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 首页切换图
-- ----------------------------
DROP TABLE IF EXISTS sc_indexslide;
CREATE TABLE sc_indexslide (
  slide_id bigint(20) NOT NULL,
  slide_path varchar(255) DEFAULT NULL,
  slide_sort int(4) DEFAULT NULL,
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time bigint(20) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time bigint(20) DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (slide_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页轮播图';

-- ----------------------------
-- 任务表
-- ----------------------------
DROP TABLE IF EXISTS sc_parkrent;
CREATE TABLE sc_parkrent (
  parkrent_id bigint(20) NOT NULL,
  parkrent_name varchar(12) DEFAULT NULL COMMENT '姓名',
  parkrent_title varchar(32) DEFAULT NULL COMMENT '标题',
  parkrent_type char(2) DEFAULT NULL COMMENT '类型',
  parkrent_tel varchar(12) DEFAULT NULL COMMENT '联系电话',
  parkrent_content varchar(2000) DEFAULT NULL COMMENT '内容',
  parkrent_file varchar(12) DEFAULT NULL COMMENT '文件路径',
  parkrent_status char(2) DEFAULT '0' COMMENT '状态',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(20) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time bigint(20) DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  parkent_audit_state char(2) DEFAULT '0',
  equipment_id varchar(128) DEFAULT NULL,
  begin_Time bigint(32) DEFAULT NULL COMMENT '任务开始时间',
  end_Time bigint(32) DEFAULT NULL COMMENT '任务结束时间',
  audit_state varchar(10) DEFAULT '0' COMMENT '审核状态',
  audit_pass_state varchar(10) DEFAULT NULL COMMENT '审核通过状态',
  audit_content varchar(255) DEFAULT NULL COMMENT '审核内容',
  audit_time bigint(20) DEFAULT NULL COMMENT '审核时间',
  audit_by varchar(32) DEFAULT NULL COMMENT '审核人',
  parkrent_score int(11) DEFAULT NULL,
  PRIMARY KEY (parkrent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 初心频道
-- ----------------------------
DROP TABLE IF EXISTS sc_partybuild;
CREATE TABLE sc_partybuild (
  partybuild_id bigint(20) NOT NULL,
  partybuild_title varchar(128) DEFAULT NULL COMMENT '标题',
  partybuild_content text COMMENT '内容',
  partybuild_file varchar(128) DEFAULT NULL COMMENT '文件路径',
  partybuild_type varchar(255) DEFAULT NULL COMMENT '类型',
  partybuild_status char(2) DEFAULT NULL COMMENT '状态',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(20) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time bigint(20) DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  partybuild_source varchar(128) DEFAULT NULL,
  partybuild_time bigint(32) DEFAULT NULL,
  partybuild_cover varchar(128) DEFAULT NULL,
  video_update_type varchar(20) DEFAULT NULL,
  PRIMARY KEY (partybuild_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党建信息';

-- ----------------------------
-- Table structure for sc_point
-- ----------------------------
DROP TABLE IF EXISTS sc_point;
CREATE TABLE sc_point (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  sum_point bigint(20) DEFAULT '0' COMMENT '总积分',
  user_id bigint(30) DEFAULT NULL COMMENT '用户id',
  user_name varchar(250) DEFAULT NULL COMMENT '所属用户',
  del_flag char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL,
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='积分表';

-- ----------------------------
-- Table structure for sc_point_detail
-- ----------------------------
DROP TABLE IF EXISTS sc_point_detail;
CREATE TABLE sc_point_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  point_id bigint(20) DEFAULT '0' COMMENT '分积id',
  task_id bigint(20) DEFAULT NULL COMMENT '任务id',
  score mediumint(20) DEFAULT '0' COMMENT '分数',
  chinese_score varchar(250) DEFAULT '' COMMENT '文中分数，用于描述是增加还是减少积分',
  del_flag varchar(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='积分表';

-- ----------------------------
-- 奖品表
-- ----------------------------
DROP TABLE IF EXISTS sc_prizes;
CREATE TABLE sc_prizes (
  prizes_id bigint(20) NOT NULL COMMENT '奖品id',
  prizes_name varchar(32) DEFAULT NULL COMMENT '奖品名称',
  prizes_num int(11) DEFAULT NULL COMMENT '奖品数量',
  prizes_score decimal(18,2) DEFAULT NULL COMMENT '奖品兑换分数',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(20) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time bigint(20) DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (prizes_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 注册表
-- ----------------------------
DROP TABLE IF EXISTS sc_register;
CREATE TABLE sc_register (
  register_id bigint(20) NOT NULL,
  register_name varchar(64) DEFAULT NULL COMMENT '姓名',
  register_tel varchar(32) DEFAULT NULL COMMENT '电话',
  register_sex varchar(10) DEFAULT NULL COMMENT '性别',
  register_bir varchar(32) DEFAULT NULL COMMENT '出生日期',
  register_idcard varchar(255) DEFAULT NULL COMMENT '身份证号',
  register_address varchar(256) DEFAULT NULL COMMENT '地址',
  register_profession varchar(128) DEFAULT NULL COMMENT '职业',
  register_synopsis varchar(512) DEFAULT NULL COMMENT '个人简介',
  audit_state char(2) DEFAULT NULL COMMENT '审核状态',
  audit_pass_state char(2) DEFAULT NULL COMMENT '审核通过状态',
  audit_by varchar(64) DEFAULT NULL COMMENT '审核人',
  audit_time bigint(20) DEFAULT NULL COMMENT '审核时间',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(20) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '编辑人',
  update_time bigint(20) DEFAULT NULL COMMENT '编辑时间',
  equipmentId varchar(128) DEFAULT NULL,
  audit_content varchar(255) DEFAULT NULL,
  PRIMARY KEY (register_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 服务电话
-- ----------------------------
DROP TABLE IF EXISTS sc_service;
CREATE TABLE sc_service (
  service_id bigint(20) NOT NULL,
  service_dept bigint(20) NOT NULL,
  service_name varchar(32) DEFAULT NULL COMMENT '部门名称',
  service_type varchar(20) DEFAULT NULL COMMENT '服务类型',
  service_content text COMMENT '服务内容',
  service_file varchar(128) DEFAULT NULL COMMENT '内容文件',
  create_by varchar(32) DEFAULT NULL,
  create_time bigint(20) DEFAULT NULL,
  update_by varchar(32) DEFAULT NULL,
  update_time bigint(20) DEFAULT NULL,
  service_ctype varchar(2) DEFAULT NULL,
  PRIMARY KEY (service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 商家
-- ----------------------------
DROP TABLE IF EXISTS sc_shop;
CREATE TABLE sc_shop (
  shop_id bigint(20) NOT NULL,
  shop_name varchar(64) DEFAULT NULL COMMENT '姓名',
  shop_title varchar(64) DEFAULT NULL COMMENT '标题',
  shop_content varchar(2000) DEFAULT NULL COMMENT '内容',
  shop_file varchar(64) DEFAULT NULL COMMENT '图片',
  shop_type char(2) DEFAULT NULL COMMENT '分类',
  shop_status char(2) DEFAULT NULL COMMENT '状态',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(20) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time bigint(20) DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  shop_tel varchar(32) DEFAULT NULL,
  shop_address varchar(258) DEFAULT NULL,
  PRIMARY KEY (shop_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sc_task
-- ----------------------------
DROP TABLE IF EXISTS sc_task;
CREATE TABLE sc_task (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  title varchar(250) DEFAULT '0' COMMENT '标题',
  content varchar(5000) DEFAULT '' COMMENT '内容',
  score mediumint(20) DEFAULT '0' COMMENT '分数',
  state tinyint(1) DEFAULT '0' COMMENT '状态',
  owner_id bigint(20) DEFAULT NULL COMMENT '有者拥id',
  notes varchar(500) DEFAULT NULL COMMENT '任务完成以后的备注',
  del_flag varchar(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  create_by varchar(64) DEFAULT '' COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT '' COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='积分表';

-- ----------------------------
-- 志愿者报名任务
-- ----------------------------
DROP TABLE IF EXISTS sc_task_volunteer;
CREATE TABLE sc_task_volunteer (
  tv_id bigint(20) NOT NULL,
  task_id bigint(20) NOT NULL COMMENT '任务id',
  volunteer_id bigint(20) NOT NULL COMMENT '志愿者id',
  audit_state varchar(10) DEFAULT NULL COMMENT '审核状态',
  audit_pass_state varchar(10) DEFAULT NULL COMMENT '审核是否通过',
  audit_content varchar(255) DEFAULT NULL COMMENT '审核内容',
  audit_by varchar(64) DEFAULT NULL COMMENT '审核人',
  audit_time bigint(64) DEFAULT NULL COMMENT '审核时间',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(64) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time bigint(64) DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (tv_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 志愿者
-- ----------------------------
DROP TABLE IF EXISTS sc_volunteer;
CREATE TABLE sc_volunteer (
  user_id bigint(20) NOT NULL COMMENT 'id',
  volunteer_tel varchar(255) DEFAULT NULL COMMENT '志愿者电话',
  volunteer_account varchar(32) DEFAULT NULL COMMENT '志愿者账号',
  volunteer_score int(11) DEFAULT '0' COMMENT '志愿者积分',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  volunteer_name varchar(64) DEFAULT NULL COMMENT '姓名',
  volunteer_sex varchar(10) DEFAULT NULL COMMENT '性别',
  volunteer_bir varchar(32) DEFAULT NULL COMMENT '出生日期',
  volunteer_idcard varchar(255) DEFAULT NULL COMMENT '身份证号',
  volunteer_address varchar(256) DEFAULT NULL COMMENT '地址',
  volunteer_profession varchar(128) DEFAULT NULL COMMENT '职业',
  volunteer_synopsis varchar(512) DEFAULT NULL COMMENT '个人简介',
  volunteer_avatar varchar(128) DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 志愿者日志风采
-- ----------------------------
DROP TABLE IF EXISTS sc_volunteer_style;
CREATE TABLE sc_volunteer_style (
  style_id bigint(20) NOT NULL COMMENT '风采id',
  volunteer_id bigint(20) NOT NULL COMMENT '志愿者id',
  style_title varchar(125) DEFAULT NULL COMMENT '标题',
  style_name varchar(125) DEFAULT NULL COMMENT '名称',
  style_content varchar(3000) DEFAULT NULL COMMENT '内容',
  create_by varchar(64) DEFAULT NULL COMMENT '创建人',
  create_time bigint(64) DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '修改人',
  update_time bigint(64) DEFAULT NULL COMMENT '修改时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (style_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;