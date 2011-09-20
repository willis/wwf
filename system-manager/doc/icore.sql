-- phpMyAdmin SQL Dump
-- version 2.11.2.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2011 年 09 月 20 日 09:05
-- 服务器版本: 5.0.45
-- PHP 版本: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- 数据库: `icore`
--

-- --------------------------------------------------------

--
-- 表的结构 `bot_images`
--

CREATE TABLE `bot_images` (
  `id` varchar(32) collate utf8_unicode_ci NOT NULL default '',
  `url` varchar(1024) collate utf8_unicode_ci NOT NULL default '',
  `filename` varchar(1024) collate utf8_unicode_ci NOT NULL default '',
  `status` char(1) collate utf8_unicode_ci NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `bot_images`
--


-- --------------------------------------------------------

--
-- 表的结构 `bot_url`
--

CREATE TABLE `bot_url` (
  `id` varchar(32) collate utf8_unicode_ci NOT NULL default '',
  `url` varchar(1024) collate utf8_unicode_ci NOT NULL default '',
  `status` char(1) collate utf8_unicode_ci NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `bot_url`
--


-- --------------------------------------------------------

--
-- 表的结构 `icore_affix`
--

CREATE TABLE `icore_affix` (
  `ID` bigint(20) NOT NULL auto_increment,
  `name` varchar(200) collate utf8_unicode_ci default NULL,
  `source` varchar(255) collate utf8_unicode_ci default NULL,
  `type` varchar(200) collate utf8_unicode_ci default NULL,
  `f_size` varchar(10) collate utf8_unicode_ci default NULL,
  `extname` varchar(10) collate utf8_unicode_ci default NULL,
  `objectId` varchar(50) collate utf8_unicode_ci default NULL,
  `createDate` datetime default NULL,
  `objectType` int(11) default NULL,
  `hotNum` bigint(20) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

--
-- 导出表中的数据 `icore_affix`
--


-- --------------------------------------------------------

--
-- 表的结构 `icore_annex`
--

CREATE TABLE `icore_annex` (
  `id` bigint(20) NOT NULL,
  `fileNames` varchar(255) collate utf8_unicode_ci default NULL,
  `fileSize` varchar(255) collate utf8_unicode_ci default NULL,
  `filePath` varchar(255) collate utf8_unicode_ci default NULL,
  `type` varchar(255) collate utf8_unicode_ci default NULL,
  `date` datetime default NULL,
  `object_id` varchar(255) collate utf8_unicode_ci default NULL,
  `user` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK9CA282DDDBC1B7F7` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `icore_annex`
--


-- --------------------------------------------------------

--
-- 表的结构 `icore_dictionary`
--

CREATE TABLE `icore_dictionary` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) collate utf8_unicode_ci NOT NULL,
  `describe_mysql` varchar(50) collate utf8_unicode_ci default NULL,
  `orderby` int(11) default NULL,
  `flag` int(11) default NULL,
  `defaultIndex` int(11) default NULL,
  `curDate` datetime default NULL,
  `extendf1` bigint(20) default NULL,
  `extendf2` bigint(20) default NULL,
  `extendf3` int(11) default NULL,
  `extendf4` int(11) default NULL,
  `extendf5` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf6` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf7` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf8` varchar(255) collate utf8_unicode_ci default NULL,
  `parentid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKCECB6DAD1B3AC462` (`parentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `icore_dictionary`
--

INSERT INTO `icore_dictionary` (`id`, `name`, `describe_mysql`, `orderby`, `flag`, `defaultIndex`, `curDate`, `extendf1`, `extendf2`, `extendf3`, `extendf4`, `extendf5`, `extendf6`, `extendf7`, `extendf8`, `parentid`) VALUES
(1, '字典根节点', '字典根节点', NULL, NULL, NULL, '2011-01-30 09:47:37', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(301, '21', '2', 2, NULL, NULL, '2011-09-17 12:12:05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
(3651, '222', '2', 2, NULL, NULL, '2011-09-19 11:14:20', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- 表的结构 `icore_group`
--

CREATE TABLE `icore_group` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) collate utf8_unicode_ci NOT NULL,
  `describe_mysql` varchar(50) collate utf8_unicode_ci default NULL,
  `orderby` bigint(20) default NULL,
  `extendf1` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf2` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf3` varchar(255) collate utf8_unicode_ci default NULL,
  `parentid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK9CF8E708BBE1A4E9` (`parentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `icore_group`
--

INSERT INTO `icore_group` (`id`, `name`, `describe_mysql`, `orderby`, `extendf1`, `extendf2`, `extendf3`, `parentid`) VALUES
(1, '组织管理', '组织管理', 1, NULL, NULL, NULL, NULL),
(3, '行政部', '行政部', 1, NULL, NULL, NULL, 1),
(152, '1', '1', 2, NULL, NULL, NULL, 1),
(251, '2', '2', 3, NULL, NULL, NULL, 1),
(252, '3', '3', 1, NULL, NULL, NULL, 251),
(951, '2', '2', 2, NULL, NULL, NULL, 251);

-- --------------------------------------------------------

--
-- 表的结构 `icore_menu`
--

CREATE TABLE `icore_menu` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) collate utf8_unicode_ci NOT NULL,
  `alias` varchar(255) collate utf8_unicode_ci default NULL,
  `description` varchar(255) collate utf8_unicode_ci default NULL,
  `img` varchar(255) collate utf8_unicode_ci default NULL,
  `link` varchar(255) collate utf8_unicode_ci default NULL,
  `orderBy` bigint(20) default NULL,
  `curDate` datetime default NULL,
  `parentid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKD3866FB6395714DF` (`parentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `icore_menu`
--

INSERT INTO `icore_menu` (`id`, `name`, `alias`, `description`, `img`, `link`, `orderBy`, `curDate`, `parentid`) VALUES
(1, '系统菜单', 'menu_system', NULL, NULL, NULL, NULL, '2011-02-09 08:34:50', NULL),
(502, '系统管理', 'sys_menu', '系统管理', '', '/user/', 2, '2011-02-18 16:04:43', 1),
(503, '组织维护', '', '组织维护', '', '/user/sysgroup.jsp', 1, '2011-02-09 15:40:05', 502),
(504, '权限代码维护', '', '权限代码维护', '', '/user/syspopedom_list.jsp', 2, '2011-02-09 15:45:27', 502),
(505, '角色维护', '', '角色维护', '', '/user/sysrole_list.jsp', 3, '2011-02-09 15:46:19', 502),
(506, '用户管理', '', '用户管理', '', '/user/sysuser.jsp', 4, '2011-02-09 15:46:50', 502),
(507, '系统菜单维护', '', '系统菜单维护', '', '/user/sysMenu!listSysMenu.action', 5, '2011-02-09 15:47:37', 502),
(508, '数据字典', '', '数据字典', '', '/manager/dictionary/dictionaryAction!listDictionary.action', 6, '2011-02-21 16:54:42', 502),
(651, '控制台', 'console', 'console', '', '', 0, '2011-09-19 16:41:21', 1),
(701, '会员管理', 'member', 'member', '', '', 1, '2011-02-24 16:01:32', 1),
(2252, '注册用户', '', '注册用户', '', '/manager/member/memberlist.jsp', 1, '2011-02-24 16:10:08', 701),
(2556, '系统日志', '', '系统日志', '', '/systemLog!logList.action', 7, '2011-02-25 09:25:22', 502),
(3001, '上传测试', 'upload', '上传测试', '', '/swfupload/index.jsp', 1, '2011-09-20 16:22:36', 651),
(3004, '数据管理', 'datamanager', '抓取数据和分析处理数据', '', '', 0, '2011-09-19 16:27:34', 1),
(3005, '数据源管理', 'dataorn', '数据源管理', '', '/manager/bot/bot_list.jsp', 1, '2011-09-08 11:34:11', 3004);

-- --------------------------------------------------------

--
-- 表的结构 `icore_popedom`
--

CREATE TABLE `icore_popedom` (
  `id` bigint(20) NOT NULL,
  `code` varchar(200) collate utf8_unicode_ci default NULL,
  `describe_mysql` varchar(100) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `icore_popedom`
--

INSERT INTO `icore_popedom` (`id`, `code`, `describe_mysql`) VALUES
(451, 'resource_add', '系统管理员'),
(3362, 'dd', 'dd');

-- --------------------------------------------------------

--
-- 表的结构 `icore_role`
--

CREATE TABLE `icore_role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) collate utf8_unicode_ci NOT NULL,
  `describe_mysql` varchar(50) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `icore_role`
--

INSERT INTO `icore_role` (`id`, `name`, `describe_mysql`) VALUES
(402, '系统管理员', '系统管理员'),
(3551, 'a', 'a');

-- --------------------------------------------------------

--
-- 表的结构 `icore_user`
--

CREATE TABLE `icore_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) collate utf8_unicode_ci NOT NULL,
  `password` varchar(50) collate utf8_unicode_ci NOT NULL,
  `truename` varchar(50) collate utf8_unicode_ci default NULL,
  `sex` varchar(4) collate utf8_unicode_ci default NULL,
  `email` varchar(50) collate utf8_unicode_ci default NULL,
  `tel` varchar(50) collate utf8_unicode_ci default NULL,
  `ask` varchar(50) collate utf8_unicode_ci default NULL,
  `answer` varchar(50) collate utf8_unicode_ci default NULL,
  `other` longtext collate utf8_unicode_ci,
  `mark` varchar(50) collate utf8_unicode_ci default NULL,
  `regtime` datetime default NULL,
  `logintime` datetime default NULL,
  `extendf1` bigint(20) default NULL,
  `extendf2` bigint(20) default NULL,
  `extendf3` bigint(20) default NULL,
  `extendf4` bigint(20) default NULL,
  `extendf5` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf6` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf7` varchar(255) collate utf8_unicode_ci default NULL,
  `extendf8` varchar(255) collate utf8_unicode_ci default NULL,
  `status` int(11) default NULL,
  `loginip` varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `icore_user`
--

INSERT INTO `icore_user` (`id`, `username`, `password`, `truename`, `sex`, `email`, `tel`, `ask`, `answer`, `other`, `mark`, `regtime`, `logintime`, `extendf1`, `extendf2`, `extendf3`, `extendf4`, `extendf5`, `extendf6`, `extendf7`, `extendf8`, `status`, `loginip`) VALUES
(1, 'admin', '202CB962AC59075B964B07152D234B70', '系统管理员', '男', '3', '3', NULL, NULL, '', NULL, '2011-01-24 17:16:39', '2011-09-20 16:37:10', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '0:0:0:0:0:0:0:1'),
(51, '1', 'C4CA4238A0B923820DCC509A6F75849B', '1', '男', '', '', NULL, NULL, '', NULL, '2011-01-26 13:41:10', '2011-02-09 15:11:03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(52, '2', 'C81E728D9D4C2F636F067F89CC14862C', '2', '男', '', '', NULL, NULL, '', NULL, '2011-01-26 13:48:15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(53, '3', 'ECCBC87E4B5CE2FE28308FD9F2A7BAF3', '3', '男', '', '', NULL, NULL, '', NULL, '2011-01-26 13:48:25', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(101, '4', 'A87FF679A2F3E71D9181A67B7542122C', '4', '男', '', '', NULL, NULL, '', NULL, '2011-01-27 08:24:01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(102, '5', 'E4DA3B7FBBCE2345D7772B0674A318D5', '5', '男', '', '', NULL, NULL, '', NULL, '2011-01-27 08:24:08', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(103, '6', '1679091C5A880FAF6FB5E6087EB1B2DC', '6', '男', '', '', NULL, NULL, '', NULL, '2011-01-27 08:24:15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(104, '7', '8F14E45FCEEA167A5A36DEDD4BEA2543', '7', '男', '', '', NULL, NULL, '', NULL, '2011-01-27 08:24:22', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(105, '8', 'C9F0F895FB98AB9159F51FD0297E236D', '8', '男', '', '', NULL, NULL, '', NULL, '2011-01-27 08:24:30', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(106, '9', '6EE28F9CE7BC54C83D305716618E25B8', '9', '男', '', '', NULL, NULL, '', NULL, '2011-01-27 08:24:38', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL),
(107, '11122', '698D51A19D8A121CE581499D7B701668', '11', '男', '', '', NULL, NULL, '', NULL, '2011-01-27 08:24:54', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `paike_picture`
--

CREATE TABLE `paike_picture` (
  `pictureid` bigint(20) NOT NULL,
  `photoMd5` varchar(255) collate utf8_unicode_ci default NULL,
  `userId` bigint(20) NOT NULL,
  `filename` varchar(255) collate utf8_unicode_ci default NULL,
  `fileType` varchar(255) collate utf8_unicode_ci default NULL,
  `mimeType` varchar(255) collate utf8_unicode_ci default NULL,
  `fileSize` varchar(255) collate utf8_unicode_ci default NULL,
  `fileDataTime` varchar(255) collate utf8_unicode_ci default NULL,
  `srcHeight` int(11) NOT NULL,
  `srcWidth` int(11) default NULL,
  `imageDescription` varchar(255) collate utf8_unicode_ci default NULL,
  `make` varchar(255) collate utf8_unicode_ci default NULL,
  `model` varchar(255) collate utf8_unicode_ci default NULL,
  `orientation` varchar(255) collate utf8_unicode_ci default NULL,
  `xResolution` varchar(255) collate utf8_unicode_ci default NULL,
  `yResolution` varchar(255) collate utf8_unicode_ci default NULL,
  `software` varchar(255) collate utf8_unicode_ci default NULL,
  `dateTime` varchar(255) collate utf8_unicode_ci default NULL,
  `artist` varchar(255) collate utf8_unicode_ci default NULL,
  `yCbCrPositioning` varchar(255) collate utf8_unicode_ci default NULL,
  `copyright` varchar(255) collate utf8_unicode_ci default NULL,
  `copyrightOfPhotographer` varchar(255) collate utf8_unicode_ci default NULL,
  `copyrightOfEditor` varchar(255) collate utf8_unicode_ci default NULL,
  `exifVersion` varchar(255) collate utf8_unicode_ci default NULL,
  `flashPixVersion` varchar(255) collate utf8_unicode_ci default NULL,
  `dateTimeDigitized` varchar(255) collate utf8_unicode_ci default NULL,
  `computedHeight` varchar(255) collate utf8_unicode_ci default NULL,
  `computedWidth` varchar(255) collate utf8_unicode_ci default NULL,
  `apertureValue` varchar(255) collate utf8_unicode_ci default NULL,
  `shutterSpeedValue` varchar(255) collate utf8_unicode_ci default NULL,
  `apertureFNumber` varchar(255) collate utf8_unicode_ci default NULL,
  `maxApertureValue` varchar(255) collate utf8_unicode_ci default NULL,
  `exposureTime` varchar(255) collate utf8_unicode_ci default NULL,
  `fNumber` varchar(255) collate utf8_unicode_ci default NULL,
  `meteringMode` varchar(255) collate utf8_unicode_ci default NULL,
  `lightSource` varchar(255) collate utf8_unicode_ci default NULL,
  `flash` varchar(255) collate utf8_unicode_ci default NULL,
  `exposureMode` varchar(255) collate utf8_unicode_ci default NULL,
  `whiteBalance` varchar(255) collate utf8_unicode_ci default NULL,
  `exposureProgram` varchar(255) collate utf8_unicode_ci default NULL,
  `exposureBiasValue` varchar(255) collate utf8_unicode_ci default NULL,
  `isoSpeedRatings` varchar(255) collate utf8_unicode_ci default NULL,
  `componentsConfiguration` varchar(255) collate utf8_unicode_ci default NULL,
  `compressedBitsPerPixel` varchar(255) collate utf8_unicode_ci default NULL,
  `focusDistance` varchar(255) collate utf8_unicode_ci default NULL,
  `focalLength` varchar(255) collate utf8_unicode_ci default NULL,
  `focalLengthIn35mmFilm` varchar(255) collate utf8_unicode_ci default NULL,
  `userCommentEncoding` varchar(255) collate utf8_unicode_ci default NULL,
  `userComment` varchar(255) collate utf8_unicode_ci default NULL,
  `colorSpace` varchar(255) collate utf8_unicode_ci default NULL,
  `exifImageLength` varchar(255) collate utf8_unicode_ci default NULL,
  `exifImageWidth` varchar(255) collate utf8_unicode_ci default NULL,
  `fileSource` varchar(255) collate utf8_unicode_ci default NULL,
  `sceneType` varchar(255) collate utf8_unicode_ci default NULL,
  `thumbnailFileType` varchar(255) collate utf8_unicode_ci default NULL,
  `thumbnailMimeType` varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`pictureid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `paike_picture`
--


-- --------------------------------------------------------

--
-- 表的结构 `paike_user`
--

CREATE TABLE `paike_user` (
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(32) collate utf8_unicode_ci NOT NULL COMMENT '用户名 ',
  `password` varchar(16) collate utf8_unicode_ci NOT NULL COMMENT '密码',
  `status` tinyint(4) NOT NULL default '1' COMMENT '状态(1=正常,2=禁用,3=删除)',
  `name` varchar(16) collate utf8_unicode_ci NOT NULL COMMENT '昵称',
  `sex` tinyint(4) NOT NULL default '1' COMMENT '性别',
  `birthday` date default NULL COMMENT '生日',
  `email` varchar(32) collate utf8_unicode_ci NOT NULL COMMENT '邮箱',
  `city` int(11) NOT NULL default '0' COMMENT '所在城市',
  `profession` varchar(32) collate utf8_unicode_ci default NULL COMMENT '职业',
  `userStatus` tinyint(4) NOT NULL default '0' COMMENT '用户状态(1=在线,2=离线,3=发表中)',
  `lastLoginDate` datetime NOT NULL COMMENT '用户最后登录时间',
  `loginCount` int(11) NOT NULL default '0' COMMENT '用户登陆次数',
  `photoCount` int(11) NOT NULL default '0' COMMENT '用户总照片数',
  `friendCount` int(11) NOT NULL default '0' COMMENT '用户朋友数',
  `accessCount` bigint(20) NOT NULL default '0' COMMENT '被访问次数',
  `immediatelyInfo` varchar(128) collate utf8_unicode_ci default NULL COMMENT '即时个性签名',
  `fattCount` int(11) NOT NULL default '0' COMMENT '被关注的数量',
  `myattCount` int(11) NOT NULL default '0' COMMENT '我关注别人的数量',
  `points` int(11) NOT NULL default '0' COMMENT '用户点数',
  `userLevel` int(11) NOT NULL default '1' COMMENT '用户级别',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `outPoints` bigint(20) default NULL,
  `userMoney` bigint(20) default NULL,
  `outMoney` bigint(20) default NULL,
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
  KEY `index_login2` (`email`,`password`,`status`),
  KEY `index_level` (`userLevel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户数据表';

--
-- 导出表中的数据 `paike_user`
--

INSERT INTO `paike_user` (`userid`, `username`, `password`, `status`, `name`, `sex`, `birthday`, `email`, `city`, `profession`, `userStatus`, `lastLoginDate`, `loginCount`, `photoCount`, `friendCount`, `accessCount`, `immediatelyInfo`, `fattCount`, `myattCount`, `points`, `userLevel`, `createdDate`, `outPoints`, `userMoney`, `outMoney`) VALUES
(1, 'test', '', 1, '', 1, NULL, '', 0, NULL, 0, '2011-02-25 12:26:05', 0, 0, 0, 0, NULL, 0, 0, 0, 1, '2011-02-25 12:26:05', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `paike_userext`
--

CREATE TABLE `paike_userext` (
  `userid` bigint(20) NOT NULL,
  `info` varchar(255) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`userid`),
  KEY `FK72EC93297F385ADD` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `paike_userext`
--


-- --------------------------------------------------------

--
-- 表的结构 `paike_weburl`
--

CREATE TABLE `paike_weburl` (
  `urlid` bigint(20) NOT NULL,
  `siteName` varchar(255) collate utf8_unicode_ci NOT NULL,
  `url` varchar(255) collate utf8_unicode_ci NOT NULL,
  `enName` varchar(255) collate utf8_unicode_ci default NULL,
  `threadNum` int(11) default NULL,
  `status` int(11) default NULL,
  `width` int(11) default NULL,
  `height` int(11) default NULL,
  PRIMARY KEY  (`urlid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `paike_weburl`
--

INSERT INTO `paike_weburl` (`urlid`, `siteName`, `url`, `enName`, `threadNum`, `status`, `width`, `height`) VALUES
(1, '亿康网', 'www.ekang120.com', 'ekang', 1, 0, 11, 11),
(2, '新浪', 'www.sina.com.cn11', 'sina', 1, 1, 11, 11),
(3951, 'adsf', 'asdf', '', 1, 0, 1, 1);

-- --------------------------------------------------------

--
-- 表的结构 `sequenceid`
--

CREATE TABLE `sequenceid` (
  `idtype` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY  (`idtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `sequenceid`
--

INSERT INTO `sequenceid` (`idtype`, `id`) VALUES
(100, 4151),
(200, 1),
(300, 1),
(400, 1),
(500, 1),
(600, 1),
(700, 1),
(800, 1),
(900, 1);

-- --------------------------------------------------------

--
-- 表的结构 `sysroletosysmenu`
--

CREATE TABLE `sysroletosysmenu` (
  `sysmenuid` bigint(20) NOT NULL,
  `sysroleid` bigint(20) NOT NULL,
  PRIMARY KEY  (`sysroleid`,`sysmenuid`),
  KEY `FKE421616E33BDE5CF` (`sysroleid`),
  KEY `FKE421616E2AA77961` (`sysmenuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `sysroletosysmenu`
--

INSERT INTO `sysroletosysmenu` (`sysmenuid`, `sysroleid`) VALUES
(1, 402),
(502, 402),
(503, 402),
(504, 402),
(505, 402),
(506, 402),
(507, 402),
(508, 402),
(651, 402),
(701, 402),
(2252, 402),
(2556, 402),
(3001, 402),
(3004, 402),
(3005, 402);

-- --------------------------------------------------------

--
-- 表的结构 `sysroletosyspopedom`
--

CREATE TABLE `sysroletosyspopedom` (
  `syspopedomid` bigint(20) NOT NULL,
  `sysroleid` bigint(20) NOT NULL,
  KEY `FK786219FF33BDE5CF` (`sysroleid`),
  KEY `FK786219FF80D7C06F` (`syspopedomid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `sysroletosyspopedom`
--

INSERT INTO `sysroletosyspopedom` (`syspopedomid`, `sysroleid`) VALUES
(451, 402);

-- --------------------------------------------------------

--
-- 表的结构 `system_log`
--

CREATE TABLE `system_log` (
  `id` bigint(20) NOT NULL,
  `logtype` bigint(20) default NULL,
  `logtitle` longtext collate utf8_unicode_ci,
  `logcontent` varchar(255) collate utf8_unicode_ci default NULL,
  `createat` datetime default NULL,
  `createby` bigint(20) default NULL,
  `username` varchar(255) collate utf8_unicode_ci default NULL,
  `ip` varchar(16) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `system_log`
--

INSERT INTO `system_log` (`id`, `logtype`, `logtitle`, `logcontent`, `createat`, `createby`, `username`, `ip`) VALUES
(1901, NULL, NULL, NULL, '2011-02-21 16:53:31', 1, '系统管理员', NULL),
(2001, NULL, NULL, NULL, '2011-02-21 16:58:30', 1, '系统管理员', NULL),
(2101, 0, 'cn.com.icore.user.service.impl.SysPopedomServiceImpl', 'saveSysPopedom', '2011-02-21 17:08:19', 1, '系统管理员', '127.0.0.1'),
(2103, 0, 'cn.com.icore.user.service.impl.SysRoleServiceImpl', 'removeSysPopedom', '2011-02-21 17:09:51', 1, '系统管理员', '127.0.0.1'),
(2151, 0, 'cn.com.icore.user.service.impl.SysRoleServiceImpl', 'removeSysPopedom', '2011-02-21 17:23:05', 1, '系统管理员', '127.0.0.1'),
(2201, 0, 'com.mpaike.user.service.impl.SysPopedomServiceImpl', 'saveSysPopedom', '2011-02-23 08:56:44', 1, '系统管理员', '127.0.0.1'),
(2251, 0, 'com.mpaike.user.service.impl.SysMenuServiceImpl', 'save', '2011-02-24 16:04:54', 1, '系统管理员', '127.0.0.1'),
(2253, 0, 'com.mpaike.user.service.impl.SysRoleServiceImpl', 'addSysMenu', '2011-02-24 16:12:11', 1, '系统管理员', '127.0.0.1'),
(2301, 0, 'com.mpaike.user.service.impl.SysGroupServiceImpl', 'addSysUserToGroup', '2011-02-24 16:34:59', 1, '系统管理员', '127.0.0.1'),
(2351, 0, 'com.mpaike.user.service.impl.SysRoleServiceImpl', 'listToGrid', '2011-02-24 16:40:14', 1, '系统管理员', '127.0.0.1'),
(2401, 0, 'com.mpaike.user.service.impl.SysGroupServiceImpl', 'listCheckUsersToGrid', '2011-02-24 16:43:46', 1, '系统管理员', '127.0.0.1'),
(2402, 0, 'com.mpaike.user.service.impl.SysGroupServiceImpl', 'listCheckUsersToGrid', '2011-02-24 16:44:12', 1, '系统管理员', '127.0.0.1'),
(2451, 0, 'com.mpaike.user.service.impl.SysUserServiceImpl', 'listToGrid', '2011-02-24 16:47:34', 1, '系统管理员', '127.0.0.1'),
(2452, 0, 'com.mpaike.user.service.impl.SysRoleServiceImpl', 'listToGrid', '2011-02-24 16:50:09', 1, '系统管理员', '127.0.0.1'),
(2501, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-24 17:04:19', 1, '系统管理员', '127.0.0.1'),
(2502, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-24 17:19:17', 1, '系统管理员', '127.0.0.1'),
(2503, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-24 17:19:50', 1, '系统管理员', '127.0.0.1'),
(2551, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 09:18:48', 1, '系统管理员', '192.168.5.183'),
(2552, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 09:19:24', 1, '系统管理员', '192.168.5.183'),
(2553, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 09:19:49', 1, '系统管理员', '192.168.5.183'),
(2554, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 09:22:14', 1, '系统管理员', '192.168.5.183'),
(2555, 0, 'com.mpaike.user.service.impl.SysMenuServiceImpl', 'save', '2011-02-25 09:23:59', 1, '系统管理员', '192.168.5.183'),
(2557, 0, 'com.mpaike.user.service.impl.SysRoleServiceImpl', 'addSysMenu', '2011-02-25 09:25:31', 1, '系统管理员', '192.168.5.183'),
(2558, 0, 'com.mpaike.user.service.impl.SysRoleServiceImpl', 'listToGrid', '2011-02-25 09:27:43', 1, '系统管理员', '192.168.5.183'),
(2559, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 09:27:47', 1, '系统管理员', '192.168.5.183'),
(2601, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 09:36:53', 1, '系统管理员', '192.168.5.183'),
(2602, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 09:37:16', 1, '系统管理员', '192.168.5.183'),
(2652, 0, 'com.mpaike.user.service.impl.SysRoleServiceImpl', 'listToGrid', '2011-02-25 10:30:37', 1, '系统管理员', '192.168.5.183'),
(2653, 0, 'com.mpaike.user.service.impl.SysUserServiceImpl', 'listToGrid', '2011-02-25 10:30:49', 1, '系统管理员', '192.168.5.183'),
(2654, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 10:31:40', 1, '系统管理员', '192.168.5.183'),
(2701, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 10:46:39', 1, '系统管理员', '192.168.5.183'),
(2702, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 10:58:33', 1, '系统管理员', '192.168.5.183'),
(2703, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 10:59:20', 1, '系统管理员', '192.168.5.183'),
(2705, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 11:10:00', 1, '系统管理员', '192.168.5.183'),
(2751, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 11:16:25', 1, '系统管理员', '192.168.5.183'),
(2801, 0, 'com.mpaike.user.service.impl.SysUserServiceImpl', 'listToGrid', '2011-02-25 11:20:08', 1, '系统管理员', '192.168.5.183'),
(2802, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 11:20:31', 1, '系统管理员', '192.168.5.183'),
(2851, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 11:28:54', 1, '系统管理员', '192.168.5.183'),
(2852, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 11:29:44', 1, '系统管理员', '192.168.5.183'),
(2853, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 12:14:36', 1, '系统管理员', '192.168.5.183'),
(2854, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 12:18:20', 1, '系统管理员', '192.168.5.183'),
(2901, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 12:26:05', 1, '系统管理员', '192.168.5.183'),
(2951, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 12:43:56', 1, '系统管理员', '192.168.5.183'),
(2952, 0, 'com.mpaike.member.service.impl.MemberServiceImpl', 'listToGrid', '2011-02-25 12:44:30', 1, '系统管理员', '192.168.5.183');

-- --------------------------------------------------------

--
-- 表的结构 `sysuserandgrouptorole`
--

CREATE TABLE `sysuserandgrouptorole` (
  `id` bigint(20) NOT NULL,
  `mark` varchar(255) collate utf8_unicode_ci NOT NULL,
  `sysroleid` bigint(20) default NULL,
  `sysuserid` bigint(20) default NULL,
  `sysgroupid` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK4140187139133B39` (`sysuserid`),
  KEY `FK4140187133BDE5CF` (`sysroleid`),
  KEY `FK414018712D0E211` (`sysgroupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `sysuserandgrouptorole`
--

INSERT INTO `sysuserandgrouptorole` (`id`, `mark`, `sysroleid`, `sysuserid`, `sysgroupid`) VALUES
(403, 'U', 402, 107, NULL),
(501, 'U', 402, 104, NULL),
(551, 'U', 402, 1, NULL),
(2051, 'U', 402, 106, NULL),
(3851, 'U', 3551, 1, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `sysusertosysgroup`
--

CREATE TABLE `sysusertosysgroup` (
  `sysgroupid` bigint(20) NOT NULL,
  `sysuserid` bigint(20) NOT NULL,
  PRIMARY KEY  (`sysgroupid`,`sysuserid`),
  KEY `FK5923314539133B39` (`sysuserid`),
  KEY `FK592331452D0E211` (`sysgroupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 导出表中的数据 `sysusertosysgroup`
--

INSERT INTO `sysusertosysgroup` (`sysgroupid`, `sysuserid`) VALUES
(3, 1),
(252, 1),
(951, 1),
(3, 51),
(252, 51),
(951, 51),
(3, 52),
(152, 52),
(252, 52),
(951, 52),
(3, 53),
(152, 53),
(252, 53),
(951, 53),
(3, 101),
(152, 101),
(252, 101),
(951, 101),
(3, 102),
(152, 102),
(252, 102),
(951, 102),
(3, 103),
(152, 103),
(951, 103),
(3, 104),
(152, 104),
(951, 104),
(3, 105),
(152, 105),
(252, 105),
(951, 105),
(3, 106),
(152, 106),
(252, 106),
(3, 107);

--
-- 限制导出的表
--

--
-- 限制表 `icore_annex`
--
ALTER TABLE `icore_annex`
  ADD CONSTRAINT `FK9CA282DDDBC1B7F7` FOREIGN KEY (`user`) REFERENCES `icore_user` (`id`);

--
-- 限制表 `icore_dictionary`
--
ALTER TABLE `icore_dictionary`
  ADD CONSTRAINT `FKCECB6DAD1B3AC462` FOREIGN KEY (`parentid`) REFERENCES `icore_dictionary` (`id`);

--
-- 限制表 `icore_group`
--
ALTER TABLE `icore_group`
  ADD CONSTRAINT `FK9CF8E708BBE1A4E9` FOREIGN KEY (`parentid`) REFERENCES `icore_group` (`id`);

--
-- 限制表 `icore_menu`
--
ALTER TABLE `icore_menu`
  ADD CONSTRAINT `FKD3866FB6395714DF` FOREIGN KEY (`parentid`) REFERENCES `icore_menu` (`id`);

--
-- 限制表 `paike_userext`
--
ALTER TABLE `paike_userext`
  ADD CONSTRAINT `FK72EC93297F385ADD` FOREIGN KEY (`userid`) REFERENCES `paike_user` (`userid`);

--
-- 限制表 `sysroletosysmenu`
--
ALTER TABLE `sysroletosysmenu`
  ADD CONSTRAINT `FKE421616E2AA77961` FOREIGN KEY (`sysmenuid`) REFERENCES `icore_menu` (`id`),
  ADD CONSTRAINT `FKE421616E33BDE5CF` FOREIGN KEY (`sysroleid`) REFERENCES `icore_role` (`id`);

--
-- 限制表 `sysroletosyspopedom`
--
ALTER TABLE `sysroletosyspopedom`
  ADD CONSTRAINT `FK786219FF33BDE5CF` FOREIGN KEY (`sysroleid`) REFERENCES `icore_role` (`id`),
  ADD CONSTRAINT `FK786219FF80D7C06F` FOREIGN KEY (`syspopedomid`) REFERENCES `icore_popedom` (`id`);

--
-- 限制表 `sysuserandgrouptorole`
--
ALTER TABLE `sysuserandgrouptorole`
  ADD CONSTRAINT `FK414018712D0E211` FOREIGN KEY (`sysgroupid`) REFERENCES `icore_group` (`id`),
  ADD CONSTRAINT `FK4140187133BDE5CF` FOREIGN KEY (`sysroleid`) REFERENCES `icore_role` (`id`),
  ADD CONSTRAINT `FK4140187139133B39` FOREIGN KEY (`sysuserid`) REFERENCES `icore_user` (`id`);

--
-- 限制表 `sysusertosysgroup`
--
ALTER TABLE `sysusertosysgroup`
  ADD CONSTRAINT `FK592331452D0E211` FOREIGN KEY (`sysgroupid`) REFERENCES `icore_group` (`id`),
  ADD CONSTRAINT `FK5923314539133B39` FOREIGN KEY (`sysuserid`) REFERENCES `icore_user` (`id`);
