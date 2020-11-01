
CREATE database if NOT EXISTS `my_tool` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `my_tool`;

SET NAMES utf8mb4;

CREATE TABLE `url_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `origin_url` varchar(300) NOT NULL comment '原始长链接',
  `origin_url_md5` varchar(32) NOT NULL comment '长链接md5值',
  `tiny_url` varchar(10) NOT NULL comment '短链接',
  `url_type` int(1) default 0 NOT NULL comment '短链接类型生成类型,系统: “system”,自定义: “custom” 0为system,1为custom 缺省为0',
  `create_time` timestamp default CURRENT_TIMESTAMP NOT NULL comment '生成时间',
  `update_time` timestamp default CURRENT_TIMESTAMP NOT NULL on update CURRENT_TIMESTAMP comment '最后更新时间',

  PRIMARY KEY (`id`),
  UNIQUE KEY `i_origin_url_md5` (`origin_url_md5`),
  UNIQUE KEY `i_tiny_url` (`tiny_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

