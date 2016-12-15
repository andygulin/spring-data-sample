<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>用户列表</title>
<link href="${ctx }/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	margin-top: 42px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-4">
				<input class="btn btn-primary btn-sm" type="button" value="列表" id="listBtn">
				<input class="btn btn-primary btn-sm" type="button" value="添加" id="addBtn">
			</div>
			<div class="col-md-8" align="right">
				<form class="form-inline" role="form" action="" method="get">
					<div class="form-group m-btn-group-fix">
						<p class="input-group input-group-sm">
							<input type="text" class="form-control input-sm" name="name" placeholder="输入姓名..." value="${param.name }">
						</p>
					</div>
					<div class="form-group m-btn-group-fix">
						<p class="input-group input-group-sm">
							<span class="input-group-btn"> 
								<input class="btn btn-default" type="submit" value="搜索">
							</span>
						</p>
					</div>
				</form>
			</div>

		</div>
		<div class="row" style="margin-top: 16px;">
			<div class="col-md-12">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>编号</th>
							<th>姓名</th>
							<th>年龄</th>
							<th>地址</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${users }">
							<tr id="tr_${user.id }">
								<td>${user.id }</td>
								<td>${user.name }</td>
								<td>${user.age }</td>
								<td>${user.address }</td>
								<td><fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<a href="${ctx }/update/${user.id }">更新</a>&nbsp;
									<a href="javascript:;" onclick="del('${user.id}');">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>


	<script src="${ctx }/static/jquery/jquery-1.11.1.min.js"></script>
	<script src="${ctx }/static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
$(function () {
	$("#listBtn").click(function(){
		window.location.href = "${ctx}";
	});
	$("#addBtn").click(function(){
		window.location.href = "${ctx}/insert";
	});
});

function del(id){
	if(confirm("确认?")){
		$.get("${ctx}/delete/"+id,{d:new Date().getTime()},function(data){$("#tr_"+id).remove();});
	}
}
</script>
</body>
</html>