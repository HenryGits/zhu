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
    <link rel="stylesheet" charset="utf-8" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
      <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
      <%--<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
            integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
  </head>
  <body>
  <h2>${message}</h2>
  <h3>${user.nickname}</h3>
  <div class="container">
    <h1>hello world!</h1>
    <form action="queueSender">
      <input name="message" value="测试mq队列" class="form-control">
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

  <script rel="script" type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"/>
  </body>
</html>
