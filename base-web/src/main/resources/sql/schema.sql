CREATE TABLE `HATCHET_SYSTEM_USER` (
  `ID` varchar(32) collate utf8_unicode_ci NOT NULL,
  `LOGIN_NAME` varchar(64) collate utf8_unicode_ci default NULL,
  `PASSWORD` varchar(128) collate utf8_unicode_ci default NULL,
  `REAL_NAME` varchar(64) collate utf8_unicode_ci default NULL,
  `LAST_LOGIN_TIME` date default NULL,
  `ROLE_ID` varchar(32) collate utf8_unicode_ci default NULL,
  `PHONE` varchar(32) collate utf8_unicode_ci default NULL,
  `TEL` varchar(32) collate utf8_unicode_ci default NULL,
  `EMAIL` varchar(128) collate utf8_unicode_ci default NULL,
  `STATUS` varchar(3) collate utf8_unicode_ci default NULL,
  `REMARK` varchar(256) collate utf8_unicode_ci default NULL,
  `CREATE_TIME` datetime default NULL,
  `CREATE_USER_ID` varchar(32) collate utf8_unicode_ci default NULL,
  `UPDATE_TIME` datetime default NULL,
  `UPDATE_USER_ID` varchar(32) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `HATCHET_SYSTEM_ROLE` (
  `ID` varchar(32) collate utf8_unicode_ci NOT NULL,
  `NAME` varchar(32) collate utf8_unicode_ci default NULL,
  `STATUS` varchar(3) collate utf8_unicode_ci default NULL,
  `CREATE_USER_ID` varchar(32) collate utf8_unicode_ci default NULL,
  `CREATE_TIME` datetime default NULL,
  `UPDATE_USER_ID` varchar(32) collate utf8_unicode_ci default NULL,
  `UPDATE_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `HATCHET_SYSTEM_PERMISSION` (
  `ID` varchar(32) collate utf8_unicode_ci NOT NULL,
  `PERMISSION_NAME` varchar(128) collate utf8_unicode_ci default NULL,
  `PERMISSION_KEY` varchar(256) collate utf8_unicode_ci default NULL,
  `PARENT_ID` varchar(32) collate utf8_unicode_ci default NULL,
  `PERMISSION_CODE` varchar(8) collate utf8_unicode_ci default NULL,
  `REQUEST_MAPPING_VALUE` varchar(128) collate utf8_unicode_ci default NULL,
  `STATUS` varchar(3) collate utf8_unicode_ci default NULL,
  `REMARK` varchar(256) collate utf8_unicode_ci default NULL,
  `CREATE_TIME` datetime default NULL,
  `CREATE_USER_ID` varchar(32) collate utf8_unicode_ci default NULL,
  `UPDATE_TIME` datetime default NULL,
  `UPDATE_USER_ID` varchar(32) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `HATCHET_SYSTEM_ROLE_PERMISSION` (
  `ID` varchar(32) collate utf8_unicode_ci NOT NULL,
  `ROLE_ID` varchar(32) collate utf8_unicode_ci default NULL,
  `PERMISSION_ID` varchar(32) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




