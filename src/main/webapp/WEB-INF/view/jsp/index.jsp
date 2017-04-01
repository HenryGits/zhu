<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/21
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>小demo</title>
    <link rel="stylesheet" charset="utf-8" href="${pageContext.request.contextPath}/static/bootStrap-3.7/css/bootstrap.min.css"/>
  </head>
  <body>
  <div class="container">
    <h1>hello world!</h1>
    <form action="doLogin" method="post">
      <input name="nickname" value="测试mq队列" class="form-control">
      <input type="submit" value="发送队列" class="btn btn-success">
    </form>
  </div>
  <div class="table-responsive">
      <table class="table">
          <!-- On cells (`td` or `th`) -->
          <tr>
              <td class="active">...</td>
              <td class="success">...</td>
              <td class="warning">...</td>
              <td class="danger">...</td>
              <td class="info">...</td>
          </tr>
      </table>
  </div>

  <script rel="script" type="text/javascript" src="${pageContext.request.contextPath}/static/bootStrap-3.7/js/bootstrap.min.js"/>
  </body>
</html>
