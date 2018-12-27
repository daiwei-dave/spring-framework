/*
 Navicat Premium Data Transfer

 Source Server         : win-localhost
 Source Server Type    : MySQL
 Source Server Version : 50703
 Source Host           : 127.0.0.1:3306
 Source Schema         : ssm

 Target Server Type    : MySQL
 Target Server Version : 50703
 File Encoding         : 65001

 Date: 19/11/2018 11:02:30
*/

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `created_time` timestamp,
  `updated_time` timestamp ,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8;