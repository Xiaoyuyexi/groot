-- ----------------------------
-- Table structure for GROOT_BUSHEET_PERMISSION
-- ----------------------------
CREATE TABLE GROOT_BUSHEET_PERMISSION (
  id varchar2(32)   NOT NULL,
  create_user_id varchar2(32)   ,
  create_time date ,
  update_user_id varchar2(32)   ,
  update_time date ,
  BUSHEET_ID varchar2(32)   ,
  DEPARTMENT_CODE varchar2(32)   ,
  USER_ID varchar2(32)   ,
  status varchar2(3)   
) ;
alter table GROOT_BUSHEET_PERMISSION
  add constraint PK_BUSHEET_PERMISSION primary key (ID);

-- ----------------------------
-- Records of GROOT_BUSHEET_PERMISSION
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_BUSHEET
-- ----------------------------
CREATE TABLE GROOT_BUSHEET (
  id varchar2(32)   NOT NULL,
  busheet_name varchar2(255)   ,
  busheet_code varchar2(10)   ,
  status varchar2(3)   ,
  create_user_id varchar2(32)   ,
  create_time date ,
  update_user_Id varchar2(32)   ,
  update_time date ,
  csv_templet varchar2(2048)   ,
  xls_templet varchar2(128)   ,
  table_name varchar2(255)
) ;
alter table GROOT_BUSHEET
  add constraint PK_BUSHEET primary key (ID);
-- ----------------------------
-- Records of GROOT_BUSHEET
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_BUSHEET_WORKFLOW
-- ----------------------------
CREATE TABLE GROOT_BUSHEET_WORKFLOW (
  id varchar2(32)   NOT NULL,
  workflow_name varchar2(255)   ,
  status_code varchar2(3)   ,
  BUSHEET_ID varchar2(32)   ,
  status varchar2(3)   ,
  create_user_id varchar2(32)   ,
  create_time date ,
  update_user_id varchar2(32)   ,
  update_time date
) ;
alter table GROOT_BUSHEET_WORKFLOW
  add constraint PK_BUSHEET_WORKFLOW primary key (ID);
-- ----------------------------
-- Records of GROOT_BUSHEET_WORKFLOW
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_CONFIG
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_CONFIG (
  id varchar2(32)   NOT NULL,
  config_name varchar2(255)   ,
  status varchar2(3)   ,
  create_user_id varchar2(32)   ,
  create_time date ,
  update_user_id varchar2(32)   ,
  config_code varchar2(32)   ,
  update_time date ,
  REMARK varchar2(255)   ,
  config_value varchar2(255)
) ;
alter table GROOT_SYSTEM_CONFIG
  add constraint PK_SYSTEM_CONFIG primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_CONFIG
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_DEPARTMENT
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_DEPARTMENT (
  ID varchar2(32) NOT NULL,
  DEPARTMENT_NAME varchar2(50) ,
  USER_ID varchar2(32) ,
  PARENT_ID varchar2(32) ,
  DEPARTMENT_CODE varchar2(32) ,
  STATUS varchar2(3) ,
  REMARK varchar2(256) ,
  CREATE_TIME date ,
  CREATE_USER_ID varchar2(32) ,
  UPDATE_TIME date ,
  UPDATE_USER_ID varchar2(32)
) ;
alter table GROOT_SYSTEM_DEPARTMENT
  add constraint PK_SYSTEM_DEPARTMENT primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_DEPARTMENT
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_LOOKUP_CDE
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_LOOKUP_CDE (
  id varchar2(32)   NOT NULL,
  type_id varchar2(32)   ,
  status varchar2(3)   ,
  cde_code varchar2(32)   ,
  cde_name varchar2(255)   ,
  REMARKS varchar2(255)   ,
  INTEGER_KEY decimal(16,6)
) ;
alter table GROOT_SYSTEM_LOOKUP_CDE
  add constraint PK_SYSTEM_LOOKUP_CDE primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_LOOKUP_CDE
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_LOOKUP_TYPE
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_LOOKUP_TYPE (
  id varchar2(32)   NOT NULL,
  type_NAME varchar2(255)   ,
  type_Code varchar2(255)   ,
  status varchar2(3)   ,
  create_user_id varchar2(32)   ,
  create_time date ,
  update_user_id varchar2(32)
) ;
alter table GROOT_SYSTEM_LOOKUP_TYPE
  add constraint PK_SYSTEM_LOOKUP_TYPE primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_LOOKUP_TYPE
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_MENU
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_MENU (
  ID varchar2(32) NOT NULL,
  MENU_NAME varchar2(128) ,
  PERMISSION_KEY varchar2(128) ,
  PARENT_ID varchar2(32) ,
  MENU_ACTION varchar2(64) ,
  MENU_CODE varchar2(16) ,
  TABLE_NAME_DEPICT varchar2(128) ,
  STATUS varchar2(3) ,
  REMARK varchar2(256) ,
  CREATE_TIME date ,
  CREATE_USER_ID varchar2(32) ,
  UPDATE_TIME date ,
  UPDATE_USER_ID varchar2(32)
) ;
alter table GROOT_SYSTEM_MENU
  add constraint PK_SYSTEM_MENU primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_MENU
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_PERMISSION
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_PERMISSION (
  ID varchar2(32) NOT NULL,
  PERMISSION_NAME varchar2(128) ,
  PERMISSION_KEY varchar2(256) ,
  PARENT_ID varchar2(32) ,
  PERMISSION_CODE varchar2(8) ,
  REQUEST_MAPPING_VALUE varchar2(128) ,
  STATUS varchar2(3) ,
  REMARK varchar2(256) ,
  CREATE_TIME date ,
  CREATE_USER_ID varchar2(32) ,
  UPDATE_TIME date ,
  UPDATE_USER_ID varchar2(32)
) ;
alter table GROOT_SYSTEM_PERMISSION
  add constraint PK_SYSTEM_PERMISSION primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_PERMISSION
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_ROLE
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_ROLE (
  ID varchar2(32) NOT NULL,
  NAME varchar2(32) ,
  STATUS varchar2(3) ,
  CREATE_USER_ID varchar2(32) ,
  CREATE_TIME date ,
  UPDATE_USER_ID varchar2(32) ,
  UPDATE_TIME date
) ;
alter table GROOT_SYSTEM_ROLE
  add constraint PK_SYSTEM_ROLE primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_ROLE_PERMISSION
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_ROLE_PERMISSION (
  ID varchar2(32) NOT NULL,
  ROLE_ID varchar2(32) ,
  PERMISSION_ID varchar2(32)
) ;
alter table GROOT_SYSTEM_ROLE_PERMISSION
  add constraint PK_SYSTEM_ROLE_PERMISSION primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_ROLE_PERMISSION
