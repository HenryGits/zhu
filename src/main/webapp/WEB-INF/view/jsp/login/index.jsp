<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/21
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>小demo</title>
    <link rel="stylesheet" charset="utf-8" href="${pageContext.request.contextPath}/static/bootStrap-3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" charset="utf-8" href="${pageContext.request.contextPath}/static/login/css/login.css"/>
  </head>
  <body>
  <div class="row container-fluid">
      <div class="col-sm-6 form_control">
          <h1>用户登录</h1>
          <div class="row">
              <div class="col-xs-8 col-sm-6 ">
                  <form action="doLogin" method="post" id="loginFrom">
                      <input type="text" name="nickname" id="name"  class="form-control" placeholder="请输入用户名" aria-describedby="basic-addon1">
                      <div class="input-group">
                          <input type="email" name="email" class="form-control" placeholder="请输入邮箱" aria-describedby="basic-addon2">
                          <span class="input-group-addon" id="basic-addon1"><s:select path="emailEna" items="${emailEna}" itemValue="name"/></span>
                      </div>
                      <input type="password" name="pswd" id="passWord"  class="form-control" placeholder="请输入密码" aria-describedby="basic-addon3">
                      <button type="button" class="btn btn-success" id="dis" onclick="loginUser();">登录</button>&nbsp;&nbsp;&nbsp;&nbsp;
                      <button type="button" class="btn btn-info" onclick="reg();">注册</button>
                  </form>
              </div>
          </div>
      </div>
  </div>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootStrap-3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/ajax/ajax.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/login.js"></script>
  </body>
</html>
