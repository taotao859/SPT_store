-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2021-12-09 05:17:12
-- 服务器版本： 10.4.18-MariaDB
-- PHP 版本： 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `store`
--
CREATE DATABASE IF NOT EXISTS `store` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `store`;

-- --------------------------------------------------------

--
-- 表的结构 `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '款项ID',
  `account_amount` decimal(10,2) NOT NULL COMMENT '款项金额',
  `account_time` datetime NOT NULL COMMENT '款项时间',
  `account_index` int(10) UNSIGNED NOT NULL COMMENT '款项订单索引',
  `account_tag` bit(1) NOT NULL COMMENT '款项订单类型',
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `aftersale`
--

DROP TABLE IF EXISTS `aftersale`;
CREATE TABLE IF NOT EXISTS `aftersale` (
  `after_sale_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '售后ID',
  `after_sale_orders_id` int(11) NOT NULL COMMENT '订单ID',
  PRIMARY KEY (`after_sale_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` char(5) NOT NULL COMMENT '用户ID',
  `customer_password` varchar(128) NOT NULL COMMENT '用户密码',
  `customer_name` varchar(128) NOT NULL COMMENT '用户姓名',
  `customer_contact` char(11) DEFAULT NULL COMMENT '联系方式',
  `customer_score` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '会员积分',
  `customer_tag` bit(1) NOT NULL DEFAULT b'0' COMMENT '客户类型',
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE IF NOT EXISTS `inventory` (
  `inventory_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `inventory_product_id` smallint(6) NOT NULL COMMENT '货品ID(FK)',
  `inventory_quantity` int(10) UNSIGNED NOT NULL COMMENT '库存数量',
  `inventory_repository_id` tinyint(4) NOT NULL COMMENT '仓库ID(FK)',
  PRIMARY KEY (`inventory_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE IF NOT EXISTS `orderitem` (
  `order_item_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `order_item_orders_id` int(11) NOT NULL COMMENT '订单ID(FK)',
  `order_item_product_id` smallint(6) NOT NULL COMMENT '货品ID(FK)',
  `order_item_product_quantity` int(10) UNSIGNED NOT NULL COMMENT '货品数量',
  `order_item_product_price` decimal(10,2) UNSIGNED NOT NULL COMMENT '货品价格',
  PRIMARY KEY (`order_item_id`) USING BTREE,
  KEY `orders_id` (`order_item_orders_id`) USING BTREE,
  KEY `product_id` (`order_item_product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `orders_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_customer_id` char(5) DEFAULT NULL COMMENT '客户ID(FK)',
  `orders_time` datetime NOT NULL COMMENT '订单时间',
  `orders_total_price` decimal(10,2) UNSIGNED NOT NULL COMMENT '订单总价',
  `orders_profit` decimal(10,2) UNSIGNED NOT NULL COMMENT '订单利润',
  `orders_state` tinyint(3) UNSIGNED NOT NULL COMMENT '订单状态',
  `orders_staff_id` char(5) NOT NULL COMMENT '员工ID(FK)',
  `orders_repository_id` tinyint(4) NOT NULL COMMENT '仓库ID(FK)',
  `orders_tag` bit(1) NOT NULL COMMENT '订单种类',
  PRIMARY KEY (`orders_id`) USING BTREE,
  KEY `staff_id` (`orders_staff_id`) USING BTREE,
  KEY `orders_repository_id` (`orders_repository_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `pandian`
--

DROP TABLE IF EXISTS `pandian`;
CREATE TABLE IF NOT EXISTS `pandian` (
  `pandian_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '盘点ID',
  `pandian_product_id` smallint(6) NOT NULL COMMENT '货品ID',
  `pandian_repository_id` tinyint(4) NOT NULL COMMENT '仓库ID',
  `pandian_change_quantity` int(11) NOT NULL COMMENT '变化ID',
  PRIMARY KEY (`pandian_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `product_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '货品ID',
  `product_name` varchar(128) NOT NULL COMMENT '货品名称',
  `product_cost` decimal(10,2) UNSIGNED NOT NULL COMMENT '最新进价',
  `product_retail_price` decimal(10,2) UNSIGNED NOT NULL COMMENT '零售价格',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `purchase`
--

DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase` (
  `purchase_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '进货ID',
  `purchase_staff_id` char(5) NOT NULL COMMENT '进货员ID(FK)',
  `purchase_repository_id` tinyint(4) NOT NULL COMMENT '仓库ID(FK)',
  `purchase_product_id` smallint(6) NOT NULL COMMENT '货品ID(FK)',
  `purchase_quantity` int(10) UNSIGNED NOT NULL COMMENT '进货数量',
  `purchase_price` decimal(10,2) UNSIGNED NOT NULL COMMENT '进货价格',
  `purchase_time` datetime NOT NULL COMMENT '进货日期',
  PRIMARY KEY (`purchase_id`) USING BTREE,
  KEY `staff_id` (`purchase_staff_id`) USING BTREE,
  KEY `product_id` (`purchase_product_id`) USING BTREE,
  KEY `purchase_repository_id` (`purchase_repository_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `record`
--

DROP TABLE IF EXISTS `record`;
CREATE TABLE IF NOT EXISTS `record` (
  `record_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库存记录ID',
  `record_repository_id` tinyint(4) NOT NULL COMMENT '仓库ID(FK)',
  `record_product_id` smallint(6) NOT NULL COMMENT '记录货品',
  `record_quantity` int(11) NOT NULL COMMENT '记录数量',
  `record_time` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`record_id`) USING BTREE,
  KEY `record_product_id` (`record_product_id`) USING BTREE,
  KEY `record_repository_id` (`record_repository_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `repository`
--

DROP TABLE IF EXISTS `repository`;
CREATE TABLE IF NOT EXISTS `repository` (
  `repository_id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `repository_name` varchar(128) NOT NULL COMMENT '仓库名称',
  PRIMARY KEY (`repository_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `staff`
--

DROP TABLE IF EXISTS `staff`;
CREATE TABLE IF NOT EXISTS `staff` (
  `staff_id` char(5) NOT NULL COMMENT '员工ID',
  `staff_password` varchar(128) NOT NULL COMMENT '密码',
  `staff_name` varchar(128) NOT NULL COMMENT '姓名',
  `staff_role` tinyint(3) UNSIGNED NOT NULL COMMENT '职位',
  `staff_contact` char(11) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- 限制导出的表
--

--
-- 限制表 `aftersale`
--
ALTER TABLE `aftersale`
  ADD CONSTRAINT `aftersale_ibfk_1` FOREIGN KEY (`after_sale_orders_id`) REFERENCES `orders` (`orders_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`inventory_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `orderitem`
--
ALTER TABLE `orderitem`
  ADD CONSTRAINT `orderItem_ibfk_2` FOREIGN KEY (`order_item_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`order_item_orders_id`) REFERENCES `orders` (`orders_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`orders_customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`orders_staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`purchase_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `purchase_ibfk_2` FOREIGN KEY (`purchase_staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `purchase_ibfk_3` FOREIGN KEY (`purchase_repository_id`) REFERENCES `repository` (`repository_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `record`
--
ALTER TABLE `record`
  ADD CONSTRAINT `record_ibfk_1` FOREIGN KEY (`record_product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `record_ibfk_2` FOREIGN KEY (`record_repository_id`) REFERENCES `repository` (`repository_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
