CREATE TABLE `company` (
  `company_id` 			int(11) 		NOT 		NULL AUTO_INCREMENT,
  `company_name` 		varchar(100)	NOT 		NULL,
  `company_short_name` 	varchar(20) 	DEFAULT 	NULL,
  `company_date` 		date 			DEFAULT	 	NULL,
  `company_phone` 		varchar(15) 	DEFAULT		NULL,
  `company_email` 		varchar(50) 	DEFAULT		NULL,
  `reg_no` 				varchar(50) 	DEFAULT		NULL,
  `servicetax_no` 		varchar(50) 	DEFAULT		NULL,
  `pan_no` 				varchar(50) 	DEFAULT		NULL,
  `company_address` 	varchar(100) 	NOT 		NULL,
  `company_status` 		varchar(10) 	NOT 		NULL,
  `company_logo` blob,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `app_screens` (
  `screen_id` 		int(11) 		NOT 		NULL AUTO_INCREMENT,
  `sort_order` 		int(3) 			NOT 		NULL,
  `screen_code` 	varchar(100) 	NOT 		NULL,
  `target_class` 	varchar(200) 	DEFAULT 	NULL,
  `parent_name` 	varchar(40) 	DEFAULT 	NULL,
  `screen_desc`		varchar(100)    DEFAULT     NULL,
  `screen_status` 	varchar(20)		NOT 	 	NULL,
  `updated_date` 	datetime 		NOT 		NULL,
  `updated_by` 		varchar(100) 	NOT 		NULL,
  PRIMARY KEY (`screen_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `employee` (
  `employee_id` 		int(11) 		NOT 	NULL AUTO_INCREMENT,
  `company_id` 			int(11) 		NOT 	NULL,
  `first_name` 			varchar(100) 	NOT 	NULL,
  `last_name` 			varchar(100) 	DEFAULT	NULL,
  `dob` 				date 			NOT 	NULL,
  `employee_code` 		varchar(10) 	DEFAULT NULL,
  `employee_salut` 		varchar(5) 		NOT 	NULL,
  `primary_phone` 		bigint(20) 		NOT 	NULL,
  `primary_email` 		varchar(50) 	DEFAULT	NULL,
  `employee_photo` 		longblob		DEFAULT NULL,
  `employee_role` 		varchar(30) 	NOT 	NULL,
  `gender` 				varchar(10) 	NOT 	NULL,
  `doj` 				date 			NOT 	NULL,
  `employee_salary` 	double 			NOT 	NULL,
  `loginaccess_yn` 	    tinyint(1) 			 	NULL,
  `employee_status` 	varchar(10) 	NOT 	NULL,
  `updated_date` 		datetime 		NOT 	NULL,
  `updated_by` 			varchar(100) 	NOT 	NULL,
  PRIMARY KEY (`employee_id`),
  KEY `fk_employee_company_id` (`company_id`),
  CONSTRAINT `fk_employee_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `app_user` (
  `app_user_id` 	int(11) 		NOT 	NULL AUTO_INCREMENT,
  `company_id` 		int(11) 		NOT 	NULL,
  `employee_id` 	int(11) 		NOT 	NULL,
  `user_name` 		varchar(100) 	NOT 	NULL,
  `user_role` 		varchar(10) 	NOT 	NULL,
  `login_password` 	varchar(200) 	NOT 	NULL,
  `login_name` 		varchar(50) 	NOT 	NULL,
  `base_admin` 		tinyint(1) 		DEFAULT NULL,
  `created_date` 	datetime 		NOT 	NULL,
  `user_status` 	varchar(10) 	NOT 	NULL,
  `updated_date` 	datetime 		NOT 	NULL,
  `updated_by` 		varchar(100) 	NOT 	NULL,
  PRIMARY KEY (`app_user_id`),
  UNIQUE KEY `uk_appuser_id` (`login_name`),
  KEY `fk_appuser_company_id` (`company_id`),
  KEY `fk_appuser_employee_id` (`employee_id`),
  CONSTRAINT `fk_appuser_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_appuser_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `scrn_access_config` (
  `screen_access_id`	int(11) 		NOT 	NULL AUTO_INCREMENT,
  `company_id` 			int(11) 		NOT 	NULL,
  `screen_id` 			int(11) 		NOT 	NULL,
  `user_role` 			varchar(10) 	NOT 	NULL,
  `view_yn` 			tinyint(1) 		DEFAULT NULL,
  `access_status` 		varchar(10) 	NOT 	NULL,
  `updated_date` 		datetime 		NOT 	NULL,
  `updated_by` 			varchar(100) 	NOT 	NULL,
  PRIMARY KEY (`screen_access_id`),
  KEY `fk_scrnaccessconfig_company_id` (`company_id`),
  KEY `fk_scrnaccessconfig_screen_id` (`screen_id`),
  CONSTRAINT `fk_scrnaccessconfig_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_scrnaccessconfig_screen_id` FOREIGN KEY (`screen_id`) REFERENCES `app_screens` (`screen_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `approval_schema` (
  `apprv_schmid` 	int(11) 		NOT NULL AUTO_INCREMENT,
  `company_id` 		int(11) 		NOT NULL,
  `app_user_id` 	int(11) 		NOT NULL,
  `screen_id` 		int(11) 		NOT NULL,
  `user_role` 		varchar(10) 	NOT NULL,
  `aprvsch_status` 	varchar(10) 	NOT NULL,
  `is_approved` 	tinyint(1) 		NOT NULL,
  `updated_date` 	datetime 		NOT NULL,
  `updated_by` 		varchar(100) 	NOT NULL,
  PRIMARY KEY (`apprv_schmid`),
  KEY `fk_approvalschema_company_id` (`company_id`),
  KEY `fk_approvalschema_user_id` (`app_user_id`),
  KEY `fk_approvalschema_screen_id` (`screen_id`),
  CONSTRAINT `fk_approvalschema_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_approvalschema_screen_id` FOREIGN KEY (`screen_id`) REFERENCES `app_screens` (`screen_id`),
  CONSTRAINT `fk_approvalschema_user_id` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`app_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

	CREATE TABLE `lookup_data` (
	  `lookup_id` 	int(11) 		NOT NULL AUTO_INCREMENT,
	  `lookup_code` varchar(100)	NOT NULL,
	  `lookup_name`	varchar(100) 	NOT NULL,
	  `table_name` 	varchar(100) 	NOT NULL,
	  `lookup_no` 	int(11) 		NOT NULL,
	  `created_date` datetime 		NOT NULL,
	  `lookup_status` varchar(10) 	NOT NULL,
	  PRIMARY KEY (`lookup_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `assets` (
  `asset_id` 			int(11) 		NOT NULL AUTO_INCREMENT,
  `company_id` 			int(11) 		NOT NULL,
  `asset_name` 			varchar(100) 	NOT NULL,
  `asset_type` 			varchar(100) 	NOT NULL,
  `asset_code` 			varchar(100) 	NOT NULL,
  `asset_value` 		double 			NOT NULL,
  `asset_description` 	varchar(200) 	NOT NULL,
  `asset_status` 		varchar(10) 	NOT NULL,
  `updated_date` 		datetime 		NOT NULL,
  `updated_by` 			varchar(100) 	NOT NULL,
  PRIMARY KEY (`asset_id`),
  KEY `fk_assets_company_id` (`company_id`),
  CONSTRAINT `fk_assets_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_login` (
  `session_login_id` 	int(11) 		NOT NULL AUTO_INCREMENT,
  `company_id`			int(11) 		NOT NULL,
  `app_user_id` 		int(11) 		NOT NULL,
  `login_date` 			datetime 		NOT NULL,
  `logout_date` 		datetime 			NULL,
  `client_ip` 			varchar(100) 	NOT NULL,
  `session_id` 			varchar(100) 	NOT NULL,
  PRIMARY KEY (`session_login_id`),
  KEY `fk_userlogin_company_id` (`company_id`),
  KEY `fk_userlogin_app_user_id` (`app_user_id`),
  CONSTRAINT `fk_userlogin_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_userlogin_app_user_id` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`app_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `employee_document` (
  `document_id` 	int(11) 		NOT NULL AUTO_INCREMENT,
  `employee_id`		int(11) 		NOT NULL,
  `company_id` 		int(11) 		NOT NULL,
  `document_name` 	varchar(50)		NULL,
  `document` 		longblob        NULL,
  `updated_date` 	datetime 		NOT NULL,
  `document_status` varchar(20) 	NOT NULL,
  `updated_by` 		varchar(100) 	NOT NULL,
  PRIMARY KEY (`document_id`),
  KEY `fk_employeedocument_company_id` (`company_id`),
  KEY `fk_employeedocument_employee_id` (`employee_id`),
  CONSTRAINT `fk_employeedocument_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`),
  CONSTRAINT `fk_employeedocument_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `slno_gen` (
  `slno_id` 		int(11) 		NOT NULL AUTO_INCREMENT,
  `company_id`		int(11) 		NOT NULL,
  `ref_key` 		varchar(50) 	NULL,
  `key_desc` 		varchar(50)		NULL,
  `autogen_yn` 		tinyint(1)      NULL,
  `prefix_key` 	 	varchar(20)		NULL,
  `prefix_cncat` 	varchar(20) 	NULL,
  `suffix_key`      varchar(20)     NULL,
  `suffix_cncat`	varchar(20)     NULL,
  `curr_seqno`		int(20)         NULL,
  `last_seq_no`     int(20)         NULL,
  `updated_by` 		varchar(100) 	NULL,
  `updated_date`    datetime        NULL,
  PRIMARY KEY (`slno_id`),
  KEY `fk_slngen_company_id` (`company_id`),
  CONSTRAINT `fk_slngen_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

