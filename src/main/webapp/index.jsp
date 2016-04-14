<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/common/header.jsp" %>
</head>
<body>
<c:forEach var="pmg" items="${pmgList }">
${pmg.name }<br/>
</c:forEach>
</body>
</html>
