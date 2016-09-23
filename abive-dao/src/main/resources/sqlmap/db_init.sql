-- passport
CREATE TABLE `passport_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(128) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `tel` varchar(32) DEFAULT NULL,
  `icon` varchar(256) DEFAULT NULL,
  `level` int(1) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `create_ip` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_ip` varchar(64) DEFAULT NULL,
  `mark` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;