使用方式：

1、增加对应附件的类型配置：在affix.spring.xml里面的
	<property name="dirs">
		<map>
			<entry key="附件类型编号，不能重复" value="保存这个类型的文件夹名称"/>
			如：<entry key="5" value="oniteVideo"/>
		</map>
	</property>

2、在jsp页面使用：
	如：
	<%
		String uuid = UUID.randomUUID().toString();
		pageContext.setAttribute("uuid",uuid);
	%>
	<input type="hidden" name="uuid" value="${empty bean.id?uuid:bean.id}">
	<iframe	src="${cxp}/affix/affixAction.do?method=list&objectType=5&objectId=${empty bean.id?uuid:bean.id}&action=update&sigin=true&types=bat,BAT"
										height="30px"	width="100%" frameborder="no" border="0" ></iframe>
	其中：
		1> ${cxp}对应：reqeust.getContextPath()，可以自由定义
		2> objectType=5对应上面你添加的附件类型编号：<entry key="5" value="oniteVideo"/>的key值
		3> objectId=${empty bean.id?uuid:bean.id}对应所属对象的ID，如果是新增对象，则取uuid,等待保存时再更新附件的objectId值
		4> action=update表示可以上传附件，在查看页面这个参数不需要
		5> sigin=true表示单文件上传，如果是多文件上传这个参数不需要
		6> types=bat,BAT表示附件上传的文件类型只支持bat或者BAT格式，如果不设置此参数表示允许任意格式文件

3、保存对象时更新附件objectId:
	service.save(bean);
	affixService.updateAffixId(5, request.getParamter("uuid"),bean.getId() + "");
	
	
	