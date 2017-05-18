<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${actionName }</title>
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
        <div class="col-md-10">
            <form role="form" class="form-horizontal" action="${ctx }/${action }" method="post">
                <input name="id" value="${user.id }" type="hidden"/>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">姓名：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="name" placeholder="请输入姓名..." value="${user.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="age" class="col-sm-2 control-label">年龄：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="age" placeholder="请输入年龄..." value="${user.age }">
                    </div>
                </div>
                <div class="form-group">
                    <label for="address" class="col-sm-2 control-label">地址：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="address" placeholder="请输入地址..."
                               value=${user.address }>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input class="btn btn-primary" type="submit" value="保存">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${ctx }/static/jquery/jquery-1.11.1.min.js"></script>
<script src="${ctx }/static/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>