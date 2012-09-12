<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    request.setAttribute("cxp",request.getContextPath());
%>
<script type="text/javascript" src="${cxp}/js/TableTree4J.js"></script>
<link rel="StyleSheet" href="${cxp }/js/css/tabletree4j.css"
			type="text/css" />
		<style>
.body {
	font-size: 12px;
}

.btnDiv a {
	color: #0000FF;
	text-decoration: none;
}

.btnDiv a:hover {
	color: #CC3300;
	text-decoration: underline;
}

.items {
	color: #669999;
	font-size: 14px;
}

.title {
	font-size: 16px;
	font-weight: bold;
}

.copyrightdiv {
	font-size: 12px;
	font-family: "Arial";
	color: #C0C0C0;
}

.centerClo {
	text-align: center;
}
</style>

<h2 class="contentTitle">数据字典</h2>
<div id="_dictionary_TreeDiv">
</div>
<!-- grid的容器. -->
<div id="_dictionary_grid_container">
</div>
<script>

    //GridTree
    var _dictionarygridTree;
    function showGridTree(){
        //init
        _dictionarygridTree=new TableTree4J("_dictionarygridTree","${cxp }/js/");
        _dictionarygridTree.config.useCookies=true;

        _dictionarygridTree.tableDesc="<table border=\"1\" class=\"GridView\" width=\"100%\" id=\"_dictionary_grid_container_table\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse\"  bordercolordark=\"#C0C0C0\" bordercolorlight=\"#C0C0C0\" >";
        var headerDataList=new Array("字典名称","更新日期","操作");
        var widthList=new Array("20%","20%","60%");
        //参数: arrayHeader,id,headerWidthList,booleanOpen,classStyle,hrefTip,hrefStatusText,icon,iconOpen
        _dictionarygridTree.setHeader(headerDataList,-1,widthList,true,"GridHead","This is a tipTitle of head href!","header status text","","");
        //设置列样式
        _dictionarygridTree.gridHeaderColStyleArray=new Array("","","","centerClo");
        _dictionarygridTree.gridDataCloStyleArray=new Array("","","","centerClo");

        var dataList=new Array("${rootObj.name}","<s:date name="rootObj.curDate" format="yyyy-MM-dd" />","<a  href=\"${cxp}/manager/dictionary/dictionaryAction!toAddDictionary.action?navTabId=&pid=${rootObj.id}\"  target=\"dialog\"  mask=\"true\" title=\"添加子节点\">添加子节点</a> ");
        //参数: dataList,id,pid,booleanOpen,order,url,target,hrefTip,hrefStatusText,classStyle,icon,iconOpen
        _dictionarygridTree.addGirdNode(dataList,${rootObj.id},-1,null,1,"#",null,"${rootObj.name}","状态栏文字",null,null,null);

        //add data
    <s:if test="!tree.isEmpty()">
    <s:iterator value="tree">

        var dataList=new Array("<s:property value="name" />"," <s:date name="curDate" format="yyyy-MM-dd" />","<a   href=\"${cxp}/manager/dictionary/dictionaryAction!toAddDictionary.action?pid=<s:property value="id" />\" target=\"dialog\" rel=\"_add<s:property value="id" />\" mask=\"true\" title=\"添加子节点\" >添加子节点</a> | <a target=\"dialog\"  mask=\"true\" title=\"修改子节点\" href=\"${cxp}/manager/dictionary/dictionaryAction!toEditDictionary.action?id=<s:property value="id" />\"  target=\"dialog\" rel=\"_update<s:property value="id" />\" mask=\"true\" title=\"修改子节点\">修改子节点</a>  |<a  target=\"ajaxTodo\" href=\"dictionaryAction!del.action?navTabId&id=<s:property value="id" />\" callback=\"navTabReload\" title=\"确定要删除吗?\" >删除</a> ");
        //参数: dataList,id,pid,booleanOpen,order,url,target,hrefTip,hrefStatusText,classStyle,icon,iconOpen
        _dictionarygridTree.addGirdNode(dataList,<s:property value="id" />,<s:property value="parentObj.id" />,null,<s:property value="orderby" />,"#",null,"<s:property value="name" />","状态栏文字",null,null,null);
    </s:iterator>
    </s:if>

        //print
        _dictionarygridTree.printTableTreeToElement("_dictionary_TreeDiv");

    }

    showGridTree();

</script>
<script src="${cxp }/appjs/plugin/dictionary/jquery.dictionary.js"></script>

<select name="s1" id="s1">
    <option value="">请选择</option>
</select>
<select name="s2" id="s2">
    <option value="">请选择</option>
</select>
<select name="s3" id="s3">
    <option value="">请选择</option>
</select>

<script >
    $("#s1").dictionary({
        url:'dictionaryAction!getDictionarysByParentId.action',
        data:{id:'1'},


        callback:function(value){
            $("#s2").dictionary({
                url:'dictionaryAction!getDictionarysByParentId.action',
                data:{id:value},
                callback:function(value){
                    $("#s3").dictionary({
                        url:'dictionaryAction!getDictionarysByParentId.action',
                        data:{id:value},
                        callback:function(value){

                        },
                        defaultOp:[{name:'请选择信息',value:''}]

                    });
                },
                defaultOp:[{name:'请选择信息',value:''}]

            });
        },
        defaultOp:[{name:'请选择信息',value:''}]

    });



</script>
