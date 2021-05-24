
CREATE database if NOT EXISTS `tinyurl` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `tinyurl`;

SET NAMES utf8mb4;

CREATE TABLE `url_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `origin_url` varchar(2083) binary NOT NULL comment '原始长链接',
  `origin_url_md5` varchar(32) binary NOT NULL comment '长链接md5值',
  `tiny_url` varchar(10) binary NOT NULL comment '短链接',
  `user_id` varchar(16) binary NOT NULL comment '用户id',
  `url_type` int(1) default 0 NOT NULL comment '短链接类型生成类型,系统: “system”,自定义: “custom” 0为system,1为custom 缺省为0',
  `create_time` timestamp default CURRENT_TIMESTAMP NOT NULL comment '生成时间',
  `update_time` timestamp default CURRENT_TIMESTAMP NOT NULL on update CURRENT_TIMESTAMP comment '最后更新时间',
  `expire_time` timestamp comment '过期时间',

  PRIMARY KEY (`id`),
  UNIQUE KEY `i_origin_url_md5` (`origin_url_md5`),
  UNIQUE KEY `i_tiny_url` (`tiny_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `url_sequence` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `start_seq` bigint(20) NOT NULL comment '起始序号',
  `end_seq` bigint(20) NOT NULL comment '结束序号',
  `host_name` varchar(256) NOT NULL comment '主机名',
  `host_ip` varchar(64) NOT NULL comment '主机IP',
  `create_time` timestamp default CURRENT_TIMESTAMP NOT NULL comment '生成时间',
  `update_time` timestamp default CURRENT_TIMESTAMP NOT NULL on update CURRENT_TIMESTAMP comment '最后更新时间',

  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

