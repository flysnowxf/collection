<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<%@ include file="/common/header.jsp" %>
</head>
<body>
第三版<br/>
<c:forEach var="pmg" items="${pmgList3 }">
${pmg.name }
<c:forEach var="gradeCount" items="${pmg.gradeCountList }" varStatus="status">
<c:if test="${status.index > 33 }">
${gradeCount.grade }:${gradeCount.count }
</c:if>
</c:forEach>
<br/>
</c:forEach>
<br/>

第二版<br/>
<c:forEach var="pmg" items="${pmgList2 }">
${pmg.name }
<c:forEach var="gradeCount" items="${pmg.gradeCountList }" varStatus="status">
<c:if test="${status.index > 33 }">
${gradeCount.grade }:${gradeCount.count }
</c:if>
</c:forEach>
<br/>
</c:forEach>
<br/>

纪念钞<br/>
<c:forEach var="pmg" items="${pmgListJn }">
${pmg.name }
<c:forEach var="gradeCount" items="${pmg.gradeCountList }" varStatus="status">
<c:if test="${status.index > 33 }">
${gradeCount.grade }:${gradeCount.count }
</c:if>
</c:forEach>
<br/>
</c:forEach>
<br/>

</body>
</html>
