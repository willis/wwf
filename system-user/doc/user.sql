
--
-- 表的结构 `paike_user`
--

CREATE TABLE `paike_user` (
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(32) collate utf8_unicode_ci NOT NULL COMMENT '用户名 ',
  `password` varchar(16) collate utf8_unicode_ci NOT NULL COMMENT '密码',
  `status` tinyint(4) NOT NULL default '1' COMMENT '状态(是否禁用,删除)',
  `name` varchar(16) collate utf8_unicode_ci NOT NULL COMMENT '昵称',
  `sex` tinyint(4) NOT NULL default '1' COMMENT '性别',
  `birthday` date default NULL COMMENT '生日',
  `email` varchar(32) collate utf8_unicode_ci NOT NULL COMMENT '邮箱',
  `city` int(11) NOT NULL default '0' COMMENT '所在城市',
  `profession` varchar(32) collate utf8_unicode_ci default NULL COMMENT '职业',
  `userStatus` tinyint(4) NOT NULL default '0' COMMENT '用户状态(浏览中,离线等)',
  `lastLoginDate` datetime NOT NULL COMMENT '用户最后登录时间',
  `loginCount` int(11) NOT NULL default '0' COMMENT '用户登陆次数',
  `photoCount` int(11) NOT NULL default '0' COMMENT '用户总照片数',
  `friendCount` int(11) NOT NULL default '0' COMMENT '用户朋友数',
  `accessCount` bigint(20) NOT NULL default '0' COMMENT '被访问次数',
  `immediatelyInfo` varchar(128) collate utf8_unicode_ci default NULL COMMENT '即时个性签名',
  `fattCount` int(11) NOT NULL default '0' COMMENT '被关注的数量',
  `myattCount` int(11) NOT NULL default '0' COMMENT '我关注别人的数量',
  `points` int(11) NOT NULL default '0' COMMENT '用户点数',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY  (`userid`),
  UNIQUE KEY `username` (`username`),
  KEY `index_sex` (`sex`),
  KEY `index_status` (`status`),
  KEY `index_city` (`city`),
  KEY `index_userStatus` (`userStatus`),
  KEY `index_loginCount` (`loginCount`),
  KEY `index_photoCount` (`photoCount`),
  KEY `index_friendCount` (`friendCount`),
  KEY `index_accessCount` (`accessCount`),
  KEY `index_login` (`username`,`password`,`status`),
  KEY `index_login2` (`email`,`password`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户数据表';


--
-- 表的结构 `paike_userext`
--

CREATE TABLE `paike_userext` (
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `info` varchar(8000) collate utf8_unicode_ci default NULL COMMENT '用户介绍',
  PRIMARY KEY  (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- 表的结构 `paike_userrelate`
--

CREATE TABLE `paike_userrelate` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `fuid` bigint(20) NOT NULL COMMENT '朋友用户id',
  `relateDate` datetime NOT NULL COMMENT '关联时间',
  `attention` tinyint(4) NOT NULL default '0' COMMENT '是否关注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uid_fid` (`userid`,`fuid`),
  KEY `index_userid` (`userid`),
  KEY `index_ui_attention` (`userid`,`attention`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户关联表';

