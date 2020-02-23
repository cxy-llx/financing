

DROP TABLE IF EXISTS `br_order`;
CREATE TABLE IF NOT EXISTS `br_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户主键id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `order_no` varchar(64) DEFAULT NULL COMMENT '系统订单编号',
  `merchant_order_no` varchar(255) DEFAULT NULL COMMENT '商户订单编号',
  `pay_order_no` varchar(255) DEFAULT NULL COMMENT '支付订单编号(支付宝)',
  `pay_type` int(1) DEFAULT 0 COMMENT '支付方式: 0->支付宝',
  `amount` varchar(32) DEFAULT NULL COMMENT '支付金额',
  `pay_code` varchar(1200) DEFAULT NULL COMMENT '支付码',
  `success_url` varchar(255) DEFAULT NULL COMMENT '成功回调地址',
  `error_url` varchar(255) DEFAULT NULL COMMENT '失败回调地址',
  `status` int(1) DEFAULT 0 COMMENT '订单状态: 0->等待下发支付码; 1->未支付; 2->已支付; 3->订单超时',
  `callback_status` int(1) DEFAULT 0 COMMENT '回调状态: 0->未回调; 2->已回调',
  `deadline_time` datetime DEFAULT NULL COMMENT '支付码超时时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  KEY `merchant_id` (`merchant_id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';


DROP TABLE IF EXISTS `br_merchant`;
CREATE TABLE IF NOT EXISTS `br_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `name` varchar(64) DEFAULT NULL COMMENT '商户名称',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `passwd_salt` varchar(20) DEFAULT NULL COMMENT '密码盐',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '头像',
  `mch_id` varchar(20) DEFAULT NULL COMMENT '商户id',
  `mch_key` varchar(32) DEFAULT NULL COMMENT '商户秘钥',
  `balance` varchar(32) DEFAULT NULL COMMENT '余额',
  `freeze_balance` varchar(32) DEFAULT NULL COMMENT '冻结余额',
  `settlement_rate` varchar(6) DEFAULT NULL COMMENT '结算费率',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_ip` varchar(64) DEFAULT NULL COMMENT '最后登录IP',
  `status` int(1) DEFAULT NULL COMMENT '帐号启用状态: 0->禁用; 1->启用',
  `delete_status` int(1) DEFAULT NULL COMMENT '删除状态: 0->未删除; 1->已删除',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `version` bigint(20) NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_mch_id` (`mch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户表';


