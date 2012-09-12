<%--
  Created by IntelliJ IDEA.
  User: devuser
  Date: 12-9-5
  Time: 上午10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>上传文件：</label>
                <input id="myFile" type="file" size="20" name="myFile" >
                <button onclick="upload();">上传</button>
            </p>
        </div>
</div>
<script type="text/javascript">
    function upload(){
        $.ajaxFileUpload({
            url:'${cxp}storage!upload.action?ip=${param.ip}',
            secureuri:false,
            fileElementId:'myFile',
            dataType: "json",//返回json格式的数据
            success: function (data, status){
                alertMsg.correct('上传成功！')
            },
            error: function (data, status, e){
                alertMsg.error("上传失败！");
            }
        })
    }


</script>
