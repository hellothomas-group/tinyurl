CREATE database if NOT EXISTS `tinyurl` default character set utf8mb4 collate utf8mb4_bin;
use `tinyurl`;

SET NAMES utf8mb4;

CREATE TABLE `url_mapping`
(
    `id`             bigint                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `partition_tag`  tinyint                             NOT NULL comment '分区标志',
    `origin_url`     varchar(2083)                       NOT NULL comment '原始长链接',
    `origin_url_md5` varchar(32)                         NOT NULL comment '长链接md5值',
    `tiny_url`       varchar(10)                         NOT NULL comment '短链接',
    `user_id`        varchar(16)                         NOT NULL comment '用户id',
    `url_type`       tinyint   default 0                 NOT NULL comment '短链接类型, 0: 系统生成, 1: 自定义生成',
    `create_time`    timestamp default CURRENT_TIMESTAMP NOT NULL comment '生成时间',
    `update_time`    timestamp default CURRENT_TIMESTAMP NOT NULL on update CURRENT_TIMESTAMP comment '最后更新时间',
    `expire_time`    timestamp comment '过期时间',

    PRIMARY KEY (`id`, `partition_tag`),
    UNIQUE KEY `i_origin_url_md5_type` (`origin_url_md5`, `partition_tag`),
    UNIQUE KEY `i_tiny_url` (`tiny_url`, `partition_tag`),
    KEY              `i_expire_time` (`partition_tag`, `expire_time`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin COMMENT='url映射表'
PARTITION BY LIST (`partition_tag`)
(
    PARTITION P01 VALUES IN (01),
    PARTITION P02 VALUES IN (02),
    PARTITION P03 VALUES IN (03),
    PARTITION P04 VALUES IN (04),
    PARTITION P05 VALUES IN (05),
    PARTITION P06 VALUES IN (06),
    PARTITION P07 VALUES IN (07),
    PARTITION P08 VALUES IN (08),
    PARTITION P09 VALUES IN (09),
    PARTITION P10 VALUES IN (10),
    PARTITION P11 VALUES IN (11),
    PARTITION P12 VALUES IN (12),
    PARTITION P13 VALUES IN (13),
    PARTITION P14 VALUES IN (14),
    PARTITION P15 VALUES IN (15),
    PARTITION P16 VALUES IN (16),
    PARTITION P17 VALUES IN (17),
    PARTITION P18 VALUES IN (18),
    PARTITION P19 VALUES IN (19),
    PARTITION P20 VALUES IN (20),
    PARTITION P21 VALUES IN (21),
    PARTITION P22 VALUES IN (22),
    PARTITION P23 VALUES IN (23),
    PARTITION P24 VALUES IN (24),
    PARTITION P25 VALUES IN (25),
    PARTITION P26 VALUES IN (26),
    PARTITION P27 VALUES IN (27),
    PARTITION P28 VALUES IN (28),
    PARTITION P29 VALUES IN (29),
    PARTITION P30 VALUES IN (30),
    PARTITION P31 VALUES IN (31),
    PARTITION P32 VALUES IN (32),
    PARTITION P33 VALUES IN (33),
    PARTITION P34 VALUES IN (34),
    PARTITION P35 VALUES IN (35),
    PARTITION P36 VALUES IN (36),
    PARTITION P37 VALUES IN (37),
    PARTITION P38 VALUES IN (38),
    PARTITION P39 VALUES IN (39),
    PARTITION P40 VALUES IN (40),
    PARTITION P41 VALUES IN (41),
    PARTITION P42 VALUES IN (42),
    PARTITION P43 VALUES IN (43),
    PARTITION P44 VALUES IN (44),
    PARTITION P45 VALUES IN (45),
    PARTITION P46 VALUES IN (46),
    PARTITION P47 VALUES IN (47),
    PARTITION P48 VALUES IN (48),
    PARTITION P49 VALUES IN (49),
    PARTITION P50 VALUES IN (50)
);

CREATE TABLE LEAF_ALLOC
(
    BIZ_TAG     VARCHAR(128) NOT NULL DEFAULT '',
    MAX_ID      BIGINT       NOT NULL DEFAULT '1',
    STEP        INT          NOT NULL,
    DESCRIPTION VARCHAR(256)          DEFAULT NULL,
    UPDATE_TIME DATETIME(3) NOT NULL DEFAULT NOW(3),
    PRIMARY KEY (BIZ_TAG)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin COMMENT='LEAF分配表';

