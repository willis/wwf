<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 12-8-31
  Time: 上午10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<c:out value='${cxp }'/>/appjs/plugin/dictionary/jquery.dictionary.js"></script>

<select name="s1" id="s1">
    <option value="">请选择</option>
</select>
<select name="s2" id="s2">
    <option value="">请选择</option>
</select>
<select name="s3" id="s3">
    <option value="">请选择</option>
</select>
            <br/>
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
                    url:'<c:out value='${cxp }'/>/manager/dictionary/listDictionary.do',
                    data:{method:'getDictionarysByParentId',id:'1'},


                    callback:function(value){
                        $("#s2").dictionary({
                            url:'<c:out value='${cxp }'/>/manager/dictionary/listDictionary.do',
                            data:{method:'getDictionarysByParentId',id:value},
                            callback:function(value){
                                $("#s3").dictionary({
                                    url:'<c:out value='${cxp }'/>/manager/dictionary/listDictionary.do',
                                    data:{method:'getDictionarysByParentId',id:value},
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
