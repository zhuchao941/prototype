/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50156
Source Host           : localhost:3306
Source Database       : prototype

Target Server Type    : MYSQL
Target Server Version : 50156
File Encoding         : 65001

Date: 2015-02-25 10:24:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `news_id` varchar(20) NOT NULL,
  `date` datetime NOT NULL COMMENT '评论时间',
  `user_id` varchar(40) NOT NULL COMMENT '评论用户',
  `content` varchar(255) NOT NULL,
  `status` int(1) NOT NULL COMMENT '该条评论的状态',
  `reply_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` varchar(20) NOT NULL,
  `title` varchar(20) DEFAULT NULL,
  `description` varchar(40) DEFAULT NULL,
  `picUrl` varchar(100) DEFAULT NULL,
  `content` text,
  `src` varchar(20) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `email` varchar(40) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(40) NOT NULL,
  `age` int(10) DEFAULT NULL,
  `height` double(10,2) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;