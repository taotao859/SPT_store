/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : store

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 02/12/2021 20:25:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `store` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `store`;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '款项ID',
  `account_amount` decimal(10, 2) NOT NULL COMMENT '款项金额',
  `account_time` datetime(0) NOT NULL COMMENT '款项时间',
  `account_index` int(0) UNSIGNED NOT NULL COMMENT '款项订单索引',
  `account_tag` bit(1) NOT NULL COMMENT '款项订单类型',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for aftersale
-- ----------------------------
DROP TABLE IF EXISTS `aftersale`;
CREATE TABLE `aftersale`  (
  `after_sale_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '售后ID',
  `after_sale_orders_id` int(0) NOT NULL COMMENT '订单ID',
  PRIMARY KEY (`after_sale_id`) USING BTREE,
  INDEX `orders_id`(`after_sale_orders_id`) USING BTREE,
  CONSTRAINT `aftersale_ibfk_1` FOREIGN KEY (`after_sale_orders_id`) REFERENCES `orders` (`orders_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `customer_id` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `customer_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `customer_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
  `customer_contact` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `customer_score` int(0) UNSIGNED NOT NULL DEFAULT 0 COMMENT '会员积分',
  `customer_tag` bit(1) NOT NULL DEFAULT b'0' COMMENT '客户类型',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
  `inventory_id` smallint(0) NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `inventory_product_id` smallint(0) NOT NULL COMMENT '货品ID(FK)',
  `inventory_quantity` int(0) UNSIGNED NOT NULL COMMENT '库存数量',
  `inventory_repository_id` tinyint(0) NOT NULL COMMENT '仓库ID(FK)',
  PRIMARY KEY (`inventory_id`) USING BTREE,
  INDEX `product_id`(`inventory_product_id`) USING BTREE,
  INDEX `inventory_repository_id`(`inventory_repository_id`) USING BTREE,
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`inventory_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`  (
  `order_item_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `order_item_orders_id` int(0) NOT NULL COMMENT '订单ID(FK)',
  `order_item_product_id` smallint(0) NOT NULL COMMENT '货品ID(FK)',
  `order_item_product_quantity` int(0) UNSIGNED NOT NULL COMMENT '货品数量',
  `order_item_product_price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '货品价格',
  PRIMARY KEY (`order_item_id`) USING BTREE,
  INDEX `orders_id`(`order_item_orders_id`) USING BTREE,
  INDEX `product_id`(`order_item_product_id`) USING BTREE,
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`order_item_orders_id`) REFERENCES `orders` (`orders_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orderItem_ibfk_2` FOREIGN KEY (`order_item_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orders_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_customer_id` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户ID(FK)',
  `orders_time` datetime(0) NOT NULL COMMENT '订单时间',
  `orders_total_price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '订单总价',
  `orders_profit` decimal(10, 2) UNSIGNED NOT NULL COMMENT '订单利润',
  `orders_state` tinyint(0) UNSIGNED NOT NULL COMMENT '订单状态',
  `orders_staff_id` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工ID(FK)',
  `orders_repository_id` tinyint(0) NOT NULL COMMENT '仓库ID(FK)',
  `orders_tag` bit(1) NOT NULL COMMENT '订单种类',
  PRIMARY KEY (`orders_id`) USING BTREE,
  INDEX `customer_id`(`orders_customer_id`) USING BTREE,
  INDEX `staff_id`(`orders_staff_id`) USING BTREE,
  INDEX `orders_repository_id`(`orders_repository_id`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`orders_customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`orders_staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` smallint(0) NOT NULL AUTO_INCREMENT COMMENT '货品ID',
  `product_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货品名称',
  `product_cost` decimal(10, 2) UNSIGNED NOT NULL COMMENT '最新进价',
  `product_retail_price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '零售价格',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase`  (
  `purchase_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '进货ID',
  `purchase_staff_id` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '进货员ID(FK)',
  `purchase_repository_id` tinyint(0) NOT NULL COMMENT '仓库ID(FK)',
  `purchase_product_id` smallint(0) NOT NULL COMMENT '货品ID(FK)',
  `purchase_quantity` int(0) UNSIGNED NOT NULL COMMENT '进货数量',
  `purchase_price` decimal(10, 2) UNSIGNED NOT NULL COMMENT '进货价格',
  `purchase_time` datetime(0) NOT NULL COMMENT '进货日期',
  PRIMARY KEY (`purchase_id`) USING BTREE,
  INDEX `staff_id`(`purchase_staff_id`) USING BTREE,
  INDEX `product_id`(`purchase_product_id`) USING BTREE,
  INDEX `purchase_repository_id`(`purchase_repository_id`) USING BTREE,
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`purchase_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_ibfk_2` FOREIGN KEY (`purchase_staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `record_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库存记录ID',
  `record_repository_id` tinyint(0) NOT NULL COMMENT '仓库ID(FK)',
  `record_product_id` smallint(0) NOT NULL COMMENT '记录货品',
  `record_quantity` int(0) NOT NULL COMMENT '记录数量',
  `record_time` datetime(0) NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `record_product_id`(`record_product_id`) USING BTREE,
  INDEX `record_repository_id`(`record_repository_id`) USING BTREE,
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`record_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for repository
-- ----------------------------
DROP TABLE IF EXISTS `repository`;
CREATE TABLE `repository`  (
  `repository_id` tinyint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `repository_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '仓库名称',
  PRIMARY KEY (`repository_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `staff_id` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工ID',
  `staff_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `staff_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `staff_role` tinyint(0) UNSIGNED NOT NULL COMMENT '职位',
  `staff_contact` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