-- ----------------------------

-- ----------------------------
-- Table structure for GROOT_SYSTEM_USER
-- ----------------------------
CREATE TABLE GROOT_SYSTEM_USER (
  ID varchar2(32) NOT NULL,
  LOGIN_NAME varchar2(64) ,
  PASSWORD varchar2(128) ,
  REAL_NAME varchar2(64) ,
  LAST_LOGIN_TIME date ,
  ROLE_ID varchar2(32) ,
  PHONE varchar2(32) ,
  TEL varchar2(32) ,
  EMAIL varchar2(128) ,
  STATUS varchar2(3) ,
  REMARK varchar2(256) ,
  CREATE_TIME date ,
  CREATE_USER_ID varchar2(32) ,
  UPDATE_TIME date ,
  UPDATE_USER_ID varchar2(32)
) ;
alter table GROOT_SYSTEM_USER
  add constraint PK_SYSTEM_USER primary key (ID);
-- ----------------------------
-- Records of GROOT_SYSTEM_USER
-- ----------------------------

CREATE TABLE GROOT_SYSTEM_MONITOR (
  ID VARCHAR2(32) NOT NULL,
  create_user_id VARCHAR2(32),
  create_time date,
  PERMISSION VARCHAR2(40),
  MESSAGE BLOB,
  MESSAGE_ID VARCHAR2(32),
  CONTROLLER_NAME VARCHAR2(32)
) ;
alter table GROOT_SYSTEM_MONITOR
  add constraint PK_SYSTEM_MONITOR primary key (ID);
-- Add comments to the columns 
comment on column GROOT_SYSTEM_MONITOR.create_user_id
  is '操作人';
comment on column GROOT_SYSTEM_MONITOR.create_time
  is '操作时间';
comment on column GROOT_SYSTEM_MONITOR.PERMISSION
  is '权限';
comment on column GROOT_SYSTEM_MONITOR.MESSAGE_ID
  is '信息ID';
comment on column GROOT_SYSTEM_MONITOR.MESSAGE
  is '信息';
comment on column GROOT_SYSTEM_MONITOR.CONTROLLER_NAME
  is '类名';
  
  
  -- ----------------------------
-- Records of GROOT_ORDER_PAYMENT 订单表
-- ----------------------------
create table GROOT_ORDER_PAYMENT
(
  ID                          VARCHAR2(32) not null,
  ORDER_NO                  VARCHAR2(32),
  ORDER_TYPE                  VARCHAR2(3),
  CARD_NO                     VARCHAR2(50),
  TRX_AMT                     NUMBER(14,2),
  STATUS                      VARCHAR2(3),
  MOBILE                      VARCHAR2(20),
  CREATE_TIME                 DATE,
  COMPLETE_TIME               DATE,
  NOTE                        VARCHAR2(256)
);
-- Add comments to the columns 
comment on column GROOT_ORDER_PAYMENT.ORDER_NO
  is  '订单号';
comment on column GROOT_ORDER_PAYMENT.ORDER_TYPE
  is  '订单类型';
comment on column GROOT_ORDER_PAYMENT.CARD_NO
  is '银行卡号';
comment on column GROOT_ORDER_PAYMENT.TRX_AMT
  is '交易金额';
comment on column GROOT_ORDER_PAYMENT.STATUS
  is '交易状态';
comment on column GROOT_ORDER_PAYMENT.MOBILE
  is '手机号';
comment on column GROOT_ORDER_PAYMENT.CREATE_TIME
  is '创建时间';
comment on column GROOT_ORDER_PAYMENT.COMPLETE_TIME
  is '完成时间';
comment on column GROOT_ORDER_PAYMENT.NOTE
  is '备注';
  
-- Create/Recreate primary, unique and foreign key constraints 
alter table GROOT_ORDER_PAYMENT
  add constraint PK_ORDER_PAYMENT primary key (ID);
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  