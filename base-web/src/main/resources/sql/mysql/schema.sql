SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------
-- Table structure for GROOT_BUSHEET_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_BUSHEET_PERMISSION`;
CREATE TABLE `GROOT_BUSHEET_PERMISSION` (
  `id` varchar(32)   NOT NULL,
  `create_user_id` varchar(32)   default NULL,
  `create_time` datetime default NULL,
  `update_user_id` varchar(32)   default NULL,
  `update_time` datetime default NULL,
  `BUSHEET_ID` varchar(32)   default NULL,
  `DEPARTMENT_CODE` varchar(32)   default NULL,
  `USER_ID` varchar(32)   default NULL,
  `status` varchar(3)   default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_BUSHEET_PERMISSION
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_BUSHEET
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_BUSHEET`;
CREATE TABLE `GROOT_BUSHEET` (
  `id` varchar(32)   NOT NULL,
  `busheet_name` varchar(255)   default NULL,
  `busheet_code` varchar(10)   default NULL,
  `status` varchar(3)   default NULL,
  `create_user_id` varchar(32)   default NULL,
  `create_time` datetime default NULL,
  `update_user_Id` varchar(32)   default NULL,
  `update_time` datetime default NULL,
  `csv_templet` varchar(2048)   default NULL,
  `xls_templet` varchar(128)   default NULL,
  `table_name` varchar(255)   default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- ----------------------------
-- Records of GROOT_BUSHEET
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_BUSHEET_WORKFLOW
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_BUSHEET_WORKFLOW`;
CREATE TABLE `GROOT_BUSHEET_WORKFLOW` (
  `id` varchar(32)   NOT NULL,
  `workflow_name` varchar(255)   default NULL,
  `status_code` varchar(3)   default NULL,
  `BUSHEET_ID` varchar(32)   default NULL,
  `status` varchar(3)   default NULL,
  `create_user_id` varchar(32)   default NULL,
  `create_time` datetime default NULL,
  `update_user_id` varchar(32)   default NULL,
  `update_time` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_BUSHEET_WORKFLOW
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_CONFIG`;
CREATE TABLE `GROOT_SYSTEM_CONFIG` (
  `id` varchar(32)   NOT NULL,
  `config_name` varchar(255)   default NULL,
  `status` varchar(3)   default NULL,
  `create_user_id` varchar(32)   default NULL,
  `create_time` datetime default NULL,
  `update_user_id` varchar(32)   default NULL,
  `config_code` varchar(32)   default NULL,
  `update_time` datetime default NULL,
  `REMARK` varchar(255)   default NULL,
  `config_value` varchar(255)   default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_CONFIG
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_DEPARTMENT
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_DEPARTMENT`;
CREATE TABLE `GROOT_SYSTEM_DEPARTMENT` (
  `ID` varchar(32) NOT NULL,
  `DEPARTMENT_NAME` varchar(50) default NULL,
  `USER_ID` varchar(32) default NULL,
  `PARENT_ID` varchar(32) default NULL,
  `DEPARTMENT_CODE` varchar(32) default NULL,
  `STATUS` varchar(3) default NULL,
  `REMARK` varchar(256) default NULL,
  `CREATE_TIME` datetime default NULL,
  `CREATE_USER_ID` varchar(32) default NULL,
  `UPDATE_TIME` date default NULL,
  `UPDATE_USER_ID` varchar(32) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_DEPARTMENT
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_LOOKUP_CDE
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_LOOKUP_CDE`;
CREATE TABLE `GROOT_SYSTEM_LOOKUP_CDE` (
  `id` varchar(32)   NOT NULL,
  `type_id` varchar(32)   default NULL,
  `status` varchar(3)   default NULL,
  `cde_code` varchar(32)   default NULL,
  `cde_name` varchar(255)   default NULL,
  `REMARKS` varchar(255)   default NULL,
  `INTEGER_KEY` decimal(16,6) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_LOOKUP_CDE
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_LOOKUP_TYPE
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_LOOKUP_TYPE`;
CREATE TABLE `GROOT_SYSTEM_LOOKUP_TYPE` (
  `id` varchar(32)   NOT NULL,
  `type_NAME` varchar(255)   default NULL,
  `type_Code` varchar(255)   default NULL,
  `status` varchar(3)   default NULL,
  `create_user_id` varchar(32)   default NULL,
  `create_time` datetime default NULL,
  `update_user_id` varchar(32)   default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_LOOKUP_TYPE
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_MENU
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_MENU`;
CREATE TABLE `GROOT_SYSTEM_MENU` (
  `ID` varchar(32) NOT NULL,
  `MENU_NAME` varchar(128) default NULL,
  `PERMISSION_KEY` varchar(128) default NULL,
  `PARENT_ID` varchar(32) default NULL,
  `MENU_ACTION` varchar(64) default NULL,
  `MENU_CODE` varchar(16) default NULL,
  `TABLE_NAME_DEPICT` varchar(128) default NULL,
  `STATUS` varchar(3) default NULL,
  `REMARK` varchar(256) default NULL,
  `CREATE_TIME` datetime default NULL,
  `CREATE_USER_ID` varchar(32) default NULL,
  `UPDATE_TIME` datetime default NULL,
  `UPDATE_USER_ID` varchar(32) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_MENU
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_PERMISSION`;
CREATE TABLE `GROOT_SYSTEM_PERMISSION` (
  `ID` varchar(32) NOT NULL,
  `PERMISSION_NAME` varchar(128) default NULL,
  `PERMISSION_KEY` varchar(256) default NULL,
  `PARENT_ID` varchar(32) default NULL,
  `PERMISSION_CODE` varchar(8) default NULL,
  `REQUEST_MAPPING_VALUE` varchar(128) default NULL,
  `STATUS` varchar(3) default NULL,
  `REMARK` varchar(256) default NULL,
  `CREATE_TIME` datetime default NULL,
  `CREATE_USER_ID` varchar(32) default NULL,
  `UPDATE_TIME` datetime default NULL,
  `UPDATE_USER_ID` varchar(32) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_PERMISSION
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_ROLE`;
CREATE TABLE `GROOT_SYSTEM_ROLE` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(32) default NULL,
  `STATUS` varchar(3) default NULL,
  `CREATE_USER_ID` varchar(32) default NULL,
  `CREATE_TIME` datetime default NULL,
  `UPDATE_USER_ID` varchar(32) default NULL,
  `UPDATE_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_ROLE_PERMISSION`;
CREATE TABLE `GROOT_SYSTEM_ROLE_PERMISSION` (
  `ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) default NULL,
  `PERMISSION_ID` varchar(32) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_ROLE_PERMISSION
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_USER
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_USER`;
CREATE TABLE `GROOT_SYSTEM_USER` (
  `ID` varchar(32) NOT NULL,
  `LOGIN_NAME` varchar(64) default NULL,
  `PASSWORD` varchar(128) default NULL,
  `REAL_NAME` varchar(64) default NULL,
  `LAST_LOGIN_TIME` date default NULL,
  `ROLE_ID` varchar(32) default NULL,
  `PHONE` varchar(32) default NULL,
  `TEL` varchar(32) default NULL,
  `EMAIL` varchar(128) default NULL,
  `STATUS` varchar(3) default NULL,
  `REMARK` varchar(256) default NULL,
  `CREATE_TIME` datetime default NULL,
  `CREATE_USER_ID` varchar(32) default NULL,
  `UPDATE_TIME` datetime default NULL,
  `UPDATE_USER_ID` varchar(32) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of GROOT_SYSTEM_USER
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_SYSTEM_MONITOR`;
CREATE TABLE `GROOT_SYSTEM_MONITOR` (
  `ID` VARCHAR(32)   NOT NULL,
  `create_user_id` VARCHAR(32)   DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `PERMISSION` VARCHAR(40)   DEFAULT NULL,
  `MESSAGE` BLOB  DEFAULT NULL,
  `MESSAGE_ID` VARCHAR(32)   DEFAULT NULL,
  `CONTROLLER_NAME` VARCHAR(32)   DEFAULT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




-- ----------------------------
-- 订单
-- ----------------------------
DROP TABLE IF EXISTS `GROOT_ORDER_PAYMENT`;
create table GROOT_ORDER_PAYMENT
(
  `ID`  VARCHAR(32) not null,
  `ORDER_NO`  VARCHAR(32) DEFAULT NULL,
  `ORDER_TYPE`  VARCHAR(3) DEFAULT NULL,
  `CARD_NO`   VARCHAR(50) DEFAULT NULL,
  `TRX_AMT`   decimal(14,2) DEFAULT NULL,
  `MOBILE`    VARCHAR(20) DEFAULT NULL,
  `STATUS`    VARCHAR(3) DEFAULT NULL,
  `CREATE_TIME`    DATETIME DEFAULT NULL,
  `COMPLETE_TIME`   DATETIME DEFAULT NULL,
  `NOTE`    VARCHAR(256) DEFAULT NULL,
  PRIMARY KEY  (`ID`)
)ENGINE=MYISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

































