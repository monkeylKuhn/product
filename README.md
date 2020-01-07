# product
# 建表语句
CREATE TABLE `dressproduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productID` varchar(255) DEFAULT NULL,
  `clientProductID` varchar(255) DEFAULT NULL,
  `spu` varchar(255) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `season` varchar(255) DEFAULT NULL,
  `isCarryOver` tinyint(4) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `retailPrice` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `pricesIncludeVat` varchar(255) DEFAULT NULL,
  `productLastUpdated` varchar(255) DEFAULT NULL,
  `photos` varchar(2550) DEFAULT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1823 DEFAULT CHARSET=utf8;

CREATE TABLE `dressskusize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productID` varchar(255) NOT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` varchar(255) DEFAULT NULL,
  `retailPrice` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;



CREATE TABLE `dressproduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productID` varchar(255) NOT NULL,
  `clientProductID` varchar(255) DEFAULT NULL,
  `spu` varchar(255) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `season` varchar(255) DEFAULT NULL,
  `isCarryOver` tinyint(4) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `retailPrice` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `pricesIncludeVat` varchar(255) DEFAULT NULL,
  `productLastUpdated` varchar(255) DEFAULT NULL,
  `photos` varchar(2550) DEFAULT NULL,
  PRIMARY KEY (`id`,`productID`)
) ENGINE=InnoDB AUTO_INCREMENT=2345 DEFAULT CHARSET=utf8;


CREATE TABLE `dressskusize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productID` varchar(255) NOT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` varchar(255) DEFAULT NULL,
  `retailPrice` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `status` int(2) DEFAULT NULL COMMENT '更新处理状态，0待更新，1更新中 ，2 此次库存被更新，3 此次价格被更新，4 库存价格均有更新',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1573 DEFAULT CHARSET=utf8;

