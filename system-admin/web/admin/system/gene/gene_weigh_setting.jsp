<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>基因库管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script src="${cxp }/appjs/ajaxform.common.js"></script>
		<style>
		.intputWidth{
			width:50px;
		}
		</style>
	</head>
	<body style="background-color: transparent;">
	<form id="myForm" name="myForm1" action="geneaction!setting.action" method="post">
	<input type="hidden" name="status" value="2" />
		<table class="tableContent">
			<tbody>
				<tr id="topRow">
					<td id="topLeft">
					</td>
					<td id="topMiddle">
					</td>
					<td id="topRight">
					</td>
				</tr>
				<tr id="middleRow">
					<td id="middleLeft">
					</td>
					<td id="tdContent" bgColor="#ffffff">
					
						<h2 class="underline">
							用户基因兴趣度衰减率管理
						</h2>
									
						<table id="inputTable" class="table" >
							<tr>
								<td class="lefttd" width="180">
									文章阅读衰减率：
								</td>
								<td>
                                    <input id="depr_artread" type="text" name="relationCountMap.depr_artread" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_artread'/>" dataType="Double" msg="文章阅读衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章收藏衰减率：
								</td>
								<td>
                                  <input id="depr_fav" type="text" name="relationCountMap.depr_fav" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_fav'/>" dataType="Double" msg="文章收藏衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td class="lefttd">
									文章分享衰减率：
								</td>
								<td>
                                    <input id="depr_share" type="text" name="relationCountMap.depr_share" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_share'/>" dataType="Double" msg="文章分享衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章称坏衰减率：
								</td>
								<td>
									<input id="depr_bad" type="text" name="relationCountMap.depr_bad" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_bad'/>" dataType="Double" msg="文章称坏衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td class="lefttd">
									文章称赞衰减率：
								</td>
								<td>
									<input id="depr_good" type="text" name="relationCountMap.depr_good" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_good'/>" dataType="Double" msg="文章称赞衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章回复衰减率：
								</td>
								<td>
									<input id="depr_review" type="text" name="relationCountMap.depr_review" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_review'/>" dataType="Double" msg="文章回复衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td class="lefttd">
									标签阅读时间衰减率：
								</td>
								<td>
									<input id="depr_readtime" type="text" name="relationCountMap.depr_readtime" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_readtime'/>" dataType="Double"  msg="标签阅读时间衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									标签频道访问衰减率：
								</td>
								<td>
									<input id="depr_tagread" type="text" name="relationCountMap.depr_tagread" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_tagread'/>" dataType="Double" msg="标签频道访问衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									游戏兴趣衰减率：
								</td>
								<td>
									<input id="depr_game" type="text" name="relationCountMap.depr_game" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_game'/>" dataType="Double" msg="游戏兴趣衰减率必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									Tag我要更多分值衰减率：
								</td>
								<td>
									<input id="depr_morecommend" type="text" name="relationCountMap.depr_morecommend" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.depr_morecommend'/>" dataType="Double" msg="Tag我要更多分值衰减率必须是实数"/>
								</td>
							</tr>
						</table>
						<h2 class="underline">
							用户基因兴趣度权重管理
						</h2>
									
						<table id="inputTable" class="table" >
							<tr>
								<td class="lefttd" width="180">
									文章阅读权重：
								</td>
								<td>
                                    <input id="weigh_artread" type="text" name="relationCountMap.weigh_artread" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_artread'/>" dataType="Double" msg="文章阅读权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章收藏权重：
								</td>
								<td>
                                  <input id="weigh_fav" type="text" name="relationCountMap.weigh_fav" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_fav'/>" dataType="Double" msg="文章收藏权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td class="lefttd">
									文章分享权重：
								</td>
								<td>
                                    <input id="weigh_share" type="text" name="relationCountMap.weigh_share" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_share'/>" dataType="Double" msg="文章分享权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章称坏权重：
								</td>
								<td>
									<input id="weigh_bad" type="text" name="relationCountMap.weigh_bad" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_bad'/>" dataType="Double" msg="文章称坏权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td class="lefttd">
									文章称赞权重：
								</td>
								<td>
									<input id="weigh_good" type="text" name="relationCountMap.weigh_good" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_good'/>" dataType="Double" msg="文章称赞权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章回复权重：
								</td>
								<td>
									<input id="weigh_review" type="text" name="relationCountMap.weigh_review" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_review'/>" dataType="Double" msg="文章回复权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td class="lefttd">
									标签阅读时间权重：
								</td>
								<td>
									<input id="weigh_readtime" type="text" name="relationCountMap.weigh_readtime" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_readtime'/>" dataType="Double" msg="标签阅读时间权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									标签频道访问权重：
								</td>
								<td>
									<input id="weigh_tagread" type="text" name="relationCountMap.weigh_tagread" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_tagread'/>" dataType="Double" msg="标签频道访问权重必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									Tag我要更多分值权重：
								</td>
								<td>
									<input id="weigh_morecommend" type="text" name="relationCountMap.weigh_morecommend" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.weigh_morecommend'/>" dataType="Double" msg="Tag我要更多分值权重必须是实数"/>
								</td>
							</tr>
						</table>
						<h2 class="underline">
							用户频道分值管理
						</h2>
									
						<table id="inputTable" class="table" >
							<tr>
								<td class="lefttd" width="180">
									关注频道所占固定分值：
								</td>
								<td>
                                    <input id="value_attention" type="text" name="relationCountMap.value_attention" class="intputWidth" maxlength="8" value="<s:property value='relationCountMap.value_attention'/>" dataType="Range" min="1000" max="99999999" msg="频道所占固定分值必须是整数并且大于1000"/>
								</td>
							</tr>
							
							<tr>
								<td>
									每次频道访问分值：
								</td>
								<td>
                                    <input id="value_tag_hit" type="text" name="relationCountMap.value_tag_hit" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.value_tag_hit'/>" dataType="Double" msg="每次频道访问分值必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									Tag我要更多分值：
								</td>
								<td>
                                    <input id="value_tag_hit" type="text" name="relationCountMap.value_tag_more" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.value_tag_more'/>" dataType="Double" msg="Tag我要更多分值必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章每秒停留对应标签分值：
								</td>
								<td>
                                    <input id="value_tag_time" type="text" name="relationCountMap.value_tag_time" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.value_tag_time'/>" dataType="Double" msg="文章每秒停留对应标签分值必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									文章每秒停留对应标签最大分值：
								</td>
								<td>
                                    <input id="value_time_max" type="text" name="relationCountMap.value_time_max" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.value_time_max'/>" dataType="Double" msg="文章每秒停留对应标签最大分值必须是实数"/>
								</td>
							</tr>
							<tr>
								<td>
									游戏标签分值：
								</td>
								<td>
                                    <input id="value_tag_game" type="text" name="relationCountMap.value_tag_game" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.value_tag_game'/>" dataType="Double" msg="游戏标签分值必须是实数"/>
								</td>
							</tr>
						</table>
						<div class="buttons" style="margin-top: 10px;">
							<input type="submit" class="button_big" id="submit" name="submit" value="保存"/>
						</div>
											
									

					</td>
					<td id="middleRight">
					</td>
				</tr>
				<tr id="bottomRow">
					<td id="bottomLeft">
					</td>
					<td id="bottomMiddle">
					</td>
					<td id="bottomRight">
					</td>
				</tr>
			</tbody>
		</table>
		</form>	 
		 <SCRIPT LANGUAGE="JavaScript">
		  <!--
		  $(document).ready(function() {
				$('#myForm').ajaxForm({
							beforeSubmit : validateForm,
							clearForm : false,
							dataType : 'json',
							success : processJson,
							error : function(response) {
								if(response.responseText.indexOf('loginwindow')!=-1){
									parent.location.reload();
								}else{
									alert(response.responseText);
									//图层解锁
									$("#myForm").unblock();
								}
							}
						});
			});

		  Validator.createCheckForm(document.forms[0]);
			/**
			回调
			**/
			function processJson(data) {

			if(data.status == SUCCESS){
				//图层解锁
			window.parent.parent.jAlert(data.message, "系统提示");
				$("#myForm").unblock();

	
			}else{
			
				//图层解锁
				$("#myForm").unblock();
			}
			}
			

			
		//-->
		</SCRIPT>
</body>
</html>