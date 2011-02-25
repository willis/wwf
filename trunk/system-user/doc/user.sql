
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


--
-- 表的结构 `paike_photo`
--

CREATE TABLE `paike_photo` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `userid` bigint(20) NOT NULL COMMENT '用户ID',
  `photoMd5` varchar(20) collate utf8_unicode_ci NOT NULL COMMENT 'md5名称',
  `photoName` varchar(255) collate utf8_unicode_ci NOT NULL COMMENT '照片名称',
  `sourceName` varchar(255) collate utf8_unicode_ci NOT NULL COMMENT '原始的文件名',
  `photoType` tinyint(4) NOT NULL COMMENT '照片格式(jpg,png...)',
  `deviceCompany` varchar(255) collate utf8_unicode_ci default NULL COMMENT '制造厂商(Canon)',
  `deviceType` varchar(255) collate utf8_unicode_ci default NULL COMMENT '设备型号(Canon EOS-1D Mark II)',
  `xResolution` int(11) default NULL COMMENT 'X方向分辨率(72)',
  `yResolution` int(11) default NULL COMMENT 'Y方向分辨率(72)',
  `resolutionUnit` varchar(255) collate utf8_unicode_ci default NULL COMMENT '分辨率单位(dpi)',
  `sourceImageWidth` int(11) NOT NULL COMMENT '指横向像素数(800 pixel)',
  `sourceImageHeight` int(11) NOT NULL COMMENT '指纵向像素数(600 pixel)',
  `colorSpace` varchar(32) collate utf8_unicode_ci default NULL COMMENT '色域、色彩空间(sRGB)',
  `iso` int(11) default NULL COMMENT 'ISO(100)',
  `aperture` varchar(255) collate utf8_unicode_ci default NULL COMMENT '光圈(F1.6)',
  `exposureTime` varchar(255) collate utf8_unicode_ci default NULL COMMENT '快门速度(0.00800 (1/125) sec)',
  `exposureProgram` varchar(255) collate utf8_unicode_ci default NULL COMMENT '曝光程序(光圈优先)',
  `dateTimeDigitized` datetime default NULL COMMENT '数字化时间',
  `dateTimeOriginal` datetime default NULL COMMENT '拍摄时间',
  `photoBits` tinyint(3) unsigned zerofill default NULL COMMENT '位数(16)',
  `focalLength` varchar(255) collate utf8_unicode_ci default NULL COMMENT '焦距(85 mm) ',
  `flash` tinyint(4) default NULL COMMENT '是否使用闪光灯(关闭)',
  `lightsource` varchar(255) collate utf8_unicode_ci default NULL COMMENT '白平衡 ',
  `meteringMode` varchar(255) collate utf8_unicode_ci default NULL COMMENT '测光方式(点测光)',
  `gps` varchar(255) collate utf8_unicode_ci default NULL COMMENT 'GPS信息',
  `software` varchar(255) collate utf8_unicode_ci default NULL COMMENT '处理软件(Adobe Photoshop CS Macintosh)',
  `exposureBiasValue` varchar(255) collate utf8_unicode_ci default NULL COMMENT '曝光补偿EV+-(0)',
  `maxApertureValue` varchar(255) collate utf8_unicode_ci default NULL COMMENT '最大光圈(85 mm) ',
  `compressedBits` varchar(255) collate utf8_unicode_ci default NULL COMMENT '压缩时每像素色彩位',
  `exifVersion` varchar(255) collate utf8_unicode_ci default NULL COMMENT 'exif版本',
  `createdDate` datetime NOT NULL COMMENT '上传时间',
  `accessCount` int(11) NOT NULL default '0' COMMENT '访问数',
  `pointCount` int(11) NOT NULL default '0' COMMENT '购买点数',
  `shareStatus` tinyint(4) NOT NULL default '0' COMMENT '是否共享',
  `tags` varchar(512) collate utf8_unicode_ci default NULL COMMENT '//tag标记(美女,野兽,性感)',
  `directory` bigint(20) NOT NULL default '0' COMMENT '所属目录',
  PRIMARY KEY  (`id`),
  KEY `index_userid` (`userid`),
  KEY `index_uid_dir` (`userid`,`directory`),
  KEY `index_uid_dir_share` (`userid`,`directory`,`shareStatus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
