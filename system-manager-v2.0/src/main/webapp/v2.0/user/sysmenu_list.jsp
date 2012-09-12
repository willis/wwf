<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="${cxp}/js/TableTree4J.js"></script>
<link rel="StyleSheet" href="${cxp }/js/css/tabletree4j.css"
      type="text/css" />

<h2 class="contentTitle">系统菜单管理</h2>
<div id="_sysmenu_gridTreeDiv">
</div>
<!-- grid的容器. -->
<div id="_sysmenu_gridTree_container">
</div>
<script>

    //GridTree
    var _sysmenu_gridTree;
    function showGridTree(){
        //init
        _sysmenu_gridTree=new TableTree4J("_sysmenu_gridTree","${cxp }/js/");
        _sysmenu_gridTree.config.useCookies=true;

        _sysmenu_gridTree.tableDesc="<table border=\"1\" class=\"GridView\" width=\"100%\" id=\"_sysmenu_gridTree_table\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse\"  bordercolordark=\"#C0C0C0\" bordercolorlight=\"#C0C0C0\" >";
        var headerDataList=new Array("菜单名称","更新日期","操作");
        var widthList=new Array("20%","20%","60%");
        //参数: arrayHeader,id,headerWidthList,booleanOpen,classStyle,hrefTip,hrefStatusText,icon,iconOpen
        _sysmenu_gridTree.setHeader(headerDataList,-1,widthList,true,"GridHead","This is a tipTitle of head href!","header status text","","");
        //设置列样式
        _sysmenu_gridTree.gridHeaderColStyleArray=new Array("","","","centerClo");
        _sysmenu_gridTree.gridDataCloStyleArray=new Array("","","","centerClo");

        var dataList=new Array("${rootObj.name}","<s:date name="rootObj.curDate" format="yyyy-MM-dd" />","<a href=\"${cxp}/user/sysMenu!toAddSysMenu.action?pid=${rootObj.id}\"  target=\"dialog\"  mask=\"true\" title=\"添加子节点\" >添加子节点</a>");
        //参数: dataList,id,pid,booleanOpen,order,url,target,hrefTip,hrefStatusText,classStyle,icon,iconOpen
        _sysmenu_gridTree.addGirdNode(dataList,${rootObj.id},-1,null,1,"#",null,"${rootObj.name}","状态栏文字",null,null,null);

        //add data
    <s:if test="!tree.isEmpty()">
    <s:iterator value="tree">

        var dataList=new Array("<s:property value="name" />"," <s:date name="curDate" format="yyyy-MM-dd" />","<a href=\"${cxp}/user/sysMenu!toAddSysMenu.action?pid=<s:property value="id" />\" target=\"dialog\"  mask=\"true\" title=\"添加子节点\">添加子节点</a> | <a href=\"${cxp}/user/sysMenu!toEditSysMenu.action?id=<s:property value="id" />\" target=\"dialog\"  mask=\"true\" title=\"修改子节点\">修改子节点</a>  |<a target=\"ajaxTodo\" href=\"sysMenu!del.action?navTabId&id=<s:property value="id" />\" callback=\"navTabReload\"  title=\"确定要删除吗?\" >删除</a> ");
        //参数: dataList,id,pid,booleanOpen,order,url,target,hrefTip,hrefStatusText,classStyle,icon,iconOpen
        _sysmenu_gridTree.addGirdNode(dataList,<s:property value="id" />,<s:property value="parentObj.id" />,null,<s:property value="orderBy" />,"#",null,"<s:property value="name" />","状态栏文字",null,null,null);
    </s:iterator>
    </s:if>
        //print
        _sysmenu_gridTree.printTableTreeToElement("_sysmenu_gridTree_container");

    }
    showGridTree();


</script>