DROP TABLE IF EXISTS `br_merchant_balance_trade`;
CREATE TABLE IF NOT EXISTS `br_merchant_balance_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户主键id',
  `trade_type` int(1) DEFAULT NULL COMMENT '交易类型: 0->收入; 1->支出',
  `trade_item` int(1) DEFAULT NULL COMMENT '交易项目',
  `value` varchar(32) DEFAULT NULL COMMENT '交易金额',
  `before_value` varchar(32) DEFAULT NULL COMMENT '交易前余额',
  `after_value` varchar(32) DEFAULT NULL COMMENT '交易后余额',
  `title` varchar(100) DEFAULT NULL COMMENT '交易标题',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户余额交易表';


DROP TABLE IF EXISTS `br_merchant_resource_pool`;
CREATE TABLE IF NOT EXISTS `br_merchant_resource_pool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户主键id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `merchant_id` (`merchant_id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户资源池表';


DROP TABLE IF EXISTS `br_merchant_withdraw_apply`;
CREATE TABLE IF NOT EXISTS `br_merchant_withdraw_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户主键id',
  `value` varchar(32) DEFAULT 0 COMMENT '值',
  `poundage` varchar(32) DEFAULT 0 COMMENT '手续费',
  `actual_value` varchar(32) DEFAULT 0 COMMENT '扣出手续费后的值',
  `bank_name` varchar(64) DEFAULT NULL COMMENT '银行名称',
  `bank_account` varchar(64) DEFAULT NULL COMMENT '银行账户名',
  `bank_card_no` varchar(64) DEFAULT NULL COMMENT '银行卡卡号',
  `withdraw_status` int(1) DEFAULT NULL COMMENT '提现状态: 0->申请中; 1->成功; 2->失败',
  `failed_reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  `arrived_time` datetime DEFAULT NULL COMMENT '到账时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户提现申请表';


DROP TABLE IF EXISTS `br_member`;
CREATE TABLE IF NOT EXISTS `br_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(64) DEFAULT NULL COMMENT '所在城市',
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `integration` varchar(32) DEFAULT NULL COMMENT '积分',
  `freeze_integration` varchar(32) DEFAULT NULL COMMENT '冻结积分',
  `agent_ratio` varchar(6) DEFAULT NULL COMMENT '代理比例',
  `inviter_id` bigint(20) DEFAULT NULL COMMENT '邀请人id',
  `invite_code` varchar(6) DEFAULT NULL COMMENT '邀请码',
  `effective_status` int(1) DEFAULT 0 COMMENT '有效会员状态: 0->无效; 1->有效',
  `status` int(1) DEFAULT NULL COMMENT '帐号启用状态:0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `version` bigint(20) NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_phone` (`phone`),
  UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';


DROP TABLE IF EXISTS `br_member_integration_trade`;
CREATE TABLE IF NOT EXISTS `br_member_integration_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `trade_type` int(1) DEFAULT NULL COMMENT '交易类型: 0->收入; 1->支出',
  `trade_item` int(1) DEFAULT NULL COMMENT '交易项目',
  `value` varchar(32) DEFAULT NULL COMMENT '交易金额',
  `before_value` varchar(32) DEFAULT NULL COMMENT '交易前余额',
  `after_value` varchar(32) DEFAULT NULL COMMENT '交易后余额',
  `title` varchar(100) DEFAULT NULL COMMENT '交易标题',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员积分交易表';


DROP TABLE IF EXISTS `br_manager_operate_member_account_log`;
CREATE TABLE IF NOT EXISTS `br_manager_operate_member_account_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '管理员用户id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `account_type` int(1) DEFAULT NULL COMMENT '账户类型: 0->积分',
  `operate_type` int(1) DEFAULT NULL COMMENT '操作类型: 0->增加; 1->减少',
  `value` varchar(32) DEFAULT NULL COMMENT '交易值',
  `before_value` varchar(32) DEFAULT NULL COMMENT '交易前余额',
  `after_value` varchar(32) DEFAULT NULL COMMENT '交易后余额',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员操作会员账户日志表';


DROP TABLE IF EXISTS `br_collection_bank_info`;
CREATE TABLE IF NOT EXISTS `br_collection_bank_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bank_name` varchar(64) DEFAULT NULL COMMENT '银行名称',
  `bank_account` varchar(64) DEFAULT NULL COMMENT '银行账户名',
  `bank_card_no` varchar(64) DEFAULT NULL COMMENT '银行卡卡号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台收款银行信息表';


DROP TABLE IF EXISTS `br_offline_recharge_record`;
CREATE TABLE IF NOT EXISTS `br_offline_recharge_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `value` varchar(32) DEFAULT NULL COMMENT '充值金额',
  `collection_account_name` varchar(100) DEFAULT NULL COMMENT '收款账户名',
  `collection_bank_name` varchar(100) DEFAULT NULL COMMENT '收款银行名称',
  `collection_bank_card_no` varchar(50) DEFAULT NULL COMMENT '收款银行卡号',
  `payment_account_name` varchar(100) DEFAULT NULL COMMENT '支付账户名',
  `payment_bank_name` varchar(100) DEFAULT NULL COMMENT '支付银行名称',
  `payment_bank_card_no` varchar(50) DEFAULT NULL COMMENT '支付银行卡号',
  `payment_screenshot` varchar(500) DEFAULT NULL COMMENT '支付(截图)凭证',
  `recharge_status` int(1) DEFAULT NULL COMMENT '充值状态: 0->充值中; 1->成功; 2->失败',
  `failed_reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  `recharge_time` datetime DEFAULT NULL COMMENT '充值时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线下充值记录表';


DROP TABLE IF EXISTS `br_effective_member_statistics`;
CREATE TABLE IF NOT EXISTS `br_effective_member_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `invite_count` int(11) DEFAULT 0 COMMENT '直接邀请有效会员人数',
  `team_count` int(11) DEFAULT 0 COMMENT '团队有效会员人数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='有效会员统计信息表';


DROP TABLE IF EXISTS `br_member_statistics`;
CREATE TABLE IF NOT EXISTS `br_member_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `invite_count` int(11) DEFAULT 0 COMMENT '邀请人数',
  `team_count` int(11) DEFAULT 0 COMMENT '团队人数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员统计信息表';


DROP TABLE IF EXISTS `br_invite_statistics_handle_queue`;
CREATE TABLE `br_invite_statistics_handle_queue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `handle_status` int(1) DEFAULT NULL COMMENT '处理状态: 0->未处理; 1->已处理',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员邀请统计处理队列表';


DROP TABLE IF EXISTS `br_general_data`;
CREATE TABLE IF NOT EXISTS `br_general_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `field` varchar(255) DEFAULT NULL COMMENT '键',
  `value` text DEFAULT NULL COMMENT '值',
  `note` text DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通用数据表';


DROP TABLE IF EXISTS `br_business_dictionary`;
CREATE TABLE IF NOT EXISTS `br_business_dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `value` varchar(50) DEFAULT NULL COMMENT '参数值',
  `comment` varchar(50) DEFAULT NULL COMMENT '备注',
  `show_status` int(1) DEFAULT '1' COMMENT '显示状态: 0->不显示; 1->显示',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务字典表';


DROP TABLE IF EXISTS `br_team_level`;
CREATE TABLE IF NOT EXISTS `br_team_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '推荐人id',
  `all_parent_id` text DEFAULT NULL COMMENT '所有上级(格式: /1001/1002/1003/)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_member_id` (`member_id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团队(直推)层级关系表';


DROP TABLE IF EXISTS `br_app_upgrade`;
CREATE TABLE IF NOT EXISTS `br_app_upgrade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` int(1) DEFAULT NULL COMMENT '类型: 0->ANDROID; 1->IOS',
  `version` varchar(255) DEFAULT NULL COMMENT '版本号',
  `msg` text DEFAULT NULL COMMENT '更新说明',  
  `url` varchar(255) DEFAULT NULL COMMENT '下载地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP版本信息表';


DROP TABLE IF EXISTS `br_verification_code`;
CREATE TABLE IF NOT EXISTS `br_verification_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `receiver` varchar(64) DEFAULT NULL COMMENT '手机号/邮箱',
  `receiver_type` int(1) DEFAULT NULL COMMENT '接收类型: 0->手机号; 1->邮箱',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `content` varchar(10) DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `receiver` (`receiver`),
  KEY `ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证码信息表';


DELETE FROM `br_business_dictionary`;
INSERT INTO `br_business_dictionary` (`name`, `value`, `comment`, `show_status`, `update_time`) VALUES
	('system_maintenance_switch', 'false', '系统维护开关(false表示没有维护,true表示系统维护中)', 1, now());
DELETE FROM `br_general_data`;

