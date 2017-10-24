<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>子账户列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="Content-Style-Type" content="text/css"/>
		<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon"/>
		<link href="/css/store.css" rel="stylesheet" type="text/css"/>
		<link href="/css/layout.css" rel="stylesheet" type="text/css"/>
		<link href="/css/jquery.validation.css" rel="stylesheet" type="text/css"/>
		<link href="/css/ie_style.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="/css/bootstrap.css" media="screen" type="text/css"/>
		<link rel="stylesheet" href="/css/normalize.css" type="text/css"/>

		<script type="text/javascript" src="/js/jquery-2.1.0.min.js"></script>
		<script type="text/javascript" src="/js/bootstrap.js"></script>

		<script type="text/javascript">
			$(function () {
				$('#header').load('/load/header.html');
				$('#header2').load('/load/header2.html');
				$('#foot').load('/load/foot.html');
			});
		</script>

	</head>

	<body>
		<div id="main">
			<!-- header -->
			<div id="header"></div>
			<div id="header2"></div>

			<table class="cart_product">
				<tr class="bg">
					<th class="name">用户名</th>
					<th class="edit" style="text-align: center">授权管理</th>
				</tr>

				<c:forEach items="${accountList}" var="account">
					<tr>
						<td class="name"><c:out value="${account}"/></td>
						<td class="edit" style="padding: 0 8px;">
							<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#updateVip" onclick="getId(${member.id});">更改会员级别</button>
						</td>
					</tr>
				</c:forEach>

				<div class="modal fade" id="updateVip" tabindex="-1" role="dialog" aria-labelledby="updateAvatarLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="color:#34495e">&times;</button>
								<h4 class="modal-title">更改会员等级</h4>
							</div>
							<form id="" action="/vip/member/updateleverl.action" method="post">
							<div class="modal-body">
								
									<div class="field">
										<div class="ui left labeled input">
											<select name="level" id="category">
											</select>
											<input type="hidden" id="memberId" name="memberid" value="">
										</div>
									</div>
								
							</div>
							<div class="modal-footer" style="text-align:center">
								<%-- <button type="button" class="btn btn-embossed btn-primary btn-modal" onclick="updateVip()">确定</button> --%>
								<button type="submit" class="btn btn-embossed btn-primary btn-modal">确定</button>
								<button type="button" class="btn btn-embossed btn-default btn-modal" data-dismiss="modal">取消</button>
							</div>
							</form>
						</div>
					</div>
				</div>
			</table>

			<div id="foot"></div>
		</div>
		<script type="text/javascript" src="http://kefu.puckart.com/mibew/js/compiled/chat_popup.js"></script>
		<script type="text/javascript" src="/js/kf.js"></script>
		<script type="text/javascript">
			$(function () {
				$.post("http://magic.puckart.com/vip/card/queryaj.action", function (data) {
					// console.log(data)
					var str = "";
					for (var i = 0; i < data.length; i++) {
						str += '<option value="'+data[i].id+'">'+data[i].name+'</option>'
					}
					$('#category').html(str);
				}, "json");
			});
			function getId(id) {
				$('#memberId').val(id);
			}
		</script>
	</body>
</html>
