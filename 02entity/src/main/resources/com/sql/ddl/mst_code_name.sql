DROP TABLE IF EXISTS `mst_code_name`;
CREATE TABLE `mst_code_name`(
  `code_id` char(4) NOT NULL COMMENT 'CODE编号', 
  `code_value` char(4) NOT NULL COMMENT 'CODE值', 
  `code_name` varchar(50) NOT NULL COMMENT 'CODE内容', 
  `option1` varchar(50) NULL COMMENT '备选显示', 
  `option2` varchar(50) NULL COMMENT '备选显示', 
  `option3` varchar(50) NULL COMMENT '备选显示', 
  `option4` varchar(50) NULL COMMENT '备选显示', 
  PRIMARY KEY (`code_id`,`code_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

