/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : prototype

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2015-03-04 14:19:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
`id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`news_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`date`  datetime NOT NULL COMMENT '评论时间' ,
`user_id`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论用户' ,
`content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status`  int(1) NOT NULL COMMENT '该条评论的状态' ,
`reply_id`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=22

;

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
`id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`title`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`description`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`picUrl`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`src`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`url`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`date`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `roles_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions` (
`id`  int(10) NOT NULL ,
`role_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`permission`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`email`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`username`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`age`  int(10) NULL DEFAULT NULL ,
`height`  double(10,2) NULL DEFAULT NULL ,
`birthday`  date NULL DEFAULT NULL ,
PRIMARY KEY (`username`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
`id`  int(10) NOT NULL ,
`username`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`role_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Auto increment value for `comment`
-- ----------------------------
ALTER TABLE `comment` AUTO_INCREMENT=22;
