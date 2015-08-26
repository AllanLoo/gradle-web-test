<%--
  Created by IntelliJ IDEA.
  User: AllanLoo
  Date: 2015/8/26
  Time: 15:41
  默认模版页.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:title/></title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>
    <sitemesh:head/>
</head>
<body <sitemesh:getProperty property="body.style" writeEntireProperty="1"/>>
   <sitemesh:body/>
</body>
</html>
